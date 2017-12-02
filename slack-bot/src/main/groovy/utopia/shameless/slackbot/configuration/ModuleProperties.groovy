package utopia.shameless.slackbot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import utopia.shameless.slackbot.model.internal.UtopiaEventType

@ConfigurationProperties(prefix = "app.module")
class ModuleProperties {

    BotProperties bot

    static class BotProperties {
        Map<UtopiaEventType, KdProperties> kdnews

        static class KdProperties {
            String inbound
            String outbound
        }

    }
}
