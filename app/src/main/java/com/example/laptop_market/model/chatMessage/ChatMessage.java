package com.example.laptop_market.model.chatMessage;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatMessage implements Cloneable{
    public static final int RECEIVER_MESSAGE = 1;
    public static final int SENT_MESSAGE = 2;
    public static final int IMAGE_TYPE = 1;
    public static final int TEXT_TYPE = 2;
    public static final int ONE_IMAGE = 1;
    public static final int TWO_IMAGE = 2;
    public static final int THREE_IMAGE = 3;
    public static final int FOUR_IMAGE = 4;
    public static final int FIVE_IMAGE = 5;
    private String idMessage;
    private String senderAccountId;
    private String conversationId;
    @Exclude
    private String receiverAccountId;
    private int type;
    private String message;
    private Date timeSendMesssage;
    private int numberOfPicture;
    @Exclude
    private List<byte[]> listUploadImage;
    @Exclude
    private ArrayList<Bitmap> downloadedImage;
    public ChatMessage()
    {}
    public ChatMessage(int type) {
        this.type = type;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(String receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeSendMesssage() {
        return timeSendMesssage;
    }

    public void setTimeSendMesssage(Date timeSendMesssage) {
        this.timeSendMesssage = timeSendMesssage;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setDownloadedImage(ArrayList<Bitmap> downloadedImage) {
        this.downloadedImage = downloadedImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumberOfPicture() {
        return numberOfPicture;
    }

    public void setNumberOfPicture(int numberOfPicture) {
        this.numberOfPicture = numberOfPicture;
    }

    public ArrayList<Bitmap> getDownloadedImage() {
        return downloadedImage;
    }

    public List<byte[]> getListUploadImage() {
        return listUploadImage;
    }

    public void setListUploadImage(List<byte[]> listUploadImage) {
        this.listUploadImage = listUploadImage;
    }

    public String getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    @Override
    public ChatMessage clone() {
        try {
            ChatMessage clone = (ChatMessage) super.clone();
            // Sao chép các thuộc tính tham chiếu
            if (this.downloadedImage != null) {
                clone.downloadedImage = new ArrayList<>(this.downloadedImage.size());
                for (Bitmap bitmap : this.downloadedImage) {
                    clone.downloadedImage.add(bitmap.copy(bitmap.getConfig(), bitmap.isMutable()));
                }
            }
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
