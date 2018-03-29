package com.chembrovich.bsuir.banknews.model;

public class NewsItem {
    private String id;
    private String title;
    private String date;
    private String type;
    private String url;
    private String text;
    private boolean wasOpened = false;

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean getWasOpenedAsBoolean() {
        return wasOpened;
    }

    public int getWasOpenedAsInteger() {
        return (wasOpened ? 1 : 0);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setWasOpened(boolean wasOpened) {
        this.wasOpened = wasOpened;
    }

    public void setWasOpened(int wasOpened) {
        this.wasOpened = (wasOpened >= 1);
    }
}
