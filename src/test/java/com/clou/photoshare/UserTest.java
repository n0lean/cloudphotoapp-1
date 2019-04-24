package com.clou.photoshare;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.clou.photoshare.controller.UserController;
import com.clou.photoshare.errorHandler.InvalidArgumentException;
import com.clou.photoshare.model.*;
import com.clou.photoshare.repository.UserRepository;
import org.junit.*;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
//import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PhotoshareApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "DYNAMODB_ENDPOINT=http://localhost:8000/",
        "DYNAMODB_REGION=us-east-1",
        "AWS_ACCESS_KEY=test1",
        "AWS_ACCESS_KEY_ID=test231" })
public class UserTest {

    @LocalServerPort
    private int port;

    @ClassRule
    public static LocalTestRule dynamoDBRule = new LocalTestRule();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    UserRepository repo;

    private DynamoDB dynamoDB;
    private String tableName = "User";
    private Class tableClass = User.class;
    private static Validator validator;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setUp() throws Exception {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        DBTestUtil.createExampleTable(amazonDynamoDB,  dynamoDB, tableName, tableClass);
    }

    @Test(expected = InvalidArgumentException.class)
    public void idIsNull() {
        S3Address address = new S3Address("Bucketname", "Key");
        User user = new UserBuilder()
                .id(null)
                .email("123@ab.com")
                .profilePhotoAddress(address)
                .nickName("nickname")
                .buildUser();
    }

    @Test
    public void isValidEmail() {
        S3Address address = new S3Address("Bucketname", "Key");
        User user = new UserBuilder()
                .id("123")
                .email("12om")
                .profilePhotoAddress(address)
                .nickName("nickname")
                .buildUser();

        Set<ConstraintViolation<User>> constraintViolations = validator.validateProperty(user, "email");
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void saveUserTest() throws URISyntaxException {
        TestRestTemplate restTemplate = new TestRestTemplate();

        UUID uuid = java.util.UUID.randomUUID();
        String uuid_str = uuid.toString();
        final String baseurl = createURLWithPort("/users/save");

        URI uri = new URI(baseurl);

        S3Address address = new S3Address("Bucketname", "Key");
        User testUser = new UserBuilder()
                .email("test@me.com")
                .id(uuid_str)
                .lastName("test")
                .firstName("before")
                .nickName("nicname")
                .profilePhotoAddress(address)
                .buildUser();

        ResponseEntity<User> res = restTemplate.postForEntity(uri, testUser, User.class);
        assertEquals(201, res.getStatusCodeValue());

        User result = repo.findById(uuid_str).get();
        assertEquals(testUser, result);
    }

    @Test
    public void userTableTest() {

        // if this test fails, run "mvn clean install" first the try again
        System.setProperty("sqlite4java.library.path", "native-libs");
        AmazonDynamoDB ddb = DynamoDBEmbedded.create().amazonDynamoDB();
        try {
            String tableName = "User";
            String hashKeyName = "Id";
            CreateTableResult res = createTable(ddb, tableName, hashKeyName);

            TableDescription tableDesc = res.getTableDescription();
            assertEquals("ACTIVE", tableDesc.getTableStatus());
        } finally {
            ddb.shutdown();
        }
    }

    @Test
    public void testUpdateUser() throws URISyntaxException {
        TestRestTemplate restTemplate = new TestRestTemplate();

        UUID uuid = java.util.UUID.randomUUID();
        String uuid_str = uuid.toString();
        final String baseurl = createURLWithPort("/users/" + uuid_str);

        URI uri = new URI(baseurl);

        S3Address address = new S3Address("Bucketname", "Key");
        User testUser = new UserBuilder()
                .email("test@me.com")
                .id(uuid_str)
                .lastName("test")
                .firstName("before")
                .nickName("nicname")
                .profilePhotoAddress(address)
                .buildUser();

        repo.save(testUser);

        // assert user has been saved
        ResponseEntity<User> res = restTemplate.getForEntity(uri, User.class);
        assertEquals(200, res.getStatusCodeValue());
        assertEquals("before", res.getBody().getFirstName());

        testUser.setFirstName("after");
        ResponseEntity<User> res2 = restTemplate.postForEntity(uri, testUser, User.class);
        User newUser = repo.findById(testUser.getId()).get();
        assertEquals(201, res2.getStatusCodeValue());
        assertEquals("after", newUser.getFirstName());
    }

    @Test
    public void testSearchUserByEmail() throws URISyntaxException {
        TestRestTemplate restTemplate = new TestRestTemplate();

        UUID uuid = java.util.UUID.randomUUID();
        String uuid_str = uuid.toString();
        final String baseurl = createURLWithPort("/users/search?email=test@me.com");

        URI uri = new URI(baseurl);

        S3Address address = new S3Address("Bucketname", "Key");
        User testUser = new UserBuilder()
                .email("test@me.com")
                .id(uuid_str)
                .lastName("test")
                .firstName("before")
                .nickName("nicname")
                .profilePhotoAddress(address)
                .buildUser();

        repo.save(testUser);

        ResponseEntity<User> res = restTemplate.getForEntity(uri, User.class);
        assertEquals(200, res.getStatusCodeValue());
    }

//    @Test
//    public void testFriendRequest() throws URISyntaxException {
//        TestRestTemplate restTemplate = new TestRestTemplate();
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.add("HeaderName", "value");
//        requestHeaders.add("Content-Type", "application/json");
//
//
//        final String baseurl = createURLWithPort("/users/addfriend");
//        URI uri = new URI(baseurl);
//
//        FriendRequest testFriendRequest = new FriendRequestBuilder()
//                                                .fromUserId("1")
//                                                .toUserId("2")
//                                                .buildFriendRequest();
//        System.out.println(testFriendRequest.toString());
//
//        HttpEntity<FriendRequest> httpEntity = new HttpEntity<>(requestHeaders);
//
//        S3Address address = new S3Address("Bucketname", "Key");
//        User testUser1 = new UserBuilder()
//                .email("test@me.com")
//                .id("1")
//                .lastName("test")
//                .firstName("before")
//                .nickName("nicname")
//                .profilePhotoAddress(address)
//                .buildUser();
//
//        User testUser2 = new UserBuilder()
//                .email("test@me2.com")
//                .id("2")
//                .lastName("test2")
//                .firstName("before2")
//                .nickName("nicname2")
//                .profilePhotoAddress(address)
//                .buildUser();
//
//        repo.save(testUser1);
//        repo.save(testUser2);
//
//        ResponseEntity<FriendRequest> res = restTemplate.postForEntity(uri, testFriendRequest, FriendRequest.class);
//        assertEquals(201, res.getStatusCodeValue());
//
////        // Friend Request accept
////        testFriendRequest.setStatus("accepted");
////        ResponseEntity<FriendRequest> res2 = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, FriendRequest.class);
////        assertEquals(200, res2.getStatusCodeValue());
////
////        User userFrom = repo.findById("1").get();
////        User userTo = repo.findById("2").get();
////
////        assertThat(userFrom.getFriends(), hasItems("2"));
////        assertThat(userTo.getFriends(), hasItems("1"));
//
//
//    }

    private static CreateTableResult createTable(AmazonDynamoDB ddb, String tableName, String hashKeyName) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition(hashKeyName, ScalarAttributeType.S));

        List<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
        ks.add(new KeySchemaElement(hashKeyName, KeyType.HASH));

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(1000L, 1000L);

        CreateTableRequest request =
                new CreateTableRequest()
                        .withTableName(tableName)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withKeySchema(ks)
                        .withProvisionedThroughput(provisionedthroughput);

        return ddb.createTable(request);
    }

    @Test
    public void createTableTest() {
        System.setProperty("sqlite4java.library.path", "native-libs");
        AmazonDynamoDB amazonDynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
        DynamoDBMapper dynamoDBMapper;

        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(User.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);
    }

    @After
    public void tearDown() {
        DBTestUtil.deleteExampleTable(dynamoDB, tableName);
    }


}
