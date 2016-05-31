package com.stardust.easyassess.pdm.models;


import java.util.ArrayList;
import java.util.List;

public class MenuGroup {
    private String text;
    private String icon;
    private List<MenuItem> items = new ArrayList<MenuItem>();

    public MenuGroup() {

    }

    public MenuGroup(String text, String icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}
