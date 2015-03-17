package com.nutsdev.deberts.klabor.app.entities;

import java.io.Serializable;

/**
 * Created by n1ck on 17.03.2015.
 */
public class Card implements Serializable {

    public static final int PIKA_SUIT = 1001;
    public static final int BUBNA_SUIT = 1002;
    public static final int KRESTA_SUIT = 1003;
    public static final int CHIRVA_SUIT = 1004;

    private int value;

    public Card(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        if (value >= 0 && value <= 7)
            return PIKA_SUIT;
        else if (value >= 8 && value <= 15)
            return BUBNA_SUIT;
        else if (value >= 16 && value <= 23)
            return KRESTA_SUIT;
        else if (value >= 24 && value <= 31)
            return CHIRVA_SUIT;

        return -1;
    }

    public String getCard() {
        // pika
        if (value == 0)
            return "pika_seven";
        else if (value == 1)
            return "pika_eight";
        else if (value == 2)
            return "pika_nine";
        else if (value == 3)
            return "pika_ten";
        else if (value == 4)
            return "pika_valet";
        else if (value == 5)
            return "pika_queen";
        else if (value == 6)
            return "pika_king";
        else if (value == 7)
            return "pika_tuz";

        // bubna
        else if (value == 8)
            return "bubna_seven";
        else if (value == 9)
            return "bubna_eight";
        else if (value == 10)
            return "bubna_nine";
        else if (value == 11)
            return "bubna_ten";
        else if (value == 12)
            return "bubna_valet";
        else if (value == 13)
            return "bubna_queen";
        else if (value == 14)
            return "bubna_king";
        else if (value == 15)
            return "bubna_tuz";

        // kresta
        else if (value == 16)
            return "kresta_seven";
        else if (value == 17)
            return "kresta_eight";
        else if (value == 18)
            return "kresta_nine";
        else if (value == 19)
            return "kresta_ten";
        else if (value == 20)
            return "kresta_valet";
        else if (value == 21)
            return "kresta_queen";
        else if (value == 22)
            return "kresta_king";
        else if (value == 23)
            return "kresta_tuz";

        // chirva
        else if (value == 24)
            return "chirva_seven";
        else if (value == 25)
            return "chirva_eight";
        else if (value == 26)
            return "chirva_nine";
        else if (value == 27)
            return "chirva_ten";
        else if (value == 28)
            return "chirva_valet";
        else if (value == 29)
            return "chirva_queen";
        else if (value == 30)
            return "chirva_king";
        else if (value == 31)
            return "chirva_tuz";

        return null;
    }

}
