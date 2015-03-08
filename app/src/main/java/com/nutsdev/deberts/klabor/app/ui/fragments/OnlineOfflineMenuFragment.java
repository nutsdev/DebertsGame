package com.nutsdev.deberts.klabor.app.ui.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

import com.nutsdev.deberts.klabor.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by n1ck on 08.03.2015.
 */
@EFragment(R.layout.fragment_online_offline)
public class OnlineOfflineMenuFragment extends Fragment {

    @ViewById
    Button playOnline_button;

    @ViewById
    Button playOffline_button;


    /* lifecycle */


    /* clicks */

    @Click(R.id.playOnline_button)
    void onlineButton_click() {
        Toast.makeText(getActivity(), "online!", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.playOffline_button)
    void offlineButton_click() {
        Toast.makeText(getActivity(), "offline!", Toast.LENGTH_SHORT).show();
    }

}