package com.stardust.easyassess.pdm.models;


import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuGroup> items = new ArrayList<MenuGroup>();

    public List<MenuGroup> getItems() {
        return items;
    }

    public void setItems(List<MenuGroup> items) {
        this.items = items;
    }
}
