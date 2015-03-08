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

@EActivity(R.layout.activity_logo)
public class LogoActivity extends Activity {

    @ViewById
    Button activityNext_button;

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

    @Click(R.id.activityNext_button)
    void nextButton_click() {
        MainMenuActivity_.intent(this).start();
        finish();
    }

}
