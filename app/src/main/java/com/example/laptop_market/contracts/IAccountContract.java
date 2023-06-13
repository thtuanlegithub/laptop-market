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
    }
    interface View{
        //region Account Fragment view
        interface AccountFragmentView {
            void LoadAccount(Account account);

            void LogoutAccount();

            void LoadNotLoginAccount();
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
    }
    //region Presenter
    interface Presenter{
        //region Account Fragment presenter
        interface AccountFragmentPresenter {
            void LoadingAccount();

            void LogoutAccount();
        }
        //endregion
        //region Login Fragment presenter
        interface  LoginFragmentPresenter{
            void LoginButtonClicked(Account account);
        }
        //endregion
        //region Signup Fragment view
        interface SignUpFragmentPresenter
        {
            void createAccountClicked(Account account);
        }
        //endregion
    }
    // endregion
}
