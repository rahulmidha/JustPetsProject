package application.justpets.dal.myapplication.WalkerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import application.justpets.dal.myapplication.Dog_walker_details.Available_time_Attributes;
import application.justpets.dal.myapplication.Helper.AvailableTimeAdapter;
import application.justpets.dal.myapplication.R;
import application.justpets.dal.myapplication.User_Details.Pet_Details;
import application.justpets.dal.myapplication.User_Details.User_data;

public class ScheduleSetfor_Walker extends AppCompatActivity {

    User_data userLoggedin;
    Calendar dateTime = Calendar.getInstance();
    String time = null;
    String date = null;
    ListView schedulelist;
    ArrayList<Available_time_Attributes> Walkertimelist = new ArrayList<Available_time_Attributes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_setfor__walker);
        Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        userLoggedin = (User_data) extras.getSerializable("UserDetails");
        userLoggedin.setAvailableTime((ArrayList<Available_time_Attributes>) extras.getSerializable("TimeList"));
        userLoggedin.setPetList((ArrayList<Pet_Details>) extras.getSerializable("petlist"));
        CalendarView scheduler = (CalendarView) findViewById(R.id.calendarView);
        schedulelist = (ListView) findViewById(R.id.TimeList);
        if(userLoggedin.getAvailableTime()!=null) {
            Toast.makeText(getApplicationContext(), userLoggedin.getAvailableTime().get(0).getDate(), Toast.LENGTH_SHORT).show();
            AvailableTimeAdapter adapter = new AvailableTimeAdapter(ScheduleSetfor_Walker.this, 0, userLoggedin.getAvailableTime());
            schedulelist.setAdapter(adapter);
        }
        scheduler.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = (year) + "-" + (month + 1) + "-" + String.valueOf(dayOfMonth);

                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
                new TimePickerDialog(ScheduleSetfor_Walker.this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
                Toast.makeText(getApplicationContext(),"Event added successfully",Toast.LENGTH_SHORT).show();

            }
        });
        schedulelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(ScheduleSetfor_Walker.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.timeoptionmenuonlist,null);
                dialogbuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogbuilder.create();
                alertDialog.show();
                Button delete;

                delete = (Button)dialogView.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userLoggedin.getAvailableTime().remove(position);
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userLoggedin.getUid());
                        databaseReference.setValue(userLoggedin);

                    }
                });
                alertDialog.dismiss();

            }
        });
    }
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            //datedisplay.setText(time);
            if(userLoggedin.getAvailableTime()==null)
            {
                Available_time_Attributes sample = new Available_time_Attributes(date,time);
                //Walkertimelist=userLoggedin.getAvailableTime();
                Walkertimelist.add(sample);
                userLoggedin.setAvailableTime(Walkertimelist);
                Toast.makeText(getApplicationContext(),userLoggedin.getAvailableTime().get(0).getDate(),Toast.LENGTH_SHORT).show();
                User_data Usersample = new User_data(userLoggedin.getCategory(),
                        userLoggedin.getPrice(),
                        userLoggedin.getBookinglist(),
                        userLoggedin.getRating(),
                        userLoggedin.getName(),
                        userLoggedin.getEmailid(),
                        userLoggedin.getUid(),
                        userLoggedin.getAddress(),
                        userLoggedin.getPhoneNumber(),
                        userLoggedin.getPetList(),
                        userLoggedin.getAvailableTime());
                //userLoggedin=Usersample;
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userLoggedin.getUid());
                userLoggedin.setAvailableTime(Walkertimelist);
                databaseReference.setValue(userLoggedin);
//                            AvailableTimeAdapter adapter = new AvailableTimeAdapter(ScheduleSetfor_Walker.this, 0, userLoggedin.getAvailableTime());
//                            schedulelist.setAdapter(adapter);

            }
            else
            {
                Available_time_Attributes sample1 = new Available_time_Attributes(date,time);
                Walkertimelist.clear();
                Walkertimelist=userLoggedin.getAvailableTime();
                Walkertimelist.add(sample1);
                userLoggedin.setAvailableTime(Walkertimelist);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userLoggedin.getUid());
                userLoggedin.setAvailableTime(Walkertimelist);
                databaseReference.setValue(userLoggedin);
//                            AvailableTimeAdapter adapter = new AvailableTimeAdapter(ScheduleSetfor_Walker.this, 0, userLoggedin.getAvailableTime());
//                            schedulelist.setAdapter(adapter);
            }
        }
    };
}
