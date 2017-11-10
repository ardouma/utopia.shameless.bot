package utopia.shameless.slackbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utopia.shameless.slackbot.model.internal.Channel;
import utopia.shameless.slackbot.model.internal.User;
import utopia.shameless.slackbot.model.slack.ChannelList;
import utopia.shameless.slackbot.model.slack.UserList;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SlackDmService {


    @Autowired
    RestTemplate restTemplate;

    @Value("${slackBotToken}")
    private String slackToken;

    private Map<User, Channel> opennedChannels = new HashMap<>();


    public Channel getChannel(User user) {
        return openChannel(user);
    }

    private Channel openChannel(User user) {
        Channel channel = opennedChannels.get(user);

        if (channel == null) {
            Map<String, String> map = new HashMap<>();
            map.put("token", slackToken);
            map.put("user", user.getId());
            String endpoint = "https://slack.com/api/im.open?token={token}&user={user}";

            ResponseEntity<Map> response = restTemplate.getForEntity(endpoint, Map.class, map);
            channel = new Channel();
            channel.setId((String) ((Map) response.getBody().get("channel")).get("id"));
            channel.setName(user.getName());
            channel.setType(Channel.ChannelType.DM);

            opennedChannels.put(user, channel);
        }

        return channel;


    }
}
