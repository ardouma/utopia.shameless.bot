package utopia.shameless.slackbot.model.slack;

import me.ramswaroop.jbot.core.slack.models.Channel;

import java.util.List;

public class ChannelInfo {
    Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
