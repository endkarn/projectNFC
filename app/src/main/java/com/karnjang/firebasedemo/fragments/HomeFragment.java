package com.karnjang.firebasedemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karnjang.firebasedemo.R;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        BannerSlider bannerSlider = (BannerSlider) view.findViewById(R.id.banner_slider_home);
        BannerSlider bannerSlider1 = (BannerSlider) view.findViewById(R.id.banner_slider_home1);
        BannerSlider bannerSlider2 = (BannerSlider) view.findViewById(R.id.banner_slider_home2);
        List<Banner> banners = new ArrayList<>();
        List<Banner> deal = new ArrayList<>();
        banners.add(new RemoteBanner("https://i.imgur.com/CRWHpLh.png"));
        banners.add(new RemoteBanner("https://i.imgur.com/lI99iNY.png"));
        banners.add(new RemoteBanner("https://i.imgur.com/cHyU97V.png"));
        deal.add(new RemoteBanner("https://i.imgur.com/hgvu8d8.png"));
        deal.add(new RemoteBanner("https://i.imgur.com/albdO1L.png"));
        deal.add(new RemoteBanner("https://i.imgur.com/hyElK5r.png"));
        deal.add(new RemoteBanner("https://i.imgur.com/2pq75Dg.png"));
        deal.add(new RemoteBanner("https://i.imgur.com/JEODChI.png"));

        bannerSlider.setBanners(banners);
        bannerSlider1.setBanners(deal);
        bannerSlider2.setBanners(deal);
        return view;

    }

}
