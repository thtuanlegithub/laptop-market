package com.example.laptop_market.utils.elses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.laptop_market.contracts.IChatMessageContract;
import com.example.laptop_market.model.chatMessage.ChatMessage;
import com.example.laptop_market.utils.tables.ChatMessageTable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class DownloadImageFunction {
   /* private ChatMessage chatMessage;
    public void DownloadImage(ChatMessage chatMessage, FinishDownloadImageListener listener)
    {
        chatMessage.getDownloadedImage().clear();
        this.chatMessage = chatMessage.clone();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseStorage.getReference(ChatMessageTable.TABLE_NAME).child( chatMessage.getIdMessage()+ "/").listAll().addOnSuccessListener(
                listResult ->
                {
                    LoadImages(listResult, chatMessage.getIdMessage(), listener);
                });
    }
    private void LoadImages(ListResult listResult, String idMessage, FinishDownloadImageListener listener)
    {
        for(StorageReference item : listResult.getItems())
        {
            item.getBytes(Long.MAX_VALUE).addOnSuccessListener(
                    bytes -> {

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        LoadFinishLoadImage(idMessage,bitmap,listener);
                    }).addOnFailureListener(
                    e -> {
                        listener.FinishDownloadImage(null,e);
                    });
        }
    }
    private void LoadFinishLoadImage(String id, Bitmap bitmap, FinishDownloadImageListener listener )
    {
        chatMessage.getDownloadedImage().add(bitmap);
        if(chatMessage.getDownloadedImage().size()==chatMessage.getNumberOfPicture()) {
            listener.FinishDownloadImage(chatMessage, null);
        }
    }
    public interface FinishDownloadImageListener{
        void FinishDownloadImage(ChatMessage chatMessage, Exception ex);
    }*/
}
