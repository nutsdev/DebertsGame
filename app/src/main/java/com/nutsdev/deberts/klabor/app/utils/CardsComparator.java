package com.nutsdev.deberts.klabor.app.utils;

import com.nutsdev.deberts.klabor.app.entities.Card;

import java.util.Comparator;

/**
 * Created by n1ck on 18.03.2015.
 */
public class CardsComparator implements Comparator<Card> {

    @Override
    public int compare(Card lhs, Card rhs) {
        int cardLhs = lhs.getValue();
        int cardRhs = rhs.getValue();

        if (cardLhs < cardRhs)
            return -1;
        else if (cardLhs > cardRhs)
            return 1;
        else
            return 0;
    }

}
