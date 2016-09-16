package com.ribasoftware.marvelproject.api;

import java.util.List;

//Character
public class Character {
    private int id;
    private String name;
    private String description;
    private String modified;
    private ThumbnailCharacter thumbnail;
    private String resourceURI;


    private ComicsCharacter  comics;
    private SeriesCharacter  series;
    private StoriesCharacter stories;
    private EventsCharacter  events;


    private List<Url> urls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public ThumbnailCharacter getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailCharacter thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public ComicsCharacter getComics() {
        return comics;
    }

    public void setComics(ComicsCharacter comics) {
        this.comics = comics;
    }

    public SeriesCharacter getSeries() {
        return series;
    }

    public void setSeries(SeriesCharacter series) {
        this.series = series;
    }

    public StoriesCharacter getStories() {
        return stories;
    }

    public void setStories(StoriesCharacter stories) {
        this.stories = stories;
    }

    public EventsCharacter getEvents() {
        return events;
    }

    public void setEvents(EventsCharacter events) {
        this.events = events;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }


}