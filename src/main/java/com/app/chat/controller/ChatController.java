package com.app.chat.controller;

import com.app.chat.model.Message;
import com.app.chat.util.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Pritesh Soni
 *
 */
@EnableScheduling
@Controller
public class ChatController {

  @Value("${messages.file.location}")
  private String fileLocation;
  private static List<String> allMessages;
  private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

  @Autowired
  private CommandExecutor commandExecutor;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @PostConstruct
  public void initializeMessages() {
    allMessages = Collections.emptyList();
    try {
      allMessages = Files.readAllLines(Paths.get(fileLocation), StandardCharsets.UTF_8);
    } catch (IOException e) {
      logger.error("Error reading messages.text", e);
    }
  }

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

  @Scheduled(fixedDelay = 5000, initialDelay = 10000)
  public void greeting() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      logger.error("Exception occurred", e);
    }
    logger.info("Scheduled!");
    String content = allMessages.get(new Random().nextInt(allMessages.size()));
    Message message = new Message(Message.MessageType.CHAT, content, "System");
    messagingTemplate.convertAndSend("/cast/all", message);
  }

}
