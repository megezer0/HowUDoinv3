package sabanciuniv.edu.howudoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sabanciuniv.edu.howudoin.model.Message;
import sabanciuniv.edu.howudoin.service.MessagesService;

import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @PostMapping("/messages/send")
    public void sendMessage(@RequestBody Map<String, Object> request) {
        String from = (String) request.get("senderUser");
        String to = (String) request.get("receiverUser");
        String content = (String) request.get("content");
        String timestamp = (String) request.get("timestamp");

        messagesService.sendMessage(from, to, content, timestamp);
    }

    @GetMapping("/messages")
    public List<Message> getMessage(@RequestBody Map<String, Object> request) {
        String user1Id = (String) request.get("firstUser");
        String user2Id = (String) request.get("secondUser");

        return messagesService.getConversation(user1Id, user2Id);
    }
}

/*
-	POST /messages/send: Send a message to a friend
-	GET /messages: Retrieve conversation history
*/