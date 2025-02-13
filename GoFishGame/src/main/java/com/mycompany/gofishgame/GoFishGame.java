/**
 * @author Somya Dwivedi Khushpreet Kaur Chanpreet Singh Ekampreet Bains
 */
package com.mycompany.gofishgame;

import java.util.List;
import java.util.Random;

public class GoFishGame extends Game {
    private Deck deck = new Deck();
    private List<Player> players;
    private Random random = new Random();

    public GoFishGame(String name, List<Player> players) {
        super(name);
        this.players = players;
    }

    public void dealCards() {
        for (int i = 0; i < 5; i++) {
            for (Player player : players) {
                player.addCard(deck.drawCard());
            }
        }
    }

    public void play() {
        System.out.println("Starting Go Fish...");
        dealCards();
        while (!deck.isEmpty()) {
            for (Player player : players) {
                takeTurn(player);
                if (deck.isEmpty()) break;
            }
        }
        System.out.println("Game Over!");
        declareWinner();
    }

    private void takeTurn(Player player) {
        if (player.getHand().isEmpty()) {
            player.addCard(deck.drawCard());
            return;
        }

        String rank = player.getHand().get(0).getRank();
        Player opponent = players.get(random.nextInt(players.size()));
        while (opponent == player) {
            opponent = players.get(random.nextInt(players.size()));
        }

        System.out.println(player.getName() + " asks " + opponent.getName() + " for " + rank);
        
        if (opponent.hasRank(rank)) {
            System.out.println(opponent.getName() + " gives " + rank + " to " + player.getName());
            player.addCards(opponent.giveCards(rank));
        } else {
            System.out.println(opponent.getName() + " says 'Go Fish!'");
            player.addCard(deck.drawCard());
        }
    }

    public void declareWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getHand().size() > winner.getHand().size()) {
                winner = player;
            }
        }
        System.out.println("Winner: " + winner.getName());
    }
}
