package com.clou.photoshare;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.clou.photoshare.model.User;
import com.clou.photoshare.model.UserBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
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
    public void createTableTest() {


    }
}
