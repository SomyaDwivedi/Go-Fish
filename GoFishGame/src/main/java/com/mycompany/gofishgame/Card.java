/**
 * @author Somya Dwivedi Khushpreet Kaur Chanpreet Singh Ekampreet Bains
 */
package com.mycompany.gofishgame;

public abstract class Card {
    
    private final String rank;

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
