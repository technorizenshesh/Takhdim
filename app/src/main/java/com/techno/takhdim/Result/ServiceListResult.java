package com.techno.takhdim.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceListResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("imagepath")
    @Expose
    private String imagepath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
