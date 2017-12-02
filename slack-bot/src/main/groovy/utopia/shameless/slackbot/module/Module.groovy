package utopia.shameless.slackbot.module;

import utopia.shameless.slackbot.model.internal.Message;

public interface Module {

    boolean accept(Message event);
    void execute(Message event);


}
