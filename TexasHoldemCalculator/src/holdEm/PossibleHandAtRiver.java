package holdEm;
import java.util.ArrayList;

public class PossibleHandAtRiver {

 //   private final ArrayList<Card> remaining45Cards = new ArrayList<>();
    private ArrayList<ComputerPocketCards> pocketCardCombinations = new ArrayList<>();



    //ArrayList of cards must equal 45
    public PossibleHandAtRiver(ArrayList<Card> remaining45Cards) {

        //Check if remaining47Cards array is - 45
        if (remaining45Cards.size() != 45) {
            throw new IllegalStateException("Must have 45 cards remaining");
        }

        //Calculate all Pocket Card Combinations
        getAllPocketCardCombinations(remaining45Cards);
    }

    // ** FUNCTIONS **

    //** GETTERS AND SETTERS **

    //Function create and store all 990 pocket card combinations
    public void getAllPocketCardCombinations(ArrayList<Card> remaining45Cards) {
        for (int firstCardIndex = 0; firstCardIndex < 44; firstCardIndex++) {
            for (int secondCardIndex = firstCardIndex + 1; secondCardIndex < 45; secondCardIndex++) {


                Card card1 = new Card(remaining45Cards.get(firstCardIndex));
                Card card2 = new Card(remaining45Cards.get(secondCardIndex));


                ComputerPocketCards cpuPocketCard = new ComputerPocketCards(card1,card2);
                pocketCardCombinations.add(cpuPocketCard);
            }
        }
    }


    //Return a Random Number Between min and max
    public static int rand(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid range");
        }
        double rand = Math.random();
        return (int) (rand * ((max - min) + 1)) + min;
    }

    public ArrayList<ComputerPocketCards> getList() {
        return pocketCardCombinations;
    }


}//End Class

