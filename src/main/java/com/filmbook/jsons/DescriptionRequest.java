package com.filmbook.jsons;


import com.google.gson.annotations.SerializedName;

public class DescriptionRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("synopsis")
    private String synopsis;

    @SerializedName("locale")
    private String locale;

    @SerializedName("sourceType")
    private int sourceType;

    @SerializedName("main")
    private boolean main;

    @SerializedName("creationDate")
    private String creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}

