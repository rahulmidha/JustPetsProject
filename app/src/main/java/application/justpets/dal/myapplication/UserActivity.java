package application.justpets.dal.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import application.justpets.dal.myapplication.Helper.AdapterPets;
import application.justpets.dal.myapplication.User_Details.Pet_Details;
import application.justpets.dal.myapplication.User_Details.User_data;

public class UserActivity extends AppCompatActivity {

    User_data userLoggedin,sample;
    Pet_Details pet_details;
    ArrayList<Pet_Details> pet_details_list,userPet_list;
    TextView name,emailid,address,phone,username,Petheading;
    Button Logout,Update,Addapets,addpetsdialog;
    EditText EditPhone,EditAddress,pettype,petname,petdescription;
    ListView petlist;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = (TextView)findViewById(R.id.UserEmailID);
        emailid = (TextView)findViewById(R.id.timeselector);
        address = (TextView)findViewById(R.id.textView3);
        phone = (TextView)findViewById(R.id.textView4);
        petlist = (ListView)findViewById(R.id.Petlist);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userLoggedin = (User_data) extras.getSerializable("UserDetails");
        userLoggedin.setPetList((ArrayList<Pet_Details>)extras.getSerializable("petlist"));
        Toast.makeText(getApplicationContext(),userLoggedin.getCategory(),Toast.LENGTH_SHORT).show();
        name.setText(userLoggedin.getName());
        emailid.setText(userLoggedin.getEmailid());
        address.setText(userLoggedin.getAddress());
        phone.setText(String.valueOf(userLoggedin.getPhoneNumber()));
        if(userLoggedin.getPetList()!=null) {
            AdapterPets adapter = new AdapterPets(UserActivity.this, 0, userLoggedin.getPetList());

            petlist.setAdapter(adapter);

        }
        Logout = (Button)findViewById(R.id.Logout2);
        Update = (Button)findViewById(R.id.Update);
        Addapets = (Button)findViewById(R.id.PetsAdd);
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot usersnapshot : dataSnapshot.getChildren()) {
                    sample = usersnapshot.getValue(User_data.class);

                    if(sample.getEmailid().toString().equalsIgnoreCase(userLoggedin.getEmailid().toString())
                            )
                    {
                        address.setText(String.valueOf(sample.getAddress()));
                        phone.setText(String.valueOf(sample.getPhoneNumber()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.Logout2){
                    AuthUI.getInstance().signOut(UserActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("AUTH","User logged out");
                            finish();
                            Intent intent1 = new Intent(UserActivity.this,SwipeActivity.class);
                            startActivity(intent1);
                        }
                    });

                }
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdatedatadialog(userLoggedin.getUid(),userLoggedin.getName());

            }
        });
        Addapets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddpetDalog(userLoggedin.getUid(),userLoggedin.getName());
            }
        });
        petlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),userLoggedin.getPetList().get(position).getPet_name(),Toast.LENGTH_LONG).show();

            }
        });


    }

    public void showUpdatedatadialog(final String id, String UserName)
    {
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.user_details_update_dialog,null);
        dialogbuilder.setView(dialogView);

        username = (TextView)dialogView.findViewById(R.id.textView5);
        EditPhone = (EditText)dialogView.findViewById(R.id.EditPhone);
        EditAddress = (EditText)dialogView.findViewById(R.id.EditAddress);
        username.setText(UserName);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();

        dialogView.findViewById(R.id.UpdateData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String updatedphone = EditPhone.getText().toString();
                final String updateAddress = EditAddress.getText().toString();
                if(TextUtils.isEmpty(updatedphone)&&TextUtils.isEmpty(updateAddress))
                {
                    Toast.makeText(getApplicationContext(),"Both Fields Requird",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
                    userLoggedin.setPhoneNumber(Long.parseLong(updatedphone));
                    userLoggedin.setAddress(updateAddress);
                    databaseReference.setValue(userLoggedin);
                    alertDialog.dismiss();
                }

            }
        });

        dialogbuilder.setTitle("Updating Artist "+ UserName);

    }

    public void AddpetDalog(final String id, String Username)
    {
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.add_pets_dialog,null);
        dialogbuilder.setView(dialogView);

        Petheading = (TextView)dialogView.findViewById(R.id.PetHeading);
        pettype = (EditText)dialogView.findViewById(R.id.PetType);
        petname = (EditText)dialogView.findViewById(R.id.PetName);
        petdescription = (EditText)dialogView.findViewById(R.id.PetDescription);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();
        dialogView.findViewById(R.id.Add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Type = pettype.getText().toString();
                String name = petname.getText().toString();
                String description = petdescription.getText().toString();
                Toast.makeText(getApplicationContext(),description,Toast.LENGTH_LONG).show();
                if(TextUtils.isEmpty(Type)&&TextUtils.isEmpty(name))
                {
                    Toast.makeText(getApplicationContext(),"Type and Name Requird",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if(userLoggedin.getPetList()!=null)
                    {
                        pet_details_list = userLoggedin.getPetList();
                        pet_details = new Pet_Details();
                        pet_details.setPet_Description(description);
                        pet_details.setPet_name(name);
                        pet_details.setPet_Type(Type);
                        pet_details_list.add(pet_details);
                    }
                    else
                    {
                        pet_details_list = new ArrayList<Pet_Details>();
                        pet_details = new Pet_Details();
                        pet_details.setPet_Description(description);
                        pet_details.setPet_name(name);
                        pet_details.setPet_Type(Type);
                        pet_details_list.add(pet_details);
                    }

                    Toast.makeText(getApplicationContext(),pet_details_list.get(0).getPet_name(),Toast.LENGTH_LONG).show();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
                    userLoggedin.setPetList(pet_details_list);
                    databaseReference.setValue(userLoggedin);

                    alertDialog.dismiss();
                }


            }
        });
    }
    public void showlocationofwalker(View view)
    {
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
    }
}
