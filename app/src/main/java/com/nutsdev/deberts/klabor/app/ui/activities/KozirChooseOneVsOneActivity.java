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
import com.nutsdev.deberts.klabor.app.utils.CardsList;
import com.nutsdev.deberts.klabor.app.utils.GameHelper;
import com.nutsdev.deberts.klabor.app.utils.ToastHelper;

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

    public static final boolean isDebug = true; // todo remove on release

    @Pref
    GameSettings_ gameSettings;

    @Extra
    boolean continueGame;
    // номер раздачи начиная с 0 - Обяз игрока, 1 - обяз андроида и тд
    @InstanceState
    int razdacha = 0;
    // какой круг слов 1 или 2
    @InstanceState
    int currentLap = 0;
    // выбранный козырь
    @InstanceState
    int chosenKozir = -1;

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
    @InstanceState
    Card kolodaLastCard;
    @InstanceState
    Card sevenCard;


    @ViewById
    View suits_view;
    @ViewById
    View changeSeven_view;
    @ViewById
    View playPassButtons_view;

    @ViewById
    Button pass_button;
    @ViewById
    Button yes_button;
    @ViewById
    Button no_button;

    @ViewById
    TextView objaz_textView;
    @ViewById
    TextView changeSeven_textView;

    @ViewById
    ImageView kolodaKozir_imageView;

    @ViewsById({R.id.pikaSuit_imageView, R.id.bubnaSuit_imageView, R.id.krestaSuit_imageView, R.id.chirvaSuit_imageView })
    List<ImageView> suits_ImageViewArray; // 0 - pika, 1 - bubna, 2 - kresta, 3 - chirva,

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

    @Click(R.id.yes_button)
    void yes_button_click() {
        playerCardsList.remove(sevenCard);
        playerCardsList.add(firstLapKozirCard);
        firstLapKozirCard = sevenCard;
        startGameOneVsOneActivity(GameHelper.PLAYER_IS_PLAYING);
    }

    @Click(R.id.no_button)
    void no_button_click() {
        startGameOneVsOneActivity(GameHelper.PLAYER_IS_PLAYING);
    }

    @Click(R.id.play_button)
    void playButton_click() {
        if (currentLap == 0) {
            chosenKozir = firstLapKozirCard.getSuit();
            if (changeSevenCard()) {
                return;
            } else {
                startGameOneVsOneActivity(GameHelper.PLAYER_IS_PLAYING);
            }
        } else if (currentLap == 1) {
            if (chosenKozir == -1) {
                ToastHelper.showCenteredToast(this, getString(R.string.choose_kozir_toast));
            } else {
                startGameOneVsOneActivity(GameHelper.PLAYER_IS_PLAYING);
            }
        }
    }

    @Click(R.id.pass_button)
    void passButton_click() {
        if (currentLap == 0) { // todo добавить проверку не играет ли бот на этом кругу
            if (razdacha % 2 != 0 && !willAndroidPlayOnFirstLap() || razdacha % 2 == 0 && !willAndroidPlayOnSecondLap()) {
                setSuitViewVisible();
                currentLap++;
            }
        } else if (currentLap == 1) {
            // todo Android должен выбрать козырь в который он играет кроме firstLapKozir
            Toast.makeText(this, "Android choosing козырь!", Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.bubnaSuit_imageView)
    void bubnaButton_click() {
        selectSuit(Card.BUBNA_SUIT);
    }

    @Click(R.id.krestaSuit_imageView)
    void krestaButton_click() {
        selectSuit(Card.KRESTA_SUIT);
    }

    @Click(R.id.chirvaSuit_imageView)
    void chirvaButton_click() {
        selectSuit(Card.CHIRVA_SUIT);
    }

    @Click(R.id.pikaSuit_imageView)
    void pikaButton_click() {
        selectSuit(Card.PIKA_SUIT);
    }

    /* private methods */

    private boolean changeSevenCard() {
        int cardSuit = -1;

        for (Card card : playerCardsList) {
            cardSuit = card.getSuit();
            if (chosenKozir == cardSuit && card.getValue() == CardsList.PIKA_SEVEN) {
                sevenCard = card;
                Toast.makeText(this, "GOT PIKA SEVEN", Toast.LENGTH_SHORT).show();
                break;
            }
            else if (chosenKozir == cardSuit && card.getValue() == CardsList.BUBNA_SEVEN) {
                sevenCard = card;
                Toast.makeText(this, "GOT BUBNA SEVEN", Toast.LENGTH_SHORT).show();
                break;
            }
            else if (chosenKozir == cardSuit && card.getValue() == CardsList.KRESTA_SEVEN) {
                sevenCard = card;
                Toast.makeText(this, "GOT KRESTA SEVEN", Toast.LENGTH_SHORT).show();
                break;
            }
            else if (chosenKozir == cardSuit && card.getValue() == CardsList.CHIRVA_SEVEN) {
                sevenCard = card;
                Toast.makeText(this, "GOT CHIRVA SEVEN", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        if (sevenCard != null) {
            changeSeven_textView.setVisibility(View.VISIBLE);
            changeSeven_view.setVisibility(View.VISIBLE);
            playPassButtons_view.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    private void saveGameState() {
        GameHelper.saveCardsToPreferences(this, androidCardsList, GameHelper.ANDROID_CARDS_LIST_PREF);
        GameHelper.saveCardsToPreferences(this, playerCardsList, GameHelper.PLAYER_CARDS_LIST_PREF);
        GameHelper.saveCardsToPreferences(this, remainingCardsList, GameHelper.REMAINING_CARDS_LIST_PREF);

        gameSettings.firstLapKozirCard().put(firstLapKozirCard.getValue());
        gameSettings.razdacha().put(razdacha);
        gameSettings.currentLap().put(currentLap);
        gameSettings.isGameSaved().put(1);
    }

    private void restoreGameState() {
        androidCardsList = GameHelper.restoreCardsFromPreferences(this, GameHelper.ANDROID_CARDS_LIST_PREF);
        playerCardsList = GameHelper.restoreCardsFromPreferences(this, GameHelper.PLAYER_CARDS_LIST_PREF);
        remainingCardsList = GameHelper.restoreCardsFromPreferences(this, GameHelper.REMAINING_CARDS_LIST_PREF);

        firstLapKozirCard = new Card(gameSettings.firstLapKozirCard().get());
        razdacha = gameSettings.razdacha().get();
        currentLap = gameSettings.currentLap().get();
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

        if (razdacha % 2 == 0) { // если обяз у Игрока, а первое слово у Андроида
            willAndroidPlayOnFirstLap();
        }
    }

    private boolean willAndroidPlayOnFirstLap() {
        // todo прописать логику выбора козыря для бота
        // todo добавить возможность менять семерку у бота на текущий козырь на первом кругу
        int firstLapKozirSuit = firstLapKozirCard.getSuit();
        int kozirSuitCardsCount = 0;
        for (Card androidCard : androidCardsList) {
            if (androidCard.getSuit() == firstLapKozirSuit) {
                kozirSuitCardsCount++;
            }
        }
        // если больше 3 карт или если обяз у андроида и больше 2х козырных карт
        if (kozirSuitCardsCount > 3 || kozirSuitCardsCount > 2 && razdacha % 2 != 0) {
            chosenKozir = firstLapKozirSuit;
            // todo добавить какой-то эффект, чтобы было видно, что андроид играет на первом кругу
            startGameOneVsOneActivity(GameHelper.ANDROID_IS_PLAYING);
            return true;
        }
        return false;
    }

    private boolean willAndroidPlayOnSecondLap() {
        // todo прописать логику выбора козыря на втором кругу
        if (false) {
            int chosenKozirSuit = firstLapKozirCard.getSuit();
            if (chosenKozirSuit == Card.CHIRVA_SUIT)
                chosenKozirSuit = Card.PIKA_SUIT;
            else
                chosenKozirSuit++;

            chosenKozir = chosenKozirSuit;
            // todo добавить какой-то эффект, чтобы было видно, что андроид играет на втором кругу
            startGameOneVsOneActivity(GameHelper.ANDROID_IS_PLAYING);
            return true;
        }
        return false;
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
        playerName = gameSettings.playerName().get();
        if (razdacha % 2 == 0)
            objaz_textView.setText(getString(R.string.objaz_title, playerName));
        else
            objaz_textView.setText(getString(R.string.objaz_title, getString(R.string.android_player_name_title)));

        if (currentLap == 1) {
            setSuitViewVisible();

            switch (chosenKozir) {
                case Card.PIKA_SUIT:
                    selectSuit(Card.PIKA_SUIT);
                    break;
                case Card.BUBNA_SUIT:
                    selectSuit(Card.BUBNA_SUIT);
                    break;
                case Card.KRESTA_SUIT:
                    selectSuit(Card.KRESTA_SUIT);
                    break;
                case Card.CHIRVA_SUIT:
                    selectSuit(Card.CHIRVA_SUIT);
                    break;
            }
        }
    }

    private void startGameOneVsOneActivity(int whosPlaying) {
        for (int i = 0; i < 7; i++) {
            if (i < 3)
                androidCardsList.add(remainingCardsList.get(i));
            else if (i > 2 && i < 6)
                playerCardsList.add(remainingCardsList.get(i));
            else
                kolodaLastCard = remainingCardsList.get(i);
        }
        GameOneVsOneActivity_.intent(this).androidCardsList(androidCardsList).playerCardsList(playerCardsList)
                .firstLapKozirCard(firstLapKozirCard).chosenKozir(chosenKozir).razdacha(razdacha)
                .kolodaLastCard(kolodaLastCard).whosPlaying(whosPlaying).start();
        finish();
    }

    private void setSuitViewVisible() {
        suits_view.setVisibility(View.VISIBLE);
        switch (firstLapKozirCard.getSuit()) {
            case Card.PIKA_SUIT:
                suits_ImageViewArray.get(Card.PIKA_SUIT).setVisibility(View.GONE);
                break;
            case Card.BUBNA_SUIT:
                suits_ImageViewArray.get(Card.BUBNA_SUIT).setVisibility(View.GONE);
                break;
            case Card.CHIRVA_SUIT:
                suits_ImageViewArray.get(Card.CHIRVA_SUIT).setVisibility(View.GONE);
                break;
            case Card.KRESTA_SUIT:
                suits_ImageViewArray.get(Card.KRESTA_SUIT).setVisibility(View.GONE);
                break;
        }

        if (razdacha % 2 == 0)
            pass_button.setVisibility(View.INVISIBLE);
    }

    private void selectSuit(int position) {
        for (int i = 0; i < 4; i++) {
            if (i == position) {
                suits_ImageViewArray.get(position).setScaleX(1.5f);
                suits_ImageViewArray.get(position).setScaleY(1.5f);
            } else {
                suits_ImageViewArray.get(i).setScaleX(1);
                suits_ImageViewArray.get(i).setScaleY(1);
            }
            chosenKozir = position;
        }
    }

}
