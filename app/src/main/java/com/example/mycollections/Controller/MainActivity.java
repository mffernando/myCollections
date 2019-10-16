package com.example.mycollections.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mycollections.Model.DataStore;
import com.example.mycollections.R;
import com.example.mycollections.View.RcvPlacesAdapter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvPlaces;
    private RcvPlacesAdapter rcvPlacesAdapter;

    private Button btnImage;

    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvPlaces = findViewById(R.id.rcvPlaces);
        rcvPlacesAdapter = new RcvPlacesAdapter(DataStore.sharedInstance().getPlaces(), this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rcvPlaces.setLayoutManager(manager);
        rcvPlaces.setAdapter(rcvPlacesAdapter);

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(updatePlaces, new IntentFilter("updatePlaces"));

        //chooseImage();

//        btnImage = findViewById(R.id.rcvPlaces);
//        btnImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseImage();
//            }
//        });

    }

    public void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onPause() {

        super.onPause();

        if (isFinishing()) {

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
            broadcastManager.unregisterReceiver(updatePlaces);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mnuAdd:

                Intent intent = new Intent(this, AddPlaceActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;

            case R.id.mnuClear:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Atenção");
                builder.setMessage("Tem certeza que deseja limpar a lista de lugares?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DataStore.sharedInstance().clear();
                        rcvPlacesAdapter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton("Não", null);
                builder.show();
                break;
        }

        return true;
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

    private BroadcastReceiver updatePlaces = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            rcvPlacesAdapter.notifyDataSetChanged();

        }
    };

}
