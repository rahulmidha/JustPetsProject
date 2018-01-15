package application.justpets.dal.myapplication.User_Details;

import java.io.Serializable;
import java.util.ArrayList;

import application.justpets.dal.myapplication.Dog_walker_details.Available_time_Attributes;
import application.justpets.dal.myapplication.Service_Details.Server_booking;

/**
 * Created by Aniket on 11-11-2017.
 */
public class User_data implements Serializable{
    private  String Category;
    private String price;
    private String name;
    private String Emailid;
    private String Uid;
    private String Address;
    private long PhoneNumber;
    private int rating;
    private transient ArrayList<Server_booking> Bookinglist;
    private transient ArrayList<Pet_Details> PetList;
    private transient ArrayList<Available_time_Attributes> AvailableTime;

    public User_data(String category, String price, ArrayList<Server_booking> bookinglist, int rating, String name, String emailid, String uid, String address, long phoneNumber, ArrayList<Pet_Details> petList, ArrayList<Available_time_Attributes> availableTime) {
        Category = category;
        this.price = price;
        this.Bookinglist = bookinglist;
        this.rating = rating;
        this.name = name;
        Emailid = emailid;
        Uid = uid;
        Address = address;
        PhoneNumber = phoneNumber;
        PetList = petList;
        AvailableTime = availableTime;

    }

    public ArrayList<Server_booking> getBookinglist() {
        return Bookinglist;
    }

    public void setBookinglist(ArrayList<Server_booking> bookinglist) {
        this.Bookinglist = bookinglist;
    }





    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public int getRating() {

        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCategory() {

        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }





    public ArrayList<Available_time_Attributes> getAvailableTime() {

        return AvailableTime;
    }

    public void setAvailableTime(ArrayList<Available_time_Attributes> availableTime) {
        AvailableTime = availableTime;
    }


    public ArrayList<Pet_Details> getPetList() {
        return PetList;
    }

    public void setPetList(ArrayList<Pet_Details> petList) {
        PetList = petList;
    }

    public User_data() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public long getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        PhoneNumber = phoneNumber;
    }



    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
