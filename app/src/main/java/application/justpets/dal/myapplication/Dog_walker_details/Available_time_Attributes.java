package application.justpets.dal.myapplication.Dog_walker_details;

import java.io.Serializable;

/**
 * Created by Aniket on 29-11-2017.
 */

public class Available_time_Attributes implements Serializable {
    private String Date;
    private String Time;

    public Available_time_Attributes() {
    }

    public Available_time_Attributes(String date, String time) {
        Date = date;
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


}
