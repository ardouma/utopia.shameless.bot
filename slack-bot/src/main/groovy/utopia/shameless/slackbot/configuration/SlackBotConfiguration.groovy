package utopia.shameless.slackbot.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(["me.ramswaroop.jbot", "utopia.shameless"])
@EnableScheduling
@EnableConfigurationProperties(ModuleProperties.class)
public class SlackBotConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
