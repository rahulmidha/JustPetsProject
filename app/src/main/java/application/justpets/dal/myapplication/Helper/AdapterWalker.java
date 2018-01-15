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

import application.justpets.dal.myapplication.Dog_walker_details.Walker_Attributes;
import application.justpets.dal.myapplication.R;
import application.justpets.dal.myapplication.User_Details.Pet_Details;

/**
 * Created by Aniket on 29-11-2017.
 */

public class AdapterWalker extends ArrayAdapter<Walker_Attributes> {
    private Activity activity;
    private ArrayList<Walker_Attributes> walkerlist;
    private static LayoutInflater inflater = null;

    public AdapterWalker(@NonNull Activity activity, int resource, @NonNull ArrayList<Walker_Attributes> objects) {

        super(activity, resource, objects);
        this.activity = activity;
        this.walkerlist = objects;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Toast.makeText(activity,"Adapter created",Toast.LENGTH_SHORT).show();
    }
    public Pet_Details getItem(Pet_Details position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder
    {
        public TextView Walkername;
        public TextView Category;
        public TextView WalkerNumber;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final AdapterWalker.ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.walker_list_data, null);
                holder = new AdapterWalker.ViewHolder();
                //Toast.makeText(getContext(),"holder created",Toast.LENGTH_SHORT).show();


                holder.Walkername = (TextView) vi.findViewById(R.id.walkerprice);
                holder.Category = (TextView) vi.findViewById(R.id.walkername);
                //holder.WalkerNumber = (TextView)vi.findViewById(R.id.textView10);
                vi.setTag(holder);
            } else {
                holder = (AdapterWalker.ViewHolder) vi.getTag();
            }



            holder.Walkername.setText(walkerlist.get(position).getName());
            holder.Category.setText(String.valueOf(walkerlist.get(position).getPrice()));
            //holder.WalkerNumber.setText(walkerlist.get(position).getAvailableTime().get(0).getDate());
            //Toast.makeText(activity,walkerlist.get(position).getName(), Toast.LENGTH_SHORT).show();


        } catch (Exception e) {


        }
        return vi;
    }

}
