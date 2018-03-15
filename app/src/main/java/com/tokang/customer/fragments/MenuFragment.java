package com.tokang.customer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tokang.customer.HomeActivity;
import com.tokang.customer.R;
import com.tokang.customer.adapter.MenuAdapter;
import com.tokang.customer.adapter.RecommendationAdapter;
import com.tokang.customer.constants.KeyConstants;
import com.tokang.customer.model.Menu;
import com.tokang.customer.model.RecommendedItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recycler_menu;
    private GridLayoutManager layoutManager;

    private RecyclerView recycler_recommendation;
    private LinearLayoutManager recomLayoutManager;

    private List<Menu> menu_list;
    private List<RecommendedItem> recom_item_list;

    private SpinKitView loadingProgress;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference adsBanner;
    private DatabaseReference menuRef;
    private DatabaseReference recomRef;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((HomeActivity) getActivity()).setActionBarImage();

        menu_list = new ArrayList<>();
        recom_item_list = new ArrayList<>();

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        adsBanner = database.getReference(KeyConstants.ADS_BANNER_KEY);
        menuRef = database.getReference(KeyConstants.MENU_KEY);
        recomRef = database.getReference(KeyConstants.RECOMMENDED_ITEM_KEY);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setActionBarImage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        loadingProgress = view.findViewById(R.id.loading_progress);

        recycler_menu = view.findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(),2);
        recycler_menu.setLayoutManager(layoutManager);

        recycler_recommendation = view.findViewById(R.id.recycler_recommendation);
        recycler_recommendation.setHasFixedSize(true);
        recomLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_recommendation.setLayoutManager(recomLayoutManager);

        menuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllMenus(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adsBanner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getBannerSlider(view, dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllRecommendation(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void getBannerSlider(View view, DataSnapshot dataSnapshot) {
        BannerSlider bannerSlider = view.findViewById(R.id.banner_slider);
        List<Banner> banners = new ArrayList<>();

        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            banners.add(new RemoteBanner(singleSnapshot.getValue(String.class)));
        }

        bannerSlider.setBanners(banners);
    }

    private void setAdapter(){
        if(menu_list.size()>0){
            MenuAdapter menuAdapter = new MenuAdapter(getContext(), menu_list);
            recycler_menu.setAdapter(menuAdapter);
        }
        loadingProgress.setVisibility(View.GONE);
    }

    private void setRecomAdapter(){
        if(recom_item_list.size()>0){
            RecommendationAdapter recomAdapter = new RecommendationAdapter(getContext(), recom_item_list);
            recycler_recommendation.setAdapter(recomAdapter);
        }
    }

    private void getAllMenus(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            String key = singleSnapshot.getKey();
            String name = singleSnapshot.child("name").getValue(String.class);
            String tagline = singleSnapshot.child("tagline").getValue(String.class);
            String logoDark = singleSnapshot.child("logoDark").getValue(String.class);
            String logoLight = singleSnapshot.child("logoLight").getValue(String.class);
            String description = singleSnapshot.child("description").getValue(String.class);
            Integer index = singleSnapshot.child("index").getValue(Integer.class);
            String menuIcon = singleSnapshot.child("menuIcon").getValue(String.class);

            ArrayList<String> imageDesc = new ArrayList<>();
            for(DataSnapshot imageDescSnapshot : singleSnapshot.child("imageDescription").getChildren()){
                imageDesc.add(imageDescSnapshot.getValue(String.class));
            }

            Menu menu = new Menu(key, name);
            menu.setTagline(tagline);
            menu.setLogoDark(logoDark);
            menu.setLogoLight(logoLight);
            menu.setDescription(description);
            menu.setImageDescription(imageDesc);
            menu.setIndex(index);
            menu.setMenuIcon(menuIcon);
            menu_list.add(menu);
        }
        Collections.sort(menu_list, new Comparator<Menu>() {
            @Override
            public int compare(Menu menu, Menu t1) {
                return menu.getIndex().compareTo(t1.getIndex());
            }
        });
        setAdapter();
    }

    private void getAllRecommendation(DataSnapshot dataSnapshot) {
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            String image = singleSnapshot.child("image").getValue(String.class);
            String name = singleSnapshot.child("name").getValue(String.class);

            RecommendedItem ri = new RecommendedItem("tes", image, name);
            recom_item_list.add(ri);
        }
        setRecomAdapter();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
