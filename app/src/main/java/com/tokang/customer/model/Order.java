package com.tokang.customer.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by royli on 2/17/2018.
 */

public class Order {
    private User user;
    private String orderKey;
    private String servicekey;
    private LatLng latLng;
    private Calendar calendar;
    private String addressDetail;
    private Long discount;
    private String referalCode;

    public Order() {
    }

    public Order(User user, String orderKey, String servicekey, LatLng latLng, Calendar calendar) {
        this.user = user;
        this.orderKey = orderKey;
        this.servicekey = servicekey;
        this.latLng = latLng;
        this.calendar = calendar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getServicekey() {
        return servicekey;
    }

    public void setServicekey(String servicekey) {
        this.servicekey = servicekey;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getReferalCode() {
        return referalCode;
    }

    public void setReferalCode(String referalCode) {
        this.referalCode = referalCode;
    }
}
