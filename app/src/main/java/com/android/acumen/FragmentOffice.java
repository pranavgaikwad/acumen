package com.android.acumen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentOffice extends Fragment {

    public FragmentOffice(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_office, container, false);

        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.officeGallery);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(0);

        return rootView;
    }
}

