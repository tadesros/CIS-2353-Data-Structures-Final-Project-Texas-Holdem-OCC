package holdEm;
import java.util.ArrayList;

public class CommunityCardSetFlop {
    //Member Data Values
    private Card unknownCommunityCard1;
    private Card unknownCommunityCard2;
    private final ArrayList<Card> remaining45Cards = new ArrayList<>();
    private ArrayList<ComputerPocketCards> pocketCardCombinations = new ArrayList<>();

    //ArrayList of cards must equal 50
    public CommunityCardSetFlop(ArrayList<Card> remaining47Cards) {
        //We need two cards to complete the full community card hand.
        int[] indexArray = new int[2];
        //Check if remaining47Cards array is - 47
        if (remaining47Cards.size() != 47) {
            throw new IllegalStateException("Must have 47 cards remaining");
        }

        //We need two random indexes to gather the two cards for remaining community cards
        for (int i = 0; i < 2; i++) {
            //Temp Random
            int tempRandom;
            boolean unique;
            //First Random
            if (i == 0) {
                indexArray[0] = rand(0, 46);
            }
            //Second Random
            else if (i == 1) {
                do {
                    tempRandom = rand(0, 46);
                    unique = tempRandom != indexArray[0];
                } while (!unique);
                //Set value
                indexArray[1] = tempRandom;
            }
        }
        //Set the community cards
        setUnknownCommunityCard1(remaining47Cards.get(indexArray[0]));
        setUnknownCommunityCard2(remaining47Cards.get(indexArray[1]));

        //Set remaining 45 cards
         remaining45Cards.addAll(remaining47Cards);

        //Remove the two unknown community cards
         remaining45Cards.remove(unknownCommunityCard1);
         remaining45Cards.remove(unknownCommunityCard2);

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

    public Card getUnknownCommunityCard2() {
        return unknownCommunityCard2;
    }

    public void setUnknownCommunityCard2(Card unknownCommunityCard2) {
        this.unknownCommunityCard2 = unknownCommunityCard2;
    }

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

