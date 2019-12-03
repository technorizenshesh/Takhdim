package com.techno.takhdim.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferListResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("users_details")
    @Expose
    private UsersDetails usersDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public UsersDetails getUsersDetails() {
        return usersDetails;
    }

    public void setUsersDetails(UsersDetails usersDetails) {
        this.usersDetails = usersDetails;
    }
}
