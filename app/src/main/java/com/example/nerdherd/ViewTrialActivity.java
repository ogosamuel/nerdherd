package com.example.nerdherd;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;



/**
 * Multiple location shower
 * The number of markers states all the locations where the experiment is performed for multiple users when they start the trial
 * All the locations are shown with a red marker on the map stating the geolocation stored in the database
 */

public class ViewTrialActivity extends Activity {

    private SupportMapFragment smf;
    private FusedLocationProviderClient client;
    private String experimentTitle;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trial);

        experimentTitle = getIntent().getStringExtra("expTitle");

        isGpsEnabled(ViewTrialActivity.this);

        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    private void getmylocation() {
    }

    void isGpsEnabled(Context context) {
    }

}
