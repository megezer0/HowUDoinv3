package sabanciuniv.edu.howudoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sabanciuniv.edu.howudoin.model.UserModel;
import sabanciuniv.edu.howudoin.service.UsersService;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public UserModel register(@RequestBody UserModel user) throws Exception {

        return usersService.registerUser(user);
    }

    @PostMapping("/login")
    public void login(){

    }

    @PostMapping("/friends/add")
    public void friendRequest(@RequestBody Map<String, String> request){
        String senderId = request.get("senderId");
        String recipientId = request.get("recipientId");

        usersService.sendFriendRequest(senderId, recipientId);
    }

    @PostMapping("/friends/accept")
    public void acceptFriendRequest(@RequestBody Map<String, String> request){
        String senderId = request.get("senderId");
        String recipientId = request.get("recipientId");
        boolean accept = Boolean.parseBoolean(request.get("accept"));

        usersService.acceptFriendRequest(senderId, recipientId, accept);
    }

    @GetMapping("/friends")
    public void friendsList(@RequestBody Map<String, String> request){
        String userId = request.get("userId");
        usersService.getFriendList(userId);
    }
}

/*
Public Endpoints
-	POST /register: Register a new user (with name, last name, email, password)
-	POST /login: Authenticate and login a user (with email and password)
Secure Endpoints
-	POST /friends/add: Send a friend request
-	POST /friends/accept: Accept a friend request ( If there is a friend request)
-	GET /friends: Retrieve friend list
*/