package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IConversationContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.chatMessage.ChatMessage;
import com.example.laptop_market.model.conversation.Conversation;
import com.example.laptop_market.presenter.activities.ConversationDetailActivityPresenter;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.ConversationTable;
import com.example.laptop_market.view.adapters.ChatMessageAdapter;
import com.example.laptop_market.view.adapters.ImageForNewPostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ConversationDetailActivity extends AppCompatActivity implements IConversationContract.View.ConversationDetailActivityView {
    private static final int PICK_IMAGE_REQUEST = 1;
    private RecyclerView conversationRecyclerView;
    private IConversationContract.Presenter.ConversationDetailActivityPresenter presenter;
    private ArrayList<byte[]> listImages;
    private ImageView bttBackConversation;
    private ImageView bttOpenImage;
    private ImageView bttSendMessage;
    private ArrayList<ChatMessage> listChatMessages;
    private ChatMessageAdapter chatMessageAdapter;
    private TextView textReceivedAccName;
    private Conversation conversation;
    private RoundedImageView imageProfile;
    private RecyclerView rcvImageForConversationDetail;
    private List<Bitmap> listBitmapImages;
    private EditText inputMessage;
    private ImageForNewPostAdapter imageForConversationDetailAdapter;
    private LinearLayout rcvImageForConversationLayout;

    private Account receiverAccount;
    private ProgressBar progressBar;
    private int count = 0;
    private ArrayList<Boolean> isNotHaveConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_detail);
        presenter = new ConversationDetailActivityPresenter(this,getApplicationContext());
        conversationRecyclerView = findViewById(R.id.conversationRecyclerView);
        bttBackConversation = findViewById(R.id.bttBackConversation);
        bttOpenImage = findViewById(R.id.bttOpenImage);
        textReceivedAccName = findViewById(R.id.textReceivedAccName);
        bttSendMessage = findViewById(R.id.bttSendMessage);
        imageProfile = findViewById(R.id.imageProfile);
        progressBar = findViewById(R.id.progressBar);
        inputMessage = findViewById(R.id.inputMessage);
        rcvImageForConversationLayout = findViewById(R.id.rcvImageForConversationLayout);
        rcvImageForConversationDetail = findViewById(R.id.rcvImageForConversationDetail);
        listChatMessages = new ArrayList<>();
        listImages = new ArrayList<>();
        listBitmapImages = new ArrayList<>();
        isNotHaveConversation = new ArrayList<>();
        isNotHaveConversation.add(false);
        isNotHaveConversation.add(false);
        GridLayoutManager gridLayoutManagerImageForNewPost = new GridLayoutManager(this,1, RecyclerView.HORIZONTAL,false);
        rcvImageForConversationDetail.setLayoutManager(gridLayoutManagerImageForNewPost);
        imageForConversationDetailAdapter =  new ImageForNewPostAdapter(listBitmapImages, getApplicationContext(), position -> {
            // Xử lý sự kiện khi người dùng nhấn nút close
            listImages.remove(position);
            listBitmapImages.remove(position);
            if(listImages.size()==0) {
                rcvImageForConversationLayout.setVisibility(View.GONE);
                if(inputMessage.getText().toString().isEmpty())
                {
                    bttSendMessage.setVisibility(View.GONE);
                }
            }
            imageForConversationDetailAdapter.notifyDataSetChanged();
        },true);
        rcvImageForConversationDetail.setAdapter(imageForConversationDetailAdapter);
        initData();
        conversationRecyclerView.setAdapter(chatMessageAdapter);
        setListener();
    }
    private void initData()
    {
        Intent intent = getIntent();
        count = 0;
        conversation = (Conversation) intent.getSerializableExtra(ConversationTable.TABLE_NAME);
        receiverAccount = (Account)  intent.getSerializableExtra(AccountTable.TABLE_NAME);
        if(conversation != null) {
            chatMessageAdapter = new ChatMessageAdapter(listChatMessages, FirebaseAuth.getInstance().getCurrentUser().getUid());
            presenter.LoadAllChatMessageInConversationFromListConversation(conversation);
        }
        else {
            if(receiverAccount.getAvatar()!=null)
                Glide.with(getApplicationContext()).load(receiverAccount.getAvatar()).into(imageProfile);
            else
                Glide.with(getApplicationContext()).load(R.drawable.avatar_basic).into(imageProfile);
            chatMessageAdapter = new ChatMessageAdapter(listChatMessages,FirebaseAuth.getInstance().getCurrentUser().getUid());
            presenter.LoadAllChatMessageInConversationFromPostDetail(receiverAccount);
        }
        progressBar.setVisibility(View.VISIBLE);
        conversationRecyclerView.setVisibility(View.GONE);
    }
    private void setListener()
    {
        inputMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = inputMessage.getText().toString();
                if(text.isEmpty() && listImages.size() == 0) {
                    bttSendMessage.setVisibility(View.GONE);
                }
                else {
                    bttSendMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bttBackConversation.setOnClickListener(view -> {
            finish();
        });
        bttOpenImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });
        bttSendMessage.setOnClickListener(view -> {
            if(!inputMessage.getText().toString().isEmpty())
            {
                ChatMessage chatMessage = new ChatMessage(ChatMessage.TEXT_TYPE);
                if(conversation!=null) {
                    chatMessage.setConversationId(conversation.getConversationId());
                }
                else
                    chatMessage.setConversationId("");
                chatMessage.setReceiverAccountId(receiverAccount.getAccountID());
                chatMessage.setMessage(inputMessage.getText().toString());
                chatMessage.setTimeSendMesssage(new Date());
                chatMessage.setNumberOfPicture(0);
                presenter.ClickSendMessage(chatMessage);
            }
            if(listImages.size()>0)
            {
                listBitmapImages.clear();
                imageForConversationDetailAdapter.notifyDataSetChanged();
                rcvImageForConversationLayout.setVisibility(View.GONE);
                ChatMessage chatMessage = new ChatMessage(ChatMessage.IMAGE_TYPE);
                if(conversation!=null)
                    chatMessage.setConversationId(conversation.getConversationId());
                else
                    chatMessage.setConversationId("");
                chatMessage.setMessage("Đã gửi " + listImages.size() + " ảnh");
                chatMessage.setType(ChatMessage.IMAGE_TYPE);
                chatMessage.setListUploadImage(listImages);
                chatMessage.setNumberOfPicture(listImages.size());
                chatMessage.setTimeSendMesssage(new Date());
                presenter.ClickSendMessage(chatMessage);
            }
            inputMessage.setText("");
            bttSendMessage.setVisibility(View.GONE);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && (resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER)) {
            if (data.getData() != null) {
                // Xử lý một ảnh duy nhất
                if(listImages.size()==1)
                {
                    Toast.makeText(getApplicationContext(),"Hiện giờ chỉ hỗ trợ 1 ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                rcvImageForConversationLayout.setVisibility(View.VISIBLE);
                Uri uri = data.getData();
                listImages.add(ConvertUriToByte(uri));
                listBitmapImages.add(uriToBitmap(uri));
                bttSendMessage.setVisibility(View.VISIBLE);
                imageForConversationDetailAdapter.notifyDataSetChanged();
            } else {
                // Xử lý nhiều ảnh
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    rcvImageForConversationLayout.setVisibility(View.VISIBLE);
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        if(listImages.size()==1)
                        {
                            Toast.makeText(getApplicationContext(),"Hiện giờ chỉ hỗ trợ 1 ảnh", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        Uri uri = clipData.getItemAt(i).getUri();
                        listImages.add(ConvertUriToByte(uri));
                        listBitmapImages.add(uriToBitmap(uri));
                        bttSendMessage.setVisibility(View.VISIBLE);
                        imageForConversationDetailAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
    @Override
    public void failedLoading(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void LoadChatMessageInConversationUI(ChatMessage chatMessage, boolean isAddNewMessage, boolean isLastAddedChatMessage) {
        if(chatMessage!=null )
        {
            if(isAddNewMessage) {
                listChatMessages.add(chatMessage);
            }
            else
            {
                for(int i=0 ; i < listChatMessages.size();i++)
                {
                    if(listChatMessages.get(i).getIdMessage().equals(chatMessage.getIdMessage()))
                    {
                        listChatMessages.get(i).setImage(chatMessage.getImage());
                        chatMessageAdapter.notifyItemChanged(i);
                    }
                }
            }
        }
        if (isLastAddedChatMessage) {
            progressBar.setVisibility(View.GONE);
            conversationRecyclerView.setVisibility(View.VISIBLE);
        }
        Collections.sort(listChatMessages, Comparator.comparing(ChatMessage::getTimeSendMesssage));
        chatMessageAdapter.notifyItemRangeInserted(listChatMessages.size(), listChatMessages.size());
        conversationRecyclerView.scrollToPosition(listChatMessages.size() - 1);
        conversationRecyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void LoadAccountMessageInConvesationUI(Account account) {
        //chatMessageAdapter = new ChatMessageAdapter(listChatMessages,account.getAccountID());
        receiverAccount = account;
        textReceivedAccName.setText(account.getAccountName());
        if(account.getAvatar()!=null)
            Glide.with(getApplicationContext()).load(account.getAvatar()).into(imageProfile);
        else
            Glide.with(getApplicationContext()).load(R.drawable.avatar_basic).into(imageProfile);
    }

    @Override
    public void SendMessageSuccess(Conversation conversation) {
        if(this.conversation == null) {
            this.conversation = conversation;
            if(isNotHaveConversation.get(0) && isNotHaveConversation.get(1))
                presenter.LoadAllChatMessageInConversationFromPostDetail(receiverAccount);
        }
        if(conversation==null && count < 2) {
            isNotHaveConversation.set(count++,true);
        }
        listImages.clear();
        rcvImageForConversationLayout.setVisibility(View.GONE);
    }



    // Hàm để chuyển từ Uri sang Bitmap
    private Bitmap uriToBitmap(Uri uri)  {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    private byte[] ConvertUriToByte(Uri uri)
    {
        Bitmap bitmap = null;
        ContentResolver contentResolver = getContentResolver();
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            int previewWidth = 480;
            int previewHeight=bitmap.getHeight()*previewWidth/bitmap.getWidth();
            Bitmap preivewBitmap= Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
            ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
            preivewBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}