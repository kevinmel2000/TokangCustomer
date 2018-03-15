package com.tokang.customer.model;

/**
 * Created by royli on 3/14/2018.
 */

public class RecommendedItem {
    private String menuKey;
    private String image;
    private String text;

    public RecommendedItem(String menuKey, String image, String text) {
        this.menuKey = menuKey;
        this.image = image;
        this.text = text;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
