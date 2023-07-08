package com.example.laptop_market.model.chatMessage;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Pair;
import android.webkit.MimeTypeMap;

import com.example.laptop_market.contracts.IChatMessageContract;
import com.example.laptop_market.contracts.ILaptopContract;
import com.example.laptop_market.model.conversation.Conversation;
import com.example.laptop_market.utils.tables.ChatMessageTable;
import com.example.laptop_market.utils.tables.ConversationTable;
import com.example.laptop_market.utils.tables.LaptopTable;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatMessageModel implements IChatMessageContract.Model {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseStorage firebaseStorage;
    private int successCount;
    private int uploadImage;
    private Context context;
    private List<byte[]> listUploadImage;
    private ArrayList<ChatMessage> listIamgeChatMessage;
    private int position;

    public ChatMessageModel(Context context)
    {
        firebaseStorage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.context = context;
        listUploadImage = new ArrayList<>();
        listIamgeChatMessage = new ArrayList<>();
        position = 0;
    }

    @Override
    public void LoadImage(ChatMessage chatMessage, OnFinishDownloadImageListener listener) {
/*        chatMessage.setDownloadedImage(new ArrayList<>());
        listIamgeChatMessage.add(chatMessage);
        firebaseStorage.getReference(ChatMessageTable.TABLE_NAME).child( chatMessage.getIdMessage()+ "/").listAll().addOnSuccessListener(
                listResult ->
                {
                    position++;
                    LoadImages(listResult, chatMessage.getIdMessage(), listener);
                });*/
    }
    private void LoadImages(ListResult listResult,String idMessage,OnFinishDownloadImageListener listener)
    {
        /*for(StorageReference item : listResult.getItems())
        {
            item.getBytes(Long.MAX_VALUE).addOnSuccessListener(
                    bytes -> {

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        LoadFinishLoadImage(idMessage,bitmap,listener);
                    }).addOnFailureListener(
                    e -> {
                        listener.OnFinishDownloadImage(null,e);
                    });
        }*/
    }
    private void LoadFinishLoadImage(String id, Bitmap bitmap,OnFinishDownloadImageListener listener )
    {
   /*     Objects.requireNonNull(listIamgeChatMessage.get(id)).getDownloadedImage().add(bitmap);
        if(listIamgeChatMessage.get(id).getDownloadedImage().size()==listIamgeChatMessage.get(id).getNumberOfPicture())
            listener.OnFinishDownloadImage(listIamgeChatMessage.get(id),null);*/
    }
    @Override
    public void addChatMessage(ChatMessage chatMessage, OnFinishAddChatMessageListener listener) {
        if(chatMessage.getConversationId()==null || chatMessage.getConversationId().isEmpty())
        {
            Conversation conversation = new Conversation();
            conversation.setPersonOneId(user.getUid());
            conversation.setLastMessage(chatMessage.getMessage());
            conversation.setPersonTwoId(chatMessage.getReceiverAccountId());
            conversation.setPersonNotSeenId(chatMessage.getReceiverAccountId());
            conversation.setLastMessageTime(chatMessage.getTimeSendMesssage());
            db.collection(ConversationTable.TABLE_NAME).add(conversation).addOnCompleteListener(task -> {
                if(!task.isSuccessful())
                    listener.OnFinishAddChatMessage(null,task.getException());
                else
                {
                    chatMessage.setConversationId(task.getResult().getId());
                    conversation.setConversationId(task.getResult().getId());
                    upLoadMessage(conversation, chatMessage,listener);
                }
            });
        }
        else {
            upLoadMessage(null,chatMessage,listener);
        }

    }
    private void UpdateConversation(String conversationId, ChatMessage chatMessage)
    {
        DocumentReference documentReference =
                db.collection(ConversationTable.TABLE_NAME).document(conversationId);
        documentReference.update(ConversationTable.LAST_MESSAGE,chatMessage.getMessage(),ConversationTable.LAST_MESSAGE_TIME,chatMessage.getTimeSendMesssage(),ConversationTable.PERSON_NOT_SEEN_ID,chatMessage.getReceiverAccountId());

    }
    void upLoadMessage(Conversation conversation, ChatMessage chatMessage, OnFinishAddChatMessageListener listener)
    {
        chatMessage.setSenderAccountId(user.getUid());
        UpdateConversation(chatMessage.getConversationId(),chatMessage);
        if(chatMessage.getType() == ChatMessage.IMAGE_TYPE) {
            listUploadImage.addAll(chatMessage.getListUploadImage());
            chatMessage.getListUploadImage().clear();
        }
        db.collection(ChatMessageTable.TABLE_NAME).add(chatMessage).addOnSuccessListener(
                documentReference -> {
                    if(chatMessage.getType()==ChatMessage.IMAGE_TYPE)
                    {
                        String ChatMessageId = documentReference.getId();
                        List<Task<byte[]>> uploadTasks = new ArrayList<>();
                        successCount = 0;
                        for (uploadImage = 0; uploadImage<listUploadImage.size(); uploadImage ++) {
                            String extension = "jpg";

                            StorageReference imageRef = firebaseStorage.getReference().child(ChatMessageTable.TABLE_NAME).child(ChatMessageId + "/Image" + Integer.toString(uploadImage) + "." + extension);
                            UploadTask uploadTask = imageRef.putBytes(listUploadImage.get(uploadImage));
                            Task<Uri> task = uploadTask.continueWithTask(singleTaskUpload -> {
                                successCount++;
                                if (!singleTaskUpload.isSuccessful()) {
                                    Exception e = singleTaskUpload.getException();
                                    listener.OnFinishAddChatMessage(null,e);
                                }
                                return imageRef.getDownloadUrl();
                            });
                        }
                    }
                    else{
                        listener.OnFinishAddChatMessage(conversation,null);
                    }
                }
        ).addOnFailureListener(e -> {listener.OnFinishAddChatMessage(null,e);});
    }
    @Override
    public void listenMessageChange(String idConversation, OnListeningMessageChangeListener listener) {
        db.collection(ChatMessageTable.TABLE_NAME).whereEqualTo(ChatMessageTable.CONVERSATION_ID,idConversation).addSnapshotListener((value, error) -> {
            if(error!=null)
                listener.OnListeningMessageChange(null,false,error);
            else
            {
                assert value != null;
                int totalChatMessage = value.getDocumentChanges().size();
                for (int i = 0; i < value.getDocumentChanges().size();i++)
                {
                    DocumentChange  dc = value.getDocumentChanges().get(i);
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        ChatMessage chatMessage = dc.getDocument().toObject(ChatMessage.class);
                        chatMessage.setIdMessage(dc.getDocument().getId());
                        if(chatMessage.getType() == ChatMessage.IMAGE_TYPE)
                            chatMessage.setDownloadedImage(new ArrayList<>());
                        listener.OnListeningMessageChange(chatMessage, i + 1 == totalChatMessage, null);

                    }
                }
            }
        });
    }

}
