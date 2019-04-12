package com.clou.photoshare;

import java.util.ArrayList;
import java.util.UUID;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.clou.photoshare.model.User;
import com.clou.photoshare.model.UserBuilder;
import com.clou.photoshare.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.Iterator;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PhotoshareApplication.class)
@WebAppConfiguration
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231" })
public class DynamoDBTest {
    @ClassRule
    public static LocalTestRule dynamoDBRule = new LocalTestRule();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    UserRepository repo;

    private DynamoDBMapper dynamoDBMapper;

    private DynamoDB dynamoDB;
    private String tableName = "User";

    @Before
    public void setUp() throws Exception {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        createExampleTable();
    }

    private void createExampleTable() {

        try {
            DynamoDBMapperConfig dynamoDBMapperConfig = DynamoDBMapperConfig.DEFAULT;
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);

            CreateTableRequest request = dynamoDBMapper
                    .generateCreateTableRequest(User.class);
            request.setProvisionedThroughput(
                    new ProvisionedThroughput(1L, 1L));

//            List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
//            attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("N"));
//
//            List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
//            keySchema.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH));
//
//            CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
//                    .withAttributeDefinitions(attributeDefinitions).withProvisionedThroughput(
//                            new ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(6L));

            System.out.println("Issuing CreateTable request for " + tableName);
            Table table = dynamoDB.createTable(request);

            System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
            table.waitForActive();
        }
        catch (Exception e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }

    }


    private void deleteExampleTable() {
        Table table = dynamoDB.getTable(tableName);
        try {
            System.out.println("Issuing DeleteTable request for " + tableName);
            table.delete();

            System.out.println("Waiting for " + tableName + " to be deleted...this may take a while...");

            table.waitForDelete();
        }
        catch (Exception e) {
            System.err.println("DeleteTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void userTestCase() {
        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        User testUser = new UserBuilder()
                .email("test@me.com")
                .id(uuid_str)
                .nickName("test")
                .firstName("c")
                .lastName("a")
                .buildUser();
        repo.save(testUser);

        List<User> result = (List<User>) repo.findAll();

        assertTrue("Only one user", result.size() == 1);
        assertEquals(uuid_str, result.get(0).getId());
        assertEquals(1, result.size());

    }

    @After
    public void tearDown() {
        deleteExampleTable();
    }
}



