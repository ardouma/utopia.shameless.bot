package utopia.shameless.slackbot.model.slack;

import me.ramswaroop.jbot.core.slack.models.Channel;

import java.util.List;

public class ChannelList {
    List<Channel> channels;

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
