package com.clou.photoshare;

import org.junit.runner.RunWith;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.clou.photoshare.model.*;
import com.clou.photoshare.repository.PhotosRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PhotoshareApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "DYNAMODB_ENDPOINT=http://localhost:8000/",
        "DYNAMODB_REGION=us-east-1",
        "AWS_ACCESS_KEY=test1",
        "AWS_ACCESS_KEY_ID=test231" })
public class PhotoTest {
    @LocalServerPort
    private int port;

    @ClassRule
    public static LocalTestRule dynamoDBRule = new LocalTestRule();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;


    @Autowired
    PhotosRepository repo;

    private DynamoDB dynamoDB;
    private String tableName = "Photo";
    private Class tableClass = Photo.class;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Before
    public void setUp() throws Exception {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        DBTestUtil.createExampleTable(amazonDynamoDB, dynamoDB, tableName, tableClass);
    }

//    @Test
//    public void testGetPhotobyId() throws URISyntaxException {
//        TestRestTemplate restTemplate = new TestRestTemplate();
//
//        UUID uuid = UUID.randomUUID();
//        String uuid_str = uuid.toString();
//        String huxin = "huxin";
//        final String baseurl = createURLWithPort("/photos/findOne/"+huxin+"/"+uuid_str);
//
//        URI uri = new URI(baseurl);
//
//        Photo testPhoto = new PhotoBuilder()
//                .photoId(uuid_str)
//                .photoKey("01010101")
//                .ownerId(huxin)
//                .tripId("MYC")
//                .buildPhoto();
//
//        repo.save(testPhoto);
//        ResponseEntity<Photo> result = restTemplate.getForEntity(uri, Photo.class);
//        assertEquals(200, result.getStatusCodeValue());
//        assertEquals(testPhoto.getOwnerId(), result.getBody().getOwnerId());
//
//    }

    @Test
    public void testAddPhoto() throws URISyntaxException {
        TestRestTemplate restTemplate = new TestRestTemplate();

        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        final String baseurl = createURLWithPort("/photos/new");

        URI uri = new URI(baseurl);

        Photo testPhoto = new PhotoBuilder()
                .photoId(uuid_str)
                .photoKey("03030303")
                .ownerId("oliver")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        ResponseEntity<Photo> result = restTemplate.postForEntity(uri, testPhoto, Photo.class);
        Photo getPhoto = repo.findById(uuid_str).get();
        assertEquals(201, result.getStatusCodeValue());
        assertEquals(testPhoto.getOwnerId(), result.getBody().getOwnerId());
        assertEquals(getPhoto.getPhotoKey(),result.getBody().getPhotoKey());
        assertEquals(getPhoto.getBucketName(),result.getBody().getBucketName());

    }


    @Test
    public void testDeletePhoto() throws URISyntaxException {
        TestRestTemplate restTemplate = new TestRestTemplate();

        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();

        final String baseurl1 = createURLWithPort("/photos/delete" + uuid_str);

        Photo testPhoto = new PhotoBuilder()
                .photoId(uuid_str)
                .photoKey("01010101")
                .ownerId("huxin")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        repo.save(testPhoto);

        URI uri1 = new URI(baseurl1);

        assertTrue(repo.findById(uuid_str).isPresent());

        restTemplate.delete(uri1);

        assertFalse(repo.findById(uuid_str).isPresent());

    }

    @After
    public void tearDown() {
        DBTestUtil.deleteExampleTable(dynamoDB, tableName);
    }

}
