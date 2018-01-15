package application.justpets.dal.myapplication.UserView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import application.justpets.dal.myapplication.Helper.AdapterPets;
import application.justpets.dal.myapplication.R;
import application.justpets.dal.myapplication.User_Details.Pet_Details;
import application.justpets.dal.myapplication.User_Details.User_data;

public class PetListView extends AppCompatActivity {

    ListView Pet_List;
    User_data userLoggedin;
    TextView petname,pettype,petdescription;
    EditText editname,edittype,editdesc;
    Button update,dialogupdate;
    int petposition;
    ArrayList<Pet_Details> userpetlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list_view);
        petname = (TextView)findViewById(R.id.PetName);
        pettype = (TextView)findViewById(R.id.PetType);
        update = (Button)findViewById(R.id.update);
        petdescription = (TextView)findViewById(R.id.PetDescription);
        petname.setVisibility(View.GONE);
        pettype.setVisibility(View.GONE);
        petdescription.setVisibility(View.GONE);
        update.setVisibility(View.GONE);
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userLoggedin = (User_data) extras.getSerializable("UserDetails");
        userLoggedin.setPetList((ArrayList<Pet_Details>)extras.getSerializable("petlist"));
        Pet_List = (ListView)findViewById(R.id.listView);
        if(userLoggedin.getPetList()!=null)
        {
            AdapterPets adapter = new AdapterPets(PetListView.this, 0, userLoggedin.getPetList());

            Pet_List.setAdapter(adapter);
        }
        Pet_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                petname.setText(userLoggedin.getPetList().get(position).getPet_name());
                pettype.setText(userLoggedin.getPetList().get(position).getPet_Type());
                petdescription.setText(userLoggedin.getPetList().get(position).getPet_Description());
                userpetlist = userLoggedin.getPetList();
                petname.setVisibility(View.VISIBLE);
                pettype.setVisibility(View.VISIBLE);
                petdescription.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                petposition  = position;
                //Toast.makeText(getApplicationContext(),userLoggedin.getPetList().get(position).getPet_name(),Toast.LENGTH_LONG).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(PetListView.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.pet_update_dialog,null);
                dialogbuilder.setView(dialogView);
                editname = (EditText)dialogView.findViewById(R.id.editname);
                edittype = (EditText)dialogView.findViewById(R.id.editType);
                editdesc = (EditText)dialogView.findViewById(R.id.editdescription);
                dialogupdate = (Button)dialogView.findViewById(R.id.updatepets);
                final AlertDialog alertDialog = dialogbuilder.create();
                alertDialog.show();
                dialogupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Type = edittype.getText().toString();
                        String name = editname.getText().toString();
                        String description = editdesc.getText().toString();
                        if(TextUtils.isEmpty(Type)&&TextUtils.isEmpty(name))
                        {
                            Toast.makeText(getApplicationContext(),"Type and Name Requird",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            userpetlist.get(petposition).setPet_name(name);
                            userpetlist.get(petposition).setPet_Type(Type);
                            userpetlist.get(petposition).setPet_Description(description);

                        }
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userLoggedin.getUid());
                        userLoggedin.setPetList(userpetlist);
                        databaseReference.setValue(userLoggedin);

                        alertDialog.dismiss();
                        Intent intent1 = new Intent(getApplicationContext(),PetListView.class);
                        startActivity(intent);
                    }
                });

            }

        });
        /*Bundle extras1 = new Bundle();
        extras1.putSerializable("UserDetails", (Serializable) userLoggedin);
        extras1.putSerializable("petlist",(Serializable) userLoggedin.getPetList());
        Intent intent1 = new Intent(getApplicationContext(),PetListView.class);
        intent1.putExtras(extras1);
        startActivity(intent1);*/


    }
}
