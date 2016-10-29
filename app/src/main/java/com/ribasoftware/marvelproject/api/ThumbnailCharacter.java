package com.ribasoftware.marvelproject.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.ribasoftware.marvelproject.util.MarvelUtil;

public class ThumbnailCharacter  implements Parcelable{
    private String path;
    private String extension;


    public ThumbnailCharacter() {

    }


    protected ThumbnailCharacter(Parcel in) {
        path = in.readString();
        extension = in.readString();
    }

    public static final Creator<ThumbnailCharacter> CREATOR = new Creator<ThumbnailCharacter>() {
        @Override
        public ThumbnailCharacter createFromParcel(Parcel in) {
            return new ThumbnailCharacter(in);
        }

        @Override
        public ThumbnailCharacter[] newArray(int size) {
            return new ThumbnailCharacter[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getStandardMedium() {
        return path +"/" +  MarvelUtil.STANDARD_MEDIUM + "." +extension ;
    }
    public String getStandardFantastic(){
        return path +"/" + MarvelUtil.STANDARD_FANTASTIC + "." + extension;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
        parcel.writeString(extension);
    }
}