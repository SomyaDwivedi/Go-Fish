package com.mycompany.gofishgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() throws EmptyDeckException {
        if (cards.isEmpty()) {
            throw new EmptyDeckException("No cards left in the deck");
        }
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public static class EmptyDeckException extends Exception {
        public EmptyDeckException(String message) {
            super(message);
        }
    }
}
