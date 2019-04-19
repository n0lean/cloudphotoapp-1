package com.clou.photoshare;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.clou.photoshare.model.*;
import com.clou.photoshare.repository.PhotosRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PhotoshareApplication.class)
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.dynamodb.region=us-east-1",
        "AWS_ACCESS_KEY=test1",
        "AWS_ACCESS_KEY_ID=test231" })
public class PhotoDBTest {
    @ClassRule
    public static LocalTestRule dynamoDBRule = new LocalTestRule();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    PhotosRepository repo;

    private DynamoDB dynamoDB;
    private String tableName = "Photo";
    private Class tableClass = Photo.class;
    private List<String> uuids = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        DBTestUtil.createExampleTable(amazonDynamoDB, dynamoDB, tableName, tableClass);
    }

    @Test
    public void PhotoTestCase() {
        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        uuids.add(uuid_str);
        Photo testPhoto = new PhotoBuilder()
                .photoId(uuid_str)
                .photoAddress("01010101")
                .ownerId("huxin")
                .addOneTripId("MYC")
                .addOneTripId("LA")
                .addOneViewerId("anda")
                .addOneViewerId("zhengzhi")
                .addOneViewerId("pengyu")
                .buildPhoto();

        repo.save(testPhoto);

        Photo result = repo.findById(uuid_str).get();
        assertEquals(testPhoto.getId(), result.getId());
        assertEquals(testPhoto.getAddress(), result.getAddress());
        assertEquals(testPhoto.getOwnerId(), result.getOwnerId());
        assertEquals(testPhoto.getTripsId(), result.getTripsId());
        assertEquals(testPhoto.getViewersId(),result.getViewersId());
        assertEquals(testPhoto.toString(),result.toString());
        for (String s : result.getViewersId()) {
            assertTrue(testPhoto.getViewersId().contains(s));
        }

        for (String s : result.getTripsId()) {
            assertTrue(testPhoto.getTripsId().contains(s));
        }

    }

    @After
    public void tearDown() {
        DBTestUtil.deleteExampleTable(dynamoDB, tableName);
    }
}
