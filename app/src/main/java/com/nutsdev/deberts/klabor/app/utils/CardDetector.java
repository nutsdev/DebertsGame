package com.nutsdev.deberts.klabor.app.utils;

import com.nutsdev.deberts.klabor.R;
import com.nutsdev.deberts.klabor.app.entities.Card;

import java.util.ArrayList;

/**
 * Created by n1ck on 15.03.2015.
 */
public class CardDetector implements CardsList {

    public static int getCardDrawable(Card card) {
        int value = card.getValue();
        // pika
        if (value == PIKA_SEVEN)
            return R.drawable.pika_seven;
        else if (value == PIKA_EIGHT)
            return R.drawable.pika_eight;
        else if (value == PIKA_NINE)
            return R.drawable.pika_nine;
        else if (value == PIKA_TEN)
            return R.drawable.pika_ten;
        else if (value == PIKA_VALET)
            return R.drawable.pika_valet;
        else if (value == PIKA_QUEEN)
            return R.drawable.pika_queen;
        else if (value == PIKA_KING)
            return R.drawable.pika_king;
        else if (value == PIKA_TUZ)
            return R.drawable.pika_tuz;

        // bubna
        else if (value == BUBNA_SEVEN)
            return R.drawable.bubna_seven;
        else if (value == BUBNA_EIGHT)
            return R.drawable.bubna_eight;
        else if (value == BUBNA_NINE)
            return R.drawable.bubna_nine;
        else if (value == BUBNA_TEN)
            return R.drawable.bubna_ten;
        else if (value == BUBNA_VALET)
            return R.drawable.bubna_valet;
        else if (value == BUBNA_QUEEN)
            return R.drawable.bubna_queen;
        else if (value == BUBNA_KING)
            return R.drawable.bubna_king;
        else if (value == BUBNA_TUZ)
            return R.drawable.bubna_tuz;

        // kresta
        else if (value == KRESTA_SEVEN)
            return R.drawable.kresta_seven;
        else if (value == KRESTA_EIGHT)
            return R.drawable.kresta_eight;
        else if (value == KRESTA_NINE)
            return R.drawable.kresta_nine;
        else if (value == KRESTA_TEN)
            return R.drawable.kresta_ten;
        else if (value == KRESTA_VALET)
            return R.drawable.kresta_valet;
        else if (value == KRESTA_QUEEN)
            return R.drawable.kresta_queen;
        else if (value == KRESTA_KING)
            return R.drawable.kresta_king;
        else if (value == KRESTA_TUZ)
            return R.drawable.kresta_tuz;

        // chirva
        else if (value == CHIRVA_SEVEN)
            return R.drawable.chirva_seven;
        else if (value == CHIRVA_EIGHT)
            return R.drawable.chirva_eight;
        else if (value == CHIRVA_NINE)
            return R.drawable.chirva_nine;
        else if (value == CHIRVA_TEN)
            return R.drawable.chirva_ten;
        else if (value == CHIRVA_VALET)
            return R.drawable.chirva_valet;
        else if (value == CHIRVA_QUEEN)
            return R.drawable.chirva_queen;
        else if (value == CHIRVA_KING)
            return R.drawable.chirva_king;
        else if (value == CHIRVA_TUZ)
            return R.drawable.chirva_tuz;

        // error! shouldn't ever be this
        return -1;
    }

    public static String detectCard(ArrayList<Integer> sourceArrayList, Integer needCard) {
        for (int i = 0; i < sourceArrayList.size(); i++) {
            Integer sourceCard = sourceArrayList.get(i);
            if (sourceCard.equals(needCard)) {
                // chirva
                if (i == 0)
                    return "chirva_seven";
                else if (i == 1)
                    return "chirva_eight";
                else if (i == 2)
                    return "chirva_nine";
                else if (i == 3)
                    return "chirva_ten";
                else if (i == 4)
                    return "chirva_valet";
                else if (i == 5)
                    return "chirva_queen";
                else if (i == 6)
                    return "chirva_king";
                else if (i == 7)
                    return "chirva_tuz";

                // pika
                else if (i == 8)
                    return "pika_seven";
                else if (i == 9)
                    return "pika_eight";
                else if (i == 10)
                    return "pika_nine";
                else if (i == 11)
                    return "pika_ten";
                else if (i == 12)
                    return "pika_valet";
                else if (i == 13)
                    return "pika_queen";
                else if (i == 14)
                    return "pika_king";
                else if (i == 15)
                    return "pika_tuz";

                // bubna
                else if (i == 16)
                    return "bubna_seven";
                else if (i == 17)
                    return "bubna_eight";
                else if (i == 18)
                    return "bubna_nine";
                else if (i == 19)
                    return "bubna_ten";
                else if (i == 20)
                    return "bubna_valet";
                else if (i == 21)
                    return "bubna_queen";
                else if (i == 22)
                    return "bubna_king";
                else if (i == 23)
                    return "bubna_tuz";

                // kresta
                else if (i == 24)
                    return "kresta_seven";
                else if (i == 25)
                    return "kresta_eight";
                else if (i == 26)
                    return "kresta_nine";
                else if (i == 27)
                    return "kresta_ten";
                else if (i == 28)
                    return "kresta_valet";
                else if (i == 29)
                    return "kresta_queen";
                else if (i == 30)
                    return "kresta_king";
                else if (i == 31)
                    return "kresta_tuz";
            }
        }
        return null;
    }

}
