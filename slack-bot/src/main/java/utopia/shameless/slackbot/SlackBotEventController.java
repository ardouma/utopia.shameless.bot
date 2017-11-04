package utopia.shameless.slackbot;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SlackBotEventController extends Bot {

    @Value("${slackBotToken}")
    private String slackToken;

    @Autowired
    SlackUserService slackUserService;

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


}
