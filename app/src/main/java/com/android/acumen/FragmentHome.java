package com.android.acumen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.pushbots.push.Pushbots;
import com.viewpagerindicator.UnderlinePageIndicator;

public class FragmentHome extends Fragment {

    public FragmentHome(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        WebView homeDescription = (WebView)rootView.findViewById(R.id.homeDescription);
        homeDescription.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = homeDescription.getSettings();
        webSettings.setDefaultFixedFontSize(8);
        webSettings.setAllowFileAccess(true);
        //homeDescription.loadData(getString(R.string.home_information),"text/html","utf-8");
        homeDescription.loadDataWithBaseURL( "file:///android_asset/", getString(R.string.home_information), "text/html", "utf-8", null);

        return rootView;
    }
}

