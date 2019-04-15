package com.clou.photoshare;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.clou.photoshare.model.Trip;
import com.clou.photoshare.model.TripBuilder;
import com.clou.photoshare.model.User;
import com.clou.photoshare.model.UserBuilder;
import com.clou.photoshare.repository.TripRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PhotoshareApplication.class)
@WebAppConfiguration
@TestPropertySource(properties = {
        "DYNAMODB_ENDPOINT=http://localhost:8000/",
        "DYNAMODB_REGION=us-east-1",
        "AWS_ACCESS_KEY=test1",
        "AWS_ACCESS_KEY_ID=test231" })
public class TripDBTest {
    @ClassRule
    public static LocalTestRule dynamoDBRule = new LocalTestRule();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TripRepository repo;

    private DynamoDB dynamoDB;
    private String tableName = "Trip";
    private Class tableClass = Trip.class;

    @Before
    public void setUp() throws Exception {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        DBTestUtil.createExampleTable(amazonDynamoDB, dynamoDB, tableName, tableClass);
    }

    @Test
    public void tripTestCase() {
        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        User testUser = new UserBuilder()
                .email("test@me.com")
                .id(uuid_str)
                .nickName("test")
                .firstName("c")
                .lastName("a")
                .buildUser();

        uuid = UUID.randomUUID();
        uuid_str = uuid.toString();
        Trip trip = new TripBuilder()
                .id(uuid_str)
                .addMember(testUser.getId())
                .tripName("Test")
                .buildTrip();

        repo.save(trip);

        Trip result = repo.findById(uuid_str).get();
        assertEquals(trip, result);
        for (String s : result.getTripMember()) {
            assertTrue(trip.getTripMember().contains(s));
        }
    }

    @After
    public void tearDown() {
        DBTestUtil.deleteExampleTable(dynamoDB, tableName);
    }

}
