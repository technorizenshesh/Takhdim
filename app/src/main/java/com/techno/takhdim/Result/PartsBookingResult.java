package com.techno.takhdim.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartsBookingResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("part_id")
    @Expose
    private String partId;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("part_details")
    @Expose
    private PartDetails partDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public PartDetails getPartDetails() {
        return partDetails;
    }

    public void setPartDetails(PartDetails partDetails) {
        this.partDetails = partDetails;
    }

}
