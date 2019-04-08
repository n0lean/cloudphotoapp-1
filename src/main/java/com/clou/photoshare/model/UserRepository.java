package com.clou.photoshare.model;


import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


@EnableScan
public interface UserRepository extends CrudRepository<User, String>{
    List<User> findByLastName(String lastName);
    List<User> findById(String id);
    List<User> findByFirstName(String firstName);
}
