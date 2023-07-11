package com.example.laptop_market.view.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laptop_market.R;
import com.example.laptop_market.databinding.ItemContainerConversationBinding;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.chatMessage.ChatMessage;
import com.example.laptop_market.model.conversation.Conversation;
import com.example.laptop_market.utils.listeners.IConversationListener;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>{
    private final List<Conversation> listConversation;
    private final IConversationListener conversationListener;
    public final int HAVE_SEEN_MESSAGE = 1;
    public final int HAVE_NOT_SEEN_MESSAGE = 2;

    public ConversationAdapter(List<Conversation> listConversation,IConversationListener conversationListener) {
        this.conversationListener=conversationListener;
        this.listConversation = listConversation;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(ItemContainerConversationBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {

        holder.setData(listConversation.get(position),HAVE_NOT_SEEN_MESSAGE);
    }
    /*    @Override
        public int getItemViewType(int position) {
            if(chatMessageList.get(position).conversationSeen.equals(userId))
            {
                return HAVE_NOT_SEEN_MESSAGE;
            }
            else
                return HAVE_SEEN_MESSAGE;
        }*/
    @Override
    public int getItemCount() {
        return listConversation.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder{
        ItemContainerConversationBinding binding;
        ConversationViewHolder(ItemContainerConversationBinding itemContainerConversationBinding)
        {
            super(itemContainerConversationBinding.getRoot());
            binding=itemContainerConversationBinding;
        }
        void setData(Conversation conversation, int type)
        {
            /*if(type==HAVE_NOT_SEEN_MESSAGE)
            {
                binding.textName.setTypeface(Typeface.DEFAULT_BOLD);
                binding.textRecentMessage.setTypeface(Typeface.DEFAULT_BOLD);
            }
            else
            {
                binding.textName.setTypeface(Typeface.DEFAULT);
                binding.textRecentMessage.setTypeface(Typeface.DEFAULT);
            }*/
            if(conversation.getConversationImage()==null || conversation.getConversationImage().isEmpty())
                Glide.with(binding.getRoot()).load(R.drawable.avatar_basic).into(binding.imageProfile);
            else
                Glide.with(binding.getRoot()).load(conversation.getConversationImage()).into(binding.imageProfile);
            binding.textConversationName.setText(conversation.getConversationName());
            if(conversation.getPersonNotSeenId() != null) {
                if (conversation.getPersonNotSeenId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    binding.textRecentMessage.setTypeface(Typeface.DEFAULT_BOLD);
                    binding.textRecentMessage.setTextColor(Color.BLACK);
                } else {
                    binding.textRecentMessage.setTypeface(Typeface.DEFAULT);
                    binding.textRecentMessage.setTextColor(binding.getRoot().getResources().getColor(R.color.secondary_text));
                }
            }
            if(conversation.getLastMessageTime()!=null) {
                SimpleDateFormat sdfDate;
                Calendar calendarNow = Calendar.getInstance();
                int dateNow = calendarNow.get(Calendar.DAY_OF_MONTH);
                int monthNow = calendarNow.get(Calendar.MONTH);
                int yearNow = calendarNow.get(Calendar.YEAR);
                Calendar calendarConversation = Calendar.getInstance();
                calendarConversation.setTime(conversation.getLastMessageTime());
                int dateConversation = calendarConversation.get(Calendar.DATE);
                int monthConversation = calendarConversation.get(Calendar.MONTH);
                int yearConversation = calendarConversation.get(Calendar.YEAR);
                String messageDate;
                if (dateNow == dateConversation && monthNow == monthConversation && yearConversation == yearNow) {
                    sdfDate = new SimpleDateFormat("HH:mm");
                } else if (yearConversation == yearNow) {
                    sdfDate = new SimpleDateFormat("dd/MM");
                } else
                    sdfDate = new SimpleDateFormat("dd/MM/yyyy");
                messageDate = sdfDate.format(conversation.getLastMessageTime());
                binding.textConversationDate.setText(messageDate);
            }
            binding.textConversationName.setText(conversation.getConversationName());
            binding.textRecentMessage.setText(conversation.getLastMessage());
            binding.getRoot().setOnClickListener(view -> {
                conversationListener.onConversationClicked(conversation);
            });
        }

    }
    private Bitmap getConversationImage(String encodedImage)
    {
        byte[] bytes= Base64.decode(encodedImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length);
    }

}
