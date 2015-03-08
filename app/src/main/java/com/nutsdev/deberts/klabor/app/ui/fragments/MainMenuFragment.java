package com.nutsdev.deberts.klabor.app.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.nutsdev.deberts.klabor.R;
import com.nutsdev.deberts.klabor.app.ui.activities.MainMenuActivity;
import com.nutsdev.deberts.klabor.app.utils.NavigationHelper;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by n1ck on 09.03.2015.
 */
@EFragment(R.layout.fragment_main_menu)
public class MainMenuFragment extends Fragment {

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

    private NavigationHelper navigationHelper;


    /* lifecycle */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationHelper = NavigationHelper.create(this);
    }

    /* clicks */

    @Click(R.id.newGame_button)
    void newGameButton_click() {
        OnlineOfflineMenuFragment onlineOfflineMenuFragment = OnlineOfflineMenuFragment_.builder().build();
        navigationHelper.switchFragment(onlineOfflineMenuFragment, MainMenuActivity.FragmentTransitionType_Slide);
    }

    @Click(R.id.exit_button)
    void exitButton_click() {
        getActivity().finish();
    }

}
