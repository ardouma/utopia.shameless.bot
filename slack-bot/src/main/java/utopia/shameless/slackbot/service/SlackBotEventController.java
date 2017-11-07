package utopia.shameless.slackbot.service;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;


@Component
public class SlackBotEventController extends Bot {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${slackBotToken}")
    private String slackToken;

    private WebSocketSession session;

    @Autowired
    private SlackUserService slackUserService;

    @Autowired
    private SlackChannelService slackChannelService;


    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }


    @Controller(events = {EventType.MESSAGE, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {
        System.out.println("ts: " + event.getTs() + " Type: " + event.getType() + " User: " + event.getUserId() + " message:" + event.getText());

        reply(session, event, new Message("Hi " + slackUserService.getUser(event.getUserId()).getName() + ", Thank you for saying: " + event.getText()));
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void scheduled(){
        sendMessage("general", "This is a test");
    }

    public void sendMessage(String channel, String text){
        Event event=null;
        Message reply = new Message();
        reply.setText(text);
        reply.setChannel(channel);
        reply.setType(EventType.MESSAGE.name().toLowerCase());
        reply(session, event, reply);
    }



    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        this.session = null;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("Error sending web message", exception);
    }
}
