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
        chatMessageModel.listenMessageChange(conversation.getConversationId(),(chatMessage, isLastChatMessage, ex) -> {
            if(ex!=null)
                view.failedLoading(ex);
            else {
                view.LoadChatMessageInConversationUI(chatMessage, isLastChatMessage);
                if(chatMessage.getType()==ChatMessage.IMAGE_TYPE) {
                    DownloadImageFunction downloadImageFunction = new DownloadImageFunction();
                    downloadImageFunction.DownloadImage(chatMessage, new DownloadImageFunction.FinishDownloadImageListener() {
                                @Override
                                public void FinishDownloadImage(ChatMessage chatMessage, Exception ex) {
                                    if (ex != null)
                                        view.failedLoading(ex);
                                    else {
                                        view.LoadImageInToConversationUI(chatMessage);
                                    }
                                }
                            }
                    );
                }
            }
        });
    }

    @Override
    public void LoadAllChatMessageInConversationFromPostDetail(Account account) {
        conversationModel.getAConversation(account.getAccountID(),(conversation, e) -> {
            if(e!=null)
                view.failedLoading(e);
            else {
                if(conversation!=null) {
                    view.SendMessageSuccess(conversation);
                    chatMessageModel.listenMessageChange(conversation.getConversationId(), new IChatMessageContract.Model.OnListeningMessageChangeListener() {
                        @Override
                        public void OnListeningMessageChange(ChatMessage chatMessage, boolean isLastChatMessage, Exception ex) {
                            if (ex != null)
                                view.failedLoading(ex);
                            else {
                                view.LoadChatMessageInConversationUI(chatMessage, isLastChatMessage);
                                if(chatMessage.getType()==ChatMessage.IMAGE_TYPE)
                                    chatMessageModel.LoadImage(chatMessage, new IChatMessageContract.Model.OnFinishDownloadImageListener() {
                                        @Override
                                        public void OnFinishDownloadImage(ChatMessage chatMessage, Exception ex) {
                                            if (ex != null)
                                                view.failedLoading(ex);
                                            else {
                                                view.LoadImageInToConversationUI(chatMessage);
                                            }
                                        }
                                    });
                            }
                        }
                    });
                }
                else{
                    view.LoadChatMessageInConversationUI(null, true);

                }
            }
        });
    }

    @Override
    public void ClickSendMessage(ChatMessage chatMessage) {
        if(chatMessage.getType() == ChatMessage.IMAGE_TYPE)
            chatMessageModel = new ChatMessageModel(context);
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
