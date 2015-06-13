package com.android.acumen;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URL;
import java.util.List;

public class FragmentFind extends Fragment implements LocationListener {

    public FragmentFind(){}
    private LocationManager locationManager;
    private String provider;
    private boolean enabled;
    private Context context;
    private GoogleMap googleMap;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_find, container, false);
        context = getActivity();
        //
        //  Code to create maps
        //
        try {
            initializeMap(context);
            // latitude and longitude

        }
        catch (Exception e){

        }

        //
        //  Code to handle Find Route
        //
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        Button findRoute = (Button)rootView.findViewById(R.id.find_button);
        findRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!enabled){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("GPS not enabled !");
                    dialog.setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(myIntent);
                            enabled = true;
                            //get gps
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            enabled = false;
                        }
                    });
                    dialog.show();
                }
                else {
                    double dlat = 18.449532;
                    double dlon = 73.798646;
                    Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if(location == null){
                        Toast.makeText(context, "GPS not available",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        double slat = (double) location.getLatitude();
                        double slon = (double) location.getLongitude();
                        final Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + slat + "," + slon + "&daddr=" + dlat + "," + dlon));
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }
            }
        });
        return rootView;
    }

    private Location getLastKnownLocation(LocationManager mLocationManager, Context context) {
        mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    private void initializeMap(Context context) {
        if (googleMap == null) {
            // googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            //MapFragment mapFragment = new MapFragment();
            //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            //transaction.add(R.id.map, mapFragment).commit();
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            double latitude = 18.449532;
            double longitude = 73.798646;

            // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Acumen Furniture");

            // adding marker
            googleMap.addMarker(marker);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(15).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(context,
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onResume() {
        super.onResume();
        // initializeMap(context);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MapFragment f = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        if(f != null){}
            getFragmentManager().beginTransaction().remove(f).commit();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
