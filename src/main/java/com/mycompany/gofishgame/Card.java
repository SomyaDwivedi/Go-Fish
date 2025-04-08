package com.mycompany.gofishgame;

import java.util.Objects;

// Suggestion 1: Removed 'abstract' - Card is a concrete concept
public class Card {

    // Suggestion 2: Use Enums for Rank and Suit for type safety and clarity
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
        // You might adjust the order or representation based on game logic needs
    }

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        // Add null checks if necessary
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        // Example: "ACE of SPADES". Adjust formatting as needed.
        return rank + " of " + suit;
    }

    // Suggestion 3: Implement equals() and hashCode() for comparisons and collections
    // This example considers cards equal if they have the same rank AND suit.
    // For Go Fish, you might only care about rank equality in some contexts,
    // so you might need additional methods like `hasSameRank(Card other)`.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    // Optional: A helper method specifically for Go Fish logic
    public boolean hasSameRank(Card other) {
        return this.rank == other.rank;
    }
}