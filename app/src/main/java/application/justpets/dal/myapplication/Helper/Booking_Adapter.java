package application.justpets.dal.myapplication.Helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import application.justpets.dal.myapplication.R;
import application.justpets.dal.myapplication.Service_Details.Server_booking;

/**
 * Created by Aniket on 30-11-2017.
 */

public class Booking_Adapter extends ArrayAdapter<Server_booking> {
    private Activity activity;
    private ArrayList<Server_booking> Bookinglist;
    private static LayoutInflater inflater = null;
    public Booking_Adapter(@NonNull Activity context, int resource, @NonNull ArrayList<Server_booking> objects) {
        super(context, resource, objects);
        activity = context;
        Bookinglist = objects;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public static class ViewHolder
    {
        public TextView Servername;
        public TextView Category;
        public TextView Bookingdate;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final Booking_Adapter.ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.bookinglistdata, null);
                holder = new Booking_Adapter.ViewHolder();
                //Toast.makeText(getContext(),"holder created",Toast.LENGTH_SHORT).show();


                holder.Servername = (TextView) vi.findViewById(R.id.walkername);
                holder.Category = (TextView) vi.findViewById(R.id.category);
                holder.Bookingdate = (TextView)vi.findViewById(R.id.Bookingdate);
                //holder.WalkerNumber = (TextView)vi.findViewById(R.id.textView10);
                vi.setTag(holder);
            } else {
                holder = (Booking_Adapter.ViewHolder) vi.getTag();
            }



            holder.Servername.setText(Bookinglist.get(position).getname());
            holder.Category.setText(String.valueOf(Bookinglist.get(position).getCategory()));
            holder.Bookingdate.setText(Bookinglist.get(position).getDate());
            //holder.WalkerNumber.setText(walkerlist.get(position).getAvailableTime().get(0).getDate());
            //Toast.makeText(activity,walkerlist.get(position).getName(), Toast.LENGTH_SHORT).show();


        } catch (Exception e) {


        }
        return vi;
    }

}
