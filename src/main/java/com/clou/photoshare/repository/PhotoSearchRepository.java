package com.clou.photoshare.repository;


import com.clou.photoshare.model.PhotoSearch;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@EnableScan
public interface PhotoSearchRepository extends CrudRepository<PhotoSearch, String> {
    Set<String> findByUserIdAndTripId(String userId, String tripId);
}
