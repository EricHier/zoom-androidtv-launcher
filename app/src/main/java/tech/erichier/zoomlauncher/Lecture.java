package tech.erichier.zoomlauncher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lecture {

    @JsonProperty("name")
    public String name;

    @JsonProperty("url")
    public String url;

    @JsonProperty("day")
    public String day;

    @JsonProperty("time")
    public String[] time;

    Lecture() {

    }

    @Override
    public String toString() {

        String weekday = "";

        switch (Integer.parseInt(day)) {
            case 1:
                weekday = "Montag";
                break;
            case 2:
                weekday = "Dienstag";
                break;
            case 3:
                weekday = "Mittwoch";
                break;
            case 4:
                weekday = "Donnerstag";
                break;
            case 5:
                weekday = "Freitag";
                break;
            case 6:
                weekday = "Samstag";
                break;
            case 0:
                weekday = "Sonntag";
                break;
        }

        return name + "\n" + weekday + ", " + time[0] + ":" + time[1];
    }
}
