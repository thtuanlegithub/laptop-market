package com.example.laptop_market.view.fragments;

import android.content.Context;
import android.content.Intent;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.laptop_market.R;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.view.activities.NotificationActivity;
import com.example.laptop_market.view.adapters.BrandAdapter;
import com.example.laptop_market.model.brand.Brand;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private AppCompatButton btnNotificationHome;
    private HomeBaseFragment homeBaseFragment;
    private LinearLayout linearLayoutFragmentHome = null;
    public SearchFragment searchFragment = null;
    private EditText edtTextHome = null;
    private RecyclerView rcvBrand;
    private PreferenceManager preferenceManager;
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
    public HomeFragment(HomeBaseFragment homeBaseFragment){
        this.homeBaseFragment = homeBaseFragment;
    }

    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Bổ sung giao diện cho trang chủ
        btnNotificationHome = view.findViewById(R.id.btnNotificationHome);

        //Tạo list image để hiển thị slide show ViewPager
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.slide_show1);
        imageList.add(R.drawable.slide_show1);
        ViewPager viewPager = view.findViewById(R.id.slide_show_view_pager);
        ImageSliderAdapter adapter = new ImageSliderAdapter(requireContext(),imageList);
        viewPager.setAdapter(adapter);
        preferenceManager = new PreferenceManager(getContext());
        // Tạo danh mục tìm kiếm
        rcvBrand = view.findViewById(R.id.rcvBrand);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        rcvBrand.setLayoutManager(gridLayoutManager);

        BrandAdapter brandAdapter = new BrandAdapter(getListBrand(),homeBaseFragment, getContext());
        rcvBrand.setAdapter(brandAdapter);
        //
        linearLayoutFragmentHome = view.findViewById(R.id.linearLayoutFragmentHome);
        linearLayoutFragmentHome.requestFocus();
        //Sự kiện search
        edtTextHome = view.findViewById(R.id.edtTextHome);
        setListener();

        return view;
    }
    private void setListener()
    {
        btnNotificationHome.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
            startActivity(intent,bundle);
        });
        edtTextHome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    preferenceManager.putBoolean("isFromHomeFragment",true);
                    // Xử lý khi EditText được nhấn
                    if (searchFragment == null) {
                        searchFragment = new SearchFragment(homeBaseFragment);
                    }
                    if (homeBaseFragment != null && searchFragment != null) {
                        homeBaseFragment.searchFragment = searchFragment;
                        homeBaseFragment.replaceFragment(searchFragment);
                        homeBaseFragment.isSearch = true;
                    }
                    // Hiển thị bàn phím
                    InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(edtTextHome, InputMethodManager.SHOW_IMPLICIT);
                    return true;
                }
                return false;
            }
        });
    }
    private List<Brand> getListBrand() {
        List<Brand> listBrand = new ArrayList<>();
        listBrand.add(new Brand(R.drawable.brand_logo_apple,"Apple",0));
        listBrand.add(new Brand(R.drawable.brand_logo_asus,"Asus",0));
        listBrand.add(new Brand(R.drawable.brand_logo_dell,"Dell",0));
        listBrand.add(new Brand(R.drawable.brand_logo_hp,"HP",0));
        listBrand.add(new Brand(R.drawable.brand_logo_lenovo,"Lenovo",0));
        listBrand.add(new Brand(R.drawable.brand_logo_lg,"LG",0));
        listBrand.add(new Brand(R.drawable.brand_logo_acer,"Acer",0));
        listBrand.add(new Brand(R.drawable.brand_logo_msi,"MSI",0));
        listBrand.add(new Brand(R.drawable.brand_logo_razer,"Razer",0));
        listBrand.add(new Brand(R.drawable.brand_logo_samsung,"Samsung",0));
        listBrand.add(new Brand(R.drawable.brand_logo_sony,"Sony",0));
        listBrand.add(new Brand(R.drawable.brand_logo_toshiba,"Toshiba",0));
        listBrand.add(new Brand(R.drawable.ic_baseline_more_horiz_24,"Khác",0));
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
