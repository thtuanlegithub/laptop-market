package com.example.laptop_market.view.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
            if(chatMessage.getDownloadedImage().size()==0)
            {
                for(int i=0;i<chatMessage.getNumberOfPicture();i++)
                {
                    chatMessage.getDownloadedImage().add(BitmapFactory.decodeResource(binding.getRoot().getResources(), R.drawable.slide_show1));
                }
            }
            switch (chatMessage.getNumberOfPicture())
            {
                case 1:
                    binding.imageView1.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                    binding.imageView2.setVisibility(View.GONE);
                    binding.imageView3.setVisibility(View.GONE);
                    binding.imageView4.setVisibility(View.GONE);
                    binding.imageView5.setVisibility(View.GONE);
                    binding.imageView6.setVisibility(View.GONE);
                    break;
                case 2:
                    binding.imageView1.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                    binding.imageView2.setVisibility(View.GONE);
                    binding.imageView3.setImageBitmap(chatMessage.getDownloadedImage().get(1));
                    binding.imageView4.setVisibility(View.GONE);
                    binding.imageView5.setVisibility(View.GONE);
                    binding.imageView6.setVisibility(View.GONE);
                    break;
                case 3:
                    binding.imageView1.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                    binding.imageView2.setVisibility(View.GONE);
                    binding.imageView3.setImageBitmap(chatMessage.getDownloadedImage().get(1));
                    binding.imageView4.setVisibility(View.GONE);
                    binding.imageView5.setImageBitmap(chatMessage.getDownloadedImage().get(2));
                    binding.imageView6.setVisibility(View.GONE);
                    break;
                case 4:
                    binding.imageView1.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                    binding.imageView2.setImageBitmap(chatMessage.getDownloadedImage().get(2));
                    binding.imageView3.setImageBitmap(chatMessage.getDownloadedImage().get(1));
                    binding.imageView4.setImageBitmap(chatMessage.getDownloadedImage().get(3));
                    binding.imageView5.setVisibility(View.GONE);
                    binding.imageView6.setVisibility(View.GONE);
                    break;
                case 5:
                    binding.imageView1.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                    binding.imageView2.setImageBitmap(chatMessage.getDownloadedImage().get(3));
                    binding.imageView3.setImageBitmap(chatMessage.getDownloadedImage().get(1));
                    binding.imageView4.setImageBitmap(chatMessage.getDownloadedImage().get(4));
                    binding.imageView5.setImageBitmap(chatMessage.getDownloadedImage().get(2));
                    binding.imageView6.setVisibility(View.GONE);
                    break;
                default:
                    binding.imageView1.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                    binding.imageView2.setImageBitmap(chatMessage.getDownloadedImage().get(3));
                    binding.imageView3.setImageBitmap(chatMessage.getDownloadedImage().get(1));
                    binding.imageView4.setImageBitmap(chatMessage.getDownloadedImage().get(4));
                    binding.imageView5.setImageBitmap(chatMessage.getDownloadedImage().get(2));
                    binding.imageView6.setImageBitmap(chatMessage.getDownloadedImage().get(5));
                    break;
            }
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

            if(chatMessage.getDownloadedImage().size()==0)
            {
                switch (chatMessage.getNumberOfPicture())
                {
                    case 1:
                        binding.imageView3.setVisibility(View.VISIBLE);
                        binding.imageView1.setVisibility(View.GONE);
                        binding.imageView2.setVisibility(View.GONE);
                        binding.imageView4.setVisibility(View.GONE);
                        binding.imageView5.setVisibility(View.GONE);
                        binding.imageView6.setVisibility(View.GONE);
                        break;
                    case 2:
                        binding.imageView2.setVisibility(View.VISIBLE);
                        binding.imageView3.setVisibility(View.VISIBLE);
                        binding.imageView1.setVisibility(View.GONE);
                        binding.imageView4.setVisibility(View.GONE);
                        binding.imageView5.setVisibility(View.GONE);
                        binding.imageView6.setVisibility(View.GONE);
                        break;
                    case 3:
                        binding.imageView1.setVisibility(View.VISIBLE);
                        binding.imageView2.setVisibility(View.VISIBLE);
                        binding.imageView3.setVisibility(View.VISIBLE);
                        binding.imageView4.setVisibility(View.GONE);
                        binding.imageView5.setVisibility(View.GONE);
                        binding.imageView6.setVisibility(View.GONE);
                        break;
                    case 4:
                        binding.imageView1.setVisibility(View.GONE);
                        binding.imageView2.setVisibility(View.VISIBLE);
                        binding.imageView3.setVisibility(View.VISIBLE);
                        binding.imageView4.setVisibility(View.GONE);
                        binding.imageView5.setVisibility(View.VISIBLE);
                        binding.imageView6.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        binding.imageView1.setVisibility(View.VISIBLE);
                        binding.imageView2.setVisibility(View.VISIBLE);
                        binding.imageView3.setVisibility(View.VISIBLE);
                        binding.imageView4.setVisibility(View.VISIBLE);
                        binding.imageView5.setVisibility(View.VISIBLE);
                        binding.imageView6.setVisibility(View.GONE);
                        break;
                    default:
                        binding.imageView1.setVisibility(View.VISIBLE);
                        binding.imageView2.setVisibility(View.VISIBLE);
                        binding.imageView3.setVisibility(View.VISIBLE);
                        binding.imageView4.setVisibility(View.VISIBLE);
                        binding.imageView5.setVisibility(View.VISIBLE);
                        binding.imageView5.setVisibility(View.VISIBLE);
                        break;
                }
            }
            else {
                switch (chatMessage.getNumberOfPicture()) {
                    case 1:
                        Glide.with(binding.getRoot().getContext())
                                .load(chatMessage.getDownloadedImage().get(0))
                                .override(180, 180)
                                .into(binding.imageView3);
                        binding.imageView1.setVisibility(View.GONE);
                        binding.imageView2.setVisibility(View.GONE);
                        binding.imageView4.setVisibility(View.GONE);
                        binding.imageView5.setVisibility(View.GONE);
                        binding.imageView6.setVisibility(View.GONE);
                        break;
                    case 2:
                        Glide.with(binding.getRoot().getContext())
                                .load(chatMessage.getDownloadedImage().get(1))
                                .override(180, 180)
                                .into(binding.imageView2);
                        Glide.with(binding.getRoot().getContext())
                                .load(chatMessage.getDownloadedImage().get(0))
                                .override(180, 180)
                                .into(binding.imageView3);
                        binding.imageView1.setVisibility(View.GONE);
                        binding.imageView4.setVisibility(View.GONE);
                        binding.imageView5.setVisibility(View.GONE);
                        binding.imageView6.setVisibility(View.GONE);
                        break;
                    case 3:
                        binding.imageView1.setImageBitmap(ConvertUriToByte(chatMessage.getDownloadedImage().get(2)));
                        binding.imageView2.setImageBitmap(ConvertUriToByte(chatMessage.getDownloadedImage().get(1)));
                        binding.imageView3.setImageBitmap(ConvertUriToByte(chatMessage.getDownloadedImage().get(0)));
                        binding.imageView4.setVisibility(View.GONE);
                        binding.imageView5.setVisibility(View.GONE);
                        binding.imageView6.setVisibility(View.GONE);
                        break;
                    case 4:
                        binding.imageView1.setVisibility(View.GONE);
                        binding.imageView2.setImageBitmap(chatMessage.getDownloadedImage().get(1));
                        binding.imageView3.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                        binding.imageView4.setVisibility(View.GONE);
                        binding.imageView5.setImageBitmap(chatMessage.getDownloadedImage().get(3));
                        binding.imageView6.setImageBitmap(chatMessage.getDownloadedImage().get(2));
                        break;
                    case 5:
                        binding.imageView1.setImageBitmap(chatMessage.getDownloadedImage().get(2));
                        binding.imageView2.setImageBitmap(chatMessage.getDownloadedImage().get(1));
                        binding.imageView3.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                        binding.imageView4.setImageBitmap(chatMessage.getDownloadedImage().get(4));
                        binding.imageView5.setImageBitmap(chatMessage.getDownloadedImage().get(3));
                        binding.imageView6.setVisibility(View.GONE);
                        break;
                    default:
                        binding.imageView1.setImageBitmap(chatMessage.getDownloadedImage().get(2));
                        binding.imageView2.setImageBitmap(chatMessage.getDownloadedImage().get(1));
                        binding.imageView3.setImageBitmap(chatMessage.getDownloadedImage().get(0));
                        binding.imageView4.setImageBitmap(chatMessage.getDownloadedImage().get(5));
                        binding.imageView5.setImageBitmap(chatMessage.getDownloadedImage().get(4));
                        binding.imageView6.setImageBitmap(chatMessage.getDownloadedImage().get(3));
                        break;
                }
            }
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
    }
}
