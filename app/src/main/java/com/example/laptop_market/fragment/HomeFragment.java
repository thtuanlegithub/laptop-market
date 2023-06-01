package com.example.laptop_market.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.laptop_market.R;
import com.example.laptop_market.adapter.BrandAdapter;
import com.example.laptop_market.model.Brand;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private LinearLayout linearLayoutFragmentHome = null;
    private SearchFragment searchFragment = null;
    private EditText edtTextHome = null;
    private RecyclerView rcvBrand;
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

        // Tạo danh mục tìm kiếm
        rcvBrand = view.findViewById(R.id.rcvBrand);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        rcvBrand.setLayoutManager(gridLayoutManager);

        BrandAdapter brandAdapter = new BrandAdapter(getListBrand());
        rcvBrand.setAdapter(brandAdapter);
        //
        linearLayoutFragmentHome = view.findViewById(R.id.linearLayoutFragmentHome);
        linearLayoutFragmentHome.requestFocus();
        //Sự kiện search

        edtTextHome = view.findViewById(R.id.edtTextHome);
            edtTextHome.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        // Xử lý khi EditText được nhấn
                        if (searchFragment == null) {
                            searchFragment = new SearchFragment();
                        }
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, searchFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        // Hiển thị bàn phím
                        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(edtTextHome, InputMethodManager.SHOW_IMPLICIT);
                        return true;
                    }
                    return false;
                }
            });

        return view;
    }

    private List<Brand> getListBrand() {
        List<Brand> listBrand = new ArrayList<>();

        listBrand.add(new Brand(R.drawable.brand_logo_apple,"Apple"));
        listBrand.add(new Brand(R.drawable.brand_logo_asus,"Asus"));
        listBrand.add(new Brand(R.drawable.brand_logo_dell,"Dell"));
        listBrand.add(new Brand(R.drawable.brand_logo_hp,"HP"));
        listBrand.add(new Brand(R.drawable.brand_logo_lenovo,"Lenovo"));
        listBrand.add(new Brand(R.drawable.brand_logo_lg,"LG"));
        listBrand.add(new Brand(R.drawable.brand_logo_acer,"Acer"));
        listBrand.add(new Brand(R.drawable.brand_logo_msi,"MSI"));
        listBrand.add(new Brand(R.drawable.brand_logo_razer,"Razer"));
        listBrand.add(new Brand(R.drawable.brand_logo_samsung,"Samsung"));
        listBrand.add(new Brand(R.drawable.brand_logo_sony,"Sony"));
        listBrand.add(new Brand(R.drawable.brand_logo_toshiba,"Toshiba"));
        listBrand.add(new Brand(R.drawable.ic_baseline_more_horiz_24,"Xem thêm"));
        return listBrand;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Đảm bảo rằng Fragment được tạo lại khi quay lại từ các Fragment khác
        if (getView() == null) {
            onCreateView(LayoutInflater.from(getContext()), null, null);
        }
    }

}
