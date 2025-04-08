package com.mycompany.gofishgame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Get user input for number of teams and players per team
            System.out.print("Enter the number of teams (minimum 2): ");
            int numTeams = scanner.nextInt();
            
            System.out.print("Enter the number of players per team (minimum 1): ");
            int playersPerTeam = scanner.nextInt();
            scanner.nextLine(); // consume newline

            // Validate input
            if (numTeams < 2) {
                System.out.println("Error: There must be at least 2 teams.");
                return;
            }
            
            if (playersPerTeam < 1) {
                System.out.println("Error: Each team must have at least 1 player.");
                return;
            }

            // Start the game
            System.out.println("\nStarting Go Fish with " + numTeams + 
                             " teams and " + playersPerTeam + " players per team!");
            GoFishGame game = new GoFishGame("Go Fish", numTeams, playersPerTeam);
            game.play();
            
        } finally {
            scanner.close();
        }
    }
}