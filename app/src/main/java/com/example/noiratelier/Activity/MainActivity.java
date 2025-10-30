package com.example.noiratelier.Activity;

import static androidx.core.view.ViewCompat.setOverScrollMode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.noiratelier.Adapter.BannerAdapter;
import com.example.noiratelier.Adapter.CategoryAdapter;
import com.example.noiratelier.Adapter.PopularAdapter;
import com.example.noiratelier.Domain.BannerModel;
import com.example.noiratelier.Domain.CategoryModel;
import com.example.noiratelier.R;
import com.example.noiratelier.ViewModel.MainViewModel;
import com.example.noiratelier.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new MainViewModel();
        initCategory();
        initBanner();
        initPopular();
        initBottomNavigation();
    }

    private void initBottomNavigation() {
          binding.bottomNavigation.setItemSelected(R.id.home,true);
          binding.bottomNavigation.setOnItemSelectedListener(i->{
              if(i==R.id.home){

              }else if(i==R.id.cart){
                  startActivity(new Intent(MainActivity.this,CartActivity.class));
              }
          });
    }

    private void initPopular() {
        binding.progressBarPopular.setVisibility(View.VISIBLE);
        viewModel.loadPopular().observeForever(itemsModels -> {
            if(!itemsModels.isEmpty()){
                binding.popularView.setLayoutManager(
                        new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                binding.popularView.setAdapter(new PopularAdapter(itemsModels));
                binding.popularView.setNestedScrollingEnabled(true);

            }
            binding.progressBarPopular.setVisibility(View.GONE);

        });
        viewModel.loadPopular();
    }

    private void initCategory() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        viewModel.loadCategory().observeForever(categoryModels ->{
            binding.categoryView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
            binding.categoryView.setAdapter(new CategoryAdapter(categoryModels));
            binding.categoryView.setNestedScrollingEnabled(true);
            binding.progressBarCategory.setVisibility(View.GONE);
        });
    }
    private void banners (ArrayList<BannerModel> bannerModels) {
        binding.viewPagerBanner.setAdapter(new BannerAdapter(bannerModels, binding.viewPagerBanner));
        binding.viewPagerBanner.setClipToPadding(false);
        binding.viewPagerBanner.setClipChildren(false);
        binding.viewPagerBanner.setOffscreenPageLimit(3);
        binding.viewPagerBanner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        binding.viewPagerBanner.setPageTransformer(compositePageTransformer);
    }
    private void initBanner() {
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        viewModel.loadBanner().observeForever( bannerModels -> {
            if (bannerModels != null && !bannerModels.isEmpty()) {
                banners(bannerModels);
                binding.progressBarBanner.setVisibility(View.GONE);
            }
        });
    }
}