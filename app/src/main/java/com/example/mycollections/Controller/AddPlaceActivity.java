package com.example.mycollections.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mycollections.Model.DataStore;
import com.example.mycollections.Model.Place;
import com.example.mycollections.R;

public class AddPlaceActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtCity;
    private TextView txtCountry;
    private TextView txtDescription;

    private int type;
    private int placePosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        txtName = findViewById(R.id.txtName);
        txtCity = findViewById(R.id.txtCity);
        txtCountry = findViewById(R.id.txtCountry);
        txtDescription = findViewById(R.id.txtDescription);

        type = getIntent().getIntExtra("placePosition", 0);

        if (type == 2) {

            placePosition = getIntent().getIntExtra("placePosition", 0);
            Place place = DataStore.sharedInstance().getPlace(placePosition);
            txtName.setText(place.getName());
            txtCity.setText(place.getCity());
            txtCountry.setText(place.getCountry());
            txtDescription.setText(place.getDescription());

        }

    }

    public void btnSaveOnClick(View view){

        Place place = new Place(
                txtName.getText().toString(),
                txtCity.getText().toString(),
                txtCountry.getText().toString(),
                txtDescription.getText().toString()

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
