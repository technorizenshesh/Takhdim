package com.techno.takhdim.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techno.takhdim.Result.PartsBookingResult;

import java.util.List;

public class PartsBookingResponse {
    @SerializedName("result")
    @Expose
    private List<PartsBookingResult> result = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PartsBookingResult> getResult() {
        return result;
    }

    public void setResult(List<PartsBookingResult> result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
