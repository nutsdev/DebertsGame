package com.nutsdev.deberts.klabor.app.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.nutsdev.deberts.klabor.R;
import com.nutsdev.deberts.klabor.app.ui.fragments.MainMenuFragment;
import com.nutsdev.deberts.klabor.app.ui.fragments.MainMenuFragment_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by n1ck on 08.03.2015.
 */
@EActivity(R.layout.activity_main_menu)
public class MainMenuActivity extends ActionBarActivity {

    public static final int FragmentTransitionType_None = 0;
    public static final int FragmentTransitionType_Slide = 1;
    public static final int FragmentTransitionType_Slide_BackOnly = 2;

    @ViewById
    FrameLayout container_view;


    /* lifecycle */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // remove title
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            MainMenuFragment mainMenuFragment = MainMenuFragment_.builder().build();
            switchFragmentInternal(mainMenuFragment, FragmentTransitionType_None, false);
        }
    }


    /* methods */

    public void switchFragment(Fragment fragment, int fragmentTransitionType) {
        switchFragmentInternal(fragment, fragmentTransitionType, true);
    }

    private void switchFragmentInternal(Fragment fragment, int fragmentTransitionType, boolean addToBackStack) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        if (fragmentTransitionType == FragmentTransitionType_Slide)
            fragmentTransaction.setCustomAnimations(R.anim.slide_left1, R.anim.slide_left2, R.anim.slide_right1, R.anim.slide_right2);
        else if (fragmentTransitionType == FragmentTransitionType_Slide_BackOnly)
            fragmentTransaction.setCustomAnimations(0, 0, R.anim.slide_right1, R.anim.slide_right2);

        fragmentTransaction.replace(R.id.container_view, fragment);

        if(addToBackStack)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

}
