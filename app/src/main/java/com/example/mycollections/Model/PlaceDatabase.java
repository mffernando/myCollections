package com.example.mycollections.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PlaceDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "books.sqlite";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "books";

    //image to db
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInByte;

    private Context context;

    public PlaceDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "author TEXT, " +
                "publisher TEXT, " +
                "description TEXT, " +
                "image BLOB);";

//        String sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + "(" +
//                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "name TEXT, " +
//                "city TEXT, " +
//                "country TEXT, " +
//                "description TEXT);";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            switch (oldVersion + 1) {

            }
        }
    }

    public void addPlace(Place place) {

        SQLiteDatabase db = getWritableDatabase();

        //store image
        Bitmap imageToStore = place.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInByte = byteArrayOutputStream.toByteArray();

        ContentValues values = new ContentValues();

        values.put("title", place.getTitle());
        values.put("author", place.getAuthor());
        values.put("publisher", place.getPublisher());
        values.put("description", place.getDescription());
        values.put("image", imageInByte);

        long id = db.insert(DB_TABLE, "", values);
        place.setId(id);
        db.close();

    }

    public void editCity(Place place) {

        SQLiteDatabase db = getWritableDatabase();

        //store image
        Bitmap imageToStore = place.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInByte = byteArrayOutputStream.toByteArray();

        ContentValues values = new ContentValues();

        values.put("title", place.getTitle());
        values.put("author", place.getAuthor());
        values.put("publisher", place.getPublisher());
        values.put("description", place.getDescription());
        values.put("image", imageInByte);

        String _id = String.valueOf(place.getId());
        int count = db.update(DB_TABLE, values, "_id = ?", new String[]{_id});
        db.close();

    }

    public void delPlace(Place place) {

        SQLiteDatabase db = getWritableDatabase();

        String _id = String.valueOf(place.getId());
        int count = db.delete(DB_TABLE, "_id = ?", new String[]{_id});
        db.close();

    }

    public List<Place> getPlaces() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, "title");
        List<Place> places = new ArrayList<>();

        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            do {

                Place place = new Place(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("author")),
                        cursor.getString(cursor.getColumnIndex("publisher")),
                        cursor.getString(cursor.getColumnIndex("description")),
                        blobImage(cursor)
                //stringToBitmap(String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))))
                //cursor.getString(cursor.getColumnIndex("image"))
                );

                places.add(place);
            }

            while (cursor.moveToNext());
            //cursor.close();

        }

        return places;
    }

    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public Bitmap blobImage(Cursor cursor) {

        byte[] imageByte = cursor.getBlob(cursor.getColumnIndex("image"));
        Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

        return image;

    }

}