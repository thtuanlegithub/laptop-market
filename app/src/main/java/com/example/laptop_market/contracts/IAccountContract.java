package com.example.laptop_market.contracts;

import android.net.Uri;

import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.account.CurrentBuyer;

import java.lang.reflect.Executable;

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
        void GetCurrentAccountInformation(OnGetCurrentAccountInfoListener listener);
        interface OnGetCurrentAccountInfoListener{
            void OnFinishGetCurrentAccountInfo(boolean isSuccess, CurrentBuyer currentBuyer, Exception error);
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
        void ClickSavePost(String postID, OnFinishSavePostListener listener);
        void ClickRemoveSavePost(String postID, OnFinishSavePostListener listener);
        void LoadSavePostButton(String postID, OnFinishSavePostListener listener);
        interface OnFinishSavePostListener{
            void OnFinishSavePost(boolean isSuccess, boolean isSaved, Exception error);
        }
        void LoadAccountSetting(OnFinishLoadingProfileListener listener);
        interface OnFinishLoadingProfileListener{
            void OnFinishLoadingProfile(Account account, Exception e);
        }
        void UpdateAccountInformation(Account account, OnFinishUpdateAccountInformationListener listener);
        interface OnFinishUpdateAccountInformationListener{
            void OnFinishUpdateAccountInformation(Exception e);
        }
        void UpdateAccountPassword(String oldPassword, String newPassword, OnFinishUpdateAccountPasswordListener listener);
        interface OnFinishUpdateAccountPasswordListener{
            void OnFinishUpdateAccountPassword(boolean isSuccess, Exception e);
        }
        void UploadAvatar(Account account,Uri uri, OnFinishUpdateAvatarListener listener);
        interface OnFinishUpdateAvatarListener{
            void OnFinishUpdateAvatar(Exception e);
        }
        void LoadStatisticInformation(String AccountID, Boolean isBuy, OnFinishLoadStatisticInformation listener);
        interface OnFinishLoadStatisticInformation{
            void OnFinishLoadStatistic(boolean isSuccess, int NoOrders, double revenue, Exception error);
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
        interface AccountSettingActivityView{
            void LoadProfileData(Account account);
            void Error(Exception e);
            void UpdateAccountSettingSuccess();
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
        //region Account pasword activity view
        interface AccountPasswordActivityView{
            void UpdatePasswordSuccess();
            void WrongOldPassword(String message);
        }
        //endregion

        //region Profile Detail Activity view
        interface ProfileActivityView{
            void LoadProfile(Account account);
            void FinishUploadImage();
            void ExceptionCatch(Exception e);
        }
        //endregion

        // region Statistic
        interface StatisticActivityView {
            void DisplayStatistic(int NoOrders, double revenue);
        }
        // endregion
    }
    //region Presenter
    interface Presenter{
        //region Account Setting activity
        interface AccountSettingActivityPresenter{
            void LoadAccountSetting();
            void UpdateProfileOnClick(Account account);
        }
        //endregion
        //region Account Fragment presenter
        interface AccountFragmentPresenter {
            void LoadAccountStatus();
            void LogoutAccount();
            void ClickSellOrderStatistic();
            void ClickBuyOrderStatistic();
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
        //region Account pasword activity view
        interface AccountPasswordActivityPresenter{
            void UpdatePassword(String oldPassword, String newPassword);
        }
        //endregion
        //region Profile Detail Activity view
        interface ProfileActivityPresenter{
            void LoadProfile();
            void UploadImageClicked(Account account, Uri uri);
        }
        //endregion
        interface StatisticActivityPresenter {
            void LoadStatisticInformation(String accountID, boolean isBuy);
        }
    }
    // endregion
}
