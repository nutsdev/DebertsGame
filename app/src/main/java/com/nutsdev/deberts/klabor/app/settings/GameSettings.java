package com.nutsdev.deberts.klabor.app.settings;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by n1ck on 18.03.2015.
 */
@SharedPref(SharedPref.Scope.APPLICATION_DEFAULT)
public interface GameSettings {

    boolean isSavedGameExists();

}
