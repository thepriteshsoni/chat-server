package com.app.chat.controller;

import com.app.chat.model.Message;
import com.app.chat.util.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * @author Pritesh Soni
 *
 */
@Controller
public class ChatController {

  @Autowired
  private CommandExecutor commandExecutor;

  @MessageMapping("/chat.sendMessage")
  @SendTo("/cast/all")
  public Message sendMessage(@Payload Message message) {
    String messageContent = message.getContent();
    if (messageContent.startsWith("//")) {
      int result = commandExecutor.executeShellCommand(messageContent.substring(2), message.getSender());
      messageContent += " -> Result of execution: ";
      messageContent += result == 0 ? "ACK" : "NOACK";
      message.setContent(messageContent);
    }
    return message;
  }

  @MessageMapping("/chat.addUser")
  @SendTo("/cast/all")
  public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
    // Add username in web socket session
    headerAccessor.getSessionAttributes().put("username", message.getSender());
    return message;
  }

}
