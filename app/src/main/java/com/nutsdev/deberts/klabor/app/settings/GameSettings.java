package com.nutsdev.deberts.klabor.app.settings;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by n1ck on 18.03.2015.
 */
@SharedPref(SharedPref.Scope.APPLICATION_DEFAULT)
public interface GameSettings {

    String playerName();
    // 0 - nothing saved, 1 - KozirChooseOneVsOneActivity, 2 - GameOneVsOneActivity
    int isGameSaved();
    // current razdacha begin from 0
    int razdacha();
    // current lap of this razdacha begin from 1
    int currentLap();
    // firstLapKozirCard or seven of the same suit
    int firstLapKozirCard();
    // перевернутая последняя карта
    int kolodaLastCard();
    // who is playing 0 - android, 1 - player
    int whosPlaying();
    // chosen kozir
    int chosenKozir();
    // which lap of words in ChooseKozirActivity
    //int lapTurn();

}
