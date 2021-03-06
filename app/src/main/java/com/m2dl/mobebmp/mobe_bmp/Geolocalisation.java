package com.m2dl.mobebmp.mobe_bmp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

/**
 * Created by MONTASSER on 11/03/2018.
 */

public class Geolocalisation extends MobeBMPActivity implements OnMapReadyCallback, GoogleMap.OnPoiClickListener, GoogleMap.OnMarkerClickListener {

    MapFragment mMapFragment;
    GoogleMap googleMap;
    Marker markerPoi;
    UiSettings mUiSettings;
    Marker marker;
    DatabaseReference databaseReference;

    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;

    private boolean mLocationPermissionDenied = false;
    private DatabaseReference mBatiments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mMapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        saveDadaInFireBase();

        mBatiments = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobe-bmp-db8ce.firebaseio.com/");
        mBatiments.push().setValue(marker);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        googleMap.setOnPoiClickListener(this);

        mUiSettings = googleMap.getUiSettings();
        mUiSettings.setMapToolbarEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setPadding(0, 800, 0, 0);

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.batiment);
        final Bitmap smallMarker = Bitmap.createScaledBitmap(bitmapdraw.getBitmap(), 60, 60, false);

        mBatiments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Batiment batiment = ds.getValue(Batiment.class);
                    LatLng latLng = new LatLng(batiment.getLongitude(), batiment.getLatitude());
                    googleMap.addMarker(new MarkerOptions()
                            .title(batiment.getName())
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (markerPoi != null) {
                markerPoi.remove();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            MarkerOptions options = new MarkerOptions().position(latLng).title(address.getAddressLine(0));
            markerPoi = googleMap.addMarker(options);
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_PERMISSION_REQUEST_CODE) {
            // Enable the My Location button if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                mUiSettings.setMyLocationButtonEnabled(true);
            } else {
                mLocationPermissionDenied = true;
            }

        } else if (requestCode == LOCATION_LAYER_PERMISSION_REQUEST_CODE) {
            // Enable the My Location layer if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
            } else {
                mLocationPermissionDenied = true;
            }
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mLocationPermissionDenied) {
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(false).show(getSupportFragmentManager(), "dialog");
            mLocationPermissionDenied = false;
        }
    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
        if (markerPoi != null) {
            markerPoi.remove();
        }
        MarkerOptions options = new MarkerOptions().position(poi.latLng).title(poi.name);
        markerPoi = googleMap.addMarker(options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    public void saveDadaInFireBase() {
        Batiment u4 = new Batiment("U4", 43.562694, 1.469332);
        Batiment u3 = new Batiment("U3", 43.561969, 1.469866);
        Batiment u2 = new Batiment("U2", 43.561175, 1.470413);
        Batiment u1 = new Batiment("U1", 43.560473, 1.470269);
        Batiment irit = new Batiment("IRIT", 43.562140, 1.468033);
        Batiment ru1 = new Batiment("RU1", 43.562451, 1.463280);
        Batiment bu = new Batiment("Bibliothèque", 43.563929, 1.465059);
        databaseReference.child(u1.getName()).setValue(u1);
        databaseReference.child(u2.getName()).setValue(u2);
        databaseReference.child(u3.getName()).setValue(u3);
        databaseReference.child(u4.getName()).setValue(u4);
        databaseReference.child(irit.getName()).setValue(irit);
        databaseReference.child(ru1.getName()).setValue(ru1);
        databaseReference.child(bu.getName()).setValue(bu);
    }
}
