package utopia.shameless.slackbot.model.internal;

public class Channel {

    public static enum ChannelType{
        CHANNEL,
        DM
    }

    private ChannelType type;
    private String id;
    private String name;

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "type=" + type +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
