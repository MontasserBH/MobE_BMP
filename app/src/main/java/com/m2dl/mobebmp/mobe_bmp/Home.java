package com.m2dl.mobebmp.mobe_bmp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class Home extends MobeBMPActivity {

    MapFragment mMapFragment;
    GoogleMap googleMap;
    private DatabaseReference databaseReference;
    Marker markerPoi;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        saveDadaInFireBase();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }
}
