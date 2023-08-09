package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Comics implements Parcelable {

    private String _id;
    private String name;
    private String desc;
    private String name_author;
    private String year;
    private String img;
    private List<String> album;

    public Comics(String _id, String name, String description, String author, String year, String coverImage, List<String> images) {
        this._id = _id;
        this.name = name;
        this.desc = description;
        this.name_author = author;
        this.year = year;
        this.img = coverImage;
        this.album = images;
    }

    protected Comics(Parcel in) {
        _id = in.readString();
        name = in.readString();
        desc = in.readString();
        name_author = in.readString();
        year = in.readString();
        img = in.readString();
        album = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(name_author);
        dest.writeString(year);
        dest.writeString(img);
        dest.writeStringList(album);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comics> CREATOR = new Creator<Comics>() {
        @Override
        public Comics createFromParcel(Parcel in) {
            return new Comics(in);
        }

        @Override
        public Comics[] newArray(int size) {
            return new Comics[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getName_author() {
        return name_author;
    }

    public String getYear() {
        return year;
    }

    public String getImg() {
        return img;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName_author(String name_author) {
        this.name_author = name_author;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }
}
