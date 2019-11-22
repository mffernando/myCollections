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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mycollections.Model.DataStore;
import com.example.mycollections.R;
import com.example.mycollections.View.RcvPlacesAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvPlaces;
    private RcvPlacesAdapter rcvPlacesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataStore.sharedInstance().setContext(this);

        rcvPlaces = findViewById(R.id.rcvPlaces);
        rcvPlacesAdapter = new RcvPlacesAdapter(DataStore.sharedInstance().getPlaces(), this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rcvPlaces.setLayoutManager(manager);
        rcvPlaces.setAdapter(rcvPlacesAdapter);

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(updatePlaces, new IntentFilter("updatePlaces"));

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
        inflater.inflate(R.menu.search, menu);

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
                builder.setMessage("Tem certeza que deseja limpar a lista de livros?");
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

    private BroadcastReceiver updatePlaces = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            rcvPlacesAdapter.notifyDataSetChanged();

        }
    };

}
