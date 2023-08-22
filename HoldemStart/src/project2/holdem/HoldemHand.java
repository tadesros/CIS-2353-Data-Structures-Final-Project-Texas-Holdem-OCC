package project2.holdem;

import java.util.ArrayList;

public class HoldemHand implements Comparable<HoldemHand>{

    private Card card1;
    private Card card2;
    private ArrayList<Card> sharedCards;

    public HoldemHand(Card card1, Card card2) {
        this.card1 = card1;
        this.card2 = card2;

        sharedCards = new ArrayList<>();
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void addSharedCard(Card card) {

        // optional
        if (sharedCards.size() == 5) {
            throw new IllegalArgumentException("Can't have more than 5 cards");
        }

        sharedCards.add(card);

    }

    public ArrayList<Card> getSharedCards() {
        // risky to return the reference
        //return sharedCards;

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.addAll(sharedCards);

        return cards;
    }

    public PokerHand getBestPossibleHand() {
        if (sharedCards.size() != 5) {
            throw new IllegalStateException("Must have 5 shared cards");
        }

        ArrayList<Card> allCards = new ArrayList<>();

        allCards.add(card1);
        allCards.add(card2);
        allCards.addAll(sharedCards);

        ArrayList<PokerHand> hands = new ArrayList<>();

        for (int firstCardIndex = 0; firstCardIndex < 3; firstCardIndex++) {
            for (int secondCardIndex = firstCardIndex + 1; secondCardIndex < 4; secondCardIndex++) {
                for (int thirdCardIndex = secondCardIndex + 1; thirdCardIndex < 5; thirdCardIndex++) {
                    for (int fourthCardIndex = thirdCardIndex + 1; fourthCardIndex < 6; fourthCardIndex++) {
                        for (int fifthCardIndex = fourthCardIndex + 1; fifthCardIndex < 7; fifthCardIndex++) {
                            ArrayList<Card> cards = new ArrayList<>();
                            cards.add(allCards.get(firstCardIndex));
                            cards.add(allCards.get(secondCardIndex));
                            cards.add(allCards.get(thirdCardIndex));
                            cards.add(allCards.get(fourthCardIndex));
                            cards.add(allCards.get(fifthCardIndex));
                            
                            PokerHand hand = new PokerHand(cards);
                            hands.add(hand);
                        }
                    }
                }
            }
        }
        
        PokerHand bestHand = hands.get(0);
        
        for ( int handIndex = 1; handIndex < hands.size(); handIndex++ ){
            if ( hands.get(handIndex).compareTo(bestHand) > 0){
                bestHand = hands.get(handIndex);
            }
        }
        
        return bestHand;

    }

    @Override
    public int compareTo(HoldemHand other) {
        return getBestPossibleHand().compareTo(other.getBestPossibleHand());
    }
    
    public PokerHand.Result call(HoldemHand other){
        return getBestPossibleHand().call(other.getBestPossibleHand());
    }
    
    @Override
    public String toString()
    {
        return card1.toString() + " " + card2.toString();
    }
    
    public double oddsOfWinning(){
       return 0; // TODO 
    }

}
