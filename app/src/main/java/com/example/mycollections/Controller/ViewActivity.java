package com.example.mycollections.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mycollections.Model.DataStore;
import com.example.mycollections.Model.Place;
import com.example.mycollections.R;

public class ViewActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtCity;
    private TextView txtCountry;
    private TextView txtDescription;
    private ImageView imgView;

    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        position = getIntent().getIntExtra("placePosition", 0);
        Place place = DataStore.sharedInstance().getPlace(position);

        txtName = findViewById(R.id.txtName);
        txtCity = findViewById(R.id.txtCity);
        txtCountry = findViewById(R.id.txtCountry);
        txtDescription = findViewById(R.id.txtDescription);
        imgView = findViewById(R.id.imgView);

        txtName.setText(place.getName());
        txtCity.setText(place.getCity());
        txtCountry.setText(place.getCountry());
        txtDescription.setText(place.getDescription());
        imgView.setImageURI(Uri.parse(place.getPath()));

    }

    public void btnEditOnClick(View view){

        Intent intent = new Intent(this, AddPlaceActivity.class);
        intent.putExtra("type", 2);
        intent.putExtra("placePosition", position);
        startActivity(intent);
        finish();

    }

    public void btnDelOnClick(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Tem certeza que deseja remover este lugar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DataStore.sharedInstance().delPlace(position);
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(ViewActivity.this);
                manager.sendBroadcast(new Intent("updatePlaces"));
                finish();

            }
        });

        builder.setNegativeButton("Não", null);
        builder.show();

    }

}
