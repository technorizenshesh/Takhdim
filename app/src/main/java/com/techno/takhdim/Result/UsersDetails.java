package com.techno.takhdim.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("qatari_id")
    @Expose
    private String qatariId;
    @SerializedName("service_cat_id")
    @Expose
    private String serviceCatId;
    @SerializedName("service_cat_name")
    @Expose
    private String serviceCatName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("types")
    @Expose
    private String types;
    @SerializedName("social_id")
    @Expose
    private String socialId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("register_id")
    @Expose
    private String registerId;
    @SerializedName("ios_register_id")
    @Expose
    private Object iosRegisterId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQatariId() {
        return qatariId;
    }

    public void setQatariId(String qatariId) {
        this.qatariId = qatariId;
    }

    public String getServiceCatId() {
        return serviceCatId;
    }

    public void setServiceCatId(String serviceCatId) {
        this.serviceCatId = serviceCatId;
    }

    public String getServiceCatName() {
        return serviceCatName;
    }

    public void setServiceCatName(String serviceCatName) {
        this.serviceCatName = serviceCatName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public Object getIosRegisterId() {
        return iosRegisterId;
    }

    public void setIosRegisterId(Object iosRegisterId) {
        this.iosRegisterId = iosRegisterId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
