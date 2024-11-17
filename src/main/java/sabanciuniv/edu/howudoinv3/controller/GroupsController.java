package sabanciuniv.edu.howudoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sabanciuniv.edu.howudoin.model.Group;
import sabanciuniv.edu.howudoin.model.Message;
import sabanciuniv.edu.howudoin.model.UserModel;
import sabanciuniv.edu.howudoin.service.GroupsService;

import java.util.List;
import java.util.Map;

@RestController
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @PostMapping("/groups/create")
    public void createGroup(@RequestBody Map<String, Object> request){

        String name = (String) request.get("name");
        String adminId = (String) request.get("adminId");
        Group group = new Group();
        List<UserModel> members = (List<UserModel>) request.get("members");

        groupsService.createGroup(name, adminId, members);
    }

    @PostMapping("/groups/{groupId}/add-member")
    public void addMember(@PathVariable String groupId, @RequestBody Map<String, String> request){
        String userId = request.get("userId");

        groupsService.addMemberToGroup(groupId, userId);
    }

    @PostMapping("/groups/{groupId}/send")
    public void sendToGroup(@PathVariable String groupId, @RequestBody Map<String, String> request){
        String senderId = request.get("senderId");
        String content = request.get("content");
        String timestamp = request.get("timestamp");

        groupsService.sendMessageToGroup(senderId, groupId, content, timestamp);

    }

    @GetMapping("/groups/{groupId}/messages")
    public List<Message> groupMessages(@PathVariable String groupId){
        return groupsService.getGroupMessages(groupId);
    }

    @GetMapping("/groups/{groupId}/members")
    public List<UserModel> groupMembers(@PathVariable String groupId){
        return groupsService.getGroupMembers(groupId);
    }
}

/*
-	POST /groups/create: Creates a new group with a given name and members.
-	POST /groups/:groupId/add-member: Adds a new member to an existing group.
-	POST /groups/:groupId/send: Sends a message to all members of the specified group.
-	GET /groups/:groupId/messages: Retrieves the message history for the specified group.
-	GET /groups/:groupId/members: Retrieves the list of members for the specified group.
*/
