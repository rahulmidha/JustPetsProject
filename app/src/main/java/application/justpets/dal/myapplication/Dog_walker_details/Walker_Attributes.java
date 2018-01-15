package application.justpets.dal.myapplication.Dog_walker_details;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aniket on 29-11-2017.
 */

public class Walker_Attributes implements Serializable {
    private String Category;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    private String price;

    public Walker_Attributes(String category, String price, int rating, String address, String emailid, String name, long phonenumber, String uid, ArrayList<Available_time_Attributes> availableTime) {
        Category = category;
        this.price = price;
        this.rating = rating;
        Address = address;
        this.emailid = emailid;
        this.name = name;
        this.phonenumber = phonenumber;
        this.uid = uid;
        AvailableTime = availableTime;
    }

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private int rating;


    private String Address;
    private String emailid;
    private String name;
    private long phonenumber;
    private String uid;
    private transient ArrayList<Available_time_Attributes> AvailableTime;


    public ArrayList<Available_time_Attributes> getAvailableTime() {
        return AvailableTime;
    }

    public void setAvailableTime(ArrayList<Available_time_Attributes> availableTime) {
        AvailableTime = availableTime;
    }




    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
