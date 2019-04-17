package com.clou.photoshare.controller;

import com.clou.photoshare.model.Trip;
import com.clou.photoshare.model.TripBuilder;
import com.clou.photoshare.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripRepository repository;

    @Autowired
    public TripController(TripRepository repo) { this.repository = repo; }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> newTrip(@RequestBody Trip trip) {
        try {
            UUID uuid = UUID.randomUUID();
            String uuid_str = uuid.toString();

            Trip new_trip = new TripBuilder()
                        .id(uuid_str)
                        .tripName(trip.getTripName())
                        .addMember(trip.getTripMember())
                        .buildTrip();

            repository.save(new_trip);
            return new ResponseEntity<>(new_trip, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTrip(@PathVariable String id) {
        try {
            if (repository.findById(id).isPresent()) {
                Trip trip = repository.findById(id).get();
                return new ResponseEntity<>(trip, HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> updateTrip(@RequestBody Trip trip) {
        try {
            if (repository.findById(trip.getId()).isPresent()) {
                repository.save(trip);
                return new ResponseEntity<>(trip, HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }





}
