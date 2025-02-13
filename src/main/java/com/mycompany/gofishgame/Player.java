/**
 * @author Somya Dwivedi Khushpreet Kaur Chanpreet Singh Ekampreet Bains
 */
package com.mycompany.gofishgame;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public void addCards(List<Card> cards) {
        hand.addAll(cards);
    }

    public boolean hasRank(String rank) {
        return hand.stream().anyMatch(card -> card.getRank().equals(rank));
    }

    public List<Card> giveCards(String rank) {
        List<Card> givenCards = new ArrayList<>();
        hand.removeIf(card -> {
            if (card.getRank().equals(rank)) {
                givenCards.add(card);
                return true;
            }
            return false;
        });
        return givenCards;
    }
}
