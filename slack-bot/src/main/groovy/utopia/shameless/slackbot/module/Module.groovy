package utopia.shameless.slackbot.module;

import utopia.shameless.slackbot.model.internal.Event;

public interface Module {

    boolean accept(Event event);
    void execute(Event event);


}
