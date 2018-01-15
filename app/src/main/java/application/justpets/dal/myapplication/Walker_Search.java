package application.justpets.dal.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import application.justpets.dal.myapplication.Dog_walker_details.Available_time_Attributes;
import application.justpets.dal.myapplication.Dog_walker_details.Walker_Attributes;
import application.justpets.dal.myapplication.Helper.AdapterWalker;
import application.justpets.dal.myapplication.User_Details.Pet_Details;
import application.justpets.dal.myapplication.User_Details.User_data;
import application.justpets.dal.myapplication.WalkerView.WalkerProfile_Activity;

public class Walker_Search extends AppCompatActivity {

    ArrayList<Walker_Attributes> walker_list = new ArrayList<Walker_Attributes>();
    ArrayList<Available_time_Attributes> samplelist = new ArrayList<Available_time_Attributes>();
    ArrayList<Walker_Attributes> sampledatematch = new ArrayList<Walker_Attributes>();
    User_data samplesearch;
    Spinner dropdown;
    ListView walkerlist;
    Button srt_rting;
    User_data userLoggedin;
    ArrayList<String> petlist = new ArrayList<String>();
    int count = 0;
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    TextView Time_selected;
    String time = null;
    String date = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walker__search);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userLoggedin = (User_data) extras.getSerializable("UserDetails");
        userLoggedin.setPetList((ArrayList<Pet_Details>) extras.getSerializable("petlist"));
        walkerlist = (ListView)findViewById(R.id.Walkerlist);
        srt_rting = (Button)findViewById(R.id.Sort);
        petlistcreator();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot usersnapshot : dataSnapshot.getChildren())
                {
                    samplesearch = usersnapshot.getValue(User_data.class);
                    count++;
                    if(samplesearch.getCategory().toString().equalsIgnoreCase("Dog Walker"))
                    {
                        Walker_Attributes samplewalker = new Walker_Attributes(samplesearch.getCategory(),
                                                                                samplesearch.getPrice(),
                                                                                samplesearch.getRating(),
                                                                                samplesearch.getAddress(),
                                                                                samplesearch.getEmailid(),
                                                                                samplesearch.getName(),
                                                                                samplesearch.getPhoneNumber(),
                                                                                samplesearch.getUid(),samplesearch.getAvailableTime());
                        Toast.makeText(getApplicationContext(),String.valueOf(samplesearch.getPrice()),Toast.LENGTH_SHORT).show();
                        walker_list.add(samplewalker);
                    }
                }
                //Toast.makeText(getApplicationContext(),String.valueOf(count),Toast.LENGTH_SHORT).show();
                //Log.d("count",String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(walker_list!=null)
        {
            //Toast.makeText(getApplicationContext(),"list not null",Toast.LENGTH_SHORT).show();
            walkerlist = (ListView)findViewById(R.id.Walkerlist);
            AdapterWalker adapterWalker = new AdapterWalker(Walker_Search.this,0,walker_list);
            walkerlist.setAdapter(adapterWalker);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"No walkers available",Toast.LENGTH_SHORT).show();
        }
        walkerdatetimepicker();
        //datefilter();
        AdapterWalker adapterWalker = new AdapterWalker(Walker_Search.this,0,walker_list);
        walkerlist.setAdapter(adapterWalker);

        srt_rting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walker_list = Sortbyrating(walker_list);
                AdapterWalker adapterWalker = new AdapterWalker(Walker_Search.this,0,walker_list);
                walkerlist.setAdapter(adapterWalker);
            }
        });
        walkerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Walker_Attributes selectedwalker = new Walker_Attributes(walker_list.get(position).getCategory(),
                        walker_list.get(position).getPrice(),walker_list.get(position).getRating(),
                        walker_list.get(position).getAddress(),walker_list.get(position).getEmailid(),
                        walker_list.get(position).getName(),walker_list.get(position).getPhonenumber(),
                        walker_list.get(position).getUid(),walker_list.get(position).getAvailableTime());
                Intent intent2 = new Intent(Walker_Search.this, WalkerProfile_Activity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("Walker",(Serializable)selectedwalker);
                extras.putSerializable("walkertimelist",(Serializable)selectedwalker.getAvailableTime());
                intent2.putExtras(extras);
                startActivity(intent2);


            }
        });

    }
    public void walkerdatetimepicker()
    {

        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.activity_date_time_picker,null);
        dialogbuilder.setView(dialogView);


        ImageView btn_date,btn_time;
        Button btn_nxt;
        final TextView datedisplay,timedisplay;

        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();
        //Time_selected = (TextView)dialogView.findViewById(R.id.txt_TextDateTime);
        dropdown = (Spinner)dialogView.findViewById(R.id.spinner1);
        btn_date = (ImageView) dialogView.findViewById(R.id.date_selector);
        btn_time = (ImageView) dialogView.findViewById(R.id.time_selector);
        btn_nxt = (Button)dialogView.findViewById(R.id.button);
        datedisplay  = (TextView)dialogView.findViewById(R.id.UserEmailID);
        timedisplay = (TextView)dialogView.findViewById(R.id.timeselector);
        if(petlist==null)
        {
            dropdown.setVisibility(View.GONE);
        }
        else{
            dropdown.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, petlist);
        dropdown.setAdapter(adapter);}
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Walker_Search.this,d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
            }
            DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1)+"-"+ String.valueOf(dayOfMonth);
                    datedisplay.setText(date);
                }
            };

        });
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Walker_Search.this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
            }
                    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time = String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                    timedisplay.setText(time);
                }
            };

        });
        btn_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    public ArrayList<Walker_Attributes> Sortbyrating(ArrayList<Walker_Attributes> basicwalkerlist)
    {
        ArrayList<Walker_Attributes> sample = new ArrayList<Walker_Attributes>() ;
        for (int i=0; i< basicwalkerlist.size() ;i++)
        {
            if(basicwalkerlist.get(i).getRating() == 5)
            {
                sample.add(basicwalkerlist.get(i));
            }
        }
        for (int i=0; i< basicwalkerlist.size() ;i++)
        {
            if(basicwalkerlist.get(i).getRating() == 4)
            {
                sample.add(basicwalkerlist.get(i));
            }
        }
        for (int i=0; i< basicwalkerlist.size() ;i++)
        {
            if(basicwalkerlist.get(i).getRating() == 3)
            {
                sample.add(basicwalkerlist.get(i));
            }
        }
        for (int i=0; i< basicwalkerlist.size() ;i++)
        {
            if(basicwalkerlist.get(i).getRating() == 2)
            {
                sample.add(basicwalkerlist.get(i));
            }
        }
        for (int i=0; i< basicwalkerlist.size() ;i++)
        {
            if(basicwalkerlist.get(i).getRating() == 2)
            {
                sample.add(basicwalkerlist.get(i));
            }
        }
        walker_list = sample;
        return walker_list;
    }
    public void petlistcreator()
    {
        for (int i=0; i<userLoggedin.getPetList().size();i++)
        {
            petlist.add(userLoggedin.getPetList().get(i).getPet_name());
        }
    }
}
