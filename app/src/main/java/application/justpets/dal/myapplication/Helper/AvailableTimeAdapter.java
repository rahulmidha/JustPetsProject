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

import application.justpets.dal.myapplication.Dog_walker_details.Available_time_Attributes;
import application.justpets.dal.myapplication.R;

/**
 * Created by Aniket on 03-12-2017.
 */

public class AvailableTimeAdapter extends ArrayAdapter<Available_time_Attributes> {
    private Activity activity;
    private ArrayList<Available_time_Attributes> Timelist;
    private static LayoutInflater inflater = null;

    public AvailableTimeAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Available_time_Attributes> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.Timelist = objects;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder
    {
        public TextView Date;
        public TextView Time;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final AvailableTimeAdapter.ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.available_time_list, null);
                holder = new AvailableTimeAdapter.ViewHolder();

                holder.Date= (TextView) vi.findViewById(R.id.Date);
                holder.Time=(TextView)vi.findViewById(R.id.Time);
                //holder.petDescription = (TextView) vi.findViewById(R.id.petdescriptiondisplay);


                vi.setTag(holder);
            } else {
                holder = (AvailableTimeAdapter.ViewHolder) vi.getTag();
            }



            holder.Date.setText(Timelist.get(position).getDate());
            holder.Time.setText(Timelist.get(position).getTime());


        } catch (Exception e) {


        }
        return vi;
    }
}
