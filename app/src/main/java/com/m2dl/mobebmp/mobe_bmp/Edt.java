package com.m2dl.mobebmp.mobe_bmp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * activity qui affiche l'emploi du temps en fonction de l'url par défaut (M2 DL) ou celle entrée dans les paramètres
 */

public class Edt extends MobeBMPActivity {
    private WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt);
        view = (WebView) findViewById(R.id.edtView);
        view.setWebViewClient(new WebViewClient());
        view.getSettings().setLoadsImagesAutomatically(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        view.loadUrl(getUrl());
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setDisplayZoomControls(false);
    }

    private String getUrl()
    {
        /*SharedPreferences settings = getSharedPreferences("preferences", Context.MODE_PRIVATE);*/
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String url = settings.getString(getString(R.string.config_urlEdt_key), getString(R.string.config_urlEdt_value));
        if (url.equals(""))
        {
            url = getString(R.string.config_urlEdt_value);
        }
        return url;
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
