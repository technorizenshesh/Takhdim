package com.techno.takhdim.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("chat_message")
    @Expose
    private String chatMessage;
    @SerializedName("chat_image")
    @Expose
    private String chatImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("clear_chat")
    @Expose
    private String clearChat;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("sender_detail")
    @Expose
    private SenderDetail senderDetail;
    @SerializedName("receiver_detail")
    @Expose
    private ReceiverDetail receiverDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClearChat() {
        return clearChat;
    }

    public void setClearChat(String clearChat) {
        this.clearChat = clearChat;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SenderDetail getSenderDetail() {
        return senderDetail;
    }

    public void setSenderDetail(SenderDetail senderDetail) {
        this.senderDetail = senderDetail;
    }

    public ReceiverDetail getReceiverDetail() {
        return receiverDetail;
    }

    public void setReceiverDetail(ReceiverDetail receiverDetail) {
        this.receiverDetail = receiverDetail;
    }


}
