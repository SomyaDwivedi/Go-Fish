package com.mycompany.gofishgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.mycompany.gofishgame.Deck.EmptyDeckException;

public class GoFishGame extends Game<Player> {
    private final int numTeams;
    private final int playersPerTeam;
    private Deck deck;
    private Map<String, List<Player>> teams;
    private ScoreDisplay scoreDisplay;

    public GoFishGame(String name, int numTeams, int playersPerTeam) {
        super(name);
        this.numTeams = numTeams;
        this.playersPerTeam = playersPerTeam;
        this.teams = new HashMap<>();
        this.scoreDisplay = new ScoreDisplay();
        initializeGame();
    }

    private void initializeGame() {
        // Initialize deck
        deck = new Deck();
        
        // Create teams and players
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= numTeams; i++) {
            String teamName = "Team " + i;
            List<Player> teamPlayers = new ArrayList<>();
            
            for (int j = 1; j <= playersPerTeam; j++) {
                System.out.print("Enter name for Player " + j + " in " + teamName + ": ");
                String playerName = scanner.nextLine();
                Player player = new Player(playerName);
                teamPlayers.add(player);
                addPlayer(player);
            }
            
            teams.put(teamName, teamPlayers);
        }
    }

    @Override
    public void play() {
        // Deal initial cards
        for (Player player : getPlayers()) {
            try {
                player.drawInitialCards(deck);
            } catch (Deck.EmptyDeckException e) {
                System.out.println("Not enough cards to deal to all players.");
                return;
            }
        }

        // Game loop
        boolean gameOver = false;
        int currentPlayerIndex = 0;
        
        while (!gameOver) {
            Player currentPlayer = getPlayers().get(currentPlayerIndex);
            playTurn(currentPlayer);
            
            // Check if game is over
            if (deck.isEmpty() && allHandsEmpty()) {
                gameOver = true;
            }
            
            currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
        }
        
        declareWinner();
    }

    private boolean allHandsEmpty() {
        return getPlayers().stream().allMatch(p -> p.getHand().isEmpty());
    }

    private void playTurn(Player currentPlayer) {
        System.out.println("\n" + currentPlayer.getName() + "'s turn:");
        currentPlayer.displayHand();
        
        // Check for books at start of turn
        currentPlayer.checkForBooks();
        
        // Select another player to ask
        Player otherPlayer = selectOtherPlayer(currentPlayer);
        if (otherPlayer == null) {
            System.out.println("No other players available to ask.");
            return;
        }
        
        // Ask for a rank
        String rank = currentPlayer.chooseRankToAskFor();
        if (otherPlayer.hasRank(rank)) {
            List<Card> cards = otherPlayer.giveCards(rank);
            currentPlayer.addCards(cards);
            System.out.println(currentPlayer.getName() + " got " + cards.size() + " " + rank + " from " + otherPlayer.getName());
        } else {
            System.out.println("Go Fish!");
            try {
                currentPlayer.takeCard(deck);
            } catch (Deck.EmptyDeckException e) {
                System.out.println("No cards left to draw.");
            }
        }
        
        // Check for books again after getting cards
        currentPlayer.checkForBooks();
        
        // Display scores
        scoreDisplay.displayPlayerScores(teams);
    }

    private Player selectOtherPlayer(Player currentPlayer) {
        return getPlayers().stream()
                .filter(p -> p != currentPlayer && !p.getHand().isEmpty())
                .findFirst()
                .orElse(null);
    }

    @Override
    public void declareWinner() {
        scoreDisplay.displayFinalResults(teams);
    }
}