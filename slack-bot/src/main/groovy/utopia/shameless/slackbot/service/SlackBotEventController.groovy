package utopia.shameless.slackbot.service

import me.ramswaroop.jbot.core.slack.Bot
import me.ramswaroop.jbot.core.slack.Controller
import me.ramswaroop.jbot.core.slack.EventType
import me.ramswaroop.jbot.core.slack.models.Event
import me.ramswaroop.jbot.core.slack.models.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import utopia.shameless.slackbot.model.internal.Channel
import utopia.shameless.slackbot.model.internal.User

@Component
class SlackBotEventController extends Bot implements Messager {

    private Logger logger = LoggerFactory.getLogger(getClass())

    @Value('${slackBotToken}')
    private String slackToken

    private WebSocketSession session

    @Autowired
    private SlackUserService slackUserService

    @Autowired
    private SlackChannelService slackChannelService

    @Autowired
    private SlackDmService slackDmService

    @Autowired
    private EventProcessor eventProcessor

    @Override
    String getSlackToken() {
        return slackToken
    }

    @Override
    Bot getSlackBot() {
        return this
    }


    @Controller(events = [ EventType.MESSAGE, EventType.DIRECT_MESSAGE, EventType.DIRECT_MENTION ])
    void onReceiveDM(WebSocketSession session, Event event) {
        utopia.shameless.slackbot.model.internal.Message uEvent = new utopia.shameless.slackbot.model.internal.Message()
        User user = slackUserService.getUser(event.getUserId())

        uEvent.setChannel(
                Optional.ofNullable(event.getChannelId())
                        .map({ slackChannelService.getChannel(it) })
                        .orElseGet({slackDmService.getChannel(event.channelId)})
        )

        uEvent.setUser(user)
        uEvent.setTs(Double.parseDouble(event.getTs()))
        uEvent.setMessage(event.getText())

        System.out.println(System.currentTimeMillis() + " ts: " + event.getTs() + " Type: " + event.getType() + " User: " + event.getUserId() + " message:" + event.getText())
        eventProcessor.processEvent(uEvent)
    }

    @Override
    void sendDm(User user, String text) {
        sendMessage(slackDmService.getChannel(user), text)
    }

    @Override
    void sendMessage(Channel channel, String text) {
        Event event = null
        Message reply = new Message()
        reply.setText(text)
        reply.setChannel(channel.getId())
        switch (channel.getType()) {
            case CHANNEL:
                reply.setType(EventType.MESSAGE.name().toLowerCase())
                break
            case DM:
                reply.setType(EventType.MESSAGE.name().toLowerCase())
                break
        }

        reply(session, event, reply)
    }


    @Override
    void afterConnectionEstablished(WebSocketSession session) {
        this.session = session
    }

    @Override
    void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        this.session = null
    }

    @Override
    void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("Error sending web message", exception)
    }
}
