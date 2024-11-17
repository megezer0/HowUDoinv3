package sabanciuniv.edu.howudoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import sabanciuniv.edu.howudoin.model.UserModel;
import sabanciuniv.edu.howudoin.repository.UsersRepository;

import java.util.List;
import java.util.Optional;
//Also import PasswordEncoder and JwtTokenProvider

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    //Registers a new user.
    public UserModel registerUser(UserModel user) throws Exception{
        UserModel newUser = new UserModel();
        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        return usersRepository.save(newUser);
    }

    //Authenticates user and generates a JWT.
    void authenticateUser(String email, String password) {

    }

    //Adds the receiver’s ID to pendingFriendRequests in the User document.
    public boolean sendFriendRequest(String senderId, String recipientId){
        UserModel sender = usersRepository.findById(senderId).orElse(null);
        UserModel recipient = usersRepository.findById(recipientId).orElse(null);

        if (sender == null || recipient == null) {
            return false; // User not found
        }

        // Check if already friends
        if (sender.getFriends().contains(recipient) || recipient.getFriends().contains(sender)) {
            return false;
        }

        // Check if request already exists
        if (recipient.getPendingFriendsRequest().contains(sender)) {
            return false;
        }

        // Add to recipient's pending requests
        recipient.getPendingFriendsRequest().add(sender);

        // Save changes
        usersRepository.save(recipient);
        return true;
    }

    //Moves the requester’s ID from pendingFriendRequests to friends list.
    public boolean acceptFriendRequest(String senderId, String recipientId, boolean accept){
        UserModel sender = usersRepository.findById(senderId).orElse(null);
        UserModel recipient = usersRepository.findById(recipientId).orElse(null);

        if (sender == null || recipient == null) {
            return false; // User not found
        }

        // Check if the request exists
        if (!recipient.getPendingFriendsRequest().contains(sender)) {
            return false; // Request doesn't exist
        }

        if (accept) {
            // Add each to the other's friends list
            sender.getFriends().add(recipient);
            recipient.getFriends().add(sender);
        }

        // Remove the request
        recipient.getPendingFriendsRequest().remove(sender);

        // Save changes
        usersRepository.save(sender);
        usersRepository.save(recipient);
        return true;
    }

    //Retrieves a user’s friend list.
    public List<UserModel> getFriendList(String userId){
        Optional<UserModel> userOptional = usersRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
        UserModel user = userOptional.get();

        return user.getFriends();
    }
}
