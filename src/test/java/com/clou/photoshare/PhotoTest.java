package com.clou.photoshare;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.clou.photoshare.model.*;
import com.clou.photoshare.repository.PhotoSearchRepository;
import com.clou.photoshare.repository.PhotosRepository;
import com.clou.photoshare.services.PhotoService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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

    @Autowired
    PhotoSearchRepository repoSearch;

    @Autowired
    PhotoService ps;

    private DynamoDB dynamoDB;
    private String tableName = "Photo";
    private String tableName1 = "PhotoSearch";
    private Class tableClass = Photo.class;
    private Class tableClass1 = PhotoSearch.class;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Before
    public void setUp() throws Exception {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        DBTestUtil.createExampleTableWithSecondaryIndex(amazonDynamoDB, dynamoDB, tableName, tableClass);
        DBTestUtil.createExampleTable(amazonDynamoDB, dynamoDB, tableName1, tableClass1);
    }

    @Test
    public void testGetPhotobyId() throws URISyntaxException {
        TestRestTemplate restTemplate = new TestRestTemplate();

        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        String huxin = "huxin";
        final String baseurl = createURLWithPort("/photos/findOne?userId=huxin&photoId="+uuid_str);

        URI uri = new URI(baseurl);

        Photo testPhoto = new PhotoBuilder()
                .photoId(uuid_str)
                .photoKey("01010101")
                .ownerId(huxin)
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        repo.save(testPhoto);
        ResponseEntity<Photo> result = restTemplate.getForEntity(uri, Photo.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(testPhoto.getOwnerId(), result.getBody().getOwnerId());
    }

    @Test
    public void testPhotoService() {


        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        UUID uuid1 = UUID.randomUUID();
        String uuid_str1 = uuid1.toString();

        Photo testPhoto = new PhotoBuilder()
                .photoId(uuid_str)
                .photoKey("03030303")
                .ownerId("huxin")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        Photo testPhoto2 = new PhotoBuilder()
                .photoId(uuid_str1)
                .photoKey("03030304")
                .ownerId("huxin")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        PhotoSearch testSearch = new PhotoSearchBuilder()
                .set_tripId("MYC")
                .set_userId("huxin")
                .add_photosId(uuid_str)
                .add_photosId(uuid_str1)
                .builder();

        S3Address testS31 = new S3Address();
        testS31.setAddressBucket("hay");
        testS31.setAddressKey("03030304");

        S3Address testS32 = new S3Address();
        testS32.setAddressBucket("hay");
        testS32.setAddressKey("03030303");

        Set<S3Address> compareS3 = new HashSet<>();
        compareS3.add(testS31);
        compareS3.add(testS32);
        repo.save(testPhoto);
        repo.save(testPhoto2);
        repoSearch.save(testSearch);
        //find by useId and tripID
        Set<S3Address> res = ps.getAllPhotoByQuery("huxin", "MYC");

    }

    @Test
    public void testAssignViewId() {
        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        UUID uuid1 = UUID.randomUUID();
        String uuid_str1 = uuid1.toString();
        UUID uuid3 = UUID.randomUUID();
        String uuid_str3 = uuid3.toString();

        Photo testPhoto1 = new PhotoBuilder()
                .photoId(uuid_str)
                .photoKey("03030303")
                .ownerId("huxin")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        Photo testPhoto2 = new PhotoBuilder()
                .photoId(uuid_str1)
                .photoKey("03030304")
                .ownerId("huxin")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        Photo testPhoto3 = new PhotoBuilder()
                .photoId(uuid_str3)
                .photoKey("03030303")
                .ownerId("huxin")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        PhotoSearch testSearch = new PhotoSearchBuilder()
                .set_tripId("MYC")
                .set_userId("huxin")
                .add_photosId(uuid_str)
                .add_photosId(uuid_str1)
                .builder();

        repo.save(testPhoto1);
        repo.save(testPhoto2);
        repoSearch.save(testSearch);
        ps.assignViewerOnPhotoById(testPhoto3,"xxx");
        PhotoSearch res = repoSearch.findByUserIdAndTripId("xxx",testPhoto3.getTripId());
        assertTrue(res.getPhotoId().contains(uuid_str3));
        assertEquals(res.getPhotoId().size(),1);
        ps.assignViewerOnPhotoById(testPhoto3,"huxin");
        PhotoSearch res1 = repoSearch.findByUserIdAndTripId("huxin",testPhoto3.getTripId());
        assertTrue(res1.getPhotoId().contains(uuid_str3));
        assertTrue(res1.getPhotoId().contains(uuid_str));
        assertTrue(res1.getPhotoId().contains(uuid_str1));
        assertEquals(res1.getPhotoId().size(),3);


    }

    @Test
    public void testAddAll() throws URISyntaxException{
        TestRestTemplate restTemplate = new TestRestTemplate();

        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        UUID uuid1 = UUID.randomUUID();
        String uuid_str1 = uuid1.toString();
        final String baseurl = createURLWithPort("/photos/newPhotos");

        URI uri = new URI(baseurl);

        PhotoStream ps = new PhotoStream();

        Photo testPhoto = new PhotoBuilder()
                .photoId(uuid_str1)
                .photoKey("03030304")
                .ownerId("oliver")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        Photo testPhoto1 = new PhotoBuilder()
                .photoId(uuid_str)
                .photoKey("03030303")
                .ownerId("oliver")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        List<Photo> ppStream = new ArrayList<>();
        ppStream.add(testPhoto);
        ppStream.add(testPhoto1);

        ps.setPhotos(ppStream);


        ResponseEntity<PhotoStream> result = restTemplate.postForEntity(uri, ps, PhotoStream.class);
        Photo getPhoto = repo.findById(uuid_str1).get();
        Photo getPhoto1 = repo.findById(uuid_str).get();

        assertEquals(201, result.getStatusCodeValue());
        //assertEquals(testPhoto.getOwnerId(), result.getBody().getOwnerId());
        //assertEquals(getPhoto.getPhotoAddress().getAddressKey(),result.getBody().getPhotoAddress().getAddressKey());
        //assertEquals(getPhoto.getPhotoAddress().getAddressBucket(),result.getBody().getPhotoAddress().getAddressBucket());
    }

    @Test
    public void testFindAllPhoto() throws URISyntaxException{
        TestRestTemplate restTemplate = new TestRestTemplate();

        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();
        UUID uuid1 = UUID.randomUUID();
        String uuid_str1 = uuid1.toString();

        final String baseurl = createURLWithPort("/photos/findAll?userId=huxin&tripId=MYC");

        URI uri = new URI(baseurl);

        Photo testPhoto = new PhotoBuilder()
                .photoId(uuid_str)
                .photoKey("03030303")
                .ownerId("huxin")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        Photo testPhoto2 = new PhotoBuilder()
                .photoId(uuid_str1)
                .photoKey("03030304")
                .ownerId("huxin")
                .bucketName("hay")
                .tripId("MYC")
                .buildPhoto();

        PhotoSearch testSearch = new PhotoSearchBuilder()
                .set_tripId("MYC")
                .set_userId("huxin")
                .add_photosId(uuid_str)
                .add_photosId(uuid_str1)
                .builder();

        S3Address testS31 = new S3Address();
        testS31.setAddressBucket("hay");
        testS31.setAddressKey("03030304");

        S3Address testS32 = new S3Address();
        testS32.setAddressBucket("hay");
        testS32.setAddressKey("03030303");

        repo.save(testPhoto);
        repo.save(testPhoto2);
        repoSearch.save(testSearch);
        Iterable<PhotoSearch> res = repoSearch.findAll();


        Photo photo1 = repo.findById(uuid_str).get();
        Photo photo2 = repo.findById(uuid_str1).get();

        PhotoSearch res1 = repoSearch.findByUserIdAndTripId("huxin","MYC");

        assertTrue(res1.getPhotoId().contains(uuid_str));
        assertTrue(res1.getPhotoId().contains(uuid_str1));

        ResponseEntity<Set<S3Address>> result = restTemplate.exchange(baseurl, HttpMethod.GET, null, new ParameterizedTypeReference<Set<S3Address>>() {});
        assertEquals(200, result.getStatusCodeValue());

    }

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
        assertEquals(getPhoto.getPhotoAddress().getAddressKey(),result.getBody().getPhotoAddress().getAddressKey());
        assertEquals(getPhoto.getPhotoAddress().getAddressBucket(),result.getBody().getPhotoAddress().getAddressBucket());
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
        DBTestUtil.deleteExampleTable(dynamoDB,tableName1);
    }

}
