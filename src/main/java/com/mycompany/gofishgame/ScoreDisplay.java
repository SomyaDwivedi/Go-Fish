package com.mycompany.gofishgame;

import java.util.List;
import java.util.Map;

public class ScoreDisplay {
    public void displayPlayerScores(Map<String, List<Player>> teams) {
        System.out.println("\nCurrent Scores:");
        for (Map.Entry<String, List<Player>> entry : teams.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Player player : entry.getValue()) {
                System.out.println("  " + player.getName() + " - " + player.getScore());
            }
        }
    }

    public void displayFinalResults(Map<String, List<Player>> teams) {
        // Individual winner
        Player individualWinner = null;
        int highestIndividualScore = 0;
        
        // Team scores
        String winningTeam = "";
        int highestTeamScore = 0;

        System.out.println("\nFinal Results:");
        System.out.println("--------------");
        
        // Calculate team scores and find winners
        for (Map.Entry<String, List<Player>> entry : teams.entrySet()) {
            int teamScore = entry.getValue().stream().mapToInt(Player::getScore).sum();
            System.out.println(entry.getKey() + " total score: " + teamScore);
            
            if (teamScore > highestTeamScore) {
                highestTeamScore = teamScore;
                winningTeam = entry.getKey();
            }
            
            // Check for individual winner
            for (Player player : entry.getValue()) {
                if (player.getScore() > highestIndividualScore) {
                    highestIndividualScore = player.getScore();
                    individualWinner = player;
                }
            }
        }
        
        System.out.println("\nWinning Team: " + winningTeam + " with " + highestTeamScore + " points");
        System.out.println("Top Player: " + individualWinner.getName() + " with " + highestIndividualScore + " points");
    }
}