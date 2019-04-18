package com.clou.photoshare;

import com.clou.photoshare.model.S3Storage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.adobe.testing.s3mock.util.HashUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.ClassRule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PhotoshareApplication.class)
public class S3StorageTest {

    @Autowired
    private S3Storage s3;




    @Test
    public void testS3UploadFile() throws Exception{

        String bucketName = "huxinxx";
        String objectKey = "test";
        String content = "upload a file";
        s3.createBucket(bucketName);
        //s3.uploadObject(objectKey,content);
        //String res = s3.downloadObject(bucketName,objectKey);
        //assertEquals(res,content);
//        final File uploadFile = new File(UPLOAD_FILE_NAME);
//
//        s3Client.createBucket(BUCKET_NAME);
//        s3Client.putObject(new PutObjectRequest(BUCKET_NAME, uploadFile.getName(), uploadFile));
//
//        final S3Object s3Object = s3Client.getObject(BUCKET_NAME, uploadFile.getName());
//
//        final InputStream uploadFileIs = new FileInputStream(uploadFile);
//        final String uploadHash = HashUtil.getDigest(uploadFileIs);
//        final String downloadedHash = HashUtil.getDigest(s3Object.getObjectContent());
//        uploadFileIs.close();
//        s3Object.close();
//
//        assertThat("Up- and downloaded Files should have equal Hashes", uploadHash,
//                is(equalTo(downloadedHash)));

    }

}
