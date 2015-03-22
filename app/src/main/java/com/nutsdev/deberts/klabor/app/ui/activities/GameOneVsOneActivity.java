package com.nutsdev.deberts.klabor.app.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutsdev.deberts.klabor.R;
import com.nutsdev.deberts.klabor.app.entities.Card;
import com.nutsdev.deberts.klabor.app.settings.GameSettings_;
import com.nutsdev.deberts.klabor.app.utils.CardDetector;
import com.nutsdev.deberts.klabor.app.utils.CardsComparator;
import com.nutsdev.deberts.klabor.app.utils.GameHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.activity_game_one_vs_one)
public class GameOneVsOneActivity extends ActionBarActivity {

    public static final boolean isDebug = true; // todo remove on release

    @Pref
    GameSettings_ gameSettings;

    @Extra
    boolean continueGame;
    @Extra
    int whosPlaying; // 0 - android, 1 - player
    @InstanceState
    int currentLap = 0;
    @Extra
    int razdacha;
    @Extra
    int chosenKozir;
    @Extra
    Card firstLapKozirCard;
    @Extra
    Card kolodaLastCard;
    @Extra
    ArrayList<Card> playerCardsList;
    @Extra
    ArrayList<Card> androidCardsList;

    @InstanceState
    int selectedCardInt = -1;
    @InstanceState
    Card selectedCard;
    @InstanceState
    String playerName;

    @ViewById
    TextView whosPlaying_textView;
    @ViewById
    Button action_button;

    @ViewById
    ImageView chosenKozir_imageView;
    @ViewById
    ImageView playerTurnCard_imageView;
    @ViewById
    ImageView androidTurnCard_imageView;
    @ViewById
    ImageView kolodaLastCard_imageView;
    @ViewById
    ImageView firstLapKozirCard_imageView;

    @ViewsById({R.id.enemyCard1_imageView, R.id.enemyCard2_imageView, R.id.enemyCard3_imageView, R.id.enemyCard4_imageView,
            R.id.enemyCard5_imageView, R.id.enemyCard6_imageView, R.id.enemyCard7_imageView, R.id.enemyCard8_imageView,
            R.id.enemyCard9_imageView })
    List<ImageView> androidCards_ImageViewArray;

    @ViewsById({R.id.playerCard1_imageView, R.id.playerCard2_imageView, R.id.playerCard3_imageView, R.id.playerCard4_imageView,
            R.id.playerCard5_imageView, R.id.playerCard6_imageView, R.id.playerCard7_imageView, R.id.playerCard8_imageView,
            R.id.playerCard9_imageView })
    List<ImageView> playerCards_ImageViewArray;

    /* lifecycle */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (playerName == null)
            playerName = gameSettings.playerName().get();
    }

    @AfterViews
    void initViews() {
        if (continueGame)
            restoreGameState();

        displayViewsSetup();
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveGameState();
    }

    @Override
    public void onBackPressed() {
        MainMenuActivity_.intent(this).start();
        super.onBackPressed();
    }

    /* clicks */

    @Click(R.id.action_button)
    void actionButton_click() {
        // todo обьеденить функционал походить и отбой
        if (selectedCard != null) {
            currentLap++;
            playerCardsList.remove(selectedCard);
            playerTurnCard_imageView.setImageResource(CardDetector.getCardDrawable(selectedCard));

            selectedCard = null;
            selectedCardInt = -1;
        } else {
            Toast.makeText(this, "Выберите карту, которой хотите походить!", Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.playerCard1_imageView)
    void playerCard1_click() {
        selectCard(0);
    }

    @Click(R.id.playerCard2_imageView)
    void playerCard2_click() {
        selectCard(1);
    }

    @Click(R.id.playerCard3_imageView)
    void playerCard3_click() {
        selectCard(2);
    }

    @Click(R.id.playerCard4_imageView)
    void playerCard4_click() {
        selectCard(3);
    }

    @Click(R.id.playerCard5_imageView)
    void playerCard5_click() {
        selectCard(4);
    }

    @Click(R.id.playerCard6_imageView)
    void playerCard6_click() {
        selectCard(5);
    }

    @Click(R.id.playerCard7_imageView)
    void playerCard7_click() {
        selectCard(6);
    }

    @Click(R.id.playerCard8_imageView)
    void playerCard8_click() {
        selectCard(7);
    }

    @Click(R.id.playerCard9_imageView)
    void playerCard9_click() {
        selectCard(8);
    }

    /* private methods */

    private void saveGameState() {
        GameHelper.saveCardsToPreferences(this, androidCardsList, GameHelper.ANDROID_CARDS_LIST_PREF);
        GameHelper.saveCardsToPreferences(this, playerCardsList, GameHelper.PLAYER_CARDS_LIST_PREF);

        gameSettings.firstLapKozirCard().put(firstLapKozirCard.getValue());
        gameSettings.kolodaLastCard().put(kolodaLastCard.getValue());
        gameSettings.razdacha().put(razdacha);
        gameSettings.currentLap().put(currentLap);
        gameSettings.whosPlaying().put(whosPlaying);
        gameSettings.chosenKozir().put(chosenKozir);
        gameSettings.isGameSaved().put(2);
    }

    private void restoreGameState() {
        androidCardsList = GameHelper.restoreCardsFromPreferences(this, GameHelper.ANDROID_CARDS_LIST_PREF);
        playerCardsList = GameHelper.restoreCardsFromPreferences(this, GameHelper.PLAYER_CARDS_LIST_PREF);

        firstLapKozirCard = new Card(gameSettings.firstLapKozirCard().get());
        kolodaLastCard = new Card(gameSettings.kolodaLastCard().get());
        razdacha = gameSettings.razdacha().get();
        currentLap = gameSettings.currentLap().get();
        whosPlaying = gameSettings.whosPlaying().get();
        chosenKozir = gameSettings.chosenKozir().get();
    }

    private void displayViewsSetup() {
        // sorts cards by suits
        Collections.sort(androidCardsList, new CardsComparator());
        Collections.sort(playerCardsList, new CardsComparator());

        int cardArrayLength = detectCardArrayLength();
        // displaying player's cards and kozir
        for (int i = 0; i < cardArrayLength; i++) {
            playerCards_ImageViewArray.get(i).setVisibility(View.VISIBLE);
            playerCards_ImageViewArray.get(i).setImageResource(CardDetector.getCardDrawable(playerCardsList.get(i)));

            androidCards_ImageViewArray.get(i).setVisibility(View.VISIBLE);
            if (isDebug)
                androidCards_ImageViewArray.get(i).setImageResource(CardDetector.getCardDrawable(androidCardsList.get(i)));
        }
        firstLapKozirCard_imageView.setImageResource(CardDetector.getCardDrawable(firstLapKozirCard));
        kolodaLastCard_imageView.setImageResource(CardDetector.getCardDrawable(kolodaLastCard));

        switch (chosenKozir) {
            case Card.PIKA_SUIT:
                chosenKozir_imageView.setImageResource(R.drawable.pika_suit);
                break;
            case Card.BUBNA_SUIT:
                chosenKozir_imageView.setImageResource(R.drawable.bubna_suit);;
                break;
            case Card.KRESTA_SUIT:
                chosenKozir_imageView.setImageResource(R.drawable.kresta_suit);;
                break;
            case Card.CHIRVA_SUIT:
                chosenKozir_imageView.setImageResource(R.drawable.chirva_suit);;
                break;
        }

        if (selectedCardInt != -1)
            selectCard(selectedCardInt);

        if (whosPlaying == 0)
            whosPlaying_textView.setText(getString(R.string.playing_is_title, getString(R.string.android_player_name_title)));
        else
            whosPlaying_textView.setText(getString(R.string.playing_is_title, playerName));
    }

    private int detectCardArrayLength() { // todo продумать шаг currentLap 1 или 2
        if (currentLap == 0)
            return 9;
        else if (currentLap == 1)
            return 8;
        else if (currentLap == 2)
            return 7;
        else if (currentLap == 3)
            return 6;
        else if (currentLap == 4)
            return 5;
        else if (currentLap == 5)
            return 4;
        else if (currentLap == 6)
            return 3;
        else if (currentLap == 7)
            return 2;
        else if (currentLap == 8)
            return 1;

        return 9;
    }

    private void selectCard(int position) {
        selectedCardInt = position;
        for (int i = 0; i < 9; i++) {
            if (i == position) {
                playerCards_ImageViewArray.get(position).setScaleX(1.5f);
                playerCards_ImageViewArray.get(position).setScaleY(1.5f);
                selectedCard = playerCardsList.get(position);
            } else {
                playerCards_ImageViewArray.get(i).setScaleX(1);
                playerCards_ImageViewArray.get(i).setScaleY(1);
            }
        }
    }

}
