package application.justpets.dal.myapplication.WalkerView;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import application.justpets.dal.myapplication.BookingActivity;
import application.justpets.dal.myapplication.Dog_walker_details.Available_time_Attributes;
import application.justpets.dal.myapplication.Dog_walker_details.Walker_Attributes;
import application.justpets.dal.myapplication.R;
import application.justpets.dal.myapplication.SwipeActivity;

public class WalkerProfile_Activity extends AppCompatActivity {

    Walker_Attributes selectedWalker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walker_profile_);
        TextView name,email,category,address,ratings;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        selectedWalker = (Walker_Attributes) extras.getSerializable("Walker");
        selectedWalker.setAvailableTime((ArrayList<Available_time_Attributes>)extras.getSerializable("walkertimelist"));
        name = (TextView)findViewById(R.id.user_profile_name);
        email = (TextView)findViewById(R.id.user_profile_short_bio);
        category = (TextView)findViewById(R.id.category);
        address = (TextView)findViewById(R.id.address);
        name.setText(selectedWalker.getName());
        email.setText(selectedWalker.getEmailid());
        category.setText(selectedWalker.getCategory());
        address.setText(selectedWalker.getAddress());
        ImageView rating= (ImageView)findViewById(R.id.ratings);
        Button book = (Button)findViewById(R.id.button2);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(WalkerProfile_Activity.this).create();
                //alertDialog.setTitle("Alert");
                alertDialog.setMessage("Your booking is successful. Thank you for availing our service.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(selectedWalker.getCategory().equalsIgnoreCase("Dog Walker")) {
                                    Intent intent = new Intent(WalkerProfile_Activity.this, BookingActivity.class);
                                    Bundle Extras = new Bundle();
                                    Extras.putSerializable("Walker", selectedWalker);
                                    Extras.putSerializable("TimeList", selectedWalker.getAvailableTime());
                                    intent.putExtras(Extras);


                                    PendingIntent pIntent = PendingIntent.getActivity(WalkerProfile_Activity.this, (int) System.currentTimeMillis(), intent, 0);

                                    Intent intent2 = new Intent(Intent.ACTION_DIAL);
                                    intent2.setData(Uri.parse("tel:"+String.valueOf(selectedWalker.getPhonenumber())));
                                    PendingIntent pIntent1 = PendingIntent.getActivity(WalkerProfile_Activity.this, (int) System.currentTimeMillis(), intent2, 0);

                                    Intent intent3 = new Intent(Intent.ACTION_VIEW);
                                    intent3.setData(Uri.parse("sms:"+String.valueOf(selectedWalker.getPhonenumber())));
                                    intent3.putExtra("sms_body", "Hi. I have a booking with you. When shall I expect your service? Thank you");
                                    PendingIntent pIntent2 = PendingIntent.getActivity(WalkerProfile_Activity.this, (int) System.currentTimeMillis(), intent3, 0);

                                    Notification noti = new Notification.Builder(WalkerProfile_Activity.this)
                                            .setContentTitle("Just Pets")
                                            .setContentText("Booking Successful - "+String.valueOf(selectedWalker.getCategory())+" service at specified time. ").setSmallIcon(R.drawable.icon)
                                            .setContentIntent(pIntent)
                                            .addAction(R.drawable.call, "Call", pIntent1)
                                            .addAction(R.drawable.icon, "Text", pIntent2)
                                            .addAction(R.drawable.alert, "View Booking", pIntent).build();
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    // hide the notification after its selected
                                    noti.flags |= Notification.FLAG_AUTO_CANCEL;

                                    notificationManager.notify(0, noti);

                                    dialog.dismiss();
                                }
                                else
                                {
//                                    Intent intent = new Intent(WalkerProfile_Activity.this, BookingActivity.class);
//                                    Bundle Extras = new Bundle();
//                                    Extras.putSerializable("Walker", selectedWalker);
//                                    Extras.putSerializable("TimeList", selectedWalker.getAvailableTime());
//                                    intent.putExtras(Extras);
//
//
                                    Intent intent1 = new Intent(WalkerProfile_Activity.this,SwipeActivity.class);
                                    PendingIntent pIntent = PendingIntent.getActivity(WalkerProfile_Activity.this, (int) System.currentTimeMillis(), intent1, 0);


                                    Intent intent2 = new Intent(Intent.ACTION_DIAL);
                                    intent2.setData(Uri.parse("tel:"+String.valueOf(selectedWalker.getPhonenumber())));
                                    PendingIntent pIntent1 = PendingIntent.getActivity(WalkerProfile_Activity.this, (int) System.currentTimeMillis(), intent2, 0);

                                    Intent intent3 = new Intent(Intent.ACTION_VIEW);
                                    intent3.setData(Uri.parse("sms:"+String.valueOf(selectedWalker.getPhonenumber())));
                                    intent3.putExtra("sms_body", "Hi. I have a booking with you. When shall I expect your service? Thank you");
                                    PendingIntent pIntent2 = PendingIntent.getActivity(WalkerProfile_Activity.this, (int) System.currentTimeMillis(), intent3, 0);

                                    Notification noti = new Notification.Builder(WalkerProfile_Activity.this)
                                            .setContentTitle("Just Pets")
                                            .setContentText("Booking Successful - "+String.valueOf(selectedWalker.getCategory())+" service at specified time. ").setSmallIcon(R.drawable.icon)
                                            .setContentIntent(pIntent)
                                            .addAction(R.drawable.call, "Call", pIntent1)
                                            .addAction(R.drawable.icon, "Text", pIntent2)
                                            .build();
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    // hide the notification after its selected
                                    noti.flags |= Notification.FLAG_AUTO_CANCEL;

                                    notificationManager.notify(0, noti);

                                    dialog.dismiss();
                                }

                            }
                        });
                alertDialog.show();
            }
        });
//        if(selectedWalker.getRating() == 5)
//        {
//            rating.setImageDrawable(R.drawable.ratings5);
//        }
    }
}
