/**
 * @author Somya Dwivedi Khushpreet Kaur Chanpreet Singh Ekampreet Bains
 */
package com.mycompany.gofishgame;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Player> players = Arrays.asList(new Player("Alice"), new Player("Bob"));
        GoFishGame game = new GoFishGame("Go Fish", players);
        game.play();
    }
}
