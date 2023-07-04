package com.example.laptop_market.contracts;

import android.graphics.Bitmap;

import com.example.laptop_market.model.laptop.Laptop;

public interface ILaptopContract {
    interface Model{
        //region Create new Laptop
        void CreateLaptop(Laptop laptop, OnCreateLaptopListener listener);
        interface OnCreateLaptopListener{
            void OnFinishCreateLaptopListener(boolean isSuccess, String idLaptop, Exception error);
        }
        // endregion
        // region Load laptop in post detail
        void LoadingLaptopInPostDetail(String idLaptop, OnLoadingLaptopInPostDetailListener listener);
        interface OnLoadingLaptopInPostDetailListener{
            void OnFinishLoadingLaptopInPostDetail(Laptop laptop, Exception error);
        }
        void LoadingImageToPostDetail(String idLaptop, OnLoadingImageLaptopInPostDetailListener listener);
        interface OnLoadingImageLaptopInPostDetailListener{
            void OnFinsishLoadingImageInPostDetail(Bitmap image,Exception error);
        }
    }
    interface View{
        interface NewPostActivityView{
            void CreateLaptopSuccess(String id_LapTop);
            void CreateLaptopFailure(Exception error);
        }
        interface PostDetailActivityView{
            void LoadingLapTopInPostDetail(Laptop laptop);
            void LoadingImageLaptopInPostDetail(Bitmap bitmap);
            void FailedLoadingPostDetail(Exception error);
        }
    }
    interface Presenter{
        interface NewPostActivityPresenter{
            void OnCreateNewLaptopClicked(Laptop laptop);
        }
        interface PostDetailActivityPresenter{
            void OnLoadingLaptopInPostDetail(String laptopId);
            void OnLoadingImageLaptopInPostDetail(String laptopId);
        }
    }
}
