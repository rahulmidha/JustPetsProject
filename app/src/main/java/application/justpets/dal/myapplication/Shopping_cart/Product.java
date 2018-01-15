package application.justpets.dal.myapplication.Shopping_cart;

import android.database.Cursor;

/**
 * Created by Aniket on 05-12-2017.
 */

public class Product {
        public int id;

        public String name;
        public String description;
        public String imageUrl;
        public Double price;
        public int userRating;


        public Product(Cursor cursor) {
            this.name = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME));
            this.description = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_DESCRIPTION));
            this.imageUrl = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_IMAGE));
            this.price = cursor.getDouble(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_PRICE));
            this.userRating = cursor.getInt(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_USERRATING));
        }

}
