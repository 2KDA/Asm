package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserData implements Parcelable {
    @SerializedName("userName")
    private String userName;
    @SerializedName("passWord")
    private String passWord;
    @SerializedName("_id")
    private String _id;
    public String toString(){
        return " "+_id + ""+userName + " "+passWord;
    }


    public UserData(String username, String password, String _id) {
        this.userName = username;
        this.passWord = password;
        this._id = _id;
    }

    protected UserData(Parcel in) {
        userName = in.readString();
        passWord = in.readString();
        _id = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return passWord;
    }

    public String getId() {
        return _id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(passWord);
        dest.writeString(_id);
    }
}
