package com.techno.takhdim.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiverDetail {


    @SerializedName("receiver_image")
    @Expose
    private String receiverImage;

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

}
