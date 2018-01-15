package application.justpets.dal.myapplication.Service_Details;

import java.util.ArrayList;

/**
 * Created by Aniket on 15-11-2017.
 */

public class Service_provider_data {
    private String Name;
    private String Emailid;
    private String Phonenumber;
    private String Address;
    private String Category;
    private ArrayList<Server_booking> BookingList;

    public ArrayList<Server_booking> getBookingList() {
        return BookingList;
    }

    public void setBookingList(ArrayList<Server_booking> bookingList) {
        BookingList = bookingList;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Service_provider_data() {

    }

    public Service_provider_data(String name, String emailid, String phonenumber, String address, String category) {

        Name = name;
        Emailid = emailid;
        Phonenumber = phonenumber;
        Address = address;
        Category = category;
    }
}
