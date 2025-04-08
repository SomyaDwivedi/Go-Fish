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
    private Scanner scanner = new Scanner(System.in);

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

        // Create teams and players with input validation
        for (int i = 1; i <= numTeams; i++) {
            String teamName = "Team " + i;
            List<Player> teamPlayers = new ArrayList<>();

            for (int j = 1; j <= playersPerTeam; j++) {
                String playerName;
                while (true) {
                    System.out.print("Enter name for Player " + j + " in " + teamName + ": ");
                    playerName = scanner.nextLine().trim();
                    if (!playerName.isEmpty()) {
                        break;
                    } else {
                        System.out.println("Player name cannot be empty. Please try again.");
                    }
                }
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
        scanner.close(); // Close the scanner when the game ends
    }

    private boolean allHandsEmpty() {
        return getPlayers().stream().allMatch(p -> p.getHand().isEmpty());
    }

    private void playTurn(Player currentPlayer) {
        System.out.println("\n" + currentPlayer.getName() + "'s turn:");
        currentPlayer.displayHand();

        // Check for books at start of turn
        currentPlayer.checkForBooks();

        // Select another player to ask with input validation
        Player otherPlayer = selectOtherPlayer(currentPlayer);
        if (otherPlayer == null) {
            System.out.println("No other players available to ask.");
            return;
        }

        // Ask for a rank with input validation
        String rank = currentPlayer.chooseRankToAskFor(currentPlayer.getHand());
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
        List<Player> availableOpponents = getPlayers().stream()
                .filter(p -> p != currentPlayer && !p.getHand().isEmpty())
                .toList();

        if (availableOpponents.isEmpty()) {
            return null;
        }

        if (availableOpponents.size() == 1) {
            return availableOpponents.get(0);
        }

        System.out.println("\nWho would you like to ask?");
        for (int i = 0; i < availableOpponents.size(); i++) {
            System.out.println((i + 1) + ". " + availableOpponents.get(i).getName());
        }

        int choice;
        while (true) {
            System.out.print("Enter the number of the player: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= availableOpponents.size()) {
                    scanner.nextLine(); // Consume newline
                    return availableOpponents.get(choice - 1);
                } else {
                    System.out.println("Invalid choice. Please enter a number from the list.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    @Override
    public void declareWinner() {
        scoreDisplay.displayFinalResults(teams);
    }
}