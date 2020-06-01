package com.app.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @author Pritesh Soni
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint("/ws").withSockJS();
    }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
    // Enables a simple in-memory broker
    registry.enableSimpleBroker("/cast");
    // For a Full featured broker like RabbitMQ
/*        registry.enableStompBrokerRelay("/cast")
                .setRelayHost("localhost")
                .setRelayPort(61617)
                .setClientLogin("admin")
                .setClientPasscode("admin");*/
  }
}
