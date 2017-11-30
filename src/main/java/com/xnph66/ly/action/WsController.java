package com.xnph66.ly.action;

import com.xnph66.ly.entity.WiselyMessage;
import com.xnph66.ly.entity.WiselyResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by xn072816 on 2017/11/29.
 */
@RestController
public class WsController {
    private static final Log log = LogFactory.getLog(WsController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg) {
        if (principal.getName().equals("dzz")) {
            messagingTemplate.convertAndSendToUser("zbb",
                    "/queue/notifications", principal.getName() + "-send:"
                            + msg);
        } else {
            messagingTemplate.convertAndSendToUser("dzz", "/queue/notifications", principal.getName() + "-send:"
                    + msg);
        }
    }

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public WiselyResponse say(WiselyMessage message) throws Exception {
        log.info("message:" + message.getName());
        WiselyResponse response = new WiselyResponse();
        response.setResponseMessage("welcome," + message.getName());
        return response;

    }
}
