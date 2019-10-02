package com.example.mycollections.Model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static DataStore instance = null;

    private List<Place> places;

    public static DataStore sharedInstance() {

        if (instance == null) {

            instance = new DataStore();
        }

        return instance;
    }

    private DataStore() {

        places = new ArrayList<>();
        addPlace(new Place("Jardim Botânico", "Curitiba", "Brasil", "Jardim Botânico em Curitiba"));
        addPlace(new Place("Praça Espanha", "Curitiba", "Brasil", "Praça Espanha em Curitiba"));
        addPlace(new Place("Praça Japão", "Curitiba", "Brasil", "Praça Japão em Curitiba"));

    }

    public List<Place> getPlaces() {

        return places;
    }

    public Place getPlace(int position) {

        return places.get(position);
    }

    public void addPlace(Place place) {

        places.add(place);
    }

    public void editPlace(Place place, int position) {

        places.set(position, place);
    }

    public void delPlace(int position) {

        places.remove(position);
    }

    public void clear() {

        places.clear();
    }
}