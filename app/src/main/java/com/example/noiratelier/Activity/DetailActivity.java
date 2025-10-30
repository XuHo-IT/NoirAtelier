package com.example.noiratelier.Activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.noiratelier.Adapter.ColorAdapter;
import com.example.noiratelier.Adapter.SizeAdapter;
import com.example.noiratelier.Adapter.picListAdapter;
import com.example.noiratelier.Domain.ItemsModel;
import com.example.noiratelier.Helper.ManagmentCart;
import com.example.noiratelier.R;
import com.example.noiratelier.databinding.ActivityDetailBinding;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
   private ActivityDetailBinding binding;
   private ItemsModel object;
   private int numberOrder = 1;
   private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        getBundle();
        initPicList();
        initSize();
        initColor();
    }

    private void initColor() {
        binding.recycleColor.setAdapter(new ColorAdapter((object.getColor())));
        binding.recycleColor.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void initSize() {
        binding.recyclerSize.setAdapter(new SizeAdapter(object.getSize()));
        binding.recyclerSize.setLayoutManager (new
                LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL,  true));
    }

    private void initPicList() {
        ArrayList<String> picList = new ArrayList<>(object.getPicUrl());
        Glide.with(this).load(picList.get(0)).into(binding.pic);
        binding.picList.setAdapter(new picListAdapter(picList,binding.pic));
        binding.picList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void getBundle() {
        object =(ItemsModel) getIntent().getSerializableExtra("object");
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$"+object.getPrice());
        binding.oldPriceTxt.setText("$"+object.getOldPrice());
        binding.oldPriceTxt.setPaintFlags(binding.oldPriceTxt.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        binding.descriptionTxt.setText(object.getDescription());
        binding.addToCartBtn.setOnClickListener( v ->{
            object.setNumberInCart(numberOrder);
            managmentCart.insertItem(object);
        });
        binding.backBtn.setOnClickListener(v->finish());
    }
}