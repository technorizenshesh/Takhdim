package com.techno.takhdim.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techno.takhdim.Result.ChatResult;

import java.util.List;

public class ChatResponse {
    @SerializedName("result")
    @Expose
    private List<ChatResult> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<ChatResult> getResult() {
        return result;
    }

    public void setResult(List<ChatResult> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
