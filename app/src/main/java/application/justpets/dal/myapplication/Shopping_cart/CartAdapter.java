package application.justpets.dal.myapplication.Shopping_cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import application.justpets.dal.myapplication.R;

import static application.justpets.dal.myapplication.Shopping_cart.DetailActivity.PRODUCT_DESCRIPTION;
import static application.justpets.dal.myapplication.Shopping_cart.DetailActivity.PRODUCT_IMAGE;
import static application.justpets.dal.myapplication.Shopping_cart.DetailActivity.PRODUCT_NAME;
import static application.justpets.dal.myapplication.Shopping_cart.DetailActivity.PRODUCT_PRICE;
import static application.justpets.dal.myapplication.Shopping_cart.DetailActivity.PRODUCT_RATING;


/**
 * Created by Aniket on 05-12-2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Cursor dataCursor;
    Context context;
    int id;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, userrating, description, price;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title);
            //  userrating = (TextView) itemView.findViewById(R.id.userrating);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra(PRODUCT_NAME, getItem(pos).name);
                        intent.putExtra(PRODUCT_DESCRIPTION, getItem(pos).description);
                        intent.putExtra(PRODUCT_PRICE, getItem(pos).price);
                        intent.putExtra(PRODUCT_IMAGE, getItem(pos).imageUrl);
                        intent.putExtra(PRODUCT_RATING, getItem(pos).userRating);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public CartAdapter(Activity mContext, Cursor cursor) {
        dataCursor = cursor;
        context = mContext;
        Toast.makeText(context,String.valueOf(PRODUCT_NAME), Toast.LENGTH_SHORT).show();
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_card, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {


        dataCursor.moveToPosition(position);

        id = dataCursor.getInt(dataCursor.getColumnIndex(CartContract.CartEntry._ID));
        String mName = dataCursor.getString(dataCursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME));
        String mDescription = dataCursor.getString(dataCursor.getColumnIndex(CartContract.CartEntry.COLUMN_DESCRIPTION));
        String mImageUrl = dataCursor.getString(dataCursor.getColumnIndex(CartContract.CartEntry.COLUMN_IMAGE));
        int mPrice = dataCursor.getInt(dataCursor.getColumnIndex(CartContract.CartEntry.COLUMN_PRICE));
        int mUserrating = dataCursor.getInt(dataCursor.getColumnIndex(CartContract.CartEntry.COLUMN_USERRATING));


        holder.name.setText(mName);

        String poster = "http://boombox.ng/images/fragrance/" + mImageUrl;

        Glide.with(context)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(holder.thumbnail);


    }


    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    @Override
    public int getItemCount() {
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }

    public Product getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        } else if (dataCursor.moveToPosition(position)) {
            return new Product(dataCursor);
        }
        return null;
    }
}

