package application.justpets.dal.myapplication;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static String latitude;
    public static String longitude;
    Marker marker;

    String walker;

    public CustomerMapActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;



        FirebaseDatabase database= FirebaseDatabase.getInstance();

        DatabaseReference walkername= database.getReference("Bookingtable/Aniket");
        walkername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                walker=String.valueOf(dataSnapshot.getValue());

                Log.d("tester","walker name is:"+walker);

                FirebaseDatabase data= FirebaseDatabase.getInstance();
                DatabaseReference myRef= data.getReference(walker+"/Location");
                LatLng loc=new LatLng(0.0,0.0);
                marker=googleMap.addMarker(new MarkerOptions().position(loc));
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        longitude= String.valueOf(dataSnapshot.child("Longitude").getValue());
                        Log.d("tester","Longitude is: "+ longitude);
                        latitude = String.valueOf(dataSnapshot.child("Latitude").getValue());
                        Log.d("tester","Longitude is: "+ latitude);

                        LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                        //marker=googleMap.addMarker(new MarkerOptions().position(latLng));
                        marker.setPosition(latLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });










        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

    }
}
