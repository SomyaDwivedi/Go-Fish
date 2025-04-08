package com.mycompany.gofishgame;

import java.util.*;

public class GoFishGame extends Game<Player> {
    private final int numTeams;
    private final int playersPerTeam;
    private Deck deck;
    private Map<String, List<Player>> teams;
    private ScoreDisplay scoreDisplay;
    private final Scanner scanner = new Scanner(System.in);

    public GoFishGame(String name, int numTeams, int playersPerTeam) {
        super(name);
        this.numTeams = numTeams;
        this.playersPerTeam = playersPerTeam;
        this.teams = new HashMap<>();
        this.scoreDisplay = new ScoreDisplay();
        initializeGame();
    }

    private void initializeGame() {
        deck = new Deck();

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
        for (Player player : getPlayers()) {
            try {
                player.drawInitialCards(deck);
            } catch (Deck.EmptyDeckException e) {
                System.out.println("Not enough cards to deal to all players.");
                return;
            }
        }

        boolean gameOver = false;
        int currentPlayerIndex = 0;

        while (!gameOver) {
            Player currentPlayer = getPlayers().get(currentPlayerIndex);
            playTurn(currentPlayer);

            if (deck.isEmpty() && allHandsEmpty()) {
                gameOver = true;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
        }

        declareWinner();
        scanner.close();
    }

    private boolean allHandsEmpty() {
        return getPlayers().stream().allMatch(p -> p.getHand().isEmpty());
    }

    private void playTurn(Player currentPlayer) {
        System.out.println("\n" + currentPlayer.getName() + "'s turn:");
        currentPlayer.displayHand();
        currentPlayer.checkForBooks();

        Player otherPlayer = selectOtherPlayer(currentPlayer);
        if (otherPlayer == null) {
            System.out.println("No other players available to ask.");
            return;
        }

        String rank = currentPlayer.chooseRankToAskFor(currentPlayer.getHand());
        if (rank == null) {
            System.out.println("No valid ranks to ask for.");
            return;
        }

        if (otherPlayer.hasRank(rank)) {
            List<Card> cards = otherPlayer.giveCards(rank);
            currentPlayer.addCards(cards);
            System.out.println(currentPlayer.getName() + " got " + cards.size() + " " + rank + "(s) from " + otherPlayer.getName());
        } else {
            System.out.println("Go Fish!");
            try {
                currentPlayer.takeCard(deck);
            } catch (Deck.EmptyDeckException e) {
                System.out.println("No cards left to draw.");
            }
        }

        currentPlayer.checkForBooks();
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
                    scanner.nextLine(); // consume newline
                    return availableOpponents.get(choice - 1);
                } else {
                    System.out.println("Invalid choice. Please enter a number from the list.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    @Override
    public void declareWinner() {
        scoreDisplay.displayFinalResults(teams);
    }
}
