package com.clou.photoshare.repository;


import java.util.List;

import com.clou.photoshare.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    @EnableScan
    List<User> findByLastName(String lastName);
    @EnableScan
    List<User> findByFirstName(String firstName);
}
