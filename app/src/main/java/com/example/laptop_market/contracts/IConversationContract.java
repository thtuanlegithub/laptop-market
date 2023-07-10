package com.example.laptop_market.contracts;

import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.chatMessage.ChatMessage;
import com.example.laptop_market.model.conversation.Conversation;

import java.util.ArrayList;

public interface IConversationContract {
    interface Model{
        //region Load list listener lại cuộc trò chuyện khi có sự thay đổi
        void LoadListeningListConversation(FinishReloadAllListConversationListener listener);
        interface FinishReloadAllListConversationListener{
            void FinishReloadAllListConversation(Conversation conversation, int type, Exception ex, boolean isLastAddedConversation);
        }
        //endregion
        //region lấy ra conversation
        void getAConversation(String ReceivedAccountid, FinishGetAConversationListener listener);
        interface FinishGetAConversationListener{
            void FinishGetAConvesation(Conversation conversation, Exception e);
        }
        //endregion
        //region tạo conversation
        void createConversation(Conversation conversation, FinishCreateConversationListener listener);
        interface FinishCreateConversationListener{
            void FinishCreateConversation(String conversationId, Exception e);
        }
        void UpdateSeenConversationStatus(Conversation conversation);
        //endregion
        //region load numberOfConversationNotSeen
        void getNumberOfNotSeenMessage();
        interface FinishGetNumberOfNotSeenMessageListener{
            void FinishGetNumberOfNotSeenMessage(int numberOfMessage);
        }

        //endregion
    }
    interface Presenter{
        interface ConversationListActivityPresenter{
            // Load lại cuộc trò chuyện khi có sự thay đổi
            void LoadListeningNewListConversation();
            // Update trạng thái trong conversation
            void UpdateSeenConversationStatus(Conversation conversation);

        }
        interface ConversationDetailActivityPresenter{
            // Load toàn bộ cuộc trò chuyện của người dùng
            void LoadAllChatMessageInConversationFromListConversation(Conversation conversation);
            // Load thông tin account
            void LoadAllChatMessageInConversationFromPostDetail(Account account);
            // Click Gửi tin nhắn
            void ClickSendMessage(ChatMessage chatMessage);
        }
    }
    interface View{
        interface ConversationListActivityView{
            // XỬ lí khi lỗi

            void failedLoading(Exception ex);
            // Cập nhật lại list Conversation đó;
            void LoadConversationUI(Conversation conversation, int type, boolean isLastAddedConversation);
        }
        interface ConversationDetailActivityView{
            // XỬ lí khi lỗi
            void failedLoading(Exception ex);
            // Load toàn bộ cuộc trò chuyện của người dùng
            void LoadChatMessageInConversationUI(ChatMessage chatMessage, boolean isAddMessage, boolean isLastAddedChatMessage);
            void LoadAccountMessageInConvesationUI(Account account);
            void SendMessageSuccess(Conversation conversation);
        }
    }
}
