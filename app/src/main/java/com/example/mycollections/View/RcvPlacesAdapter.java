package com.example.mycollections.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycollections.Controller.ViewActivity;
import com.example.mycollections.Model.Place;
import com.example.mycollections.R;

import java.util.List;

public class RcvPlacesAdapter extends RecyclerView.Adapter<RcvPlacesAdapter.PlaceHolder> {

    private List<Place> places;
    private Context context;

    public RcvPlacesAdapter(List<Place> places, Context context) {

        this.places = places;
        this.context = context;

    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rcv_places, viewGroup, false);

        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcvPlacesAdapter.PlaceHolder holder, final int position) {

        Place place = places.get(position);

        holder.txtTitle.setText(place.getTitle());
        holder.txtAuthor.setText(place.getAuthor());
        holder.txtPublisher.setText(place.getPublisher());
        holder.txtDescription.setText(place.getDescription());
        holder.imgImage.setImageBitmap(place.getImage());
        //holder.txtImage.setText(place.getImage());
        //holder.txtImage.setImageURI(Uri.parse(place.getImage()));

        holder.layPlace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra("placePosition", position);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return places.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder{

        public LinearLayout layPlace;
        public TextView txtTitle;
        public TextView txtAuthor;
        public TextView txtPublisher;
        public TextView txtDescription;
        public ImageView imgImage;
        //public TextView txtImage;

        public PlaceHolder(@NonNull View itemView) {

            super(itemView);

            layPlace = itemView.findViewById(R.id.layPlace);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtPublisher = itemView.findViewById(R.id.txtPublisher);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgImage = itemView.findViewById(R.id.imgView);
            //txtImage = itemView.findViewById(R.id.txtImage);

        }
    }
}
