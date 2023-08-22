package holdEm;
import java.util.ArrayList;

public class CommunityCardSetTurn {
    //Member Data Values
    private Card unknownCommunityCard1;

    private final ArrayList<Card> remaining45Cards = new ArrayList<>();
    private ArrayList<ComputerPocketCards> pocketCardCombinations = new ArrayList<>();

    //ArrayList of cards must equal 46
    public CommunityCardSetTurn(ArrayList<Card> remaining46Cards,Card currentCard) {

        //Check if remaining47Cards array is - 46
        if (remaining46Cards.size() != 46) {
            throw new IllegalStateException("Must have 47 cards remaining");
        }

        //Set the community cards
        setUnknownCommunityCard1(currentCard);

        //Set remaining 45 cards
         remaining45Cards.addAll(remaining46Cards);

        //Remove the one unknown card
         remaining45Cards.remove(unknownCommunityCard1);

        //Calculate all Pocket Card Combinations
         getAllPocketCardCombinations();
    }

    // ** FUNCTIONS **

    public ArrayList<ComputerPocketCards> getList() {
        return pocketCardCombinations;
    }

    //Return a Random Number Between min and max
    public static int rand(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid range");
        }
        double rand = Math.random();
        return (int) (rand * ((max - min) + 1)) + min;
    }

    //** GETTERS AND SETTERS **

    public Card getUnknownCommunityCard1() {
        return unknownCommunityCard1;
    }

    public void setUnknownCommunityCard1(Card unknownCommunityCard1) {
        this.unknownCommunityCard1 = unknownCommunityCard1;
    }

    //Function create and store all 990 pocket card combinations
    public void getAllPocketCardCombinations() {
        for (int firstCardIndex = 0; firstCardIndex < 44; firstCardIndex++) {
            for (int secondCardIndex = firstCardIndex + 1; secondCardIndex < 45; secondCardIndex++) {


                  Card card1 = new Card(remaining45Cards.get(firstCardIndex));
                  Card card2 = new Card(remaining45Cards.get(secondCardIndex));

                  ComputerPocketCards cpuPocketCard = new ComputerPocketCards(card1,card2);
                  pocketCardCombinations.add(cpuPocketCard);
            }
        }
    }

}//End Class

