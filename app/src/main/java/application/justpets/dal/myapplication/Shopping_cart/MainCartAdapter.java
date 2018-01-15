package application.justpets.dal.myapplication.Shopping_cart;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import application.justpets.dal.myapplication.R;

/**
 * Created by Aniket on 05-12-2017.
 */

public class MainCartAdapter extends RecyclerView.Adapter<MainCartAdapter.CartViewHolder> {

// Class variables for the Cursor that holds task data and the Context
private Cursor mCursor;
private Context mContext;


/**
 * Constructor for the CustomCursorAdapter that initializes the Context.
 *
 * @param mContext the current Context
 */
public MainCartAdapter(Context mContext) {
        this.mContext = mContext;
        }



@Override
public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
        }

@Override
public void onBindViewHolder(CartViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(CartContract.CartEntry._CARTID);
        int fragranceName = mCursor.getColumnIndex(CartContract.CartEntry.COLUMN_CART_NAME);
        int image = mCursor.getColumnIndex(CartContract.CartEntry.COLUMN_CART_IMAGE);
        int quantity = mCursor.getColumnIndex(CartContract.CartEntry.COLUMN_CART_QUANTITY);
        int price = mCursor.getColumnIndex(CartContract.CartEntry.COLUMN_CART_TOTAL_PRICE);


        mCursor.moveToPosition(position); // get to the right location in the cursor

// Determine the values of the wanted data
final int id = mCursor.getInt(idIndex);
        String name = mCursor.getString(fragranceName);
        String fragranceImage = mCursor.getString(image);
        int fragranceQuantity = mCursor.getInt(quantity);
        Double fragrancePrice = mCursor.getDouble(price);

        DecimalFormat precision = new DecimalFormat("0.00");



        //Set values
        holder.itemView.setTag(id);
        holder.fragName.setText(name);
        holder.fragQuantity.setText("Quantity ordering: " + String.valueOf(fragranceQuantity));
        holder.fragPrice.setText("$" + precision.format(fragrancePrice));

        String poster = "http://boombox.ng/images/fragrance/" + fragranceImage;

        Glide.with(mContext)
        .load(poster)
        .placeholder(R.drawable.load)
        .into(holder.image);

        }


@Override
public int getItemCount() {
        if (mCursor == null) {
        return 0;
        }
        return mCursor.getCount();
        }

public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
        return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
        this.notifyDataSetChanged();
        }
        return temp;
        }

public class CartViewHolder extends RecyclerView.ViewHolder {
    TextView fragName, fragQuantity, fragPrice;
    ImageView image;
    public CartViewHolder(View itemView) {
        super(itemView);

        fragName = (TextView) itemView.findViewById(R.id.fragranceName);
        fragQuantity = (TextView) itemView.findViewById(R.id.quantity);
        fragPrice = (TextView) itemView.findViewById(R.id.price);
        image = (ImageView) itemView.findViewById(R.id.cartImage);
    }

}
}
