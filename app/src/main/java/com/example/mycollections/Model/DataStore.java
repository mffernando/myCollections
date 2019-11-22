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
