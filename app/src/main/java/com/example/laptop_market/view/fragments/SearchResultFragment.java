package com.example.laptop_market.view.fragments;

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
import android.widget.ProgressBar;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.contracts.IStringFilterSearchContract;
import com.example.laptop_market.presenter.fragments.SearchResultFragmentPresenter;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.view.adapters.BrandAdapter;
import com.example.laptop_market.view.adapters.FilterAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResultAdapter;
import com.example.laptop_market.model.brand.Brand;
import com.example.laptop_market.model.filter.Filter;
import com.example.laptop_market.model.post.Post;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment implements IStringFilterSearchContract.View.SearchResultFragmentView
        , IPostContract.View.SearchResultFragmentView {
    private HomeBaseFragment homeBaseFragment;
    private RecyclerView rcvBrand;
    private RecyclerView rcvFilter;
    private RecyclerView rcvPostSearchResult;
    private HomeFragment homeFragment;
    private EditText edtTextSearchResult;
    private Button btnSearchResultBack;
    private SearchFragment searchFragment;
    private PreferenceManager preferenceManager;
    private IStringFilterSearchContract.Presenter.SearchResultFragmentPresenter stringSearchpresenter;
    private IPostContract.Presenter.SearchResultFragmentPresenter postPreseneter;
    private ProgressBar isLoading;
    public SearchResultFragment(HomeBaseFragment homeBaseFragment) {
        // Required empty public constructor
        this.homeBaseFragment = homeBaseFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getContext());
        stringSearchpresenter = new SearchResultFragmentPresenter(getContext(),this,this);
        postPreseneter = new SearchResultFragmentPresenter(getContext(),this,this);

    }
    private List<Filter> getListFilter(){
        List<Filter> listFilter = new ArrayList<>();
        listFilter.add(new Filter("Hãng +"));
        listFilter.add(new Filter("Giá +"));
        listFilter.add(new Filter("Tình trạng +"));
        listFilter.add(new Filter("Bộ xử lý +"));
        listFilter.add(new Filter("RAM +"));
        listFilter.add(new Filter("Loại ổ cứng +"));
        listFilter.add(new Filter("Kích thước ổ cứng +"));
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
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        isLoading= view.findViewById(R.id.isLoading);
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

        // Hiển thị Recycler View Post Search Result
        rcvPostSearchResult = view.findViewById(R.id.rcvPostSearchResult);
        GridLayoutManager gridLayoutManagerPost = new GridLayoutManager(requireContext(),1);
        rcvPostSearchResult.setLayoutManager(gridLayoutManagerPost);
        //
        return view;
    }

    private List<Post> getListPostSearchResult() {
        List<Post> postSearchResultList = new ArrayList<>();
      /*  postSearchResultList.add(new Post("0","Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM","26,000,000 đ",R.drawable.slide_show1,"TPHCM"));
        postSearchResultList.add(new Post("1","Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM","26,000,000 đ",R.drawable.slide_show1,"TPHCM"));
        postSearchResultList.add(new Post("2","Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM","26,000,000 đ",R.drawable.slide_show1,"TPHCM"));
        postSearchResultList.add(new Post("3","Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM","26,000,000 đ",R.drawable.slide_show1,"TPHCM"));
        postSearchResultList.add(new Post("4","Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM","26,000,000 đ",R.drawable.slide_show1,"TPHCM"));
        postSearchResultList.add(new Post("5","Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM","26,000,000 đ",R.drawable.slide_show1,"TPHCM"));
        postSearchResultList.add(new Post("6","Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM","26,000,000 đ",R.drawable.slide_show1,"TPHCM"));
*/
        return postSearchResultList;
    }

    @Override
    public void LoadSearchingFragment(String itemString) {
        isLoading.setVisibility(View.VISIBLE);
        rcvPostSearchResult.setVisibility(View.GONE);
        edtTextSearchResult.setText(itemString);
        postPreseneter.OnSearchPost(itemString);
    }

    @Override
    public void onResume() {
        super.onResume();
        stringSearchpresenter.OnLoadingPageView();
    }


    @Override
    public void FinishLoadingSearchPost(ArrayList<PostSearchResult> posts) {
        PostSearchResultAdapter postSearchResultAdapter = new PostSearchResultAdapter(posts, homeBaseFragment);
        rcvPostSearchResult.setAdapter(postSearchResultAdapter);
        isLoading.setVisibility(View.GONE);
        rcvPostSearchResult.setVisibility(View.VISIBLE);
        //rcvPostSearchResult.notify();
    }
}