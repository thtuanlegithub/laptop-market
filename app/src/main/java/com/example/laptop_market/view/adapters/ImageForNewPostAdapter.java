package com.example.laptop_market.view.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.view.activities.PictureDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImageForNewPostAdapter extends RecyclerView.Adapter<ImageForNewPostAdapter.ImageViewHolder> {

    private List<Bitmap> images;
    private OnImageCloseClickListener closeClickListener;
    private Context context;
    private boolean notFromActivityNewPost;
    public ImageForNewPostAdapter(List<Bitmap> images, Context context, OnImageCloseClickListener closeClickListener) {
        this.images = images;
        this.closeClickListener = closeClickListener;
        this.context = context;
    }
    public ImageForNewPostAdapter(List<Bitmap> images, Context context, OnImageCloseClickListener closeClickListener, boolean notFromActivityNewPost) {
        this.images = images;
        this.closeClickListener = closeClickListener;
        this.context = context;
        this.notFromActivityNewPost = notFromActivityNewPost;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_for_new_post, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Bitmap image = images.get(position);
        if(!notFromActivityNewPost) {
            if (position == 0)
                holder.mainImageView.setVisibility(View.VISIBLE);
        }
        holder.imageView.setImageBitmap(image);
        holder.imageView.setOnClickListener(view -> {
            JSONObject data = new JSONObject();
            try {
                String encodedString = encode_img(image);
                data.put(Constants.KEY_IMAGE , encodedString);
                File file = new File(context.getFilesDir(), "image.json");
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(data.toString());
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, PictureDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isSingleImage",true);
                intent.putExtra("image_file", file.getAbsolutePath());
                context.startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        holder.closeImageView.setOnClickListener(v -> {
            if (closeClickListener != null) {
                closeClickListener.onImageCloseClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView closeImageView;
        TextView mainImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            closeImageView = itemView.findViewById(R.id.closeImageView);
            mainImageView = itemView.findViewById(R.id.mainImageView);
        }
    }

    public interface OnImageCloseClickListener {
        void onImageCloseClick(int position);
    }
    private String encode_img(Bitmap bitmap)
    {
        int previewWidth = 480;
        int previewHeight=bitmap.getHeight()*previewWidth/bitmap.getWidth();
        Bitmap preivewBitmap= Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        preivewBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);

    }
}
