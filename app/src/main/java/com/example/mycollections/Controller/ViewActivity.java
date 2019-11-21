package com.example.mycollections.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

    private TextView txtTitle;
    private TextView txtAuthor;
    private TextView txtPublisher;
    private TextView txtDescription;
    private TextView txtImage;
    private ImageView imgView;


    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        position = getIntent().getIntExtra("placePosition", 0);
        Place place = DataStore.sharedInstance().getPlace(position);
        //Place place = DataStore.sharedInstance().getPlace(position);

        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtPublisher = findViewById(R.id.txtPublisher);
        txtDescription = findViewById(R.id.txtDescription);
        //txtImage = findViewById(R.id.txtImage);
        imgView = findViewById(R.id.imgView);

        txtTitle.setText(place.getTitle());
        txtAuthor.setText(place.getAuthor());
        txtPublisher.setText(place.getPublisher());
        txtDescription.setText(place.getDescription());
        //txtImage.setText(place.getImage().toString());
        //imgView.setImageBitmap(place.getImage());

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
        builder.setMessage("Tem certeza que deseja remover este livro?");
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
