package com.nutsdev.deberts.klabor.app.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.nutsdev.deberts.klabor.R;
import com.nutsdev.deberts.klabor.app.Settings.PlayerSettings_;
import com.nutsdev.deberts.klabor.app.utils.NavigationHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by n1ck on 09.03.2015.
 */
@EFragment(R.layout.fragment_main_menu)
public class MainMenuFragment extends Fragment {

    @Pref
    PlayerSettings_ playerSettings;

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

    @AfterViews
    void initViews() {
        if (playerSettings.isSavedGameExists().getOr(false))
            continueGame_button.setVisibility(View.VISIBLE);
    }

    /* clicks */

    @Click(R.id.newGame_button)
    void newGameButton_click() {
        GametypeMenuFragment gametypeMenuFragment = GametypeMenuFragment_.builder().build();
        navigationHelper.switchFragment(gametypeMenuFragment, NavigationHelper.FragmentTransitionType_Slide);
    }

    @Click(R.id.exit_button)
    void exitButton_click() {
        getActivity().finish();
    }

}
