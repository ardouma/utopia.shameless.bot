package utopia.shameless.slackbot.model.internal

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = "name")
class Channel {
    static enum ChannelType {
        CHANNEL,
        DM
    }

    private ChannelType type
    private String id
    private String name


    @Override
    String toString() {
        String sym = "!"
        switch (type) {
            case DM:
                sym = "@"
                break
            case CHANNEL:
                sym = "#"
                break
        }

        return sym + name + "(" + id + ")"
    }


}
