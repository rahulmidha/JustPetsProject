package application.justpets.dal.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import application.justpets.dal.myapplication.Dog_walker_details.Available_time_Attributes;
import application.justpets.dal.myapplication.UserView.User_home_screen;
import application.justpets.dal.myapplication.User_Details.Pet_Details;
import application.justpets.dal.myapplication.User_Details.User_data;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;
    public FirebaseAuth fbauth;

    String Name = null,Email  = null,UID = null,Address1,Phonenumber;
    TextView name,email,uid;
    ImageView photo;
    User_data sample,sample1;
    TextView testName;
    EditText Phone,Address;
    Button Signup,Login;
    DatabaseReference databaseUsers;
    Integer count = 0;
    ArrayList<Pet_Details> sampleList = new ArrayList<Pet_Details>();
    ArrayList<Available_time_Attributes> walkertimelist = new ArrayList<Available_time_Attributes>();


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);


        if (fbauth.getInstance().getCurrentUser() != null) {
            // user signed in
            databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
            databaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot usersnapshot : dataSnapshot.getChildren()) {
                        sample = usersnapshot.getValue(User_data.class);

                        if(sample.getEmailid().toString()
                                .equalsIgnoreCase(fbauth.getInstance().getCurrentUser().getEmail().toString()))
                        {

                            User_data sampleuser = new User_data(sample.getCategory(),sample.getPrice(),sample.getBookinglist(),sample.getRating(),sample.getName(),sample.getEmailid(),sample.getUid(),sample.getAddress(),sample.getPhoneNumber(),sample.getPetList(),sample.getAvailableTime());
                            sampleList = sampleuser.getPetList();
                            walkertimelist = sampleuser.getAvailableTime();
                            Intent intent = new Intent(LoginActivity.this,User_home_screen.class);
                            Bundle extras = new Bundle();
                            extras.putSerializable("UserDetails", (Serializable) sampleuser);
                            extras.putSerializable("petlist",(Serializable) sampleList);
                            extras.putSerializable("Timelist",(Serializable) walkertimelist);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(AuthUI.FACEBOOK_PROVIDER, AuthUI.EMAIL_PROVIDER,AuthUI.GOOGLE_PROVIDER)
                    .setTheme(R.style.LoginTheme).setLogo(R.drawable.logo1)
                    .build(), RC_SIGN_IN);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setContentView(R.layout.activity_login);
        Phone = (EditText)findViewById(R.id.EditPhone);
        Address = (EditText)findViewById(R.id.EditAddress);
        name = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        Login = (Button)findViewById(R.id.LoginButton);
        Signup = (Button)findViewById(R.id.SignUp);
        photo = (ImageButton) findViewById(R.id.photo);
        if(requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK && resultCode != RESULT_CANCELED) {
                //user logged in
                Log.d("AUTH",fbauth.getInstance().getCurrentUser().getEmail());
                name.setText(fbauth.getInstance().getCurrentUser().getDisplayName());
                email.setText(fbauth.getInstance().getCurrentUser().getEmail());

                String facebookUserId = "";
                FirebaseUser user = fbauth.getInstance().getCurrentUser();
                for(UserInfo profile : user.getProviderData()) {
                    if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                        facebookUserId = profile.getUid();
                    }
                }

                String photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";
                Picasso.with(this).load(photoUrl).into(photo);


               // String url = fbauth.getInstance().getCurrentUser().getPhotoUrl().toString();
                //Picasso.with(getApplicationContext()).load(url).into(photo);
                //photo.setImageURI(fbauth.getInstance().getCurrentUser().getPhotoUrl());
               // uid.setText(fbauth.getInstance().getCurrentUser().getUid());
                databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
                databaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot UserSnapshot: dataSnapshot.getChildren())
                        {
                            sample1 = UserSnapshot.getValue(User_data.class);
                            String name = sample1.getEmailid();

                            if(sample1.getEmailid().toString()
                                    .equalsIgnoreCase(fbauth.getInstance().getCurrentUser().getEmail())
                                    )
                            {
                                Address.setVisibility(View.GONE);
                                Phone.setVisibility(View.GONE);
                                Signup.setVisibility(View.GONE);
                                Login.setVisibility(View.VISIBLE);
                                break;
                            }
                            else
                            {
                                Address.setVisibility(View.VISIBLE);
                                Phone.setVisibility(View.VISIBLE);
                                Signup.setVisibility(View.VISIBLE);
                                Login.setVisibility(View.GONE);

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sampleList = sample1.getPetList();
                        walkertimelist = sample1.getAvailableTime();
                        Intent intent = new Intent(LoginActivity.this,User_home_screen.class);
                        Bundle extras = new Bundle();
                        extras.putSerializable("UserDetails", (Serializable) sample1);
                        extras.putSerializable("petlist",(Serializable) sampleList);

                       extras.putSerializable("Timelist",(Serializable)walkertimelist);
                        //extras.putSerializable("ProfilePhoto",(Serializable) photo);
                        intent.putExtras(extras);

                        startActivity(intent);

                    }
                });

                Signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Phonenumber = Phone.getText().toString();
                        Address1 = Address.getText().toString();
                        String id = databaseUsers.push().getKey();
                        final User_data newUser = new User_data("User","x",null,0,fbauth.getInstance().getCurrentUser().getDisplayName(),
                                fbauth.getInstance().getCurrentUser().getEmail(),
                                id,
                                Address1,Long.parseLong(Phonenumber),null,null);

                        databaseUsers.child(id).setValue(newUser);
                    }
                });
            }
            else {
                Log.d("AUTH","User not authenticated");
            }
        }
    }


}
