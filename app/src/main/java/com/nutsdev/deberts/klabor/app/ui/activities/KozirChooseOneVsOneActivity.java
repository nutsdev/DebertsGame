package com.nutsdev.deberts.klabor.app.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import com.nutsdev.deberts.klabor.R;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_kozir_choose_one_vs_one)
public class KozirChooseOneVsOneActivity extends ActionBarActivity {

    /* lifecycle */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // remove title
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

}
