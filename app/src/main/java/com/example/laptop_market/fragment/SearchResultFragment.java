package com.example.laptop_market.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.laptop_market.R;
import com.example.laptop_market.adapter.BrandAdapter;
import com.example.laptop_market.adapter.FilterAdapter;
import com.example.laptop_market.model.Brand;
import com.example.laptop_market.model.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {
    private HomeBaseFragment homeBaseFragment;
    private RecyclerView rcvBrand;
    private RecyclerView rcvFilter;
    private HomeFragment homeFragment;
    private EditText edtTextSearchResult;
    private Button btnSearchResultBack;
    private SearchFragment searchFragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchResultFragment(HomeBaseFragment homeBaseFragment) {
        // Required empty public constructor
        this.homeBaseFragment = homeBaseFragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private List<Filter> getListFilter(){
        List<Filter> listFilter = new ArrayList<>();
        listFilter.add(new Filter("Hãng +"));
        listFilter.add(new Filter("Giá +"));
        listFilter.add(new Filter("Tình trạng +"));
        listFilter.add(new Filter("Bộ xử lý +"));
        listFilter.add(new Filter("RAM +"));
        listFilter.add(new Filter("Ổ cứng +"));
        listFilter.add(new Filter("Card màn hình +"));
        listFilter.add(new Filter("Kích cỡ màn hình +"));
        return listFilter;
    }
    private List<Brand> getListBrand() {
        List<Brand> listBrand = new ArrayList<>();

        listBrand.add(new Brand(R.drawable.brand_logo_apple,"Apple",1));
        listBrand.add(new Brand(R.drawable.brand_logo_asus,"Asus",1));
        listBrand.add(new Brand(R.drawable.brand_logo_dell,"Dell",1));
        listBrand.add(new Brand(R.drawable.brand_logo_hp,"HP",1));
        listBrand.add(new Brand(R.drawable.brand_logo_lenovo,"Lenovo",1));
        listBrand.add(new Brand(R.drawable.brand_logo_lg,"LG",1));
        listBrand.add(new Brand(R.drawable.brand_logo_acer,"Acer",1));
        listBrand.add(new Brand(R.drawable.brand_logo_msi,"MSI",1));
        listBrand.add(new Brand(R.drawable.brand_logo_razer,"Razer",1));
        listBrand.add(new Brand(R.drawable.brand_logo_samsung,"Samsung",1));
        listBrand.add(new Brand(R.drawable.brand_logo_sony,"Sony",1));
        listBrand.add(new Brand(R.drawable.brand_logo_toshiba,"Toshiba",1));
        listBrand.add(new Brand(R.drawable.ic_baseline_more_horiz_24,"Khác",1));
        return listBrand;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        btnSearchResultBack = view.findViewById(R.id.btnSearchResultBack);
        btnSearchResultBack.setOnClickListener(view1 -> {
            homeBaseFragment.replaceFragment(homeBaseFragment.homeFragment);
            //Ẩn bàn phím:
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = getActivity().getCurrentFocus();
            if (inputMethodManager != null && currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        });
        //Hiển thị recycler view filter
        rcvFilter = view.findViewById(R.id.rcvFilter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),1,0,false);
        rcvFilter.setLayoutManager(gridLayoutManager);

        FilterAdapter filterAdapter = new FilterAdapter(getListFilter());
        rcvFilter.setAdapter(filterAdapter);
        //

        // Hiển thị recycler view brand
        rcvBrand = view.findViewById(R.id.rcvBrandSearchResult);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(requireContext(),1,0,false);
        rcvBrand.setLayoutManager(gridLayoutManager2);

        BrandAdapter brandAdapter = new BrandAdapter(getListBrand());
        rcvBrand.setAdapter(brandAdapter);
        //

        //Xử lý sự kiện edit text touch
        edtTextSearchResult = view.findViewById(R.id.edtTextSearchResult);
        edtTextSearchResult.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Xử lý khi EditText được nhấn
                    homeBaseFragment.replaceFragment(homeBaseFragment.searchFragment);
                    // Hiển thị bàn phím
                    InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(edtTextSearchResult, InputMethodManager.SHOW_IMPLICIT);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

}