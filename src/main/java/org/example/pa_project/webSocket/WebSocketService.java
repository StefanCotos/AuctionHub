package org.example.pa_project.webSocket;

import org.example.pa_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for handling WebSocket communications.
 */
@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    /**
     * Constructs a new WebSocketService with the specified SimpMessagingTemplate and UserRepository.
     *
     * @param messagingTemplate the messaging template for sending WebSocket messages
     * @param userRepository    the user repository
     */
    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate, UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
    }

    /**
     * Notifies clients with a message.
     *
     * @param message the message to send to clients
     */
    public void notifyClients(String message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
