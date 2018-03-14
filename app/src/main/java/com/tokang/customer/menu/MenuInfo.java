package com.tokang.customer.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tokang.customer.R;
import com.tokang.customer.constants.KeyConstants;
import com.tokang.customer.menu.renovation.ToRenov;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class MenuInfo extends AppCompatActivity {
    private String key;
    private String actionBarTitle;
    private ArrayList<String> imageDescriptions;
    private String description;

    private BannerSlider bannerSlider;
    private ImageView singleImage;
    private TextView txtDescription;
    private Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_info);
        bannerSlider = findViewById(R.id.banner_slider);
        singleImage = findViewById(R.id.single_image);
        txtDescription = findViewById(R.id.descriptions);
        btnOrder = findViewById(R.id.btn_order);

        if(getIntent() != null){
            key = getIntent().getStringExtra("Key");
            actionBarTitle = getIntent().getStringExtra("Name");
            imageDescriptions = getIntent().getStringArrayListExtra("ImageDescriptions");
            description = getIntent().getStringExtra("Description");
        }
        setActionBarTitle();
        getImageDescription();
        txtDescription.setText(description);
        btnOrder.setOnClickListener(btnOrderClickListener());
    }

    private void setActionBarTitle(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(actionBarTitle);
            actionBar.setElevation(0);
        }
    }

    private void getImageDescription() {
        if(imageDescriptions.size()>1) {
            List<Banner> banners = new ArrayList<>();

            //add banner using resource drawable
            for (String url : imageDescriptions) {
                banners.add(new RemoteBanner(url));
            }
            bannerSlider.setVisibility(View.VISIBLE);
            bannerSlider.setBanners(banners);
            singleImage.setVisibility(View.GONE);
        } else {
            singleImage.setVisibility(View.VISIBLE);
            Picasso.with(this).load(imageDescriptions.get(0)).into(singleImage);
            bannerSlider.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener btnOrderClickListener(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (key){
                    case KeyConstants.RENOVATION_KEY:
                        setIntent(ToRenov.class);
                        break;
                    default:
                        finish();
                }
            }
        };

    }

    private void setIntent(Class objClass){
        Intent intent = new Intent(this, objClass);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
