package com.example.laptop_market.presenter.fragments;

import com.example.laptop_market.contracts.IConversationContract;
import com.example.laptop_market.model.conversation.ConversationModel;

public class HomeFragmentPresenter implements IConversationContract.Presenter.HomeFragmentPresenter {
    private IConversationContract.View.HomeFragmentView view;
    private IConversationContract.Model model;
    public HomeFragmentPresenter(IConversationContract.View.HomeFragmentView view)
    {
        this.view = view;
        model = new ConversationModel();
    }
    @Override
    public void LoadingNotSeenMessage() {
        model.getNumberOfNotSeenMessage(numberOfMessage -> {
            view.LoadNotSeenMessage(numberOfMessage);
        });
    }
}
