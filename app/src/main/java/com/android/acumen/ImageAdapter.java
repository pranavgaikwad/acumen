package com.android.acumen;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends PagerAdapter {
    Context context;
    public String[] temp;      //stores the list of files obtained from server. Contains shitty output
    public List<String> GalImagesList = new ArrayList<String>();
    public String[] GalImages = {""};
    public String[] ImageInfo = {""};
    String dirLink = "";
    String imgLink = "";
    public String listOfFiles = "";
    LayoutInflater myInflater;

    ImageAdapter(Context context, String dirLink, String imgLink){
        this.context = context;
        this.dirLink = dirLink;
        this.imgLink = imgLink;

        new GetList(this).execute(dirLink);

    }

    public void removeStrings(){}
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
                .load(imgLink + GalImages[position])
                .into(imgView, new Callback() {
                    @Override
                    public void onSuccess() {
                        pb.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Please wait while the image loads",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        ((ViewPager)container).addView(myView);
        return myView;

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ScrollView) object);
    }
}

class GetList extends AsyncTask<String, Void, String> {

    private Exception exception;
    private ImageAdapter imageAdapter;
    private String result = "";

    public GetList(ImageAdapter imageAdapter){
        this.imageAdapter = imageAdapter;
    }

    protected String doInBackground(String... urls) {
        String link = urls[0];
        InputStream is;
        StringBuilder sb;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        try {
            int timeout= 7000;
            HttpPost httppost= null;
            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, timeout);
            httppost = new HttpPost(link);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
            sb = new StringBuilder();
            //sb.append(reader.readLine() + "\n");
            String line="0";
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();
            Log.d("Inside Async Task (Result) : ", result);
            return result;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String str) {
        imageAdapter.listOfFiles = result;
        imageAdapter.temp = imageAdapter.listOfFiles.split("\n");
        for(int i = 0; i < imageAdapter.temp.length-4 ; i++)
            imageAdapter.GalImagesList.add(imageAdapter.temp[i]);
        imageAdapter.GalImages = new String[imageAdapter.GalImagesList.size()];
        imageAdapter.GalImagesList.toArray(imageAdapter.GalImages);
        for(int i = 0; i < imageAdapter.GalImages.length; i++)
            Log.d("Array : ", imageAdapter.GalImages[i]);
        imageAdapter.notifyDataSetChanged();
    }
}