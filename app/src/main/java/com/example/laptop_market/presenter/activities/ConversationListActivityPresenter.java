package com.example.laptop_market.presenter.activities;

import com.example.laptop_market.contracts.IConversationContract;
import com.example.laptop_market.model.conversation.Conversation;
import com.example.laptop_market.model.conversation.ConversationModel;

import java.util.ArrayList;

public class ConversationListActivityPresenter implements IConversationContract.Presenter.ConversationListActivityPresenter {
    private IConversationContract.Model conversationModel;
    private IConversationContract.View.ConversationListActivityView view;
    public ConversationListActivityPresenter(IConversationContract.View.ConversationListActivityView view)
    {
        conversationModel = new ConversationModel();
        this.view = view;
    }


    @Override
    public void LoadListeningNewListConversation() {
        conversationModel.LoadListeningListConversation((conversation, type, ex, isLastConversation) -> {
            if(ex!=null)
                view.failedLoading(ex);
            else
                view.LoadConversationUI(conversation,type,isLastConversation);
        });
    }
}
