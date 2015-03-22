package com.nutsdev.deberts.klabor.app.ui.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nutsdev.deberts.klabor.R;
import com.nutsdev.deberts.klabor.app.settings.GameSettings_;
import com.nutsdev.deberts.klabor.app.ui.activities.KozirChooseOneVsOneActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by n1ck on 09.03.2015.
 */
@EFragment(R.layout.fragment_gametype)
public class GametypeMenuFragment extends Fragment {

    @Pref
    GameSettings_ gameSettings;

    @ViewById
    Button startGame_button;

    @ViewById
    EditText enterName_editText;


    /* lifecycle */

    @AfterViews
    void initViews() {
        String playerName = gameSettings.playerName().get();
        if (!playerName.trim().isEmpty()) {
            enterName_editText.setText(playerName);
        }
    }

    /* clicks */

    @Click(R.id.startGame_button)
    void startGameButton_click() {
        String playerName = enterName_editText.getText().toString();
        if (playerName.trim().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.empty_name_error_title), Toast.LENGTH_SHORT).show();
            enterName_editText.setText("");
            return;
        } else {
            gameSettings.playerName().put(playerName);
            KozirChooseOneVsOneActivity_.intent(this).continueGame(false).start();
            getActivity().finish();
        }
    }

}
