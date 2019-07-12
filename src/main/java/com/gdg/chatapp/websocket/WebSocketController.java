package com.gdg.chatapp.websocket;

import com.gdg.chatapp.models.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author azar
 */

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(String message) {
        System.out.println("Message " + message);
        this.template.convertAndSend("/chat", new SimpleDateFormat("HH:mm:ss").format(new Date()) + "-" + message);
    }

    /**
     * @param message It is {@link ChatModel} model that receives message to be sent
     * @author azar
     */
    @MessageMapping("msg/send/{username}")
    public void sendMessage(@RequestBody ChatModel messageModel) {
        this.template.convertAndSend("/chat/" + messageModel.getToPhoneNo(), messageModel.getMessage());
    }

}
