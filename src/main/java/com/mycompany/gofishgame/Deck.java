/**
 * @author Somya Dwivedi Khushpreet Kaur Chanpreet Singh Ekampreet Singh Bains
 */
package com.mycompany.gofishgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<>();
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    public Deck() {
        for (String rank : RANKS) {
            for (int i = 0; i < 4; i++) {
                cards.add(new PlayingCard(rank));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
