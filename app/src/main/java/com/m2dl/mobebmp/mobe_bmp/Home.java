package com.m2dl.mobebmp.mobe_bmp;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

public class Home extends AppCompatActivity {
    MapView mMapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //inflate and create the map
        setContentView(R.layout.main);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);

        IMapController mapController = mMapView.getController();
        mapController.setZoom(17);
        GeoPoint startPoint = new GeoPoint(43.5610329,1.4720187);
        mapController.setCenter(startPoint);
    }
}
