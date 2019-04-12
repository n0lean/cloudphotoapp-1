package com.clou.photoshare.repository;

import com.clou.photoshare.model.Trip;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface TripRepository extends CrudRepository<Trip, String> {

}
