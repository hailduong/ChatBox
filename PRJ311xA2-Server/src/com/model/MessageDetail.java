package com.model;

import java.util.Date;

public class MessageDetail {
    public int messageId;
    public String fromUser;
    public String toUser;
    public Date dateCreated;
    public String content;
    public String messageType;

    public MessageDetail(String fromUser, String toUser, String content, String messageType) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
        this.messageType = messageType;
        this.dateCreated = new Date();
    }
}
