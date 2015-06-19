package com.android.acumen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.UnderlinePageIndicator;

public class FragmentResidential extends Fragment {

    public FragmentResidential(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_office, container, false);

        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.officeGallery);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(),"http://pg18.comeze.com/apps/acumen/residential/list.php","http://pg18.comeze.com/apps/acumen/residential/");
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(0);

        UnderlinePageIndicator linePageIndicator = (UnderlinePageIndicator)rootView.findViewById(R.id.officeGalleryIndicator);
        linePageIndicator.setFades(false);
        linePageIndicator.setViewPager(viewPager);

        return rootView;
    }
}

