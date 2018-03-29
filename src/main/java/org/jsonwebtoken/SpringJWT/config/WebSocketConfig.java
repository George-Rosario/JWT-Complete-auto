package org.jsonwebtoken.SpringJWT.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		/*
		 * we register a websocket endpoint that the clients will use to connect to our websocket server
		 * SockJS is used to enable fallback options for browsers that don’t support websocket.
		 */
		registry.addEndpoint("/ws").withSockJS();
		
	}

	/*
	 *
	 *  we’re configuring a message broker that will be used to route messages from one client to another.
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		/*
		 * The first line defines that the messages whose destination starts with “/app” 
		 * should be routed to message-handling methods 
		 * (we’ll define these methods shortly).
		 */
		//registry.enableSimpleBroker("/topic");
		
		registry.setApplicationDestinationPrefixes("/app");
		/*
		 * defines that the messages whose destination starts with “/channel” should
		 *  be routed to the message broker. Message broker broadcasts messages 
		 *  to all the connected clients who are subscribed to a particular channel
		 */
		registry.enableSimpleBroker("/channel");
	}
   
}