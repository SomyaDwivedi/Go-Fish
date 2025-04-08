package com.mycompany.gofishgame;

import java.util.ArrayList;

public abstract class Game<T extends Player> {
    private final String name;
    private ArrayList<T> players;

    public Game(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<T> getPlayers() {
        return new ArrayList<>(players);
    }

    public void addPlayer(T player) {
        players.add(player);
    }

    public abstract void play();
    public abstract void declareWinner();
}