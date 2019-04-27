package com.clou.photoshare.controller;

import com.clou.photoshare.errorHandler.UserNotFoundException;
import com.clou.photoshare.model.FriendRequest;
import com.clou.photoshare.model.UserBuilder;
import com.clou.photoshare.repository.FriendRequestRepository;
import com.clou.photoshare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.clou.photoshare.model.User;
import com.clou.photoshare.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository repo,
                          UserService userService) {
        this.repository = repo;
        this.userService = userService;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST) // didn't use @ModelAttribute, decide later after know Congito
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try{
            repository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String id) {
        // return repository.findById(id).get(); // naive approach, return's a User JSON

        return Optional
                .ofNullable(repository.findById(id))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            if (repository.findById(user.getId()).isPresent()) {
                repository.save(user);
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchUserByEmail(@RequestParam("email") String email) {
        try {
            List<User> result = repository.findDistinctByEmail(email);
            if (result.size() > 0) {
                return new ResponseEntity<>(result.get(0), HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    // <----- Friend ------>
    @RequestMapping(value = "/addfriend", method = RequestMethod.POST)
    public ResponseEntity<?> createFriendRequest(@RequestBody FriendRequest friendRequest) {


        System.out.print("=======================================");
        System.out.println("Request Received");

        String fromUserId= friendRequest.getFromUserId();
        String toUserId = friendRequest.getToUserId();

        System.out.println(fromUserId);
        System.out.println(toUserId);
        // this should not happend since it should prevented by Frontend

        try {
            if (this.userService.checkIfFriends(fromUserId, toUserId)) {
                System.out.println("no problem");
                return new ResponseEntity<>("Already Friends", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        int requestStatus = this.userService.checkFriendRequestSend(fromUserId, toUserId);
        if (requestStatus == 1) {
            return new ResponseEntity<>("Request have been sent", HttpStatus.OK);
        } else if (requestStatus == 2) {
            return new ResponseEntity<>("User have received request from another side, advise user to check notifications",
                    HttpStatus.OK);
        }

        this.userService.addFriendRequest(friendRequest);
        return new ResponseEntity<>(friendRequest, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addfriend", method = RequestMethod.PUT)
    public ResponseEntity<?> respondFriendRequest(@RequestBody FriendRequest friendRequest) {
        try {
            String status = friendRequest.getStatus();
            if (status == "accepted") {
                this.userService.acceptFriendRequest(friendRequest);
            } else {
                this.userService.declineFriendRequest(friendRequest);
            }
            return new ResponseEntity<>(friendRequest, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/friendrequestsfrom/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSendFriendsRequest(@PathVariable String userId) {
        try {
            List<FriendRequest> result = this.userService.getSendFriendRequests(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/friendrequeststo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getReceivedFriendsRequest(@PathVariable String userId) {
        try {
            List<FriendRequest> result = this.userService.getReceivedFriendRequest(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}