package holdEm;
import java.util.ArrayList;

public class CommunityCardSetPreFlop {
    private Card community1;
    private Card community2;
    private Card community3;
    private Card community4;
    private Card community5;
    private ArrayList<Card> remaining45Cards = new ArrayList<>();
    private ArrayList<ComputerPocketCards> pocketCardCombinations = new ArrayList<>();

    //ArrayList of cards must equal 50
    public CommunityCardSetPreFlop(ArrayList<Card> remaining50Cards) {

        //Array of five integers representing the unique indexes of the cards.
        int[] indexArray = new int[5];

        if (remaining50Cards.size() != 50) {
            throw new IllegalStateException("Must have 50 cards remaining");
        }

        //We need five integers representing indexes to gather the cards
        for (int i = 0; i < 5; i++) {
            //Temp Random
            int tempRandom;
            boolean unique = false;
            //First Random
            if (i == 0) {
                indexArray[0] = rand(0, 49);
            }
            //Second Random
            else if (i == 1) {
                do {
                    tempRandom = rand(0, 49);
                    unique = tempRandom != indexArray[0];
                } while (!unique);
                //Set value
                indexArray[1] = tempRandom;
            }
            //Third Random
            else if (i == 2) {
                do {
                    tempRandom = rand(0, 49);
                    for (int idx = 0; idx < 2; idx++) {
                        unique = tempRandom != indexArray[idx];
                    }
                }
                while (!unique);

                //Set
                indexArray[2] = tempRandom;
            }
            //Fourth Random
            else if (i == 3) {
                do {
                    tempRandom = rand(0, 49);

                    for (int idx = 0; idx < 3; idx++) {
                        unique = tempRandom != indexArray[idx];
                    }
                }
                while (!unique);

                //Set
                indexArray[3] = tempRandom;

            }
            //Fifth Random
            else {
                do {
                    tempRandom = rand(0, 49);
                    for (int idx = 0; idx < 4; idx++) {
                        unique = tempRandom != indexArray[idx];
                    }
                }
                while (!unique);

                //Set
                indexArray[4] = tempRandom;
            }
        }
        //Set the community cards
        setCommunity1(remaining50Cards.get(indexArray[0]));
        setCommunity2(remaining50Cards.get(indexArray[1]));
        setCommunity3(remaining50Cards.get(indexArray[2]));
        setCommunity4(remaining50Cards.get(indexArray[3]));
        setCommunity5(remaining50Cards.get(indexArray[4]));

        //Set remaining 45 cards
        remaining45Cards.addAll(remaining50Cards);

        //Remove the five unknown community cards
        remaining45Cards.remove(community1);
        remaining45Cards.remove(community2);
        remaining45Cards.remove(community3);
        remaining45Cards.remove(community4);
        remaining45Cards.remove(community5);

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
    public Card getCommunity1() {
        return community1;
    }

    public void setCommunity1(Card community1) {
        this.community1 = community1;
    }

    public Card getCommunity2() {
        return community2;
    }

    public void setCommunity2(Card community2) {
        this.community2 = community2;
    }

    public Card getCommunity3() {
        return community3;
    }

    public void setCommunity3(Card community3) {
        this.community3 = community3;
    }

    public Card getCommunity4() {
        return community4;
    }

    public void setCommunity4(Card community4) {
        this.community4 = community4;
    }

    public Card getCommunity5() {
        return community5;
    }

    public void setCommunity5(Card community5) {
        this.community5 = community5;
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

