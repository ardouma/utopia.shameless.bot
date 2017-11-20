package utopia.shameless.slackbot.service


import me.ramswaroop.jbot.core.slack.models.Channel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import utopia.shameless.slackbot.model.slack.ChannelInfo
import utopia.shameless.slackbot.model.slack.ChannelList

import javax.annotation.PostConstruct
import java.util.Collections
import java.util.HashMap
import java.util.Map
import java.util.Optional
import java.util.stream.Collectors

@Component
 class SlackChannelService {

    private static final Channel EMPTY = new Channel()

    @Autowired
    RestTemplate restTemplate

    @Value('${slackBotToken}')
    private String slackToken

    private Map<String, Channel> channelMap = Collections.emptyMap()

    @PostConstruct
    @Scheduled(cron = "0 0 * * * *")
     void init() {

        Map<String, String> map = new HashMap<>()
        map.put("token", slackToken)
        String endpoint = "https://slack.com/api/channels.list?token={token}&pretty=1"
        System.out.println("Channels Service Endpoint: " + endpoint + " \n Map: " + map)
        ResponseEntity<ChannelList> response = restTemplate.getForEntity(endpoint, ChannelList.class, map)

        channelMap = response.getBody().getChannels()
                .stream()
                .collect({it.name},{it})

        System.out.println("user list results:\n\n" + channelMap)
    }

     utopia.shameless.slackbot.model.internal.Channel getChannel(String name) {
        utopia.shameless.slackbot.model.internal.Channel channel = new utopia.shameless.slackbot.model.internal.Channel()

        Channel slackChannel = channelMap.get(name)
        if(slackChannel == null){
            slackChannel = fetchChannel(name)
            if(slackChannel != null) {
                channelMap.put(slackChannel.getId(), slackChannel)
                channelMap.put(slackChannel.getName(), slackChannel)
            }
        }
        if (slackChannel != null) {
            channel.setType(utopia.shameless.slackbot.model.internal.Channel.ChannelType.CHANNEL)
            channel.setName(slackChannel.getName())
            channel.setId(slackChannel.getId())

            return channel
        } else {

            return null
        }


    }

    private Channel fetchChannel(String name) {
        Map<String, String> map = new HashMap<>()
        map.put("token", slackToken)
        map.put("channel", name)
        String endpoint = "https://slack.com/api/channels.info?token={token}&channel={channel}"
        System.out.println("Channels Service Endpoint: " + endpoint + " \n Map: " + map)
        ResponseEntity<ChannelInfo> response = restTemplate.getForEntity(endpoint, ChannelInfo.class, map)

        return Optional.ofNullable(response.getBody()).map({it.channel}).orElse(null)
    }


}
