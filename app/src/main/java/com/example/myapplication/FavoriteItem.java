package com.example.myapplication;


public class FavoriteItem {
    private String title;
    private String snippet;
    private String uniqueKey;

    public FavoriteItem(String title, String snippet, String uniqueKey) {
        this.title = title;
        this.snippet = snippet;
        this.uniqueKey = uniqueKey;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }
}