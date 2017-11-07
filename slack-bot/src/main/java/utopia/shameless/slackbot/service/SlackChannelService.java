package utopia.shameless.slackbot.service;


import me.ramswaroop.jbot.core.slack.models.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utopia.shameless.slackbot.model.internal.User;
import utopia.shameless.slackbot.model.slack.ChannelList;
import utopia.shameless.slackbot.model.slack.UserList;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SlackChannelService {

    private static final Channel EMPTY = new Channel();

    @Autowired
    RestTemplate restTemplate;

    @Value("${slackApiToken}")
    private String slackToken;

    private Map<String, Channel> channelMap = Collections.emptyMap();

    @PostConstruct
    @Scheduled(cron = "0 0 * * * *")
    public void init() {

        Map<String, String> map = new HashMap<>();
        map.put("token", slackToken);
        String endpoint = "https://slack.com/api/channels.list?token={token}&pretty=1";
        System.out.println("Channels Service Endpoint: " + endpoint + " \n Map: " + map);
        ResponseEntity<ChannelList> response = restTemplate.getForEntity(endpoint, ChannelList.class, map);

        channelMap = response.getBody().getChannels()
                .stream()
                .collect(Collectors.toMap(k -> k.getName(), v -> v));

        System.out.println("user list results:\n\n" + channelMap);
    }

    public String getChannelId(String name){
        return channelMap.getOrDefault(name, EMPTY).getId();
    }


}
