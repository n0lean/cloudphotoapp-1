package com.clou.photoshare.services;


import com.clou.photoshare.errorHandler.UserNotFoundException;
import com.clou.photoshare.model.FriendRequest;
import com.clou.photoshare.model.User;
import com.clou.photoshare.repository.FriendRequestRepository;
import com.clou.photoshare.repository.UserRepository;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Autowired
    public UserService(UserRepository userRepository, FriendRequestRepository friendRequestRepository) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    /**
     *
     * @param user1Id
     * @param user2Id
     * @return boolean
     * @throws UserNotFoundException
     */
    public boolean checkIfFriends(String user1Id, String user2Id) throws UserNotFoundException {
        User user1 = userRepository.findById(user1Id).orElseThrow(() -> new UserNotFoundException(user1Id));
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new UserNotFoundException(user2Id));

        if (user1.getFriends().contains(user2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if request between two users already exsits
     * @param fromUserId requester id
     * @param toUserId receiver id
     * @return 1 if already friend request exist, 2 if reverse friend request exsit, 0 for not exist
     */
    public int checkFriendRequestSend(String fromUserId, String toUserId) {
        List<FriendRequest> fromToToRequests = this.friendRequestRepository.findFriendRequestByFromUserId(fromUserId);
        if (fromToToRequests.size() > 0) {
            // request already exist
            return 1;
        }

        List<FriendRequest> ToToFromRequests = this.friendRequestRepository.findFriendRequestByToUserId(toUserId);
        if (ToToFromRequests.size() > 0) {
            // reverse request exist
            return 2;
        }

        return 0;
    }

    /**
     *
     * @param friendRequest
     */
    public void addFriendRequest(FriendRequest friendRequest) {
        this.friendRequestRepository.save(friendRequest);
    }

    /**
     *
     * @param friendRequest
     * @throws UserNotFoundException
     */
    public void acceptFriendRequest(FriendRequest friendRequest) throws UserNotFoundException {
        String fromUserId = friendRequest.getFromUserId();
        String toUserId = friendRequest.getToUserId();

        User userFrom = this.userRepository.findById(fromUserId).orElseThrow(() -> new UserNotFoundException(fromUserId));
        User userTo = this.userRepository.findById(toUserId).orElseThrow(() -> new UserNotFoundException(toUserId));

        userFrom.getFriends().add(toUserId);
        userTo.getFriends().add(fromUserId);

        this.userRepository.save(userFrom);
        this.userRepository.save(userTo);
        this.friendRequestRepository.delete(friendRequest);
    }


}
