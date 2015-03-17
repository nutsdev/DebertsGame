package com.nutsdev.deberts.klabor.app.ui.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nutsdev.deberts.klabor.R;
import com.nutsdev.deberts.klabor.app.entities.Card;
import com.nutsdev.deberts.klabor.app.settings.PlayerSettings_;
import com.nutsdev.deberts.klabor.app.utils.CardDetector;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EActivity(R.layout.activity_kozir_choose_one_vs_one)
public class KozirChooseOneVsOneActivity extends ActionBarActivity {

    public static final int PIKA_SUIT = 1001;
    public static final int BUBNA_SUIT = 1002;
    public static final int KRESTA_SUIT = 1003;
    public static final int CHIRVA_SUIT = 1004;
    // todo remove on release
    public static final boolean isDebug = true;

    @Pref
    PlayerSettings_ playerSettings;
    // номер раздачи начиная с 0
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
    ArrayList<Card> playerCardList = new ArrayList<>();
    // android's cards
    @InstanceState
    ArrayList<Card> androidCardList = new ArrayList<>();
    // else cards
    @InstanceState
    ArrayList<Card> remainingCardList = new ArrayList<>();
    @InstanceState
    // opened kozir card on first lap
    Card firstLapKozirCard;

    TypedArray cardDrawablesArray;
    // стартовая карта на колоде
    @InstanceState
    Integer kolodaKozirCard;
    // список всех карт по порядку в виде ссылок на ресурсы картинок
    @InstanceState
    ArrayList<Integer> cardsArrayList = new ArrayList<>();
    // список всех карт после shuffle в виде 0, 1, 2, ... 31
    @InstanceState
    ArrayList<Integer> shuffledCardsList = new ArrayList<>();
    // список карт игрока
    @InstanceState
    ArrayList<Integer> playerCardsList = new ArrayList<>();
    // список карт противника
    @InstanceState
    ArrayList<Integer> androidCardsList = new ArrayList<>();

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
        // remove title
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            for (int i = 0; i < 32; i++) {
                cardsList.add(new Card(i));
            }
            Collections.shuffle(cardsList);

            for (int i = 0; i < 32; i++) {
                if (i < 6)
                    androidCardList.add(cardsList.get(i));
                else if (i > 5 && i < 12)
                    playerCardList.add(cardsList.get(i));
                else if (i == 12)
                    firstLapKozirCard = cardsList.get(i);
                else
                    remainingCardList.add(cardsList.get(i));
            }
            Toast.makeText(this, "size " + remainingCardList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    @AfterViews
    void initViews() {
    //    firstLaunchInit();

        displayViewsSetup();
    }

    @Override
    public void onBackPressed() {
        playerSettings.isSavedGameExists().put(true);
        MainMenuActivity_.intent(this).start();
        super.onBackPressed();
    }

    /* clicks */

    @Click(R.id.play_button)
    void playButton_click() {
        if (lapTurn == 1) {
            chosenKozir = detectKozir();
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
        chosenKozir = BUBNA_SUIT;

        scaleBubnaImageView();
    }

    @Click(R.id.krestaSuit_imageView)
    void krestaButton_click() {
        chosenKozir = KRESTA_SUIT;

        scaleKrestaImageView();
    }

    @Click(R.id.chirvaSuit_imageView)
    void chirvaButton_click() {
        chosenKozir = CHIRVA_SUIT;

        scaleChirvaImageView();
    }

    @Click(R.id.pikaSuit_imageView)
    void pikaButton_click() {
        chosenKozir = PIKA_SUIT;

        scalePikaImageView();
    }

    /* private methods */

    private void firstLaunchInit() {
        if (cardsArrayList.isEmpty()) {
            cardDrawablesArray = getResources().obtainTypedArray(R.array.cardImagesArray);
            for (int i = 0; i < cardDrawablesArray.length(); i++) {
                cardsArrayList.add(cardDrawablesArray.getResourceId(i, 0));
                shuffledCardsList.add(i);
            }
            Collections.shuffle(shuffledCardsList);
            cardDrawablesArray.recycle();

            for (int i = 0; i < 13; i++) {
                if (i < 6)
                    androidCardsList.add(cardsArrayList.get(shuffledCardsList.get(i)));
                else if (i > 5 && i < 12)
                    playerCardsList.add(cardsArrayList.get(shuffledCardsList.get(i)));
                else
                    kolodaKozirCard = cardsArrayList.get(shuffledCardsList.get(i));
            }
            // todo remove this bicycle
            for (int i = 0; i < 13; i++) {
                shuffledCardsList.remove(i);
            }
            Toast.makeText(this, "size " + shuffledCardsList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    private void displayViewsSetup() {
        // displaying player's cards and kozir
        for (int i = 0; i < 6; i++) {
            playerCards_ImageViewArray.get(i).setImageResource(playerCardsList.get(i));

            if (isDebug)
                androidCards_ImageViewArray.get(i).setImageResource(androidCardsList.get(i));
        }
        kolodaKozir_imageView.setImageResource(kolodaKozirCard);
        // display who must play
        playerName = playerSettings.playerName().get();
        if (razdacha % 2 == 0)
            objaz_textView.setText(getString(R.string.objaz_title, playerName));
        else
            objaz_textView.setText(getString(R.string.objaz_title, getString(R.string.android_player_name_title)));

        if (lapTurn == 2) {
            setSuitViewVisible();

            switch (chosenKozir) {
                case PIKA_SUIT:
                    scalePikaImageView();
                    break;
                case BUBNA_SUIT:
                    scaleBubnaImageView();
                    break;
                case KRESTA_SUIT:
                    scaleKrestaImageView();
                    break;
                case CHIRVA_SUIT:
                    scaleChirvaImageView();
                    break;
            }
        }
    }

    private int detectKozir() {
        if (CardDetector.detectCard(cardsArrayList, kolodaKozirCard).contains("pika"))
            return PIKA_SUIT;
        else if (CardDetector.detectCard(cardsArrayList, kolodaKozirCard).contains("bubna"))
            return BUBNA_SUIT;
        else if (CardDetector.detectCard(cardsArrayList, kolodaKozirCard).contains("chirva"))
            return CHIRVA_SUIT;
        else if (CardDetector.detectCard(cardsArrayList, kolodaKozirCard).contains("kresta"))
            return KRESTA_SUIT;

        return 0;
    }

    private void setSuitViewVisible() {
        suits_view.setVisibility(View.VISIBLE);
        switch (firstLapKozir = detectKozir()) {
            case PIKA_SUIT:
                pikaSuit_imageView.setVisibility(View.GONE);
                break;
            case BUBNA_SUIT:
                bubnaSuit_imageView.setVisibility(View.GONE);
                break;
            case CHIRVA_SUIT:
                chirvaSuit_imageView.setVisibility(View.GONE);
                break;
            case KRESTA_SUIT:
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
