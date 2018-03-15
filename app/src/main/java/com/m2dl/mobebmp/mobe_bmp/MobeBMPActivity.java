package com.m2dl.mobebmp.mobe_bmp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by mazab on 11/03/2018.
 */

public class MobeBMPActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_ma_fac:
                return true;
            case R.id.menu_geoloc:
                intent = new Intent(this, Geolocalisation.class);
                startActivity(intent);
                return true;
            case R.id.menu_anomalies:
                intent = new Intent(this, Anomalies.class);
                startActivity(intent);
                return true;
            case R.id.menu_mes_cours:
                return true;
            case R.id.menu_configuration:
                return true;
            case R.id.menu_informations_personnelles :
                intent = new Intent(this, QRCodeReader.class);
                startActivity(intent);
                return true;
            case R.id.menu_emploi_du_temps:
                intent = new Intent(this, Edt.class);
                startActivity(intent);
                return true;
            case R.id.menu_informations_fac:
                intent = new Intent(this,InfosActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_point_interet:
                intent = new Intent(this,PartagerPointsDInteret.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
