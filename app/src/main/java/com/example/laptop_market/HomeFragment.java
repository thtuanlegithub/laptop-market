package com.example.laptop_market;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public class ImageSliderAdapter extends PagerAdapter{
        private List<Integer> imageList;
        private Context context;

        public ImageSliderAdapter(Context context, List<Integer> imageList){
            this.context = context;
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position){
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imageList.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount(){
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }
    public HomeFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Bổ sung giao diện cho trang chủ

        //Tạo list image để hiển thị slide show ViewPager
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.slide_show1);
        imageList.add(R.drawable.slide_show1);
        ViewPager viewPager = view.findViewById(R.id.slide_show_view_pager);
        ImageSliderAdapter adapter = new ImageSliderAdapter(requireContext(),imageList);
        viewPager.setAdapter(adapter);
        //

        return view;
    }
}
