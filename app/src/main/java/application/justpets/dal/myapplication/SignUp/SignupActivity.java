package application.justpets.dal.myapplication.SignUp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import application.justpets.dal.myapplication.R;

public class SignupActivity extends AppCompatActivity {

    TextView testName;
    String Name = null;
    EditText Phone,Address;
    public FirebaseAuth fbauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        testName = (TextView)findViewById((R.id.testFBname));
        //Name = fbauth.getInstance().getCurrentUser().getDisplayName();
        //testName.setText(Name);


        Phone = (EditText)findViewById(R.id.EditPhone);
        Address = (EditText)findViewById(R.id.EditAddress);


        String Phonenumber = String.valueOf(Phone.getText());
        String Address1 = String.valueOf(Address.getText());


    }
}