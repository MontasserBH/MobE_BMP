package com.m2dl.mobebmp.mobe_bmp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

public class Home extends AppCompatActivity implements OnMapReadyCallback {

    MapFragment mMapFragment;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mMapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        int height = 60;
        int width = 60;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.batiment);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        LatLng u4_position = new LatLng(43.562715, 1.469315);
        LatLng u3_position = new LatLng(43.562043, 1.469969);

        MarkerOptions u4_maker = new MarkerOptions()
                .position(u4_position)
                .title("U4")
                .snippet(u4_position.toString())
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        MarkerOptions u3_maker = new MarkerOptions()
                .position(u3_position)
                .title("U3")
                .snippet(u3_position.toString())
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

        map.addMarker(u3_maker);
        map.addMarker(u4_maker);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            }
        });

        googleMap.setPadding(0, 840, 0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_ma_fac:
            /* DO EDIT */
                return true;
            case R.id.menu_geoloc:
                intent = new Intent(Home.this, Geolocalisation.class);
                startActivity(intent);
                return true;
            case R.id.menu_mes_cours:
            /* DO ADD */
                return true;
            case R.id.menu_configuration:
            /* DO DELETE */
                return true;
            case R.id.menu_informations_personnelles :
                intent = new Intent(Home.this, Configurations.class);
                startActivity(intent);
                return true;
            case R.id.menu_emploi_du_temps:
                intent = new Intent(Home.this, Edt.class);
                startActivity(intent);
                return true;
            case R.id.menu_informations_fac:
                intent = new Intent(Home.this,QRCodeReader.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
