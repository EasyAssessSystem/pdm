package com.stardust.easyassess.pdm.models;

public class MenuItem {

    private String text;

    private String link;

    public MenuItem() {

    }

    public MenuItem (String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
