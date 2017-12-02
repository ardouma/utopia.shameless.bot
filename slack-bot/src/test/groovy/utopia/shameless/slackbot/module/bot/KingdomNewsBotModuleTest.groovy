package utopia.shameless.slackbot.module.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import spock.lang.Specification
import utopia.shameless.slackbot.configuration.ModuleProperties
import utopia.shameless.slackbot.model.internal.Channel
import utopia.shameless.slackbot.model.internal.Message
import utopia.shameless.slackbot.model.internal.User

import java.util.concurrent.TimeUnit

@SpringBootTest
//@RunWith(SpringRunner.class)
@DataMongoTest
@Import(AppConfig)
class KingdomNewsBotModuleTest extends Specification {

    @TestConfiguration
    @ComponentScan(["utopia.shameless.slackbot.module","utopia.shameless.slackbot.repository"])
    @EnableConfigurationProperties(ModuleProperties.class)
    public static class AppConfig{

    }

    @Autowired
    KingdomNewsBotModule module

    def "Test Traditional March"(){
        setup:
        assert module != null
        Message msg = new Message()
        msg.date = new Date()
        msg.channel = new Channel(name: "Room 1")
        msg.user = new User(name: "User 1")
        msg.ts = TimeUnit.MILLISECONDS.toSeconds(msg.date.time)
        msg.message = message

        when:
        module.execute(msg)
        def results = module.repository.findAll()
        println(results)
        then:
        results.size() == 1

        where:
        type | message
        null    | 'NEW KDNEWS: An unknown province from The WolfPack Projects AoD (6:3) invaded Voltron DefenderOfTheUniverse (5:10) and captured 230 acres of land. [fortnight]'


    }
}
