package application.justpets.dal.myapplication.UserView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import application.justpets.dal.myapplication.R;
import application.justpets.dal.myapplication.User_Details.Pet_Details;
import application.justpets.dal.myapplication.User_Details.User_data;

public class User_profile extends AppCompatActivity {

    User_data userLoggedin;
    TextView name,number,address,username;
    EditText EditPhone,EditAddress;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userLoggedin = (User_data) extras.getSerializable("UserDetails");
        userLoggedin.setPetList((ArrayList<Pet_Details>)extras.getSerializable("petlist"));
        setContentView(R.layout.activity_user_profile);
        name = (TextView)findViewById(R.id.UserNameDisplay);
        number = (TextView)findViewById(R.id.UserPhoneNumberdisplay);
        address = (TextView)findViewById(R.id.UserAddressDisplay);
        update = (Button)findViewById(R.id.UpdateUser);
        name.setText(userLoggedin.getName());
        number.setText(String.valueOf(userLoggedin.getPhoneNumber()));
        address.setText(userLoggedin.getAddress());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(User_profile.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.user_details_update_dialog,null);
                dialogbuilder.setView(dialogView);

                username = (TextView)dialogView.findViewById(R.id.textView5);
                EditPhone = (EditText)dialogView.findViewById(R.id.EditPhone);
                EditAddress = (EditText)dialogView.findViewById(R.id.EditAddress);
                username.setText((CharSequence) userLoggedin.getName());
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
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userLoggedin.getUid());
                            userLoggedin.setPhoneNumber(Long.parseLong(updatedphone));
                            userLoggedin.setAddress(updateAddress);
                            databaseReference.setValue(userLoggedin);
                            alertDialog.dismiss();
                        }

                    }
                });

            }
        });
    }
}
