package utopia.shameless.slackbot.service;

import utopia.shameless.slackbot.model.internal.Channel;
import utopia.shameless.slackbot.model.internal.User;

public interface Messager {
    void sendDm(User user, String text);
    void sendMessage(Channel channel, String text);
}
