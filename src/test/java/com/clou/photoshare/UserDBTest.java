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
import com.clou.photoshare.model.Trip;
import com.clou.photoshare.model.User;
import com.clou.photoshare.model.UserBuilder;
import com.clou.photoshare.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

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

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PhotoshareApplication.class)
@WebAppConfiguration
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.dynamodb.region=us-east-1",
        "AWS_ACCESS_KEY=test1",
        "AWS_ACCESS_KEY_ID=test231" })
public class UserDBTest {
    @ClassRule
    public static LocalTestRule dynamoDBRule = new LocalTestRule();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    UserRepository repo;

    private DynamoDB dynamoDB;
    private String tableName = "User";
    private Class tableClass = User.class;

    @Before
    public void setUp() throws Exception {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        DBTestUtil.createExampleTable(amazonDynamoDB, dynamoDB, tableName, tableClass);
    }

    @Test
    public void findAllTest() {
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

        assertEquals("Only one user", 1, result.size());
        assertEquals(uuid_str, result.get(0).getId());
        assertEquals(1, result.size());

        repo.delete(testUser);
        assertFalse(repo.existsById(uuid_str));
    }

    @Test
    public void findByIdTest() {

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

        User result = repo.findById(uuid_str).get();
        assertEquals(testUser, result);

        repo.delete(testUser);
        assertFalse(repo.existsById(uuid_str));
    }

//    @Test
//    public void findAllTest() {
//        UUID uuid = UUID.randomUUID();
//        String uuid_str = uuid.toString();
//        User testUser = new UserBuilder()
//                .email("test@me.com")
//                .id(uuid_str)
//                .nickName("test")
//                .firstName("c")
//                .lastName("a")
//                .buildUser();
//        repo.save(testUser);
//
//        List<User> result = (List<User>) repo.findAll();
//
//        assertEquals("Only one user", 1, result.size());
//        assertEquals(uuid_str, result.get(0).getId());
//        assertEquals(1, result.size());
//
//        repo.delete(testUser);
//        assertFalse(repo.existsById(uuid_str));
//    }


    @After
    public void tearDown() {
        DBTestUtil.deleteExampleTable(dynamoDB, tableName);
    }
}



