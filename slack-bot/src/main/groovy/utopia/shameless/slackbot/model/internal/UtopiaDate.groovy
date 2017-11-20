package utopia.shameless.slackbot.model.internal;

public class UtopiaDate {
    public static enum UtopiaMonth{
        January,
        February,
        March,
        April,
        May,
        June,
        July

    }
    private int year;
    private UtopiaMonth month;
    private int day;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public UtopiaMonth getMonth() {
        return month;
    }

    public void setMonth(UtopiaMonth month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String toString(){
        return  month + " " + day + ", Y"+year;
    }
}
