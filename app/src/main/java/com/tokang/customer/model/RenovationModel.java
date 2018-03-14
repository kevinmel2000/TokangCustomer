package com.tokang.customer.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by royli on 2/16/2018.
 */

public class RenovationModel {
    private String key;
    private String name;
    private String image;
    private BigDecimal priceMinimum;
    private BigDecimal priceMaximum;
    private String menuId;
    private Integer index;

    private ArrayList<String> imageDescription;
    private String description;

    public RenovationModel(String key, String menuId) {
        this.key = key;
        this.menuId = menuId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPriceMinimum() {
        return priceMinimum;
    }

    public void setPriceMinimum(BigDecimal priceMinimum) {
        this.priceMinimum = priceMinimum;
    }

    public BigDecimal getPriceMaximum() {
        return priceMaximum;
    }

    public void setPriceMaximum(BigDecimal priceMaximum) {
        this.priceMaximum = priceMaximum;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
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
}
