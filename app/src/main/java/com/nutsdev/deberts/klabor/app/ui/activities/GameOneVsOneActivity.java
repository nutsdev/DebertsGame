package com.nutsdev.deberts.klabor.app.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.nutsdev.deberts.klabor.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;

@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.activity_game_one_vs_one)
public class GameOneVsOneActivity extends ActionBarActivity {

    /* lifecycle */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
