package com.m2dl.mobebmp.mobe_bmp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by mazab on 11/03/2018.
 */

public class MobeBMPActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

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
                intent = new Intent(this, Configurations.class);
                startActivity(intent);
                return true;
            case R.id.menu_emploi_du_temps:
                intent = new Intent(this, Edt.class);
                startActivity(intent);
                return true;
            case R.id.menu_informations_fac:
                intent = new Intent(this,QRCodeReader.class);
                startActivity(intent);
                return true;
            case R.id.menu_point_interet:
                intent = new Intent(this,InfosActivity.class);
                startActivity(intent);
                return true;
        }
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
