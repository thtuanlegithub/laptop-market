package com.example.laptop_market.model.conversation;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Conversation implements Serializable {
    public static final int MODIFY_LIST_CONVERSATION = 1;
    public static final int ADD_LIST_CONVERSATION = 2;
    private String personNotSeenId;
    private String lastMessage;
    private Date lastMessageTime;
    private String personOneId;
    private String personTwoId;
    @Exclude
    private String conversationId;
    @Exclude
    private String senderId;
    @Exclude
    private String receivedId;
    @Exclude
    private String conversationImage;
    @Exclude
    private String conversationName;

    public Conversation() {
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getPersonOneId() {
        return personOneId;
    }

    public void setPersonOneId(String personOneId) {
        this.personOneId = personOneId;
    }

    public String getPersonTwoId() {
        return personTwoId;
    }

    public void setPersonTwoId(String personTwoId) {
        this.personTwoId = personTwoId;
    }

    public String getPersonNotSeenId() {
        return personNotSeenId;
    }

    public void setPersonNotSeenId(String personNotSeenId) {
        this.personNotSeenId = personNotSeenId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getConversationImage() {
        return conversationImage;
    }

    public void setConversationImage(String conversationImage) {
        this.conversationImage = conversationImage;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public Date getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceivedId() {
        return receivedId;
    }

    public void setReceivedId(String receivedId) {
        this.receivedId = receivedId;
    }
}
