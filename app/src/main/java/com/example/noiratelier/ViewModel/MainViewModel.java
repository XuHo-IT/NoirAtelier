package com.example.noiratelier.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.noiratelier.Domain.BannerModel;
import com.example.noiratelier.Domain.CategoryModel;
import com.example.noiratelier.Domain.ItemsModel;
import com.example.noiratelier.Repository.MainRepository;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private final MainRepository repository = new MainRepository();
    public LiveData<ArrayList<CategoryModel>> loadCategory(){ return repository.loadCategory();}
    public LiveData<ArrayList<BannerModel>> loadBanner(){ return repository.loadBanner();}
    public LiveData<ArrayList<ItemsModel>> loadPopular(){ return repository.loadPopular();}
}
