package com.example.laptop_market.contracts;

import com.example.laptop_market.model.account.Account;

public interface IAccountContract {
    interface Model{
        // region Login Fuction
        void Login(Account account, OnLoginFinishListener listener);
        interface OnLoginFinishListener
        {
            void OnLoginListener(boolean isSuccess, String message);
        }
        // endregion
        //region Function Loading account
        void LoadAccount(OnLoadingAccountListener listener);
        interface OnLoadingAccountListener
        {
            void OnLoadingListener(boolean isSignIn,Account account);
        }
        //endregion
        //region Logout Function
        void LogoutAccount(OnLogOutAccountListener listener);
        interface OnLogOutAccountListener
        {
            void OnLogoutListener();
        }
        //endregion
        // region Create Account Function
        void CreateAccount(Account account,OnCreateAccountListener listener);
        interface OnCreateAccountListener
        {
            void OnCreateAccountResult(int type, String message);
        }
        //endregion
        //region Account had post
        void LoadAccountWithId(String accountId,OnLoadingAccountWithIdListener listener);
        interface OnLoadingAccountWithIdListener{
            void OnFinishLoadingAccountWithId(Account account, Exception error);
        }
        //endregion
        void CheckSignedInAccount(OnCheckingSignInAccountListener listener);
        interface  OnCheckingSignInAccountListener{
            void OnFinishCheckingSignInAccount(boolean isLogin);
        }
    }
    interface View{
        //region Account Fragment view
        interface AccountFragmentView {
            void LoadAccount(Account account);
            void LoadNotLoginAccount();
            void LogoutAccount();
            void LoginAccount();
            void LoadSellOrder();
            void LoadBuyOrder();
            void LoadSavedPost();
            void LoadYourRating();
            void LoadAccountSettings();
        }
        //endregion
        //region Login Fragment view
        interface LoginFragmentView{
            void LoginSuccess();
            void LoginFailed(String message);
        }
        //endregion
        //region Signup Fragment view
        interface  SignUpFragmentView{
            void ShowError(String message);
            void RegisterSuccess();
        }
        //endregion
        //region PostDetail Activity view
        interface PostDetailActivityView{
            void LoadingAccountInPostDetail(Account account);
            void FailedLoadingPostDetail(Exception error);
        }
        //endregion
    }
    //region Presenter
    interface Presenter{
        //region Account Fragment presenter
        interface AccountFragmentPresenter {
            void LoadAccountStatus();
            void LogoutAccount();
            void ClickSellOrder();
            void ClickBuyOrder();
            void ClickSavedPost();
            void ClickYourRating();
            void ClickAccountSettings();
        }
        //endregion
        //region Login Fragment presenter
        interface  LoginFragmentPresenter{
            void LoginButtonClicked(Account account);
        }
        //endregion
        //region Signup Fragment presenter
        interface SignUpFragmentPresenter
        {
            void createAccountClicked(Account account);
        }
        //endregion
        //region PostDetail Activity presenter
        interface PostDetailActivityPresenter{
            void OnLoadingAccountInPostDetail(String accountId);
        }
        //endregion
    }
    // endregion
}
