package com.m2dl.mobebmp.mobe_bmp;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;

public class Home extends AppCompatActivity {
    MapView mMapView = null;
    private CompassOverlay mCompassOverlay;

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

        this.mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), mMapView);
        this.mCompassOverlay.enableCompass();
        this.mCompassOverlay.setCompassCenter(40, 500);
        mMapView.getOverlays().add(this.mCompassOverlay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ma_fac:
            /* DO EDIT */
                return true;
            case R.id.menu_mes_cours:
            /* DO ADD */
                return true;
            case R.id.menu_configuration:
            /* DO DELETE */
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
