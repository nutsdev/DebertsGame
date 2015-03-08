package com.nutsdev.deberts.klabor.app.utils;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.nutsdev.deberts.klabor.app.ui.activities.MainMenuActivity;

/**
 * Created by maxim_000 on 02.03.2015.
 */
public class NavigationHelper {

    public static final int FragmentTransitionType_None = 0;
    public static final int FragmentTransitionType_Slide = 1;
    public static final int FragmentTransitionType_Slide_BackOnly = 2;

    public static NavigationHelper create(Fragment fragment) {
        return new NavigationHelper(fragment);
    }

    private final Fragment fragment;

    private NavigationHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setTitle(String title) {
        FragmentActivity activity = getFragmentActivity();
        ActionBar actionBar = activity.getActionBar();
        if(actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setTitle(int titleResId) {
        FragmentActivity activity = getFragmentActivity();
        ActionBar actionBar = activity.getActionBar();
        if(actionBar != null) {
            actionBar.setTitle(titleResId);
        }
    }

    public void switchFragment(Fragment fragment, int fragmentTransitionType) {
        MainMenuActivity navigationActivity = getNavigationActivity();
        navigationActivity.switchFragment(fragment, fragmentTransitionType);
    }

    public void switchFragment(Fragment fragment) {
        MainMenuActivity navigationActivity = getNavigationActivity();
        navigationActivity.switchFragment(fragment, FragmentTransitionType_Slide);
    }

/*    public boolean goBack(Fragment fragment) {
        MainMenuActivity navigationActivity = getNavigationActivity();
        return navigationActivity.goBack(fragment);
    } */

    private FragmentActivity getFragmentActivity() {
        FragmentActivity activity = fragment.getActivity();
        return activity;
    }

    private MainMenuActivity getNavigationActivity() {
        FragmentActivity activity = getFragmentActivity();
        if(!(activity instanceof MainMenuActivity))
            throw new RuntimeException("Activity must extents NavigationActivity class.");

        return (MainMenuActivity) activity;
    }

}
