package com.nutsdev.deberts.klabor.app.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.nutsdev.deberts.klabor.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by n1ck on 08.03.2015.
 */
@EActivity(R.layout.activity_main_menu)
public class MainMenuActivity extends Activity {

    @ViewById
    Button continueGame_button;

    @ViewById
    Button newGame_button;

    @ViewById
    Button settings_button;

    @ViewById
    Button adOff_button;

    @ViewById
    Button exit_button;


    /* lifecycle */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /* clicks */

    @Click(R.id.exit_button)
    void exitButton_click() {
        finish();
    }

}
