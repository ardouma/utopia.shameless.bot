package utopia.shameless.slackbot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.module")
public class ModuleProperties {

    BotProperties bot

    public static class BotProperties {
        KdProperties kdnews

        public static class KdProperties {
            String traditionalMarch
            String abduct
            String conquest
            String plunder
            String massacre
            String raze
            String bounce
        }

    }
}
