package utopia.shameless.slackbot.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import utopia.shameless.slackbot.model.internal.Channel
import utopia.shameless.slackbot.model.internal.User

@Component
class SlackDmService {


    @Autowired
    RestTemplate restTemplate

    @Autowired
    SlackUserService slackUserService

    @Value('${slackBotToken}')
    private String slackToken

    private Map<User, Channel> opennedChannels = new HashMap<>()
    private Map<String, Channel> channelsByNameOrId = new HashMap<>()

    /**
     * Used for fetching or openning a channel with a user (ie Direct Message)
     * @param user
     * @return
     */
    Channel getChannel(User user) {
        return openChannel(user)
    }

    /**
     * Should only be called for channels that should already exist
     */
    Channel getChannel(String idOrName) {
        return fetchChannelFromSlack(idOrName)
    }

    private Channel fetchChannelFromSlack(String idOrName) {
        Channel channel = channelsByNameOrId.get(idOrName)
        if (channel == null) {
            Map<String, String> map = new HashMap<>()
            map.put("token", slackToken)
            String endpoint = "https://slack.com/api/im.list?token={token}"
            ResponseEntity<Map> response = restTemplate.getForEntity(endpoint, Map.class, map)

            List<Map> ims = Optional.ofNullable(response.getBody())
                    .map({ x -> (List) x.get("ims") })
                    .orElse(Collections.emptyList())
            ims.stream().map({ x ->
                Channel c = new Channel()
                c.setName(Optional.ofNullable(x.get("user"))
                        .map({ it.toString() })
                        .map({ slackUserService.getUser(it) })
                        .map({ it.name })
                        .orElse("<UKN>"))
                c.setId((String) x.get("id"))
                c.setType(Channel.ChannelType.DM)
                return c

            }).forEach({ x ->
                channelsByNameOrId.put(x.getName(), x)
                channelsByNameOrId.put(x.getId(), x)
            })
            channel = channelsByNameOrId.get(idOrName)
        }
        return channel

    }


    private Channel openChannel(User user) {
        Channel channel = opennedChannels.get(user)

        if (channel == null) {
            Map<String, String> map = new HashMap<>()
            map.put("token", slackToken)
            map.put("user", user.getId())
            String endpoint = "https://slack.com/api/im.open?token={token}&user={user}"

            ResponseEntity<Map> response = restTemplate.getForEntity(endpoint, Map.class, map)
            channel = new Channel()
            channel.setId((String) ((Map) response.getBody().get("channel")).get("id"))
            channel.setName(user.getName())
            channel.setType(Channel.ChannelType.DM)

            opennedChannels.put(user, channel)

        }

        return channel


    }
}
