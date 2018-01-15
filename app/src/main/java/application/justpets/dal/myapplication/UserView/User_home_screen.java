package application.justpets.dal.myapplication.UserView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

import application.justpets.dal.myapplication.BookingActivity;
import application.justpets.dal.myapplication.Dog_walker_details.Available_time_Attributes;
import application.justpets.dal.myapplication.Helper.AboutUs;
import application.justpets.dal.myapplication.Helper.BecomeWalker;
import application.justpets.dal.myapplication.Helper.ContactUs;
import application.justpets.dal.myapplication.R;
import application.justpets.dal.myapplication.ServiceSearch;
import application.justpets.dal.myapplication.Shopping_cart.ShoppingCartActivity;
import application.justpets.dal.myapplication.SwipeActivity;
import application.justpets.dal.myapplication.User_Details.Pet_Details;
import application.justpets.dal.myapplication.User_Details.User_data;
import application.justpets.dal.myapplication.WalkerView.ScheduleSetfor_Walker;
import application.justpets.dal.myapplication.Walker_Search;

public class User_home_screen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog mPrgsDialog;
    User_data userLoggedin;
    EditText pettype,petname,petdescription;
    TextView Petheading;
    Pet_Details pet_details;
    ArrayList<Pet_Details> pet_details_list ;
    private static final int GALLERY_INTENT=2;
    private static final int CAMERA_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userLoggedin = (User_data) extras.getSerializable("UserDetails");
        userLoggedin.setPetList((ArrayList<Pet_Details>) extras.getSerializable("petlist"));
        userLoggedin.setAvailableTime((ArrayList<Available_time_Attributes>) extras.getSerializable("Timelist"));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView Name = (TextView)findViewById(R.id.nametextView);
        Name.setText(userLoggedin.getName());
        ImageView imageView = (ImageView) findViewById(R.id.photo);
        ImageView imageView2 = (ImageView) findViewById(R.id.pets);
        ImageView Addpets = (ImageView) findViewById(R.id.addpets);
        TextView Walkers = (TextView)findViewById(R.id.bookings);
        TextView Schedule = (TextView)findViewById(R.id.walker);
        ImageView pets = (ImageView)findViewById(R.id.pets);
        TextView shop = (TextView)findViewById(R.id.shop);
        TextView services = (TextView)findViewById(R.id.services);
        //TextView menuname = (TextView)findViewById(R.id.UserName);
        //TextView menuId = (TextView)findViewById(R.id.UserEmailID);
        //menuname.setText(userLoggedin.getName());
        //menuId.setText(userLoggedin.getEmailid());

        //Button MenuSchedule = (Button)findViewById();

        if(userLoggedin.getCategory().equalsIgnoreCase("User"))
        {
            Schedule.setVisibility(View.GONE);
            //NavigationView nav = (NavigationView)findViewById(R.id.nav_view);
            MenuItem schedule = (MenuItem)navigationView.getMenu().findItem(R.id.nav_schedule);
            schedule.setVisible(false);
        }
        else if(userLoggedin.getCategory().equalsIgnoreCase("Dog Walker"))
        {
            Walkers.setVisibility(View.GONE);

        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.aniket);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(User_home_screen.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.cameraoptions,null);
                dialogbuilder.setView(dialogView);
                Button Camera= (Button)dialogView.findViewById(R.id.camera);
                Button Gallery  = (Button)dialogView.findViewById(R.id.gallery);

                final AlertDialog alertDialog = dialogbuilder.create();
                alertDialog.show();

                Camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                });
                Gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("JustPetsProject/*");
                        startActivityForResult(intent,GALLERY_INTENT );
                    }
                });
            }
        });


        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.dog);
        RoundedBitmapDrawable roundedBitmapDrawable2 = RoundedBitmapDrawableFactory.create(getResources(),bitmap2);
        roundedBitmapDrawable2.setCircular(true);
        imageView2.setImageDrawable(roundedBitmapDrawable2);

        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.add);
        RoundedBitmapDrawable roundedBitmapDrawable3 = RoundedBitmapDrawableFactory.create(getResources(),bitmap3);
        roundedBitmapDrawable3.setCircular(true);
        Addpets.setImageDrawable(roundedBitmapDrawable3);
        Addpets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddpetDalog(userLoggedin.getUid(),userLoggedin.getName());

            }
        });
        Walkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_home_screen.this, Walker_Search.class);
                Bundle extra = new Bundle();
                extra.putSerializable("UserDetails", (Serializable) userLoggedin);
                extra.putSerializable("petlist",(Serializable) userLoggedin.getPetList());
                i.putExtras(extra);
                startActivity(i);
            }
        });
        pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putSerializable("UserDetails", (Serializable) userLoggedin);
                extras.putSerializable("petlist",(Serializable) userLoggedin.getPetList());
                Intent intent = new Intent(getApplicationContext(),PetListView.class);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
        Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_home_screen.this, ScheduleSetfor_Walker.class);
                Bundle extra = new Bundle();
                extra.putSerializable("UserDetails", (Serializable) userLoggedin);
                extra.putSerializable("petlist",(Serializable) userLoggedin.getPetList());
                //Toast.makeText(getApplicationContext(),userLoggedin.getAvailableTime().get(0).getDate(),Toast.LENGTH_SHORT).show();
                extra.putSerializable("TimeList",(Serializable) userLoggedin.getAvailableTime());
                i.putExtras(extra);
                startActivity(i);

            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_home_screen.this, ServiceSearch.class);
                Bundle extra = new Bundle();
                extra.putSerializable("UserDetails", (Serializable) userLoggedin);
                extra.putSerializable("petlist",(Serializable) userLoggedin.getPetList());
                i.putExtras(extra);
                startActivity(i);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_home_screen.this, ShoppingCartActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AuthUI.getInstance().signOut(User_home_screen.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("AUTH","User logged out");
                    finish();
                    Intent intent1 = new Intent(User_home_screen.this,SwipeActivity.class);
                    startActivity(intent1);
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
        } else if (id == R.id.nav_profile) {
            Bundle extras = new Bundle();
            extras.putSerializable("UserDetails", (Serializable) userLoggedin);
            extras.putSerializable("petlist",(Serializable) userLoggedin.getPetList());
            Intent intent = new Intent(getApplicationContext(),User_profile.class);
            intent.putExtras(extras);
            startActivity(intent);

        } else if (id == R.id.nav_pets) {
            Bundle extras = new Bundle();
            extras.putSerializable("UserDetails", (Serializable) userLoggedin);
            extras.putSerializable("petlist",(Serializable) userLoggedin.getPetList());
            Intent intent = new Intent(getApplicationContext(),PetListView.class);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (id == R.id.nav_schedule) {
           // Intent intent = new Intent(getApplicationContext(),ScheduleActivity.class);
            //startActivity(intent);

        } else if (id == R.id.nav_past) {
            Bundle extras = new Bundle();
            extras.putSerializable("Walker", (Serializable) userLoggedin);
            //extras.putSerializable("petlist",(Serializable) userLoggedin.getPetList());
            //extras.putSerializable("TimeList",(Serializable)userLoggedin.getAvailableTime());
            Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
            intent.putExtras(extras);
            startActivity(intent);


        } else if (id == R.id.nav_settings) {

        }else if (id == R.id.nav_about) {
            Intent homeIntent = new Intent(User_home_screen.this, AboutUs.class);
            startActivity(homeIntent);

        }else if (id == R.id.nav_contact) {
            Intent homeIntent = new Intent(User_home_screen.this, ContactUs.class);
            startActivity(homeIntent);

        }else if (id == R.id.nav_becomeWalker) {

            Intent homeIntent = new Intent(User_home_screen.this, BecomeWalker.class);
            startActivity(homeIntent);

        }
        else if (id == R.id.nav_logout) {
            AuthUI.getInstance().signOut(User_home_screen.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("AUTH","User logged out");
                    finish();
                    Intent intent1 = new Intent(User_home_screen.this,SwipeActivity.class);
                    startActivity(intent1);
                }
            });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        setContentView(R.layout.activity_user_home_screen);
//        final ImageView photo = (ImageView)findViewById(R.id.photo);
//        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
//            mPrgsDialog = new ProgressDialog(this);
//            mPrgsDialog.setMessage("Uploading......");
//            mPrgsDialog.show();
//            Uri uri = data.getData();
//
//            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("Photos").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(User_home_screen.this, "Upload Done", Toast.LENGTH_LONG).show();
//
//
//                    Uri downloadUri = taskSnapshot.getDownloadUrl();
//
//                    Picasso.with(User_home_screen.this).load(downloadUri).fit().centerCrop().rotate(90).into(photo);
//
//                    mPrgsDialog.dismiss();
//                }
//            });
//        }
//        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
//            //mPrgsDialog.setMessage("Uploading......");
//            //mPrgsDialog.show();
//            Uri uri = data.getData();
//            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("Photos").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    //mPrgsDialog.dismiss();
//
//                    Uri downloadUri = taskSnapshot.getDownloadUrl();
//
//                    photo.setImageURI(downloadUri);
//                    //Picasso.with(User_home_screen.this).load(downloadUri).fit().centerCrop().rotate(90).into(photo);
//                    Toast.makeText(User_home_screen.this, "Upload Finished", Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//    private void verifyPermissions(){
//        //Log.d(TAG, "verifyPermissions: asking user for permissions");
//        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA};
//
//        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                permissions[0]) == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                permissions[1]) == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                permissions[2]) == PackageManager.PERMISSION_GRANTED){
//            //setupViewPager();
//
//        }else{
//            ActivityCompat.requestPermissions(User_home_screen.this,permissions,REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        verifyPermissions();
//    }

}
