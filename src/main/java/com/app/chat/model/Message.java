package com.app.chat.model;

/**
 * @author Pritesh Soni
 *
 */
public class Message {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

  public Message(MessageType type, String content, String sender) {
    this.type = type;
    this.content = content;
    this.sender = sender;
  }

  public Message() {
  }

  public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
