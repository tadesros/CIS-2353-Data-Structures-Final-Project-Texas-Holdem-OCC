package project2.holdem;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class HoldemHandTest {

    public HoldemHandTest() {
    }

    @Test
    public void testGetBestPossibleHand() {

        // Arrange
        HoldemHand hand = new HoldemHand(
                new Card(Card.Face.SEVEN, Card.Suit.CLUBS),
                new Card(Card.Face.EIGHT, Card.Suit.CLUBS)
        );

        hand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.CLUBS));
        hand.addSharedCard(new Card(Card.Face.SIX, Card.Suit.HEARTS));
        hand.addSharedCard(new Card(Card.Face.NINE, Card.Suit.CLUBS));
        hand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.DIAMONDS));
        hand.addSharedCard(new Card(Card.Face.TEN, Card.Suit.SPADES));

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Face.SEVEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Face.SIX, Card.Suit.HEARTS));
        cards.add(new Card(Card.Face.EIGHT, Card.Suit.CLUBS));
        cards.add(new Card(Card.Face.NINE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Face.TEN, Card.Suit.SPADES));

        PokerHand expectedHand = new PokerHand(cards);

        // Act
        PokerHand actualBestHand = hand.getBestPossibleHand();

        // Assert
        assertEquals(expectedHand.toString(), actualBestHand.toString());

    }

    @Test
    public void testCompareToWinsAndLoses() {
        // Arrange
        HoldemHand winningHand = new HoldemHand(
                new Card(Card.Face.SEVEN, Card.Suit.CLUBS),
                new Card(Card.Face.EIGHT, Card.Suit.CLUBS)
        );

        winningHand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.CLUBS));
        winningHand.addSharedCard(new Card(Card.Face.SIX, Card.Suit.HEARTS));
        winningHand.addSharedCard(new Card(Card.Face.NINE, Card.Suit.CLUBS));
        winningHand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.DIAMONDS));
        winningHand.addSharedCard(new Card(Card.Face.TEN, Card.Suit.SPADES));

        HoldemHand losingHand = new HoldemHand(
                new Card(Card.Face.SEVEN, Card.Suit.CLUBS),
                new Card(Card.Face.ACE, Card.Suit.HEARTS)
        );

        losingHand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.CLUBS));
        losingHand.addSharedCard(new Card(Card.Face.SIX, Card.Suit.HEARTS));
        losingHand.addSharedCard(new Card(Card.Face.NINE, Card.Suit.CLUBS));
        losingHand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.DIAMONDS));
        losingHand.addSharedCard(new Card(Card.Face.TEN, Card.Suit.SPADES));

        // act
        int winningResult = winningHand.compareTo(losingHand);
        int losingResult = losingHand.compareTo(winningHand);

        // assert
        assertTrue(winningResult > 0);
        assertTrue(losingResult < 0);

    }

    @Test
    public void testCallDraw() {
        // Arrange
        HoldemHand firstHand = new HoldemHand(
                new Card(Card.Face.SEVEN, Card.Suit.CLUBS),
                new Card(Card.Face.EIGHT, Card.Suit.CLUBS)
        );

        firstHand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.CLUBS));
        firstHand.addSharedCard(new Card(Card.Face.SIX, Card.Suit.HEARTS));
        firstHand.addSharedCard(new Card(Card.Face.NINE, Card.Suit.CLUBS));
        firstHand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.DIAMONDS));
        firstHand.addSharedCard(new Card(Card.Face.TEN, Card.Suit.SPADES));

        HoldemHand secondHand = new HoldemHand(
                new Card(Card.Face.SEVEN, Card.Suit.SPADES),
                new Card(Card.Face.EIGHT, Card.Suit.HEARTS)
        );

        secondHand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.CLUBS));
        secondHand.addSharedCard(new Card(Card.Face.SIX, Card.Suit.HEARTS));
        secondHand.addSharedCard(new Card(Card.Face.NINE, Card.Suit.CLUBS));
        secondHand.addSharedCard(new Card(Card.Face.ACE, Card.Suit.DIAMONDS));
        secondHand.addSharedCard(new Card(Card.Face.TEN, Card.Suit.SPADES));
        
        PokerHand.Result expectedResult = PokerHand.Result.DRAW;

        // act
        PokerHand.Result actualResult = firstHand.call(secondHand);
        
        // assert
        assertEquals(expectedResult, actualResult);
    }

}
