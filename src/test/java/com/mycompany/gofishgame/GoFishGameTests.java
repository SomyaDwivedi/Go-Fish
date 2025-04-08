package com.mycompany.gofishgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GoFishGameTests {

    // ─── Card Tests ─────────────────────────────────────────────────────────────
    @Test
    public void testCardEquality() {
        Card c1 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card c2 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        assertEquals(c1, c2);
    }

    @Test
    public void testCardHasSameRank() {
        Card c1 = new Card(Card.Rank.KING, Card.Suit.HEARTS);
        Card c2 = new Card(Card.Rank.KING, Card.Suit.CLUBS);
        assertTrue(c1.hasSameRank(c2));
    }

    @Test
    public void testCardToString() {
        Card c = new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS);
        assertEquals("QUEEN of DIAMONDS", c.toString());
    }

    // ─── Deck Tests ─────────────────────────────────────────────────────────────
    @Test
    public void testDeckSizeOnInit() {
        Deck deck = new Deck();
        assertEquals(52, deck.size());
    }

    @Test
    public void testDrawCardReducesSize() throws Deck.EmptyDeckException {
        Deck deck = new Deck();
        int initialSize = deck.size();
        deck.drawCard();
        assertEquals(initialSize - 1, deck.size());
    }

    @Test
    public void testDeckEmptyThrowsException() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            try {
                deck.drawCard();
            } catch (Deck.EmptyDeckException e) {
                fail("Deck shouldn't be empty yet.");
            }
        }

        assertThrows(Deck.EmptyDeckException.class, deck::drawCard);
    }

    // ─── Player Tests ───────────────────────────────────────────────────────────
    @Test
    public void testPlayerAddCard() {
        Player player = new Player("Alice");
        Card card = new Card(Card.Rank.FOUR, Card.Suit.HEARTS);
        player.addCard(card);
        assertEquals(1, player.getHand().size());
    }

    @Test
    public void testPlayerHasRank() {
        Player player = new Player("Bob");
        player.addCard(new Card(Card.Rank.FIVE, Card.Suit.CLUBS));
        assertTrue(player.hasRank("FIVE"));
    }

    @Test
    public void testPlayerGiveCards() {
        Player player = new Player("Eve");
        player.addCard(new Card(Card.Rank.SEVEN, Card.Suit.SPADES));
        player.addCard(new Card(Card.Rank.SEVEN, Card.Suit.HEARTS));
        List<Card> given = player.giveCards("SEVEN");

        assertEquals(2, given.size());
        assertEquals(0, player.getHand().size());
    }

    @Test
    public void testPlayerIncrementScore() {
        Player player = new Player("John");
        assertEquals(0, player.getScore());
        player.incrementScore();
        assertEquals(1, player.getScore());
    }
}
