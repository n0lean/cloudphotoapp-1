package com.clou.photoshare.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import com.clou.photoshare.model.Photo;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface PhotosRepository extends CrudRepository<Photo, String> {
//    @EnableScan
//    Photo findByAddress(String address);
//    @EnableScan
//    List<Photo> findByOwnerId(String ownerId);
      List<Photo> findByTripId(String TripId);
}
