package com.tokang.customer.model;

import java.util.ArrayList;

/**
 * Created by royli on 1/28/2018.
 */

public class Menu {
    private String key;
    private String name;
    private String tagline;
    private String logoDark;
    private String logoLight;

    private ArrayList<String> imageDescription;
    private String description;

    private Integer index;
    private String menuIcon;

    public Menu(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getLogoDark() {
        return logoDark;
    }

    public void setLogoDark(String logoDark) {
        this.logoDark = logoDark;
    }

    public String getLogoLight() {
        return logoLight;
    }

    public void setLogoLight(String logoLight) {
        this.logoLight = logoLight;
    }

    public ArrayList<String> getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(ArrayList<String> imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
}
