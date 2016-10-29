package com.ribasoftware.marvelproject.api;

import android.os.Parcel;
import android.os.Parcelable;

public class Url implements Parcelable {
    private String type;
    private String url;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected Url(Parcel in) {
        type = in.readString();
        url = in.readString();
    }

    public static final Creator<Url> CREATOR = new Creator<Url>() {
        @Override
        public Url createFromParcel(Parcel in) {
            return new Url(in);
        }

        @Override
        public Url[] newArray(int size) {
            return new Url[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(url);
    }
}
