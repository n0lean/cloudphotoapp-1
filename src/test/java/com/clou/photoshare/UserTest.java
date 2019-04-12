package com.clou.photoshare;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.clou.photoshare.controller.UserController;
import com.clou.photoshare.model.User;
import com.clou.photoshare.model.UserBuilder;
import com.clou.photoshare.repository.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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


public class UserTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void idIsNull() {
        User user = new UserBuilder()
                .id(null)
                .email("123@ab.com")
                .buildUser();

        Set<ConstraintViolation<User>> constraintViolations = validator.validateProperty(user, "id");
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void isValidEmail() {
        User user = new UserBuilder()
                .id("123")
                .email("12om")
                .buildUser();

        Set<ConstraintViolation<User>> constraintViolations = validator.validateProperty(user, "email");
        assertEquals(1, constraintViolations.size());
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


}
