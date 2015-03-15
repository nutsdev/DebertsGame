package com.nutsdev.deberts.klabor.app.ui.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nutsdev.deberts.klabor.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EActivity(R.layout.activity_kozir_choose_one_vs_one)
public class KozirChooseOneVsOneActivity extends ActionBarActivity {

    public static final int PIKA_SUIT = 1001;
    public static final int BUBNA_SUIT = 1002;
    public static final int KRESTA_SUIT = 1003;
    public static final int CHIRVA_SUIT = 1004;

    TypedArray cardDrawablesArray;
    // козырь для первого круга
    @InstanceState
    Integer kolodaKozirCard;
    // список всех карт по порядку
    @InstanceState
    static ArrayList<Integer> cardsArrayList = new ArrayList<>();
    // список всех карт после shuffle
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
    }

    @AfterViews
    void initCards() {
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
        }

        // displaying player's cards and kozir
        for (int i = 0; i < 6; i++) {
            playerCards_ImageViewArray.get(i).setImageResource(playerCardsList.get(i));
        }
        kolodaKozir_imageView.setImageResource(kolodaKozirCard);
        /*    for (int i = 0; i < cardDrawablesArray.length(); i++) {
                cardsArrayList.add(cardDrawablesArray.getResourceId(i, 0));
            }
            Collections.shuffle(cardsArrayList);
            cardDrawablesArray.recycle();

            // setting up player's cards and opponent's and kozir one
            for (int i = 0; i < 13; i++) {
                if (i < 6)
                    androidCardsList.add(cardsArrayList.get(i));
                else if (i > 5 && i < 12)
                    playerCardsList.add(cardsArrayList.get(i));
                else
                    kolodaKozirCard = cardsArrayList.get(i);
            }
        }

        // displaying player's cards and kozir
        for (int i = 0; i < 6; i++) {
            playerCards_ImageViewArray.get(i).setImageResource(playerCardsList.get(i));
        }
        kolodaKozir_imageView.setImageResource(kolodaKozirCard); */
    }

}
