package com.app.chat.controller;

import com.app.chat.model.Message;
import com.app.chat.util.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

/**
 * @author Pritesh Soni
 *
 */
@EnableScheduling
@Controller
public class ChatController {

  private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

  @Autowired
  private CommandExecutor commandExecutor;

  @MessageMapping("/chat.sendMessage")
  @SendTo("/cast/all")
  public Message sendMessage(@Payload Message message) {
    String messageContent = message.getContent();
    String sender = message.getSender();
    logger.info("Received new chat message from user: " + sender + " -> " + messageContent);
    if (messageContent.startsWith("//")) {
      int result = commandExecutor.executeShellCommand(messageContent.substring(2), sender);
      messageContent += " -> Result of execution: ";
      messageContent += result == 0 ? "ACK" : "NOACK";
      message.setContent(messageContent);
    }
    return message;
  }

  @MessageMapping("/chat.addUser")
  @SendTo("/cast/all")
  public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
    String sender = message.getSender();
    logger.info("New user connected: " + sender);
    headerAccessor.getSessionAttributes().put("username", sender);
    return message;
  }

}
