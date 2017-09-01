package com.zl.contoller;

import com.zl.entity.SocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: CaoXiaoLong create on 2017/8/28 16:23.
 */
@Controller
public class SocketMsgController {
    private Logger logger = LoggerFactory.getLogger(SocketMsgController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping("/socket")
    public String socket() {
        return "socket";
    }

    /**
     * 用于接收客户端发送过来的websocket请求。
     * @param message
     */
    @MessageMapping("/send") //和@RequestMapping功能类似，用于设置URL映射地址
    @SendTo("/topic/send")  //当服务器有消息时,会对订阅了@SendTo中的路径的浏览器发送消息
    public SocketMessage send(SocketMessage message) throws Exception {
        logger.info("ininininininiininini");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.toString();
        message.date = df.format(new Date());
        return message;
    }


    @Scheduled(fixedRate = 1000) //springBoot定时任务
//    @Async
    @SendTo("/topic/callback")
    public Object callback() throws Exception {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
        return "callback";
       /* while(true){
            // 发现消息
             messagingTemplate.convertAndSend("/topic/callback", "NUM + "+RandomUtils.nextInt(100)*100);
//            return "callback";
        }*/
    }

}
