/**
 * @author Somya Dwivedi Khushpreet Kaur Chanpreet Singh Ekampreet Singh Bains
 */
package com.mycompany.gofishgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            System.out.print("Enter Player Name: " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            players.add(new Player(playerName));
        }

        GoFishGame game = new GoFishGame("Go Fish", players);
        game.play();

        scanner.close();
    }
}
