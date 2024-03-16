package com.filmbook.jsons;

import com.google.gson.annotations.SerializedName;

public class PhotosRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("sourcePath")
    private String sourcePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}

