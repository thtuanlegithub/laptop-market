package com.example.laptop_market.model.conversation;

import com.example.laptop_market.contracts.IConversationContract;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.ConversationTable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConversationModel implements IConversationContract.Model {
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private int totalConversation = 0;
    private ArrayList<Conversation> listConversations;
    private int count1;
    private int count2;
    boolean isfinish1;
    boolean isfinish2;
    public ConversationModel()
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        db = FirebaseFirestore.getInstance();
        listConversations = new ArrayList<>();
    }
    @Override
    public void LoadListeningListConversation(FinishReloadAllListConversationListener listener) {
        db.collection(ConversationTable.TABLE_NAME)
                .whereEqualTo(ConversationTable.PERSON_ONE_ID,firebaseUser.getUid())
                .addSnapshotListener((value,error) -> {
                    count1 = 0;
                    if(error!=null)
                    {
                        listener.FinishReloadAllListConversation(null,0,error,false);
                    }
                    else {
                        for (int i =0; i< value.getDocumentChanges().size();i++) {
                            DocumentChange dc = value.getDocumentChanges().get(i);
                            switch (dc.getType()) {
                                case ADDED:
                                    Conversation conversation = dc.getDocument().toObject(Conversation.class);
                                    conversation.setConversationId(dc.getDocument().getId());
                                    conversation.setSenderId(firebaseUser.getUid());
                                    if(conversation.getPersonOneId() == firebaseUser.getUid())
                                        conversation.setReceivedId(conversation.getPersonOneId());
                                    else
                                        conversation.setReceivedId(conversation.getPersonTwoId());
                                    db.collection(AccountTable.TABLE_NAME).document(conversation.getReceivedId()).get().addOnCompleteListener(task -> {
                                        if(!task.isSuccessful())
                                            listener.FinishReloadAllListConversation(null,0, task.getException(),false);
                                        else
                                        {
                                            conversation.setConversationName(task.getResult().getString(AccountTable.ACCOUNT_NAME));
                                            listener.FinishReloadAllListConversation(conversation,Conversation.ADD_LIST_CONVERSATION,null, ++count1 == value.getDocumentChanges().size());
                                        }
                                    });
                                    break;
                                case MODIFIED:
                                    Conversation conversation1 = dc.getDocument().toObject(Conversation.class);
                                    conversation1.setConversationId(dc.getDocument().getId());
                                    listener.FinishReloadAllListConversation(conversation1, Conversation.MODIFY_LIST_CONVERSATION, null, true);
                                    break;
                                case REMOVED:
                                    // Xử lý khi có Conversation bị xóa
                                    break;
                            }
                        }
                    }
                });
        db.collection(ConversationTable.TABLE_NAME)
                .whereEqualTo(ConversationTable.PERSON_TWO_ID,firebaseUser.getUid())
                .addSnapshotListener((value,error) -> {
                    count2 = 0;
                    if(error!=null)
                    {
                        listener.FinishReloadAllListConversation(null,0,error,false);
                    }
                    else {
                        for (int i =0; i< value.getDocumentChanges().size();i++) {
                            DocumentChange dc =value.getDocumentChanges().get(i);
                            switch (dc.getType()) {
                                case ADDED:
                                    Conversation conversation = dc.getDocument().toObject(Conversation.class);
                                    conversation.setConversationId(dc.getDocument().getId());
                                    conversation.setSenderId(firebaseUser.getUid());
                                    conversation.setReceivedId(conversation.getPersonOneId());
                                    db.collection(AccountTable.TABLE_NAME).document(conversation.getReceivedId()).get().addOnCompleteListener(task -> {
                                        if(!task.isSuccessful())
                                            listener.FinishReloadAllListConversation(null,0, task.getException(),false);
                                        else
                                        {
                                            conversation.setConversationName(task.getResult().getString(AccountTable.ACCOUNT_NAME));
                                            listener.FinishReloadAllListConversation(conversation,Conversation.ADD_LIST_CONVERSATION,null, ++count2 == value.getDocumentChanges().size());
                                        }
                                    });
                                    break;
                                case MODIFIED:
                                    Conversation conversation1 = dc.getDocument().toObject(Conversation.class);
                                    conversation1.setConversationId(dc.getDocument().getId());
                                    listener.FinishReloadAllListConversation(conversation1,Conversation.MODIFY_LIST_CONVERSATION,null, true);
                                    break;
                                case REMOVED:
                                    // Xử lý khi có Conversation bị xóa
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    public void getAConversation(String ReceivedAccountid, FinishGetAConversationListener listener) {
        isfinish1 = false;
        isfinish2 = false;
        db.collection(ConversationTable.TABLE_NAME)
                .whereEqualTo(ConversationTable.PERSON_ONE_ID,firebaseUser.getUid())
                .whereEqualTo(ConversationTable.PERSON_TWO_ID,ReceivedAccountid)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        QuerySnapshot querySnapshot = task.getResult();
                        if(querySnapshot.getDocuments().size()==0)
                        {
                            LoadAConversation(null,listener);
                            return;
                        }
                            List<Conversation> conversations = new ArrayList<>();
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        Conversation conversation = documentSnapshot.toObject(Conversation.class);
                        conversation.setConversationId(documentSnapshot.getId());
                        LoadAConversation(conversation,listener);
                    }
                    else
                        listener.FinishGetAConvesation(null, task.getException());
                });
        db.collection(ConversationTable.TABLE_NAME)
                .whereEqualTo(ConversationTable.PERSON_TWO_ID,firebaseUser.getUid())
                .whereEqualTo(ConversationTable.PERSON_ONE_ID,ReceivedAccountid)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        QuerySnapshot querySnapshot = task.getResult();
                        if(querySnapshot.getDocuments().size()==0)
                        {
                            LoadAConversation(null,listener);
                            return;
                        }
                        List<Conversation> conversations = new ArrayList<>();
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        Conversation conversation = documentSnapshot.toObject(Conversation.class);
                        conversation.setConversationId(documentSnapshot.getId());
                        LoadAConversation(conversation,listener);
                    }
                    else
                        listener.FinishGetAConvesation(null, task.getException());
                });
    }
    private void LoadAConversation(Conversation conversation, FinishGetAConversationListener listener)
    {
        if(conversation!=null)
            listener.FinishGetAConvesation(conversation,null);
    }

    @Override
    public void createConversation(Conversation conversation, FinishCreateConversationListener listener) {
        db.collection(ConversationTable.TABLE_NAME).add(conversation).addOnCompleteListener(task -> {
            if(task.isSuccessful())
                listener.FinishCreateConversation(task.getResult().getId(),null);
            else
                listener.FinishCreateConversation(null,null);
        });
    }

}
