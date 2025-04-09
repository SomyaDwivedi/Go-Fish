package com.mycompany.gofishgame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            int numTeams;
            int playersPerTeam;

            // Validate number of teams
            while (true) {
                System.out.print("Enter the number of teams (minimum 2): ");
                if (scanner.hasNextInt()) {
                    numTeams = scanner.nextInt();
                    if (numTeams >= 2) break;
                    else System.out.println("Error: There must be at least 2 teams.");
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // consume invalid token
                }
            }

            // Validate number of players per team
            while (true) {
                System.out.print("Enter the number of players per team (minimum 1): ");
                if (scanner.hasNextInt()) {
                    playersPerTeam = scanner.nextInt();
                    if (playersPerTeam >= 1) break;
                    else System.out.println("Error: Each team must have at least 1 player.");
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // consume invalid token
                }
            }

            scanner.nextLine(); // consume newline

            System.out.println("\nStarting Go Fish with " + numTeams +
                    " teams and " + playersPerTeam + " players per team!");
            GoFishGame game = new GoFishGame("Go Fish", numTeams, playersPerTeam);
            game.play();

        } finally {
            scanner.close();
        }
    }
}
