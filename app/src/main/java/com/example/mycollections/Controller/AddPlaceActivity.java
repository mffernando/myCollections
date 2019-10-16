package com.example.mycollections.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;

public class AddPlaceActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtCity;
    private TextView txtCountry;
    private TextView txtDescription;
    private ImageView imgView;
    private Button btnImage;

    private int type;
    private int placePosition;

    private int PICK_IMAGE_REQUEST = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        txtName = findViewById(R.id.txtName);
        txtCity = findViewById(R.id.txtCity);
        txtCountry = findViewById(R.id.txtCountry);
        txtDescription = findViewById(R.id.txtDescription);
        imgView = findViewById(R.id.imgView);

        type = getIntent().getIntExtra("type", 0);

        if (type == 2) {

            placePosition = getIntent().getIntExtra("placePosition", 0);
            Place place = DataStore.sharedInstance().getPlace(placePosition);
            txtName.setText(place.getName());
            txtCity.setText(place.getCity());
            txtCountry.setText(place.getCountry());
            txtDescription.setText(place.getDescription());
            imgView.setImageURI(Uri.parse(place.getPath()));

        btnImage = findViewById(R.id.btnImage);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        }

    }

    public void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.imgView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnSaveOnClick(View view){

        Place place = new Place(
                txtName.getText().toString(),
                txtCity.getText().toString(),
                txtCountry.getText().toString(),
                txtDescription.getText().toString(),
                imgView.toString()
//                imgView.getResources().toString() //VERIFICAR

        );

        if (type == 1)

            DataStore.sharedInstance().addPlace(place);

            else

                DataStore.sharedInstance().editPlace(place, placePosition);
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
            manager.sendBroadcast(new Intent("updatePlaces"));
            finish();

    }
}
