package com.example.mycollections.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mycollections.Model.DataStore;
import com.example.mycollections.Model.Place;
import com.example.mycollections.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddPlaceActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtAuthor;
    private TextView txtPublisher;
    private TextView txtDescription;
    private TextView txtImage;
    private ImageView imgView;
    private Button btnGallery;
    private Button btnCamera;

    private int type;
    private int placePosition;

    private int PICK_IMAGE_REQUEST = 200;
    private int PICK_CAMERA_REQUEST = 300;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtPublisher = findViewById(R.id.txtPublisher);
        txtDescription = findViewById(R.id.txtDescription);
        //txtImage = findViewById(R.id.txtImage);
        imgView = findViewById(R.id.imgView);
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);

        //open gallery
        btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        //open camera
        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        type = getIntent().getIntExtra("type", 0);

        if (type == 2) {

            placePosition = getIntent().getIntExtra("placePosition", 0);
            Place place = DataStore.sharedInstance().getPlace(placePosition);
            txtTitle.setText(place.getTitle());
            txtAuthor.setText(place.getAuthor());
            txtPublisher.setText(place.getPublisher());
            txtDescription.setText(place.getDescription());
            //txtImage.setText(place.getImage().toString());
            imgView.setImageBitmap(place.getImage());

            //open gallery
            btnGallery = findViewById(R.id.btnGallery);
            btnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });

            //open camera
            btnCamera = findViewById(R.id.btnCamera);
            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                }
            });

        }

    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //from gallery
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {
                Uri uri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(uri);
                imgView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //Log.d(TAG, String.valueOf(bitmap));
                //ImageView imageView = findViewById(R.id.imgView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //from camera
        if (requestCode == PICK_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            imgView.setImageBitmap(image);
        }
    }

    public void btnSaveOnClick(View view) {

        Place place = new Place(
                txtTitle.getText().toString(),
                txtAuthor.getText().toString(),
                txtPublisher.getText().toString(),
                txtDescription.getText().toString(),
                //txtImage.getText().toString()
                ((BitmapDrawable)imgView.getDrawable()).getBitmap()
                //imgView.getDrawable()
        );

        if (type == 1)

            DataStore.sharedInstance().addPlace(place);

        else

            DataStore.sharedInstance().editPlace(place, placePosition);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(new Intent("updatePlaces"));
        finish();

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
}
