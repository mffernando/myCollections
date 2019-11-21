package com.example.mycollections.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static DataStore instance = null;

    private List<Place> places;
    private PlaceDatabase database;
    private Context context;

    public static DataStore sharedInstance() {

        if (instance == null) {

            instance = new DataStore();
        }

        return instance;
    }

    public void setContext(Context context) {

        this.context = context;
        database = new PlaceDatabase(context);
        places = database.getPlaces();

    }

    private DataStore() {

        places = new ArrayList<>();
        //addPlace(new Place("Jardim Botânico", "Curitiba", "Brasil", "Jardim Botânico em Curitiba", "/sdcard/Pictures/hd_0.jpg"));
        //addPlace(new Place("Praça Espanha", "Curitiba", "Brasil", "Praça Espanha em Curitiba", "/sdcard/Pictures/hd_1.jpg"));
        //addPlace(new Place("Praça Japão", "Curitiba", "Brasil", "Praça Japão em Curitiba", "/sdcard/Pictures/hd_2.jpg"));

    }

    public List<Place> getPlaces() {

        return places;
    }

    public Place getPlace(int position) {

        return places.get(position);
    }

    public void addPlace(Place place) {

        database.addPlace(place);
        places.add(place);
    }

    public void editPlace(Place place, int position) {

        Place oldPlace = getPlace(position);
        oldPlace.setTitle(place.getTitle());
        oldPlace.setAuthor(place.getAuthor());
        oldPlace.setPublisher(place.getPublisher());
        oldPlace.setDescription(place.getDescription());
        oldPlace.setImage(place.getImage());

        database.editCity(oldPlace);
        places.set(position, place);
    }

    public void delPlace(int position) {

        database.delPlace(getPlace(position));
        places.remove(position);
    }

    public void clear() {

        places.clear();
    }
}
