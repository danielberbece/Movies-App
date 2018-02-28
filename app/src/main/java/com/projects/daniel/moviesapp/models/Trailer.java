package com.projects.daniel.moviesapp.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 2/28/2018.
 */

public class Trailer {
    private String youtubeKey;
    private String name;

    public Trailer(String name, String youtubeKey) {
        this.youtubeKey = youtubeKey;
        this.name = name;
    }

    public String getYoutubeKey() {
        return youtubeKey;
    }

    public void setYoutubeKey(String youtubeKey) {
        this.youtubeKey = youtubeKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Trailer buildFromJson(JSONObject jsonObject) throws JSONException {
        String youtubeKey = jsonObject.getString("source");
        String name = jsonObject.optString("name");

        return new Trailer(name, youtubeKey);
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "youtubeKey='" + youtubeKey + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
