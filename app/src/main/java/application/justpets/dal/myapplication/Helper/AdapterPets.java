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
import application.justpets.dal.myapplication.User_Details.Pet_Details;

/**
 * Created by Aniket on 15-11-2017.
 */

public class AdapterPets extends ArrayAdapter<Pet_Details> {
    private Activity activity;
    private ArrayList<Pet_Details> petDetailslist;
    private static LayoutInflater inflater = null;

    public AdapterPets(Activity activity, int resource, @NonNull ArrayList<Pet_Details> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.petDetailslist = objects;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /*public int getCount() {
        return petDetailslist.size();
    }*/

    public Pet_Details getItem(Pet_Details position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder
    {
        public TextView petname;
        public TextView pettype;
        public TextView petDescription;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.pet_list_data, null);
                holder = new ViewHolder();

                holder.petname = (TextView) vi.findViewById(R.id.petnamedisplay);
                //holder.petDescription = (TextView) vi.findViewById(R.id.petdescriptiondisplay);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.petname.setText(petDetailslist.get(position).getPet_name());
            holder.petDescription.setText(petDetailslist.get(position).getPet_Description());


        } catch (Exception e) {


        }
        return vi;
    }

}
