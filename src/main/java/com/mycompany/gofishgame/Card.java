/**
 * @author Somya Dwivedi Khushpreet Kaur Chanpreet Singh Ekampreet Singh Bains
 */
package com.mycompany.gofishgame;

public abstract class Card {
    
    private final String rank;

    /**
     * Constructor for Card class
     * @param rank The rank of the card
     */
    public Card(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank;
    }
}
