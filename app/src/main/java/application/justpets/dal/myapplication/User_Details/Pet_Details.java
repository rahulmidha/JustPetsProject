package application.justpets.dal.myapplication.User_Details;

import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Aniket on 15-11-2017.
 */

public class Pet_Details implements Serializable {
    private String Pet_Type;
    private String Pet_name;
    private String Pet_Description;

    public String getPet_Type() {
        return Pet_Type;
    }

    public void setPet_Type(String pet_Type) {
        Pet_Type = pet_Type;
    }
    public String getPet_name() {
        return Pet_name;
    }

    public void setPet_name(String pet_name) {
        Pet_name = pet_name;
    }

    public String getPet_Description() {
        return Pet_Description;
    }

    public void setPet_Description(String pet_Description) {
        Pet_Description = pet_Description;
    }

    public Pet_Details() {
    }

    public Pet_Details(String pet_Type, String pet_name, String Description) {
        Log.d("s",Description);
        Pet_Type = pet_Type;
        Pet_name = pet_name;
        Pet_Description = Description;
    }
}
