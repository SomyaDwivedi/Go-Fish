package com.mycompany.gofishgame;  // Add this package declaration

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
public class GoFishGameTests {

    // Card Tests
    @Test
    public void testCardCreation() {
        Card card = new PlayingCard("Ace", "Spades");
        assertEquals("Ace", card.getRank());
        assertEquals("Spades", card.getSuit());
    }
    
    @Test
    public void testCardToString() {
        Card card = new PlayingCard("King", "Hearts");
        assertEquals("King of Hearts", card.toString());
    }

    // Deck Tests
    @Test
    public void testDeckInitialization() {
        Deck deck = new Deck();
        assertEquals(52, deck.size());
    }
    
    @Test
    public void testDrawCard() throws Deck.EmptyDeckException {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(51, deck.size());
    }
    
    @Test
    public void testDrawCardEmptyDeck() {
        Deck deck = new Deck();
        try {
            for (int i = 0; i < 53; i++) {
                deck.drawCard();
            }
            fail("Expected EmptyDeckException");
        } catch (Deck.EmptyDeckException e) {
            assertEquals("No cards left in the deck", e.getMessage());
        }
    }
    
    @Test
    public void testDeckIsEmpty() {
        Deck deck = new Deck();
        assertFalse(deck.isEmpty());
        try {
            for (int i = 0; i < 52; i++) {
                deck.drawCard();
            }
        } catch (Deck.EmptyDeckException e) {
            fail("Unexpected exception");
        }
        assertTrue(deck.isEmpty());
    }

    // Player Tests
    @Test
    public void testPlayerCreation() {
        Player player = new Player("Alice");
        assertEquals("Alice", player.getName());
        assertTrue(player.getHand().isEmpty());
        assertEquals(0, player.getScore());
    }
    
    @Test
    public void testAddCardToPlayer() {
        Player player = new Player("Bob");
        Card card = new PlayingCard("Queen", "Diamonds");
        player.addCard(card);
        assertEquals(1, player.getHand().size());
        assertEquals("Queen", player.getHand().get(0).getRank());
    }
    
    @Test
    public void testAddNullCardToPlayer() {
        Player player = new Player("Charlie");
        player.addCard(null);
        assertEquals(0, player.getHand().size());
    }
    
    @Test
    public void testPlayerHasRank() {
        Player player = new Player("David");
        player.addCard(new PlayingCard("10", "Clubs"));
        assertTrue(player.hasRank("10"));
        assertFalse(player.hasRank("Ace"));
    }
    
    @Test
    public void testPlayerGiveCards() {
        Player player = new Player("Eve");
        player.addCard(new PlayingCard("7", "Hearts"));
        player.addCard(new PlayingCard("7", "Spades"));
        player.addCard(new PlayingCard("8", "Diamonds"));
        
        List<Card> given = player.giveCards("7");
        assertEquals(2, given.size());
        assertEquals(1, player.getHand().size());
        assertEquals("8", player.getHand().get(0).getRank());
    }
    
    @Test
    public void testPlayerGiveCardsNoMatch() {
        Player player = new Player("Frank");
        player.addCard(new PlayingCard("9", "Hearts"));
        List<Card> given = player.giveCards("10");
        assertEquals(0, given.size());
        assertEquals(1, player.getHand().size());
    }
    
    @Test
    public void testPlayerCheckForBooks() {
        Player player = new Player("Grace");
        // Add 4 cards of same rank to complete a book
        player.addCard(new PlayingCard("Jack", "Hearts"));
        player.addCard(new PlayingCard("Jack", "Diamonds"));
        player.addCard(new PlayingCard("Jack", "Clubs"));
        player.addCard(new PlayingCard("Jack", "Spades"));
        
        player.checkForBooks();
        assertEquals(1, player.getScore());
        assertTrue(player.getHand().isEmpty());
    }
    
    @Test
    public void testPlayerCheckForNoBooks() {
        Player player = new Player("Henry");
        player.addCard(new PlayingCard("Queen", "Hearts"));
        player.addCard(new PlayingCard("King", "Diamonds"));
        
        player.checkForBooks();
        assertEquals(0, player.getScore());
        assertEquals(2, player.getHand().size());
    }
    
    @Test
    public void testPlayerIncrementScore() {
        Player player = new Player("Ivy");
        assertEquals(0, player.getScore());
        player.incrementScore();
        assertEquals(1, player.getScore());
    }
    
    @Test
    public void testPlayerAddMultipleCards() {
        Player player = new Player("Jack");
        List<Card> cards = List.of(
            new PlayingCard("2", "Hearts"),
            new PlayingCard("3", "Diamonds")
        );
        player.addCards(cards);
        assertEquals(2, player.getHand().size());
    }

    // Game Tests
    @Test
    public void testGoFishGameInitialization() {
        GoFishGame game = new GoFishGame("Test Game", 2, 1);
        assertEquals(2, game.getPlayers().size());
        assertEquals("Test Game", game.getName());
    }
    
    @Test
    public void testAllHandsEmpty() {
        GoFishGame game = new GoFishGame("Test Game", 2, 1);
        assertTrue(game.allHandsEmpty());
        
        // Add a card to one player
        game.getPlayers().get(0).addCard(new PlayingCard("Ace", "Spades"));
        assertFalse(game.allHandsEmpty());
    }
    
    @Test
    public void testSelectOtherPlayer() {
        GoFishGame game = new GoFishGame("Test Game", 2, 1);
        Player current = game.getPlayers().get(0);
        Player other = game.getPlayers().get(1);
        
        // With empty hands, should return null
        assertNull(game.selectOtherPlayer(current));
        
        // Add cards to other player
        other.addCard(new PlayingCard("2", "Hearts"));
        assertEquals(other, game.selectOtherPlayer(current));
    }

    // ScoreDisplay Tests
    @Test
    public void testScoreDisplay() {
        ScoreDisplay display = new ScoreDisplay();
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        p1.incrementScore();
        p1.incrementScore();
        p2.incrementScore();
        
        Map<String, List<Player>> teams = new HashMap<>();
        teams.put("Team A", List.of(p1));
        teams.put("Team B", List.of(p2));
        
        // Just verify these don't throw exceptions
        assertDoesNotThrow(() -> display.displayPlayerScores(teams));
        assertDoesNotThrow(() -> display.displayFinalResults(teams));
    }
}