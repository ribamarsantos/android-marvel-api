package com.ribasoftware.marvelproject.api;

import java.util.List;

public class EventsCharacter {
    private int available;
    private String collectionURI;
    private int returned;
    /**
     * resourceURI : http://gateway.marvel.com/v1/public/events/269
     * name : Secret Invasion
     */

    private List<ItemsCharacter> items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public List<ItemsCharacter> getItems() {
        return items;
    }

    public void setItems(List<ItemsCharacter> items) {
        this.items = items;
    }

}