package com.clou.photoshare.repository;


import com.clou.photoshare.model.PhotoSearch;
import com.clou.photoshare.model.PhotoSearchId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@EnableScan
public interface PhotoSearchRepository extends CrudRepository<PhotoSearch, String> {

    PhotoSearch findByUserIdAndTripId(@Param("UserId") String userId, @Param("TripId") String tripId);
    PhotoSearch findByPhotoSearchId(@Param("threadId") PhotoSearchId Id);
    List<PhotoSearch> findByTripId(String tripId);

}