package utopia.shameless.slackbot.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import utopia.shameless.slackbot.model.internal.Message

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class EventProcessor {

    @Value('${munkbotUser}')
    String munkbotUserName

    Messager messager

    final Logger logger = LoggerFactory.getLogger(getClass())


    @Async
    void processEvent(Message event) {
        logEvent(event)
        String user = Optional.ofNullable(event).map({ it.user }).map({ it.name }).orElse("N/A")
        String msg = Optional.ofNullable(event).map({ it.message }).orElse("N/A")

        if (msg.startsWith(".")) {
            processCommand(event)
        } else if (user.equals(munkbotUserName)) {
            processMunkEvent(event)
        } else {
            // no-op
        }


    }

    void processMunkEvent(Message event) {

    }


    void processCommand(Message event) {


    }

    void logEvent(Message event) {
        String ts = Optional.ofNullable(event)
                .map({ it.date })
                .map({ x -> LocalDateTime.ofInstant(x.toInstant(), ZoneId.of("UTC")) })
                .map({ DateTimeFormatter.ISO_DATE_TIME.format(it) })
                .orElse("N/A")
        String channel = Optional.ofNullable(event)
                .map({ it.channel })
                .map({ it.name })
                .orElse("N/A")
        String user = Optional.ofNullable(event)
                .map({ it.user })
                .map({ it.name })
                .orElse("N/A")
        String msg = Optional.ofNullable(event)
                .map({ it.message })
                .orElse("N/A")
        logger.info("ts: {}, channel: {}, user: {}, message: {}", ts, channel, user, msg)
    }
}
