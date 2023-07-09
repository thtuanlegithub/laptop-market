package com.example.laptop_market.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityOptionsCompat;
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
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.contracts.IStringFilterSearchContract;
import com.example.laptop_market.presenter.fragments.SearchResultFragmentPresenter;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.BrandTable;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.tables.SearchFilterPost;
import com.example.laptop_market.view.activities.ConversationListActivity;
import com.example.laptop_market.view.activities.NotificationActivity;
import com.example.laptop_market.view.adapters.BrandAdapter;
import com.example.laptop_market.view.adapters.FilterAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResultAdapter;
import com.example.laptop_market.model.brand.Brand;
import com.example.laptop_market.model.filter.Filter;
import com.example.laptop_market.model.post.Post;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment implements IStringFilterSearchContract.View.SearchResultFragmentView
        , IPostContract.View.SearchResultFragmentView {
    public static final int ADAPTER_TYPE0_CLICK = 1;
    public static final int SEARCH_CLICK = 2;
    public static final int SEARCH_CLICK_FROM_SEARCH_FRAGMENT = 3;
    private int searchResultFragmentType;
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
    private SearchFilterPost searchFilterPost;
    private List<Filter> listFilter;
    private FilterAdapter filterAdapter;
    private AppCompatButton btnNotificationPostSearchResult;
    private AppCompatButton chatMessageBtt;
    public SearchResultFragment(HomeBaseFragment homeBaseFragment) {
        // Required empty public constructor
        searchFilterPost = new SearchFilterPost();
        this.homeBaseFragment = homeBaseFragment;
    }

    public SearchResultFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getContext());
        stringSearchpresenter = new SearchResultFragmentPresenter(getContext(),this,this);
        postPreseneter = new SearchResultFragmentPresenter(getContext(),this,this);
        preferenceManager.putSerializable(Constants.KEY_FILTER_SEARCH,searchFilterPost);

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
        chatMessageBtt = view.findViewById(R.id.chatMessageBtt);
        chatMessageBtt.setOnClickListener(view1 -> {
            if(FirebaseAuth.getInstance().getCurrentUser() == null)
            {
                Toast.makeText(getContext(), "Bạn phải đăng nhập để thực hiện chức nắng này", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getContext(), ConversationListActivity.class);
            startActivity(intent);});
        btnSearchResultBack.setOnClickListener(view1 -> {
            homeBaseFragment.replaceFragment(homeBaseFragment.homeFragment);
            //Ẩn bàn phím:
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = getActivity().getCurrentFocus();
            if (inputMethodManager != null && currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        });

        btnNotificationPostSearchResult = view.findViewById(R.id.btnNotificationPostSearchResult);
        btnNotificationPostSearchResult.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
            startActivity(intent,bundle);
        });

        //Hiển thị recycler view filter
        rcvFilter = view.findViewById(R.id.rcvFilter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),1,0,false);
        rcvFilter.setLayoutManager(gridLayoutManager);
        listFilter = getListFilter();
        filterAdapter = new FilterAdapter(listFilter,getContext());
        rcvFilter.setAdapter(filterAdapter);
        //

        // Hiển thị recycler view brand
        rcvBrand = view.findViewById(R.id.rcvBrandSearchResult);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(requireContext(),1,0,false);
        rcvBrand.setLayoutManager(gridLayoutManager2);

        BrandAdapter brandAdapter = new BrandAdapter(getListBrand(),this, getContext());
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
                    preferenceManager.putInt(Constants.KEY_POST_SEARCH_RESULT_TYPE,SEARCH_CLICK);
                    if (homeBaseFragment.searchFragment == null) {
                        homeBaseFragment.searchFragment = new SearchFragment(homeBaseFragment);
                    }
                    if (homeBaseFragment != null && homeBaseFragment.searchFragment != null) {
                        searchFragment = homeBaseFragment.searchFragment;
                        homeBaseFragment.replaceFragment(searchFragment);
                        homeBaseFragment.isSearch = true;
                    }
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

    private void reloadListFilter()
    {

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String textViewHint = "";
        if(searchFilterPost.getListBrandName().size()>0)
        {
            String brand = "";
            textViewHint += "Laptop ";
            ArrayList<String> list = searchFilterPost.getListBrandName();
            for(int i = 0; i<list.size();i++)
            {
                textViewHint += list.get(i) + ", ";
                brand += list.get(i) + ", ";
            }
            textViewHint = textViewHint.substring(0,textViewHint.length()-2);
            brand = brand.substring(0,brand.length()-2) + " +";
            if (brand.length() > 10) {
                brand = brand.substring(0, 7) + "..." + " +";
            }
            filterAdapter.updateFilterName(0,brand);
        }
        if(searchFilterPost.getMinimumPrice()!=0 && searchFilterPost.getMaximumPrice()!=50000000) {
            if (searchFilterPost.getMinimumPrice() > 0)
                textViewHint += " có giá trên " + formatter.format(searchFilterPost.getMinimumPrice()) + " và";
            textViewHint += " có giá dưới " + formatter.format(searchFilterPost.getMaximumPrice());
        }
        if (searchFilterPost.getListGuarantee().size() > 0) {
            String guarantee = "";
            textViewHint += " và có tình trạng sử dụng là: ";
            ArrayList<String> list = searchFilterPost.getListGuarantee();
            for (int i = 0; i < list.size(); i++) {
                textViewHint += list.get(i) + ", ";
                guarantee += list.get(i) + ", ";
            }
            textViewHint = textViewHint.substring(0,textViewHint.length()-2);
            guarantee = guarantee.substring(0, guarantee.length() - 2);
            if (guarantee.length() > 10) {
                guarantee = guarantee.substring(0, 7) + "..." + " +";
            } else {
                guarantee += " +";
            }
            filterAdapter.updateFilterName(2, guarantee);
        }
        if (searchFilterPost.getListCPU().size() > 0) {
            String cpu = "";
            textViewHint += " và có CPU là: ";
            ArrayList<String> list = searchFilterPost.getListCPU();
            for (int i = 0; i < list.size(); i++) {
                textViewHint += list.get(i) + ", ";
                cpu += list.get(i) + ", ";
            }
            cpu = cpu.substring(0, cpu.length() - 2);
            textViewHint = textViewHint.substring(0,textViewHint.length()-2);
            if (cpu.length() > 10) {
                cpu = cpu.substring(0, 7) + "..." + " +";
            } else {
                cpu += " +";
            }

            filterAdapter.updateFilterName(3, cpu);
        }
        if (searchFilterPost.getListRam().size() > 0) {
            String ram = "";
            textViewHint += " và có Ram là: ";
            ArrayList<String> list = searchFilterPost.getListRam();
            for (int i = 0; i < list.size(); i++) {
                textViewHint += list.get(i) + ", ";
                ram += list.get(i) + ", ";
            }
            ram = ram.substring(0, ram.length() - 2);
            textViewHint = textViewHint.substring(0,textViewHint.length()-2);
            if (ram.length() > 10) {
                ram = ram.substring(0, 7) + "..." + " +";
            } else {
                ram += " +";
            }
            filterAdapter.updateFilterName(4, ram);
        }
        if (searchFilterPost.getListHardDrive().size() > 0) {
            String hardDrive = "";
            textViewHint += " và có loại ổ cứng là: ";
            ArrayList<String> list = searchFilterPost.getListHardDrive();
            for (int i = 0; i < list.size(); i++) {
                textViewHint += list.get(i) + ", ";
                hardDrive += list.get(i) + ", ";
            }
            textViewHint = textViewHint.substring(0,textViewHint.length()-2);
            hardDrive = hardDrive.substring(0, hardDrive.length() - 2);

            if (hardDrive.length() > 10) {
                hardDrive = hardDrive.substring(0, 7) + "..." + " +";
            } else {
                hardDrive += " +";
            }

            filterAdapter.updateFilterName(5, hardDrive);
        }
        if (searchFilterPost.getListHardDriveSize().size() > 0) {
            String hardDriveSize = "";
            textViewHint += " và có dung lượng ổ cứng là: ";
            ArrayList<String> list = searchFilterPost.getListHardDriveSize();
            for (int i = 0; i < list.size(); i++) {
                textViewHint += list.get(i) + ", ";
                hardDriveSize += list.get(i) + ", ";
            }
            hardDriveSize = hardDriveSize.substring(0, hardDriveSize.length() - 2);
            textViewHint = textViewHint.substring(0,textViewHint.length()-2);
            if (hardDriveSize.length() > 10) {
                hardDriveSize = hardDriveSize.substring(0, 7) + "..." + " +";
            } else {
                hardDriveSize += " +";
            }

            filterAdapter.updateFilterName(6, hardDriveSize);
        }
        if (searchFilterPost.getListGraphics().size() > 0) {
            String graphics = "";
            textViewHint += " và có card đồ họa là: ";
            ArrayList<String> list = searchFilterPost.getListGraphics();
            for (int i = 0; i < list.size(); i++) {
                textViewHint += list.get(i) + ", ";
                graphics += list.get(i) + ", ";
            }
            graphics = graphics.substring(0, graphics.length() - 2);
            textViewHint = textViewHint.substring(0,textViewHint.length()-2);
            if (graphics.length() > 10) {
                graphics = graphics.substring(0, 7) + "..." + " +";
            } else {
                graphics += "+";
            }

            filterAdapter.updateFilterName(7, graphics);
        }
        if (searchFilterPost.getListScreenSize().size() > 0) {
            String screenSize = "";
            textViewHint += " và có màn hình là: ";
            ArrayList<String> list = searchFilterPost.getListScreenSize();
            for (int i = 0; i < list.size(); i++) {
                textViewHint += list.get(i) + ", ";
                screenSize += list.get(i) + ", ";
            }
            screenSize = screenSize.substring(0, screenSize.length() - 2);
            textViewHint = textViewHint.substring(0,textViewHint.length()-2);
            if (screenSize.length() > 10) {
                screenSize = screenSize.substring(0, 7) + "..." + " +";
            } else {
                screenSize += " +";
            }

            filterAdapter.updateFilterName(8, screenSize);
        }
        if(textViewHint.length()>25)
            textViewHint = textViewHint.substring(0,22) + "...";
        edtTextSearchResult.setHint(textViewHint);
    }

    @Override
    public void LoadSearchingFragment(String itemString) {
        isLoading.setVisibility(View.VISIBLE);
        rcvPostSearchResult.setVisibility(View.GONE);
        edtTextSearchResult.setText(itemString);
        if(preferenceManager.getBoolean("isFromHomeFragment")) {
            cleanSearchFilterPost();
            postPreseneter.OnSearchPost(itemString);
            preferenceManager.putBoolean("isFromHomeFragment", false);
        }
        else{
            searchFilterPost.setSearchPost(itemString);
            postPreseneter.OnSearchPostByFilter(searchFilterPost);
            preferenceManager.putSerializable(Constants.KEY_FILTER_SEARCH,searchFilterPost);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        searchResultFragmentType = preferenceManager.getInt(Constants.KEY_POST_SEARCH_RESULT_TYPE,0);
        if(searchResultFragmentType == ADAPTER_TYPE0_CLICK)
        {
            cleanSearchFilterPost();
            isLoading.setVisibility(View.VISIBLE);
            rcvPostSearchResult.setVisibility(View.GONE);
            String brand = preferenceManager.getString(BrandTable.BRAND_NAME);
            preferenceManager.removeKey(BrandTable.BRAND_NAME);
            searchFilterPost.getListBrandName().add(brand);
            postPreseneter.OnSearchPostByFilter(searchFilterPost);
            preferenceManager.putSerializable(Constants.KEY_FILTER_SEARCH,searchFilterPost);
        }
        else if(searchResultFragmentType == SEARCH_CLICK){
            stringSearchpresenter.OnLoadingPageView();
        }
        else
        {
            if(preferenceManager.getSerializable(Constants.KEY_FILTER_SEARCH) != null)
            {
                isLoading.setVisibility(View.VISIBLE);
                rcvPostSearchResult.setVisibility(View.GONE);
                searchFilterPost = (SearchFilterPost) preferenceManager.getSerializable(Constants.KEY_FILTER_SEARCH);
                postPreseneter.OnSearchPostByFilter(searchFilterPost);
            }
        }
        preferenceManager.removeKey(Constants.KEY_POST_SEARCH_RESULT_TYPE);
        reloadListFilter();

    }
    private void cleanSearchFilterPost()
    {
        edtTextSearchResult.setText("");
        searchFilterPost.setSearchPost("");
        searchFilterPost.setMaximumPrice(50000000);
        searchFilterPost.setMinimumPrice(0);
        searchFilterPost.getListGuarantee().clear();;
        searchFilterPost.getListGraphics().clear();
        searchFilterPost.getListRam().clear();
        searchFilterPost.getListScreenSize().clear();
        searchFilterPost.getListCPU().clear();
        searchFilterPost.getListHardDriveSize().clear();
        searchFilterPost.getListHardDrive().clear();
        searchFilterPost.getListBrandName().clear();
    }
    public void clickOnBrandButton()
    {
        searchResultFragmentType = preferenceManager.getInt(Constants.KEY_POST_SEARCH_RESULT_TYPE,0);
        preferenceManager.removeKey(Constants.KEY_POST_SEARCH_RESULT_TYPE);
        if(searchResultFragmentType == ADAPTER_TYPE0_CLICK)
        {
            cleanSearchFilterPost();
            isLoading.setVisibility(View.VISIBLE);
            rcvPostSearchResult.setVisibility(View.GONE);
            String brand = preferenceManager.getString(BrandTable.BRAND_NAME);
            preferenceManager.removeKey(BrandTable.BRAND_NAME);
            searchFilterPost.getListBrandName().add(brand);
            postPreseneter.OnSearchPostByFilter(searchFilterPost);
            preferenceManager.putSerializable(Constants.KEY_FILTER_SEARCH,searchFilterPost);
            reloadListFilter();
        }
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