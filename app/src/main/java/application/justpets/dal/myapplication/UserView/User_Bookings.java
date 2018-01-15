package application.justpets.dal.myapplication.UserView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import application.justpets.dal.myapplication.Helper.Booking_Adapter;
import application.justpets.dal.myapplication.R;
import application.justpets.dal.myapplication.Service_Details.Server_booking;
import application.justpets.dal.myapplication.User_Details.User_data;

public class User_Bookings extends AppCompatActivity {
    //ArrayList<Server_booking> BookingList;
    User_data userLoggedin,sampleuser;
    ListView Bookinglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__bookings);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userLoggedin = (User_data) extras.getSerializable("UserDetails");
        userLoggedin.setBookinglist((ArrayList<Server_booking>) extras.getSerializable("pastbookings"));
        Toast.makeText(getApplicationContext(),userLoggedin.getName(), Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot usersnapshot:dataSnapshot.getChildren())
                {
                    sampleuser = usersnapshot.getValue(User_data.class);
                    if(sampleuser.getUid().equalsIgnoreCase(userLoggedin.getUid()))
                    {
                        Toast.makeText(getApplicationContext(),"User Matched",Toast.LENGTH_SHORT).show();
                        userLoggedin.setBookinglist(sampleuser.getBookinglist());
                        //Toast.makeText(getApplicationContext(),userLoggedin.getBookinglist().get(0).getname(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(userLoggedin.getBookinglist()!=null) {
            Bookinglist = (ListView) findViewById(R.id.BookingDisplay);
            Booking_Adapter adapterbooking = new Booking_Adapter(User_Bookings.this, 0, userLoggedin.getBookinglist());
            Bookinglist.setAdapter(adapterbooking);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No Bookings available",Toast.LENGTH_SHORT).show();
        }
    }
}
