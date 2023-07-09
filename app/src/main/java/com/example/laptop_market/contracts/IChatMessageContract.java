package com.example.laptop_market.contracts;

import android.net.Uri;

import com.example.laptop_market.model.chatMessage.ChatMessage;
import com.example.laptop_market.model.conversation.Conversation;

import java.util.ArrayList;

public interface IChatMessageContract {
    interface Model{
        //region Thêm một cuộc trò chuyện mới
        void LoadImage(ChatMessage chatMessage, OnFinishDownloadImageListener listener);
        interface OnFinishDownloadImageListener{
            void OnFinishDownloadImage(ChatMessage chatMessage, Exception ex);
        }
        void addChatMessage(ChatMessage chatMessage, OnFinishAddChatMessageListener listener);
        interface OnFinishAddChatMessageListener{
            void OnFinishAddChatMessage(Conversation conversation, Exception ex);
        }
        //endregion

        //region kiểm tra sự thay đổi trong danh sách tin nhắn
        void listenMessageChange(String idConversation, OnListeningMessageChangeListener listener);
        interface OnListeningMessageChangeListener{
            void OnListeningMessageChange(ChatMessage chatMessage,boolean isAddMessage, boolean isLastChatMessage, Exception ex);
        }
        //endregion
    }
}
