package com.clou.photoshare;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.clou.photoshare.model.User;
import org.springframework.beans.factory.annotation.Autowired;


public class DBTestUtil {
    public static void createExampleTable(AmazonDynamoDB amazonDynamoDB, DynamoDB dynamoDB, String tableName, Class tableClass) {
        try {
            DynamoDBMapperConfig dynamoDBMapperConfig = DynamoDBMapperConfig.DEFAULT;
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);

            CreateTableRequest request = dynamoDBMapper
                    .generateCreateTableRequest(tableClass);
            request.setProvisionedThroughput(
                    new ProvisionedThroughput(1L, 1L));

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

    public static void deleteExampleTable(DynamoDB dynamoDB, String tableName) {
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


}
