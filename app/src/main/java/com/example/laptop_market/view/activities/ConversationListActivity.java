package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IConversationContract;
import com.example.laptop_market.model.conversation.Conversation;
import com.example.laptop_market.presenter.activities.ConversationListActivityPresenter;
import com.example.laptop_market.utils.listeners.IConversationListener;
import com.example.laptop_market.utils.tables.ConversationTable;
import com.example.laptop_market.view.adapters.ConversationAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class ConversationListActivity extends AppCompatActivity implements IConversationListener, IConversationContract.View.ConversationListActivityView {
    private RecyclerView listConversationRecyclerView;
    private ImageView bttBackConversation;
    private ConversationAdapter conversationAdapter;
    private ArrayList<Conversation> listConversations;
    private TextView textViewNotConversation;
    private ProgressBar progressBar;
    private IConversationContract.Presenter.ConversationListActivityPresenter conversationListActivityPresenter;
    private boolean backFromConversationDetail = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        conversationListActivityPresenter = new ConversationListActivityPresenter(this);
        listConversationRecyclerView = findViewById(R.id.listConversationRecyclerView);
        bttBackConversation = findViewById(R.id.bttBackConversation);
        textViewNotConversation = findViewById(R.id.textViewNotConversation);
        progressBar = findViewById(R.id.progressBar);
        listConversations = new ArrayList<>();
        conversationAdapter = new ConversationAdapter(listConversations,this);
        listConversationRecyclerView.setAdapter(conversationAdapter);
        setListener();
        setConversationReloadingListener();
    }
    private void setListener()
    {
        bttBackConversation.setOnClickListener(view -> {
            finish();
        });
    }
    private void setConversationReloadingListener()
    {
        listConversationRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        conversationListActivityPresenter.LoadListeningNewListConversation();
    }
    @Override
    public void onConversationClicked(Conversation conversation) {
        Intent intent = new Intent(getApplicationContext(),ConversationDetailActivity.class);
        intent.putExtra(ConversationTable.TABLE_NAME,conversation);
        backFromConversationDetail = true;
        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
        startActivity(intent,bundle);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(backFromConversationDetail)
        {
            this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            backFromConversationDetail = false;
        }
    }
    @Override
    public void failedLoading(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void LoadConversationUI(Conversation conversation, int type, boolean isLastAddedConversation) {
        if(type == Conversation.ADD_LIST_CONVERSATION)
        {
            if(conversation!=null)
                this.listConversations.add(conversation);
        }
        else if(type == Conversation.MODIFY_LIST_CONVERSATION)
        {
            for(int i=0;i<listConversations.size();i++)
            {
                if(listConversations.get(i).getConversationId().equals(conversation.getConversationId()))
                {
                    this.listConversations.get(i).setLastMessage(conversation.getLastMessage());
                    this.listConversations.get(i).setLastMessageTime(conversation.getLastMessageTime());
                    //conversations.get(i).conversationSeen=documentChange.getDocument().getString(Constants.KEY_SEEN_MESSAGE_ID);
                    break;
                }
            }
        }
        Collections.sort(listConversations,(obj1, obj2)->obj2.getLastMessageTime().compareTo(obj1.getLastMessageTime()));
        conversationAdapter.notifyDataSetChanged();
        if(isLastAddedConversation) {
            listConversationRecyclerView.smoothScrollToPosition(0);
            listConversationRecyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            LoadConversation(this.listConversations.size());
        }
    }
    private void LoadConversation(int length)
    {
        if(length==0) {
            textViewNotConversation.setVisibility(View.VISIBLE);
            listConversationRecyclerView.setVisibility(View.GONE);
        }
        else{
            textViewNotConversation.setVisibility(View.GONE);
            listConversationRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}