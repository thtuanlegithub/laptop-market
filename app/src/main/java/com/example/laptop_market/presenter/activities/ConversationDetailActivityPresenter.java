package com.example.laptop_market.presenter.activities;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.IChatMessageContract;
import com.example.laptop_market.contracts.IConversationContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.model.chatMessage.ChatMessage;
import com.example.laptop_market.model.chatMessage.ChatMessageModel;
import com.example.laptop_market.model.conversation.Conversation;
import com.example.laptop_market.model.conversation.ConversationModel;
import com.example.laptop_market.utils.elses.DownloadImageFunction;

public class ConversationDetailActivityPresenter implements IConversationContract.Presenter.ConversationDetailActivityPresenter {
    private IConversationContract.Model conversationModel;
    private IChatMessageContract.Model chatMessageModel;
    private IAccountContract.Model accountModel;
    private Context context;
    private IConversationContract.View.ConversationDetailActivityView view;
    public ConversationDetailActivityPresenter(IConversationContract.View.ConversationDetailActivityView view,Context context)
    {
        this.view =view;
        conversationModel = new ConversationModel();
        chatMessageModel = new ChatMessageModel(context);
        accountModel = new AccountModel();
        this.context = context;
    }
    @Override
    public void LoadAllChatMessageInConversationFromListConversation(Conversation conversation) {
        accountModel.LoadAccountWithId(conversation.getReceivedId(),(account, error) -> {
            if(error!=null)
                view.failedLoading(error);
            else
                view.LoadAccountMessageInConvesationUI(account);
        });
        chatMessageModel.listenMessageChange(conversation.getConversationId(),(chatMessage, isAddMessage, isLastChatMessage, ex) -> {
            if(ex!=null)
                view.failedLoading(ex);
            else {
                view.LoadChatMessageInConversationUI(chatMessage, isAddMessage, isLastChatMessage);
            }
        });
    }

    @Override
    public void LoadAllChatMessageInConversationFromPostDetail(Account account) {
        conversationModel.getAConversation(account.getAccountID(),(conversation, e) -> {
            if(e!=null)
                view.failedLoading(e);
            else {
                view.SendMessageSuccess(conversation);
                if(conversation!=null) {
                    chatMessageModel.listenMessageChange(conversation.getConversationId(), new IChatMessageContract.Model.OnListeningMessageChangeListener() {
                        @Override
                        public void OnListeningMessageChange(ChatMessage chatMessage,boolean isAddNewMessage, boolean isLastChatMessage, Exception ex) {
                            if (ex != null)
                                view.failedLoading(ex);
                            else {
                                view.LoadChatMessageInConversationUI(chatMessage, isAddNewMessage, isLastChatMessage);
                            }
                        }
                    });
                }
                else{
                    view.LoadChatMessageInConversationUI(null, true,true);

                }
            }
        });
    }

    @Override
    public void ClickSendMessage(ChatMessage chatMessage) {
        chatMessageModel.addChatMessage(chatMessage, new IChatMessageContract.Model.OnFinishAddChatMessageListener() {
            @Override
            public void OnFinishAddChatMessage(Conversation conversation, Exception ex) {
                if(ex!=null)
                    view.failedLoading(ex);
                else
                    view.SendMessageSuccess(conversation);
            }
        });
    }
}
