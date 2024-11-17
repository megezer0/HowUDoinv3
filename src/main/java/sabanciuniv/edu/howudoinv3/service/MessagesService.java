package sabanciuniv.edu.howudoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabanciuniv.edu.howudoin.model.Message;
import sabanciuniv.edu.howudoin.model.UserModel;
import sabanciuniv.edu.howudoin.repository.MessagesRepository;
import sabanciuniv.edu.howudoin.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessagesService {
    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private UsersRepository usersRepository;

    //Saves a direct message to the Message collection.
    public void sendMessage(String senderId, String recieverId, String content, String timestamp){
        Message newMessage = new Message();

        Optional<UserModel> senderOptional = usersRepository.findById(senderId);
        if (senderOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + senderId + " not found");
        }
        UserModel sender = senderOptional.get();

        Optional<UserModel> recieverOptional = usersRepository.findById(recieverId);
        if (recieverOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + recieverId + " not found");
        }
        UserModel reciever = senderOptional.get();

        newMessage.setSenderUser(sender);
        newMessage.setRecieverUser(reciever);
        newMessage.setTimestamp(timestamp);
        newMessage.setMessageContent(content);

        messagesRepository.save(newMessage);
    }

    //Retrieves messages between two users, filtering by senderId and receiverId.
    public List<Message> getConversation(String userId1, String userId2){
        Optional<UserModel> user1Optional = usersRepository.findById(userId1);
        if (user1Optional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId1 + " not found");
        }
        UserModel user1 = user1Optional.get();

        Optional<UserModel> user2Optional = usersRepository.findById(userId2);
        if (user2Optional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId2 + " not found");
        }
        UserModel user2 = user2Optional.get();

        return messagesRepository.findAllBySenderUserAndRecieverUser(user1, user2);
    }
}
