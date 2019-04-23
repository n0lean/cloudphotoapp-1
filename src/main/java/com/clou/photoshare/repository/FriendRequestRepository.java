package com.clou.photoshare.repository;

import com.clou.photoshare.model.FriendRequest;
import com.clou.photoshare.model.PhotoSearch;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@EnableScan
public interface FriendRequestRepository extends CrudRepository<FriendRequest, String> {
    List<FriendRequest> findFriendRequestByFromUserId(String fromUserId);
    List<FriendRequest> findFriendRequestByToUserId(String toUserId);
}
