package com.clou.photoshare.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import com.clou.photoshare.model.Photo;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface PhotoRepository extends CrudRepository<Photo, String> {
}
