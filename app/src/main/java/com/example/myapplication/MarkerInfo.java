package com.example.myapplication;

import com.google.android.gms.maps.model.LatLng;

public class MarkerInfo {
    private LatLng latLng;
    private String title;
    private String snippet;
    private int imageResId;
    private String additionalInfo;

    public MarkerInfo(LatLng latLng, String title, String snippet, int imageResId, String additionalInfo) {
        this.latLng = latLng;
        this.title = title;
        this.snippet = snippet;
        this.imageResId = imageResId;
        this.additionalInfo = additionalInfo;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}