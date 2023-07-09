package com.example.laptop_market.view.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laptop_market.R;
import com.example.laptop_market.databinding.ItemContainerReceivedMessageBinding;
import com.example.laptop_market.databinding.ItemContainerReceivedMutipleImageMessageBinding;
import com.example.laptop_market.databinding.ItemContainerSenderMutipleImageMessageBinding;
import com.example.laptop_market.databinding.ItemContainerSentMessageBinding;
import com.example.laptop_market.model.chatMessage.ChatMessage;
import com.example.laptop_market.utils.elses.StringFormat;
import com.example.laptop_market.utils.tables.ChatMessageTable;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.view.activities.PictureDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ChatMessage> chatMessages;
    private List<Boolean> listIsShowTime;
    private final String accountSenderId;
    private Context context;
    public static final int VIEW_TEXT_SENT=1;
    public static final int VIEW_TEXT_RECEIVED=2;
    public static final int VIEW_IMAGE_SENT = 3;
    public static final int VIEW_IMAGE_RECEIVED = 4;
    public ChatMessageAdapter(List<ChatMessage> chatMessages, String accountSenderId) {
        this.chatMessages = chatMessages;
        this.accountSenderId = accountSenderId;
        listIsShowTime = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TEXT_SENT)
            return new SentMessageViewHolder(ItemContainerSentMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        else if (viewType == VIEW_TEXT_RECEIVED)
            return new ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        else if(viewType == VIEW_IMAGE_SENT)
            return  new SentImageMessageViewHolder(ItemContainerSenderMutipleImageMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        else
            return  new ReceivedImageMessageViewHolder(ItemContainerReceivedMutipleImageMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==VIEW_TEXT_SENT)
        {
            ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
        }
        else if(getItemViewType(position) == VIEW_TEXT_RECEIVED)
            ((ReceivedMessageViewHolder) holder).setData((chatMessages.get(position)));
        else if(getItemViewType(position) == VIEW_IMAGE_SENT)
            ((SentImageMessageViewHolder) holder).setData(chatMessages.get(position));
        else
            ((ReceivedImageMessageViewHolder) holder).setData(chatMessages.get(position));
    }
    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).getSenderAccountId().equals(accountSenderId))
        {
            if(chatMessages.get(position).getType() == ChatMessage.SENT_MESSAGE)
                return VIEW_TEXT_SENT;
            else
                return VIEW_IMAGE_SENT;
        }
        else
        {
            if(chatMessages.get(position).getType() == ChatMessage.SENT_MESSAGE)
                return VIEW_TEXT_RECEIVED;
            else
                return VIEW_IMAGE_RECEIVED;
        }
    }
    // Hàm cập nhật bitmap tại một vị trí
    public void updateBitmap(int position, ChatMessage chatMessage) {
        // Thực hiện cập nhật dữ liệu cho ViewHolder tại vị trí position
        chatMessages.set(position, chatMessage);

        // Thông báo cho RecyclerView cập nhật lại item tại vị trí position
        notifyItemChanged(position, "bitmap");
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        // Nếu đối tượng payload chứa thông tin về bitmap mới
        if (payloads.contains("bitmap")) {
            // Lấy bitmap mới từ danh sách bitmap
            ChatMessage chatMessage = chatMessages.get(position);
            if(getItemViewType(position)==VIEW_TEXT_SENT)
            {
                ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
            }
            else if(getItemViewType(position) == VIEW_TEXT_RECEIVED)
                ((ReceivedMessageViewHolder) holder).setData((chatMessages.get(position)));
            else if(getItemViewType(position) == VIEW_IMAGE_SENT)
                ((SentImageMessageViewHolder) holder).setData(chatMessages.get(position));
            else
                ((ReceivedImageMessageViewHolder) holder).setData(chatMessages.get(position));
        } else {
            // Nếu không có payload hoặc payload không chứa thông tin về bitmap mới
            // Thực hiện các xử lý bình thường để cập nhật dữ liệu cho ViewHolder
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    // Override phương thức onBindViewHolder() để cập nhật dữ liệu cho ViewHol

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerSentMessageBinding binding;
        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSendMessageBinding)
        {
            super(itemContainerSendMessageBinding.getRoot());
            binding=itemContainerSendMessageBinding;
        }
        void setData(ChatMessage chatMessage)
        {
            binding.textMessage.setText(chatMessage.getMessage());
            binding.textMessage.setOnClickListener(view -> {
                if(binding.textDateTime.getVisibility()==View.VISIBLE)
                {
                    binding.textDateTime.setVisibility(View.GONE);
                }
                else
                    binding.textDateTime.setVisibility(View.VISIBLE);
            });
            binding.textDateTime.setText(StringFormat.getReadableDateTime(chatMessage.getTimeSendMesssage()));
        }
    }
    static class ReceivedMessageViewHolder extends  RecyclerView.ViewHolder{
        private final ItemContainerReceivedMessageBinding binding;
        ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding itemContainerReceivedMessageBinding)
        {
            super(itemContainerReceivedMessageBinding.getRoot());
            binding=itemContainerReceivedMessageBinding;
        }
        void setData(ChatMessage chatMessage)
        {
            binding.textMessage.setText(chatMessage.getMessage());
            binding.textMessage.setOnClickListener(view -> {
                if(binding.textDateTime.getVisibility()==View.VISIBLE)
                {
                    binding.textDateTime.setVisibility(View.GONE);
                }
                else
                    binding.textDateTime.setVisibility(View.VISIBLE);
            });
            binding.textDateTime.setText(StringFormat.getReadableDateTime(chatMessage.getTimeSendMesssage()));
        }
    }
    static class ReceivedImageMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerReceivedMutipleImageMessageBinding binding;
        ReceivedImageMessageViewHolder(ItemContainerReceivedMutipleImageMessageBinding itemContainerReceivedMutipleImageMessageBinding)
        {
            super(itemContainerReceivedMutipleImageMessageBinding.getRoot());
            binding=itemContainerReceivedMutipleImageMessageBinding;
        }
        void setData(ChatMessage chatMessage)
        {
            if(chatMessage.getImage() != null)
            {
                Glide.with(binding.getRoot())
                        .load(chatMessage.getImage())
                        .into(binding.imageView1);
            }
            binding.imageView1.setOnClickListener(view -> {
                Intent intent = new Intent(binding.getRoot().getContext(), PictureDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isSingleImageFromURL",true);
                intent.putExtra(ChatMessageTable.IMAGE, chatMessage.getImage());
                binding.getRoot().getContext().startActivity(intent);

            });
        }
    }
    static class SentImageMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerSenderMutipleImageMessageBinding binding;
        SentImageMessageViewHolder(ItemContainerSenderMutipleImageMessageBinding itemContainerReceivedMutipleImageMessageBinding)
        {
            super(itemContainerReceivedMutipleImageMessageBinding.getRoot());
            binding=itemContainerReceivedMutipleImageMessageBinding;
        }
        void setData(ChatMessage chatMessage)
        {

            if(chatMessage.getImage() != null)
            {
                Glide.with(binding.getRoot())
                        .load(chatMessage.getImage())
                        .into(binding.imageView1);
            }
            binding.imageView1.setOnClickListener(view -> {
                Intent intent = new Intent(binding.getRoot().getContext(), PictureDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isSingleImageFromURL",true);
                intent.putExtra(ChatMessageTable.IMAGE, chatMessage.getImage());
                binding.getRoot().getContext().startActivity(intent);

            });

        }
        private Bitmap ConvertUriToByte(Bitmap bitmap)
        {
            ContentResolver contentResolver = binding.getRoot().getContext().getContentResolver();
                int previewWidth = 180;
                int previewHeight=bitmap.getHeight()*previewWidth/bitmap.getWidth();
                Bitmap preivewBitmap= Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
                ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                preivewBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                return preivewBitmap;
        }
        public static Bitmap getBitmapFromView(View view) {
            // Tạo một bản sao bitmap của ImageView
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            return bitmap;
        }
        private String encode_img(Bitmap bitmap)
        {
            int previewWidth = 720;
            int previewHeight=bitmap.getHeight()*previewWidth/bitmap.getWidth();
            Bitmap preivewBitmap= Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
            ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
            preivewBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(bytes,Base64.DEFAULT);

        }
    }
}
