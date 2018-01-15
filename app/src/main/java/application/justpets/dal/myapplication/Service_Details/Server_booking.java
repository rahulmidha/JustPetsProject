package application.justpets.dal.myapplication.Service_Details;

import java.io.Serializable;

/**
 * Created by Aniket on 15-11-2017.
 */

public class Server_booking implements Serializable {
    private String category;
    private String uid;
    private String Date;
    private String name;
    private String ratings;
    private String Status;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getname() {
        return name;
    }

    public void setname(String userName) {
        name = userName;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Server_booking(String category, String uid, String date, String userName, String ratings, String status) {
        this.category = category;
        this.uid = uid;
        Date = date;
        name = userName;
        this.ratings = ratings;
        Status = status;
    }
}
