package application.justpets.dal.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import application.justpets.dal.myapplication.Dog_walker_details.Available_time_Attributes;
import application.justpets.dal.myapplication.Dog_walker_details.Walker_Attributes;

public class BookingActivity extends AppCompatActivity {

    Walker_Attributes selectedWalker;
    Button custmap,walkermap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        custmap = (Button)findViewById(R.id.UserMAp);
        walkermap = (Button)findViewById(R.id.WalkerMap);

        selectedWalker = (Walker_Attributes) extras.getSerializable("Walker");
        selectedWalker.setAvailableTime((ArrayList<Available_time_Attributes>)extras.getSerializable("Timelist"));

        if(selectedWalker.getCategory().equalsIgnoreCase("User"))
        {
            custmap.setVisibility(View.GONE);
            walkermap.setVisibility(View.VISIBLE);
        }
        else if(selectedWalker.getCategory().equalsIgnoreCase("Dog Walker"))
        {
            walkermap.setVisibility(View.GONE);
            custmap.setVisibility(View.VISIBLE);
        }
        custmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookingActivity.this,CustomerMapActivity.class);
                startActivity(i);
            }
        });
        walkermap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookingActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });
    }
}
