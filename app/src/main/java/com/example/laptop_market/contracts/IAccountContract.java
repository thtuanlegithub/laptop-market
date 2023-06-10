package com.example.laptop_market.contracts;

import com.example.laptop_market.model.AccountModel;

public interface IAccountContract {
    interface View {
        void showLoginSuccess(AccountModel accountModel);
        void showLoginError(String errorMessage);

        void showSignUpSuccess();
        void showSignUpError(String errorMessage);

        void showPasswordResetSuccess();
        void showPasswordResetError(String errorMessage);
    }

    interface Presenter {
        void login(String username, String password);
        void signUp(AccountModel accountModel);
        void resetPassword(String email);
        void onDestroy();
    }

    interface Model {
        void login(String username, String password, OnFinishedListener listener);
        void signUp(AccountModel accountModel, OnFinishedListener listener);
        void resetPassword(String email, OnFinishedListener listener);

        interface OnFinishedListener {
            void onLoginSuccess(AccountModel accountModel);
            void onLoginError(String errorMessage);

            void onSignUpSuccess();
            void onSignUpError(String errorMessage);

            void onPasswordResetSuccess();
            void onPasswordResetError(String errorMessage);
        }
    }
}
