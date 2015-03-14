package com.nutsdev.deberts.klabor.app.Settings;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by n1ck on 14.03.2015.
 */
@SharedPref(SharedPref.Scope.APPLICATION_DEFAULT)
public interface PlayerSettings {

    String playerName();

}
