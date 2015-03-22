package com.nutsdev.deberts.klabor.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nutsdev.deberts.klabor.app.entities.Card;

import java.util.ArrayList;

/**
 * Created by n1ck on 22.03.2015.
 */
public class GameHelper {

    public static final int KOZIR_CHOOSE_ONE_VS_ONE_STATE = 1;
    public static final int GAME_ONE_VS_ONE_STATE = 2;

    public static final String ANDROID_CARDS_LIST_PREF = "androidCardsList";
    public static final String PLAYER_CARDS_LIST_PREF = "playerCardsList";
    public static final String REMAINING_CARDS_LIST_PREF = "remainingCardsList";

    public static boolean saveCardsToPreferences(Context context, ArrayList<Card> cardsList, String listName) {
        SharedPreferences prefs = context.getSharedPreferences(listName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putInt(listName + "_size", cardsList.size());
        for (int i = 0; i < cardsList.size(); i++)
            editor.putInt(listName + "_" + i, cardsList.get(i).getValue());
        return editor.commit();
    }

    public static ArrayList<Card> restoreCardsFromPreferences(Context context, String listName) {
        SharedPreferences prefs = context.getSharedPreferences(listName, Context.MODE_PRIVATE);
        int size = prefs.getInt(listName + "_size", -1);
        ArrayList<Card> cardsList = new ArrayList<>();
        for (int i = 0; i < size; i++)
            cardsList.add(new Card(prefs.getInt(listName + "_" + i, -1)));
        return cardsList;
    }

}
