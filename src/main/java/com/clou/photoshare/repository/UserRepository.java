package com.clou.photoshare.repository;


import java.util.List;

import com.clou.photoshare.model.User;
        import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;

@EnableScan
//@Repository
public interface UserRepository extends CrudRepository<User, String> {
    List<User> findByLastName(String lastName);
    List<User> findByFirstName(String firstName);
}
