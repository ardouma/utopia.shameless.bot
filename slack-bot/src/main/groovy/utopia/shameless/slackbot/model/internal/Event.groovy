package utopia.shameless.slackbot.model.internal

class Event {
    User user;
    Channel channel;
    Double ts;
    Date date;

    String message;


     void setTs(Double ts) {
        this.ts = ts;
        this.date = new Date(this.ts.longValue() * 1000);
    }


}
