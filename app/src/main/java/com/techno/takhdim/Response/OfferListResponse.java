package com.techno.takhdim.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techno.takhdim.Result.OfferListResult;

import java.util.List;

public class OfferListResponse {
    @SerializedName("result")
    @Expose
    private List<OfferListResult> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<OfferListResult> getResult() {
        return result;
    }

    public void setResult(List<OfferListResult> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
