package com.android.acumen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

public class FragmentOffice extends Fragment {

    public FragmentOffice(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_office, container, false);

        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.officeGallery);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(),"http://pg18.comeze.com/apps/acumen/office/list.php","http://pg18.comeze.com/apps/acumen/office/");
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(0);

        UnderlinePageIndicator linePageIndicator = (UnderlinePageIndicator)rootView.findViewById(R.id.officeGalleryIndicator);
        linePageIndicator.setFades(false);
        linePageIndicator.setBackgroundColor(getResources().getColor(R.color.scroll_bg));
        linePageIndicator.setViewPager(viewPager);

        return rootView;
    }
}

