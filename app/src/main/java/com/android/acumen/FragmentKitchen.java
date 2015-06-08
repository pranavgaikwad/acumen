package com.android.acumen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentKitchen extends Fragment {

    public FragmentKitchen(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_office, container, false);

        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.officeGallery);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(),"http://pg18.comeze.com/apps/acumen/kitchen/list.php","http://pg18.comeze.com/apps/acumen/kitchen/");
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(0);

        return rootView;
    }
}

