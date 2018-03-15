package com.tokang.customer.menu.renovation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tokang.customer.R;
import com.tokang.customer.adapter.RenovationAdapter;
import com.tokang.customer.constants.KeyConstants;
import com.tokang.customer.model.RenovationModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ToRenov extends AppCompatActivity {
    private RecyclerView recycler_renovation;
    private GridLayoutManager layoutManager;
    private List<RenovationModel> renovation_list;

    private SpinKitView loadingProgress;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference toRenovPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_renov);
        setActionBarImage();

        loadingProgress = findViewById(R.id.loading_progress);

        renovation_list = new ArrayList<>();
        recycler_renovation = findViewById(R.id.recycler_renovation);
        recycler_renovation.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);

        recycler_renovation.setLayoutManager(layoutManager);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        toRenovPackage = database.getReference(KeyConstants.RENOVATION_KEY);

        toRenovPackage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllRenovationPackage(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setAdapter(){
        if(renovation_list.size()>0) {
            RenovationAdapter renovationAdapter = new RenovationAdapter(this, renovation_list);
            recycler_renovation.setAdapter(renovationAdapter);
        }
        loadingProgress.setVisibility(View.GONE);
    }
    private void getAllRenovationPackage(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            String key = singleSnapshot.getKey();
            String name = singleSnapshot.child("name").getValue(String.class);
            String image = singleSnapshot.child("image").getValue(String.class);
            Long priceMinimum = singleSnapshot.child("priceMinimum").getValue(Long.class);
            Long priceMaximum = singleSnapshot.child("priceMaximum").getValue(Long.class);
            Integer index = singleSnapshot.child("index").getValue(Integer.class);
            BigDecimal priceMin = new BigDecimal("0");
            BigDecimal priceMax = new BigDecimal("0");

            if(priceMinimum != null) {
                priceMin = new BigDecimal(priceMinimum);
            }
            if(priceMaximum != null){
                priceMax = new BigDecimal(priceMaximum);
            }

            String description = singleSnapshot.child("description").getValue(String.class);

            ArrayList<String> renovationImageDesc = new ArrayList<>();

            for(DataSnapshot imageDescSnapshot : singleSnapshot.child("imageDescription").getChildren()){
                renovationImageDesc.add(imageDescSnapshot.getValue(String.class));
            }

            RenovationModel renovationPackage = new RenovationModel(key, KeyConstants.RENOVATION_KEY);
            renovationPackage.setName(name);
            renovationPackage.setImage(image);
            renovationPackage.setPriceMinimum(priceMin);
            renovationPackage.setPriceMaximum(priceMax);
            renovationPackage.setImageDescription(renovationImageDesc);
            renovationPackage.setDescription(description);
            renovationPackage.setIndex(index);
            renovation_list.add(renovationPackage);
        }

        Collections.sort(renovation_list, new Comparator<RenovationModel>() {
            @Override
            public int compare(RenovationModel renovationModel, RenovationModel t1) {
                return renovationModel.getIndex().compareTo(t1.getIndex());
            }
        });

        setAdapter();
    }

    private void setActionBarImage(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(inflater != null) {
                View v = inflater.inflate(R.layout.actionbar_image_renovation, null);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setElevation(0);
                actionBar.setCustomView(v);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
