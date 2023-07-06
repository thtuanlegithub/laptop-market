package com.example.laptop_market.view.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;

import java.util.List;

public class ImageForNewPostAdapter extends RecyclerView.Adapter<ImageForNewPostAdapter.ImageViewHolder> {

    private List<Bitmap> images;
    private OnImageCloseClickListener closeClickListener;

    public ImageForNewPostAdapter(List<Bitmap> images, OnImageCloseClickListener closeClickListener) {
        this.images = images;
        this.closeClickListener = closeClickListener;
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
        if(position==0)
            holder.mainImageView.setVisibility(View.VISIBLE);
        holder.imageView.setImageBitmap(image);
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
}
