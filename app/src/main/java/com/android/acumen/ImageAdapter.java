package com.android.acumen;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import android.widget.TextView;

import com.android.acumen.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends PagerAdapter {
    Context context;
    private int[] GalImages = new int[] {1,2,3};
    private String[] ImageInfo = new String[]{""};
    LayoutInflater myInflater;

    ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ScrollView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        myInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = myInflater.inflate(R.layout.container, container,false);

        TextView txtView = (TextView)myView.findViewById(R.id.imgMapDescription);
        ImageView imgView = (ImageView)myView.findViewById(R.id.imgMap);

        txtView.setText("Sample");
        final ProgressBar pb = (ProgressBar)myView.findViewById(R.id.progressBar);
//        new LoadImage(img,pb).execute("http://goo.gl/UjlvzJ");
        pb.setVisibility(View.VISIBLE);
        Picasso.with(context)
                .load("http://goo.gl/UjlvzJ")
                .into(imgView, new Callback() {
                    @Override
                    public void onSuccess() {
                        pb.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        ((ViewPager)container).addView(myView);
        return myView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ScrollView) object);
    }
}
