package com.example.huynhvannhan.game2.Object;

/**
 * Created by HUYNH VAN NHAN on 26/04/2017.
 */

public class ChatMessage {
    private int id;
    private boolean isMe;
    private String message;
    private Long userId;
    private String dateTime;

    public ChatMessage() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
