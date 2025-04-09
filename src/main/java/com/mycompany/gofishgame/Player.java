package com.mycompany.gofishgame;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
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
        return hand.stream().anyMatch(card -> card.getRank().name().equalsIgnoreCase(rank));
    }

    public List<Card> giveCards(String rank) {
        List<Card> givenCards = new ArrayList<>();
        hand.removeIf(card -> {
            if (card.getRank().name().equalsIgnoreCase(rank)) {
                givenCards.add(card);
                return true;
            }
            return false;
        });
        return givenCards;
    }

    public void incrementScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void drawInitialCards(Deck deck) throws Deck.EmptyDeckException {
        for (int i = 0; i < 7; i++) {
            if (deck.isEmpty()) {
                throw new Deck.EmptyDeckException("Not enough cards in deck");
            }
            addCard(deck.drawCard());
        }
    }

    public void takeCard(Deck deck) throws Deck.EmptyDeckException {
        Card card = deck.drawCard();
        addCard(card);
        System.out.println(name + " drew a card: " + card);
    }

    public void checkForBooks() {
        for (Card.Rank rank : Card.Rank.values()) {
            long count = hand.stream().filter(card -> card.getRank() == rank).count();
            if (count == 4) {
                removeCardsOfRank(rank);
                incrementScore();
                System.out.println(name + " completed a book of " + rank + "s!");
            }
        }
    }

    private void removeCardsOfRank(Card.Rank rank) {
        hand.removeIf(card -> card.getRank() == rank);
    }

    public void displayHand() {
        if (hand.isEmpty()) {
            System.out.println(name + " has no cards left.");
            return;
        }

        System.out.println(name + "'s hand:");
        hand.forEach(System.out::println);
    }

    public Set<String> getAvailableRanks() {
        return hand.stream()
                .map(card -> card.getRank().name())
                .collect(Collectors.toSet());
    }
}
