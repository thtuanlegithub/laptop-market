package com.example.laptop_market.model.laptop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.example.laptop_market.contracts.ILaptopContract;
import com.example.laptop_market.utils.tables.LaptopTable;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LaptopModel implements ILaptopContract.Model {
    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage;
    private int uploadImage = 0;
    private int successCount = 0;
    private Context context;
    public LaptopModel(Context context)
    {
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        this.context = context;
    }
    //region Create Laptop
    @Override
    public void CreateLaptop( Laptop laptop, OnCreateLaptopListener listener) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(LaptopTable.LAPTOP_NAME, laptop.getLaptopName());
        hashMap.put(LaptopTable.BRAND_ID, laptop.getBrandID());
        hashMap.put(LaptopTable.PRICE, laptop.getPrice());
        hashMap.put(LaptopTable.CPU, laptop.getCpu());
        hashMap.put(LaptopTable.RAM, laptop.getRam());
        hashMap.put(LaptopTable.HARD_DRIVE, laptop.getHardDrive());
        hashMap.put(LaptopTable.HARD_DRIVE_SIZE, laptop.getHardDriveSize());
        hashMap.put(LaptopTable.GRAPHICS, laptop.getGraphics());
        hashMap.put(LaptopTable.SCREEN_SIZE, laptop.getScreenSize());
        hashMap.put(LaptopTable.GUARANTEE, laptop.getGuarantee());
        hashMap.put(LaptopTable.ORIGIN, laptop.getOrigin());
        hashMap.put(LaptopTable.NUM_OF_IMAGE,laptop.getNumOfImage());
        db.collection(LaptopTable.TABLE_NAME).add(hashMap).addOnSuccessListener(
                documentReference -> {
                    upLoadImageToFireStorage(laptop.getImgLists(), documentReference.getId(),listener);
                }
        ).addOnFailureListener(e -> {
            listener.OnFinishCreateLaptopListener(false, null, e);
        });
    }

    //endregion
    //region upload Image to Firebase Storage
    private void upLoadImageToFireStorage(ArrayList<Uri> listImage,String Laptop_Id, OnCreateLaptopListener listener)
    {
        List<Task<Uri>> uploadTasks = new ArrayList<>();
        successCount = 0;
        for (uploadImage = 0; uploadImage<listImage.size(); uploadImage ++) {
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(listImage.get(uploadImage)));

// Nếu không lấy được phần mở rộng, hãy sử dụng mặc định là ".jpg"
            if (extension == null) {
                extension = "jpg";
            }
            StorageReference imageRef = firebaseStorage.getReference().child(LaptopTable.TABLE_NAME).child(Laptop_Id + "/Image" + Integer.toString(uploadImage) +"."+ extension);
            UploadTask uploadTask = imageRef.putFile(listImage.get(uploadImage));
            Task<Uri> task = uploadTask.continueWithTask(singleTaskUpload -> {
                successCount++;
                if (!singleTaskUpload.isSuccessful()) {
                    Exception e = singleTaskUpload.getException();
                    listener.OnFinishCreateLaptopListener(false, null, e);
                }
                return imageRef.getDownloadUrl();
            });
        }
        // Đợi toàn bộ task hoàn thành
        Task<List<Uri>> allTasks = Tasks.whenAllSuccess(uploadTasks);
        allTasks.addOnCompleteListener(AllTask -> {
            if (AllTask.isSuccessful()) {
                // Tất cả các task đã hoàn thành thành công
                List<Uri> downloadUrls = AllTask.getResult();
                listener.OnFinishCreateLaptopListener(true, Laptop_Id, null);
            } else {
                // Một trong các task đã thất bại
                Exception e = AllTask.getException();
                listener.OnFinishCreateLaptopListener(false, null, e);
            }
        });

    }
    //endregion
    @Override
    public void LoadingLaptopInPostDetail(String idLaptop, OnLoadingLaptopInPostDetailListener listener) {
        db.collection(LaptopTable.TABLE_NAME).document(idLaptop).get().addOnSuccessListener(documentSnapshot -> {
            Laptop laptop = documentSnapshot.toObject(Laptop.class);
            ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
            laptop.setImgLists(new ArrayList<>(laptop.getNumOfImage()));
            laptop.setListDownloadImages(bitmapArrayList);
            listener.OnFinishLoadingLaptopInPostDetail(laptop,null);

        }).addOnFailureListener(e -> {
            listener.OnFinishLoadingLaptopInPostDetail(null,e);
        });
    }

    @Override
    public void LoadingImageToPostDetail(String idLaptop, OnLoadingImageLaptopInPostDetailListener listener) {
        firebaseStorage.getReference().child(LaptopTable.TABLE_NAME).child(idLaptop + "/").listAll().addOnSuccessListener(
                listResult -> {
                    for(StorageReference item : listResult.getItems())
                    {
                        item.getBytes(Long.MAX_VALUE).addOnSuccessListener(
                                bytes -> {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    listener.OnFinsishLoadingImageInPostDetail(bitmap,null);
                                }).addOnFailureListener(
                                e -> {
                                        listener.OnFinsishLoadingImageInPostDetail(null,e);
                                });
                    }
                }
        );
    }

}
