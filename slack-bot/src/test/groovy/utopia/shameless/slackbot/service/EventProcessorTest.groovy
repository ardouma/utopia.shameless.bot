package utopia.shameless.slackbot.service

import spock.lang.Specification
import spock.lang.Unroll
import utopia.shameless.slackbot.model.internal.Channel
import utopia.shameless.slackbot.model.internal.Event
import utopia.shameless.slackbot.model.internal.User

@Unroll
class EventProcessorTest extends Specification {

    void setup() {
        mockProcessor = Spy(EventProcessor)
        mockProcessor.munkbotUserName = "bot"
        mockProcessor.messager = Mock(Messager)
    }

    void cleanup() {
    }

    EventProcessor mockProcessor

    def "ProcessEvent"() {
        setup:
        Event event = new Event()
        event.setChannel(new Channel(type:Channel.ChannelType.DM, id: "1234", name:"user1"))
        event.setTs(System.currentTimeMillis()/1000.0)
        event.setUser(new User(id: "user1Id", name: user, provence: "myProv"))
        event.setMessage(message)


        when:
        mockProcessor.processEvent(event)

        then:
        muckEvent * mockProcessor.processMunkEvent(_)
        cmdEvent * mockProcessor.processCommand(_)


        where:
        muckEvent | cmdEvent | user | message
        0 | 0 | "user" | "random message"
        0 | 1 | "user" | ".command"
        1 | 0 | "bot" | ""
    }
}
