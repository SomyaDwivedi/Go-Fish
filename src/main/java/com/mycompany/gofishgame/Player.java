package com.mycompany.gofishgame;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import com.mycompany.gofishgame.Deck.EmptyDeckException;
import java.util.ArrayList;

public class Player {
    private String name;
    private List<Card> hand = new ArrayList<>();
    private int score = 0;
    private Scanner scanner = new Scanner(System.in);

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

    public String chooseRankToAskFor(List<Card> currentHand) {
        Set<String> availableRanks = currentHand.stream()
                .map(Card::getRank)
                .collect(Collectors.toSet());

        if (availableRanks.isEmpty()) {
            return null; // Should not happen if the player has a turn
        }

        String chosenRank;
        while (true) {
            System.out.print("Enter a rank to ask for (from your hand: " + availableRanks + "): ");
            chosenRank = scanner.nextLine().trim();
            if (availableRanks.contains(chosenRank)) {
                return chosenRank;
            } else {
                System.out.println("Invalid rank. Please choose a rank from your hand.");
            }
        }
    }
}