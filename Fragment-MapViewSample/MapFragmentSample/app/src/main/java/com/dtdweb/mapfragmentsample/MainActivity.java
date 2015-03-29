package com.dtdweb.mapfragmentsample;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.dtdweb.mapfragmentsample.fragments.MapSampleFragment;
import com.dtdweb.mapfragmentsample.fragments.SampleFragment;


public class MainActivity extends ActionBarActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_sample_1).setOnClickListener(this);
        findViewById(R.id.button_sample_2).setOnClickListener(this);

        // Map Fragmentを呼び出す
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.layout_fragment, MapSampleFragment.newInstance())
                .commit();

    }

    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (v.getId()) {
            case R.id.button_sample_1:
                fragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .replace(R.id.layout_fragment, MapSampleFragment.newInstance())
                        .commit();
                break;
            case R.id.button_sample_2:
                fragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .replace(R.id.layout_fragment, SampleFragment.newInstance())
                        .commit();
                break;
        }
    }
}