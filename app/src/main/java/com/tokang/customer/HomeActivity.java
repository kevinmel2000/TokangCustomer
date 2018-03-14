package com.tokang.customer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.tokang.customer.fragments.AccountFragment;
import com.tokang.customer.fragments.HelpFragment;
import com.tokang.customer.fragments.MenuFragment;
import com.tokang.customer.fragments.OrderFragment;
import com.tokang.customer.utils.BottomNavigationViewHelper;

public class HomeActivity extends AppCompatActivity implements
        MenuFragment.OnFragmentInteractionListener, OrderFragment.OnFragmentInteractionListener,
        HelpFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = MenuFragment.newInstance("","");
                    break;
                case R.id.navigation_order:
                    selectedFragment = OrderFragment.newInstance("","");
                    break;
                case R.id.navigation_help:
                    selectedFragment = HelpFragment.newInstance("","");
                    break;
                case R.id.navigation_account:
                    selectedFragment = AccountFragment.newInstance("","");
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, MenuFragment.newInstance("",""));
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

        getSupportActionBar().setElevation(0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
    }

    public void setActionBarImage(){
        if(getSupportActionBar().getCustomView() == null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.actionbar_image_home, null);

            actionBar.setCustomView(v);
        } else {
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }
        getSupportActionBar().setTitle("");
    }
}
