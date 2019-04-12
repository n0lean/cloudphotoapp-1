package com.clou.photoshare;

import com.clou.photoshare.model.Trip;
import com.clou.photoshare.model.TripBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TripTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void idIsNull() {
        Trip trip = new TripBuilder()
                .buildTrip();

        Set<ConstraintViolation<Trip>> constraintViolations = validator.validateProperty(trip, "id");
        assertEquals(1, constraintViolations.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteNotExistMember() {
        Trip trip = new TripBuilder()
                .id("123")
                .addMember("abc")
                .buildTrip();
        trip.deleteTripMember("a");
    }

    @Test
    public void deleteMember() {

    }

}
