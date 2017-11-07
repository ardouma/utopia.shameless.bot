package utopia.shameless.slackbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import utopia.shameless.slackbot.model.internal.User;
import utopia.shameless.slackbot.model.slack.UserList;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SlackUserService {


    @Autowired
    RestTemplate restTemplate;

    @Value("${slackApiToken}")
    private String slackToken;

    private Map<String, User> userMap = Collections.emptyMap();

    @PostConstruct
    @Scheduled(cron = "0 0 * * * *")
    public void init() {

        Map<String, String> map = new HashMap<>();
        map.put("token", slackToken);
        String endpoint = "https://slack.com/api/users.list?token={token}&pretty=1";
        System.out.println("User Service Endpoint: " + endpoint + " \n Map: " + map);
        ResponseEntity<UserList> response = restTemplate.getForEntity(endpoint, UserList.class, map);

        userMap = response.getBody().getMembers()
                .stream()
                .map(this::toUser)
                .collect(Collectors.toMap(k -> k.getId(), v -> v));

        System.out.println("user list results:\n\n" + userMap);
    }

    public User getUser(String id){
        return userMap.get(id);
    }

    private User toUser(UserList.Member member) {
        User user = new User();
        user.setId(member.getId());
        user.setName(member.getProfile().getDisplay_name());
        user.setEmail(member.getProfile().getEmail());
        return user;
    }
}
