package application.justpets.dal.myapplication.Shopping_cart;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import application.justpets.dal.myapplication.R;

/**
 * Created by Aniket on 05-12-2017.
 */

public class CerDbHelper extends SQLiteOpenHelper {
    private static final String TAG = CerDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "fragrances.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;



    //Used to read data from res/ and assets/
    private Resources mResources;



    public CerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
        mContentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FRAGRANCE_TABLE = "CREATE TABLE " + CartContract.CartEntry.TABLE_NAME + " (" +
                CartContract.CartEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CartContract.CartEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                CartContract.CartEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                CartContract.CartEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                CartContract.CartEntry.COLUMN_PRICE + " REAL NOT NULL, " +
                CartContract.CartEntry.COLUMN_USERRATING + " INTEGER NOT NULL " + " );";

        final String SQL_CREATE_CART_FRAGRANCE_TABLE = "CREATE TABLE " + CartContract.CartEntry.CART_TABLE + " (" +
                CartContract.CartEntry._CARTID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CartContract.CartEntry.COLUMN_CART_NAME + " TEXT UNIQUE NOT NULL, " +
                CartContract.CartEntry.COLUMN_CART_IMAGE + " TEXT NOT NULL, " +
                CartContract.CartEntry.COLUMN_CART_QUANTITY + " INTEGER NOT NULL, " +
                CartContract.CartEntry.COLUMN_CART_TOTAL_PRICE + " REAL NOT NULL " + " );";


        db.execSQL(SQL_CREATE_FRAGRANCE_TABLE);
        db.execSQL(SQL_CREATE_CART_FRAGRANCE_TABLE);
        Log.d(TAG, "Database Created Successfully" );


        try {
            readFragrancesFromResources(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
        db.execSQL("DROP TABLE IF EXISTS " + CartContract.CartEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CartContract.CartEntry.CART_TABLE);
        onCreate(db);
    }


    private void readFragrancesFromResources(SQLiteDatabase db) throws IOException, JSONException {
        //db = this.getWritableDatabase();
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.fragrance);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();

        final String BGS_FRAGRANCES = "fragrances";

        final String BGS_FRAGRANCENAME = "fragranceName";
        final String BGS_DESCRIPTION = "description";
        final String BGS_IMAGEURL = "imageUrl";
        final String BGS_PRICE = "price";
        final String BGS_USERRATING = "userRating";

        try {
            JSONObject fragranceJson = new JSONObject(rawJson);
            JSONArray fragranceArray = fragranceJson.getJSONArray(BGS_FRAGRANCES);


            for (int i = 0; i < fragranceArray.length(); i++) {

                String fragranceName;
                String description;
                String imageUrl;
                Double price;
                int userRating;

                JSONObject fragranceDetails = fragranceArray.getJSONObject(i);

                fragranceName = fragranceDetails.getString(BGS_FRAGRANCENAME);
                description = fragranceDetails.getString(BGS_DESCRIPTION);
                imageUrl = fragranceDetails.getString(BGS_IMAGEURL);
                price = fragranceDetails.getDouble(BGS_PRICE);
                userRating = fragranceDetails.getInt(BGS_USERRATING);


                ContentValues fragranceValues = new ContentValues();

                fragranceValues.put(CartContract.CartEntry.COLUMN_NAME, fragranceName);
                fragranceValues.put(CartContract.CartEntry.COLUMN_DESCRIPTION, description);
                fragranceValues.put(CartContract.CartEntry.COLUMN_IMAGE, imageUrl);
                fragranceValues.put(CartContract.CartEntry.COLUMN_PRICE, price);
                fragranceValues.put(CartContract.CartEntry.COLUMN_USERRATING, userRating);

                db.insert(CartContract.CartEntry.TABLE_NAME, null, fragranceValues);

                Log.d(TAG, "Inserted Successfully " + fragranceValues );
            }

            Log.d(TAG, "Inserted Successfully " + fragranceArray.length() );

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }


}

