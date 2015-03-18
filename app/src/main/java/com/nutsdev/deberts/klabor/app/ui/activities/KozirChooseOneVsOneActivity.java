package com.nutsdev.deberts.klabor.app.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nutsdev.deberts.klabor.R;
import com.nutsdev.deberts.klabor.app.entities.Card;
import com.nutsdev.deberts.klabor.app.settings.GameSettings_;
import com.nutsdev.deberts.klabor.app.settings.PlayerSettings_;
import com.nutsdev.deberts.klabor.app.utils.CardDetector;
import com.nutsdev.deberts.klabor.app.utils.CardsComparator;

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
@EActivity(R.layout.activity_kozir_choose_one_vs_one)
public class KozirChooseOneVsOneActivity extends ActionBarActivity {

    public static final String ANDROID_CARDS_LIST_PREF = "androidCardsList";
    public static final String PLAYER_CARDS_LIST_PREF = "playerCardsList";

    // todo remove on release
    public static final boolean isDebug = true;

    @Pref
    PlayerSettings_ playerSettings;

    @Pref
    GameSettings_ gameSettings;

    @Extra
    boolean continueGame;
    // номер раздачи начиная с 0 // todo переместитть в преференсы
    @InstanceState
    public static int razdacha = 0;
    // какой круг слов 1 или 2
    @InstanceState
    int lapTurn = 1;
    // выбранный козырь
    @InstanceState
    int chosenKozir;
    // козырь на первом кругу
    @InstanceState
    int firstLapKozir;

    @InstanceState
    String playerName;
    // all 32 cards (shuffled)
    @InstanceState
    ArrayList<Card> cardsList = new ArrayList<>();
    // player's cards
    @InstanceState
    ArrayList<Card> playerCardsList = new ArrayList<>();
    // android's cards
    @InstanceState
    ArrayList<Card> androidCardsList = new ArrayList<>();
    // else cards
    @InstanceState
    ArrayList<Card> remainingCardsList = new ArrayList<>();
    // opened kozir card on first lap
    @InstanceState
    Card firstLapKozirCard;


    @ViewById
    LinearLayout suits_view;

    @ViewById
    Button pass_button;

    @ViewById
    ImageView bubnaSuit_imageView;

    @ViewById
    ImageView krestaSuit_imageView;

    @ViewById
    ImageView chirvaSuit_imageView;

    @ViewById
    ImageView pikaSuit_imageView;

    @ViewById
    TextView objaz_textView;

    @ViewById
    ImageView kolodaKozir_imageView;

    @ViewsById({R.id.enemyStartCard1_imageView, R.id.enemyStartCard2_imageView, R.id.enemyStartCard3_imageView,
            R.id.enemyStartCard4_imageView, R.id.enemyStartCard5_imageView, R.id.enemyStartCard6_imageView})
    List<ImageView> androidCards_ImageViewArray;

    @ViewsById({R.id.startCard1_imageView, R.id.startCard2_imageView, R.id.startCard3_imageView,
            R.id.startCard4_imageView, R.id.startCard5_imageView, R.id.startCard6_imageView})
    List<ImageView> playerCards_ImageViewArray;


    /* lifecycle */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null && !continueGame) {
            firstLaunchInit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameSettings.isGameSaved().put(1);
        saveCardsToPrefs();
    }

    @AfterViews
    void initViews() {
        if (continueGame)
            restoreCardsFromPrefs();

        displayViewsSetup();
    }

    @Override
    public void onBackPressed() {
        MainMenuActivity_.intent(this).start();
        super.onBackPressed();
    }

    /* clicks */

    @Click(R.id.play_button)
    void playButton_click() {
        if (lapTurn == 1) {
            chosenKozir = firstLapKozirCard.getSuit();
            // todo добавить возможность менять семерку на текущий козырь на первом кругу
        } else if (lapTurn == 2) {
            if (chosenKozir == 0) {
                Toast.makeText(this, "Выберите козырь!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Click(R.id.pass_button)
    void passButton_click() {
        if (lapTurn == 1) { // todo добавить проверку не играет ли бот на этом кругу
            setSuitViewVisible();
            lapTurn++;
        } else if (lapTurn == 2) {
            // todo Android должен выбрать козырь в который он играет кроме firstLapKozir
        }
    }

    @Click(R.id.bubnaSuit_imageView)
    void bubnaButton_click() {
        chosenKozir = Card.BUBNA_SUIT;

        scaleBubnaImageView();
    }

    @Click(R.id.krestaSuit_imageView)
    void krestaButton_click() {
        chosenKozir = Card.KRESTA_SUIT;

        scaleKrestaImageView();
    }

    @Click(R.id.chirvaSuit_imageView)
    void chirvaButton_click() {
        chosenKozir = Card.CHIRVA_SUIT;

        scaleChirvaImageView();
    }

    @Click(R.id.pikaSuit_imageView)
    void pikaButton_click() {
        chosenKozir = Card.PIKA_SUIT;

        scalePikaImageView();
    }

    /* private methods */

    private void saveCardsToPrefs() {
        boolean savedAndroid = saveCardsToPreferences(androidCardsList, ANDROID_CARDS_LIST_PREF);
        boolean savedPlayer = saveCardsToPreferences(playerCardsList, PLAYER_CARDS_LIST_PREF);
        gameSettings.firstLapKozirCard().put(firstLapKozirCard.getValue());
    }

    private boolean saveCardsToPreferences(ArrayList<Card> cardsList, String listName) {
        SharedPreferences prefs = getSharedPreferences(listName, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putInt(listName + "_size", cardsList.size());
        for (int i = 0; i < cardsList.size(); i++)
            editor.putInt(listName + "_" + i, cardsList.get(i).getValue());
        return editor.commit();
    }

    private void restoreCardsFromPrefs() {
        androidCardsList = restoreCardsFromPreferences(ANDROID_CARDS_LIST_PREF);
        playerCardsList = restoreCardsFromPreferences(PLAYER_CARDS_LIST_PREF);
        firstLapKozirCard = new Card(gameSettings.firstLapKozirCard().get());
    }

    private ArrayList<Card> restoreCardsFromPreferences(String listName) {
        SharedPreferences prefs = getSharedPreferences(listName, MODE_PRIVATE);
        int size = prefs.getInt(listName + "_size", -1);
        ArrayList<Card> cardsList = new ArrayList<>();
        for (int i = 0; i < size; i++)
            cardsList.add(new Card(prefs.getInt(listName + "_" + i, -1)));
        return cardsList;
    }

    private void firstLaunchInit() {
        for (int i = 0; i < 32; i++) {
            cardsList.add(new Card(i));
        }
        Collections.shuffle(cardsList);

        for (int i = 0; i < 32; i++) {
            if (i < 6)
                androidCardsList.add(cardsList.get(i));
            else if (i > 5 && i < 12)
                playerCardsList.add(cardsList.get(i));
            else if (i == 12)
                firstLapKozirCard = cardsList.get(i);
            else
                remainingCardsList.add(cardsList.get(i));
        }
        // sorts cards by suits
        Collections.sort(androidCardsList, new CardsComparator());
        Collections.sort(playerCardsList, new CardsComparator());
        Toast.makeText(this, "size " + remainingCardsList.size(), Toast.LENGTH_SHORT).show();
    }

    private void displayViewsSetup() {
        // displaying player's cards and kozir
        for (int i = 0; i < 6; i++) {
            playerCards_ImageViewArray.get(i).setImageResource(CardDetector.getCardDrawable(playerCardsList.get(i)));

            if (isDebug)
                androidCards_ImageViewArray.get(i).setImageResource(CardDetector.getCardDrawable(androidCardsList.get(i)));
        }
        kolodaKozir_imageView.setImageResource(CardDetector.getCardDrawable(firstLapKozirCard));
        // display who must play
        playerName = playerSettings.playerName().get();
        if (razdacha % 2 == 0)
            objaz_textView.setText(getString(R.string.objaz_title, playerName));
        else
            objaz_textView.setText(getString(R.string.objaz_title, getString(R.string.android_player_name_title)));

        if (lapTurn == 2) {
            setSuitViewVisible();

            switch (chosenKozir) {
                case Card.PIKA_SUIT:
                    scalePikaImageView();
                    break;
                case Card.BUBNA_SUIT:
                    scaleBubnaImageView();
                    break;
                case Card.KRESTA_SUIT:
                    scaleKrestaImageView();
                    break;
                case Card.CHIRVA_SUIT:
                    scaleChirvaImageView();
                    break;
            }
        }
    }

    private void setSuitViewVisible() {
        suits_view.setVisibility(View.VISIBLE);
        switch (firstLapKozirCard.getSuit()) {
            case Card.PIKA_SUIT:
                pikaSuit_imageView.setVisibility(View.GONE);
                break;
            case Card.BUBNA_SUIT:
                bubnaSuit_imageView.setVisibility(View.GONE);
                break;
            case Card.CHIRVA_SUIT:
                chirvaSuit_imageView.setVisibility(View.GONE);
                break;
            case Card.KRESTA_SUIT:
                krestaSuit_imageView.setVisibility(View.GONE);
                break;
        }

        if (razdacha % 2 == 0)
            pass_button.setVisibility(View.INVISIBLE);
    }

    private void scaleBubnaImageView() {
        bubnaSuit_imageView.setScaleX(1.35F);
        bubnaSuit_imageView.setScaleY(1.35F);

        krestaSuit_imageView.setScaleX(1);
        krestaSuit_imageView.setScaleY(1);

        chirvaSuit_imageView.setScaleX(1);
        chirvaSuit_imageView.setScaleY(1);

        pikaSuit_imageView.setScaleX(1);
        pikaSuit_imageView.setScaleY(1);
    }

    private void scaleKrestaImageView() {
        bubnaSuit_imageView.setScaleX(1);
        bubnaSuit_imageView.setScaleY(1);

        krestaSuit_imageView.setScaleX(1.5F);
        krestaSuit_imageView.setScaleY(1.5F);

        chirvaSuit_imageView.setScaleX(1);
        chirvaSuit_imageView.setScaleY(1);

        pikaSuit_imageView.setScaleX(1);
        pikaSuit_imageView.setScaleY(1);
    }

    private void scaleChirvaImageView() {
        bubnaSuit_imageView.setScaleX(1);
        bubnaSuit_imageView.setScaleY(1);

        krestaSuit_imageView.setScaleX(1);
        krestaSuit_imageView.setScaleY(1);

        chirvaSuit_imageView.setScaleX(1.4F);
        chirvaSuit_imageView.setScaleY(1.4F);

        pikaSuit_imageView.setScaleX(1);
        pikaSuit_imageView.setScaleY(1);
    }

    private void scalePikaImageView() {
        bubnaSuit_imageView.setScaleX(1);
        bubnaSuit_imageView.setScaleY(1);

        krestaSuit_imageView.setScaleX(1);
        krestaSuit_imageView.setScaleY(1);

        chirvaSuit_imageView.setScaleX(1);
        chirvaSuit_imageView.setScaleY(1);

        pikaSuit_imageView.setScaleX(1.35F);
        pikaSuit_imageView.setScaleY(1.35F);
    }

}
