package com.mycompany.gofishgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.mycompany.gofishgame.Deck.EmptyDeckException;

public class Player {
    private String name;
    private List<Card> hand = new ArrayList<>();
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

    public void incrementScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void drawInitialCards(Deck deck) throws EmptyDeckException {
        for (int i = 0; i < 7; i++) {
            if (deck.isEmpty()) {
                throw new EmptyDeckException("Not enough cards in deck");
            }
            addCard(deck.drawCard());
        }
    }

    public void takeCard(Deck deck) throws EmptyDeckException {
        Card card = deck.drawCard();
        addCard(card);
        System.out.println(name + " drew a card: " + card);
    }

    public void checkForBooks() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        for (String rank : ranks) {
            long count = hand.stream().filter(card -> card.getRank().equals(rank)).count();
            if (count == 4) {
                removeCardsOfRank(rank);
                incrementScore();
                System.out.println(name + " completed a book of " + rank + "s!");
            }
        }
    }

    private void removeCardsOfRank(String rank) {
        hand.removeIf(card -> card.getRank().equals(rank));
    }

    public void displayHand() {
        if (hand.isEmpty()) {
            System.out.println(name + " has no cards left.");
            return;
        }
        
        System.out.println(name + "'s hand:");
        hand.forEach(card -> System.out.println(card));
    }

    public String chooseRankToAskFor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a rank to ask for: ");
        return scanner.nextLine();
    }
}