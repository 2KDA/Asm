package com.example.myapplication.Model;

public class Comment {
    private String _id, id_comics, id_user, desc, time;

    public Comment(String _id, String id_comics, String id_user, String desc, String time) {
        this._id = _id;
        this.id_comics = id_comics;
        this.id_user = id_user;
        this.desc = desc;
        this.time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_comics() {
        return id_comics;
    }

    public void setId_comics(String id_comics) {
        this.id_comics = id_comics;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
