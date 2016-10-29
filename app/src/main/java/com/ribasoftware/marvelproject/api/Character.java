package com.ribasoftware.marvelproject.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//Character
public class Character implements Parcelable {
    private long _id;
    @SerializedName("id")
    private int idWeb;
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


    public Character() {

    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public int getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(int idWeb) {
        this.idWeb = idWeb;
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

    public static Creator<Character> getCREATOR() {
        return CREATOR;
    }

    protected Character(Parcel in) {
        _id = in.readLong();
        idWeb = in.readInt();
        name = in.readString();
        description = in.readString();
        modified = in.readString();
        thumbnail = in.readParcelable(ThumbnailCharacter.class.getClassLoader());
        resourceURI = in.readString();
        urls = in.createTypedArrayList(Url.CREATOR);
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeInt(idWeb);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(modified);
        parcel.writeParcelable(thumbnail, i);
        parcel.writeString(resourceURI);
        parcel.writeTypedList(urls);
    }
}
