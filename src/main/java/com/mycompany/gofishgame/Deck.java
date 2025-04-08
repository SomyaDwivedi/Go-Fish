package com.mycompany.gofishgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

   // Inside the Deck class...

public Deck() {
    cards = new ArrayList<>();
    // Iterate through all enum values for Suit and Rank
    for (Card.Suit suit : Card.Suit.values()) {
        for (Card.Rank rank : Card.Rank.values()) {
            // Create a new Card object using the Enums
            cards.add(new Card(rank, suit));
        }
    }
    Collections.shuffle(cards);
}

// ... rest of the Deck class (drawCard, isEmpty, size, EmptyDeckException) ...
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

    // Inner class for EmptyDeckException
    public static class EmptyDeckException extends Exception {
        public EmptyDeckException(String message) {
            super(message);
        }
    }
}