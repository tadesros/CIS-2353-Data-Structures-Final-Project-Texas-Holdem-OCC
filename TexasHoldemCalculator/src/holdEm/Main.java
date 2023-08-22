package holdEm;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.text.DecimalFormat;


//Used this site for inspiration on the format table site
//https://www.logicbig.com/how-to/code-snippets/jcode-java-cmd-command-line-table.html

//For the game playing and poker Holdem classes took inspiration from Erich Charnesky's sample code

public class Main {
    /**DATA MEMBERS**/
    static int playerMoney = 100;
    static int computerMoney = 100;
    static Scanner keyboard = new Scanner(System.in);
    static boolean playerBetsFirst = true;
    static int pot;
    static Random random = new Random();
    static int numberPreFlopCommCards = 600;
    /**MAIN**/
    public static void main(String[] args) {
        //Display Intro
        System.out.println("***********************************");
        System.out.println("**** WELCOME TO TEXAS HOLD-EM ****");
        System.out.println("***********************************");
        System.out.println(" ");
        //Enter loop for the game
        while (playerMoney > 1 && computerMoney > 1) {
            System.out.println("Player has: $" + playerMoney + "           Computer Opponent has $" + computerMoney);
            pot = 3;

            if (playerBetsFirst) {
                System.out.println("Player bets first, $2      Computer bets $1");
                System.out.println("");
                playerMoney -= 2;
                computerMoney--;
            } else {
                System.out.println("Computer bets first, $2");
                System.out.println("Player bets $1");
                computerMoney -= 2;
                playerMoney--;
            }

            //Deal the deck
            Deck deck = new Deck();
            //Deal the players pocket
            HoldemHand player1Hand = new HoldemHand(deck.deal(), deck.deal());

            //---Pre-Flop----
            //Calculate and display Pre-Flop Stats
            calcPreFlopStats(deck, numberPreFlopCommCards, player1Hand);
            System.out.println(" ");

            //Get input before deal
            if (bettingRoundDidSomeoneFold(1)) {
                continue;
            }

            System.out.println("Hit any key to deal the FLOP.");
            keyboard.nextLine();

            //  **  Flop  **
            //Deal the flop 3 - cards to the community cards
            player1Hand.addSharedCard(deck.deal());
            player1Hand.addSharedCard(deck.deal());
            player1Hand.addSharedCard((deck.deal()));
            //Calculate and display Flop Stats
            calcFlopStats(deck, numberPreFlopCommCards, player1Hand);

            if (playerBetsFirst) {
                String choice = promptForCallRaiseOrFold();
                if (choice.equalsIgnoreCase("fold")) {
                    System.out.println("You fold!");
                    computerMoney += pot;
                    continue; // ends the round, starts the loop again
                } else if (choice.equalsIgnoreCase("call")) {
                    System.out.println("You call!");
                    System.out.println("Computer calls!");
                    System.out.println("");
                    System.out.println("Hit any key to deal the Turn.");
                    keyboard.nextLine();
                    //Ask to deal the flop
                } else {
                    int raiseAmount = getRaiseAmount();
                    if (bettingRoundDidSomeoneFold(raiseAmount)) {
                        continue;
                    }
                }
            } else {
                if (bettingRoundDidSomeoneFold(0)) {
                    continue;
                }
            }

            // ** Turn **
            //Deal 1 card for the turn
            player1Hand.addSharedCard(deck.deal());
            //Calculate turn statistics
            calcTurnStats(deck, player1Hand);

            if (playerBetsFirst) {
                String choice = promptForCallRaiseOrFold();
                if (choice.equalsIgnoreCase("fold")) {
                    System.out.println("You fold!");
                    computerMoney += pot;
                    continue; // ends the round, starts the loop again
                } else if (choice.equalsIgnoreCase("call")) {
                    System.out.println("You call!");
                    System.out.println("Computer calls!");
                    System.out.println("Hit any key to deal the River.");
                    keyboard.nextLine();
                } else {
                    int raiseAmount = getRaiseAmount();
                    if (bettingRoundDidSomeoneFold(raiseAmount)) {
                        continue;
                    }
                }
            } else {
                if (bettingRoundDidSomeoneFold(0)) {
                    continue;
                }
            }

            //--River--
            //Deal one more card for the river
            player1Hand.addSharedCard(deck.deal());
            //Calculate River statistics
            calcRiverStats(deck, player1Hand);
            //Deal the players pocket
            HoldemHand computerHand = new HoldemHand(deck.deal(), deck.deal());
            ArrayList<Card> playerCommunityCards = player1Hand.getSharedCards();
            //Add Community Cards
            computerHand.addSharedCard(playerCommunityCards.get(0));
            computerHand.addSharedCard(playerCommunityCards.get(1));
            computerHand.addSharedCard(playerCommunityCards.get(2));
            computerHand.addSharedCard(playerCommunityCards.get(3));
            computerHand.addSharedCard(playerCommunityCards.get(4));


            System.out.println("Players Hand:   " + player1Hand.toString());
            System.out.println("Computers Hand: " + computerHand.toString());

            System.out.println("Players Best Hand:   " + player1Hand.getBestPossibleHand());
            System.out.println("Computers Best Hand: " + computerHand.getBestPossibleHand());

            int result = player1Hand.getBestPossibleHand().compareTo(computerHand.getBestPossibleHand());
            if (result > 0) {
                System.out.println("YOU WIN!");
                playerMoney += pot;
            } else if (result == 0) {
                System.out.println("Draw!");
                playerMoney += pot / 2;
                computerMoney += pot / 2;
            } else {
                System.out.println("Computer wins!");
                computerMoney += pot;
            }

            playerBetsFirst = !playerBetsFirst;

            System.out.println("Hit any key to start the next round.");
            keyboard.nextLine();

            System.out.println("");

        }//End of game loop


    } /* END MAIN */


    // ####  FUNCTIONS ####
    /**
     *Display Pre-Flop Hand
     */
    public static void displayPreFlopHand(HoldemHand player1Hand)
    {
        System.out.println("Your pocket cards are: " + player1Hand.toString());
        System.out.println("Their are: 2,118,760 ways to pick 5 community cards from the 50 remaining unknown cards and 990 ways to pick 2 computer player pocket cards from the remaining 45.");
        System.out.println("Total possible hand combinations: 2,097,572,400" + " we are testing " + 990*numberPreFlopCommCards+ " hands.");

      //  System.out.println("We are testing " + numberPreFlopCommCards + " combinations of community cards against the 990 computer pocket combinations for a total of: " + 990*numberPreFlopCommCards+ " hands.");

    }
    /**
     *Display Flop Hand
     */
    public static void displayFlopHand(HoldemHand player1Hand)
    {
        System.out.println("Your pocket cards are:    " + player1Hand.toString());
        System.out.println("Flop (community cards):   " + player1Hand.getSharedCards());
        System.out.println("Their are: 1061 ways to pick 2 community cards from the 47 remaining unknown cards and 990 ways to pick 2 opponent player pocket cards from the remaining 45 unknown.");
        System.out.println("Total hand combinations: 1,050,390");
        System.out.println("We are testing " + numberPreFlopCommCards + " combinations of community cards against the 990 opponent pocket combinations for a total of: " + 990*numberPreFlopCommCards+ " hands.");

    }
    /**
     *Display Turn Hand
     */
    public static void displayHandAtTurn(HoldemHand player1Hand)
    {
        System.out.println("Your pocket cards are:    " + player1Hand.toString());
        System.out.println("Turn (community cards):   " + player1Hand.getSharedCards());
        System.out.println("Their are: 46 ways to pick 1 community cards from 46 unknown cards and 990 ways to pick 2 opponent player pocket cards from the remaining 45.");
        System.out.println("Total hand combinations: 45,540");
        System.out.println("We are testing all 45,540 hands.");
    }
    /*Display the River Hand*/
    public static void displayHandAtRiver(HoldemHand player1Hand)
    {
        System.out.println("Your pocket cards are:    " + player1Hand.toString());
        System.out.println("Turn (community cards):   " + player1Hand.getSharedCards());
        System.out.println("Their are: 990 possible ways to pick 2 opponent player cards from the 45 unknown cards.");
        System.out.println("We are testing all 990 combinations.");
    }
    /*
          FUNCTION: calcPreFlopStats
          Send fiftyCards left Card Array
          Send integer number of community card combinations
        */
    public static void calcPreFlopStats(Deck deck,int numberOfCommunityCombinations, HoldemHand playerHand)
    {
        ArrayList<Card> fiftyCardsLeft = new ArrayList<>();
        //Display Stage
        System.out.println("------ PRE-FLOP ------");
        //Display Current hand and pre-flop information
        displayPreFlopHand(playerHand);
        //Make a copy of fifty cards left after dealing player pocket
        for (int i = 0; i < deck.numberOfCardsLeftInTheDeck(); i++) {
            fiftyCardsLeft.add(deck.accessCardAtIndex(i));
        }

        //Number of sample hands
        int numSampleHands = 0;
        int numPlayerWins = 0;
        int numComputerWins = 0;
        int numOfTies = 0;


        //Make an arraylist of CommunityCardSetPreFlop
        ArrayList<CommunityCardSetPreFlop> communityCardArray = new ArrayList<>();

        //This creates an array of Pre-Flop community card objects along with all 990 combinations
        for(int i = 0; i < numberOfCommunityCombinations; i++)
        {
            CommunityCardSetPreFlop newSet = new CommunityCardSetPreFlop(fiftyCardsLeft);
            communityCardArray.add(newSet);
        }

        //Outer Loop go through  each combination
        for(int indexComb = 0; indexComb < numberOfCommunityCombinations; indexComb++)
        {
            //Get new CommunityCardSetPreFlop
            CommunityCardSetPreFlop newSet = communityCardArray.get(indexComb);
            //Get the list of all pocket card combinations
            ArrayList<ComputerPocketCards> pocketCombinations = newSet.getList();
            //Create Players Hold-em Hand
            HoldemHand playerHoldemHand = new HoldemHand(playerHand.getCard1(),playerHand.getCard2());
            //Add Community Cards
            playerHoldemHand.addSharedCard(newSet.getCommunity1());
            playerHoldemHand.addSharedCard(newSet.getCommunity2());
            playerHoldemHand.addSharedCard(newSet.getCommunity3());
            playerHoldemHand.addSharedCard(newSet.getCommunity4());
            playerHoldemHand.addSharedCard(newSet.getCommunity5());

            //Go through every pocket combination
            for(int indexPocket = 0; indexPocket < pocketCombinations.size(); indexPocket++)
            {
                //Get next Pocket Cards
                ComputerPocketCards thisPocketCard = pocketCombinations.get(indexPocket);

                //Create Computer Hold em Hand
                HoldemHand computerHand = new HoldemHand(thisPocketCard.getPocketCard1(),thisPocketCard.getPocketCard2());
                //Add Community Cards
                computerHand.addSharedCard(newSet.getCommunity1());
                computerHand.addSharedCard(newSet.getCommunity2());
                computerHand.addSharedCard(newSet.getCommunity3());
                computerHand.addSharedCard(newSet.getCommunity4());
                computerHand.addSharedCard(newSet.getCommunity5());

                int result = playerHoldemHand.getBestPossibleHand().compareTo(computerHand.getBestPossibleHand());


                if (result > 0) {
                    // System.out.println("Player wins!");
                    numPlayerWins++;
                } else if (result == 0) {
                    // System.out.println("Draw!");
                    numOfTies++;
                } else {
                    // System.out.println("Computer wins!");
                    numComputerWins++;
                }
                numSampleHands++;
            }
        }


        double winningOdds = (double) numPlayerWins/numSampleHands;
        double oddsToTie = (double) numOfTies/numSampleHands;
        double oddsToLose = (double) numComputerWins/numSampleHands;

        DecimalFormat df = new DecimalFormat("##.##%");

        String strNumPlayerWins = NumberFormat.getInstance().format(numPlayerWins);
        String strNumComputerWins = NumberFormat.getInstance().format(numComputerWins);
        String strNumTies = NumberFormat.getInstance().format(numOfTies);
        String strNumSampleHands = NumberFormat.getInstance().format(numSampleHands);

        System.out.printf("-----------------------------------------------------%n");
        System.out.printf("| %10s | %10s | %10s | %10s |%n", "WIN","LOSS", "TIE", "TOTAL HANDS");
        System.out.printf("-----------------------------------------------------%n");
        System.out.printf("| %10s | %10s | %10s | %10s |%n", strNumPlayerWins, strNumComputerWins, strNumTies,  strNumSampleHands);
        System.out.printf("| %10s | %10s | %10s |%n", df.format(winningOdds), df.format(oddsToLose), df.format(oddsToTie));
        System.out.printf("-----------------------------------------------------%n");

    //    System.out.println("Win# "+numPlayerWins + " ; Tie# " + numOfTies + " ; Lose# " + numComputerWins +" = "+ numSampleHands +" Simulations");
    //    System.out.println("Win:"+ df.format(winningOdds) + " ; Tie: " + df.format(oddsToTie) + " ; Lose: " + df.format(oddsToLose));

    }
    /*
        FUNCTION: calcFlopStats
        Send fortySeven left Card Array
        Send integer number of community card combinations
      */
    public static void calcFlopStats(Deck deck,int numberOfCommunityCombinations, HoldemHand playerHand)
    {

        ArrayList<Card> fortySevenCardsLeft = new ArrayList<>();

        System.out.println("");
        System.out.println("------ FLOP ------    ");

        //Display current Flop hand
        displayFlopHand(playerHand);

        //Make a copy of 47 cards left after dealing flop
        for (int i = 0; i < deck.numberOfCardsLeftInTheDeck(); i++) {
            fortySevenCardsLeft.add(deck.accessCardAtIndex(i));
        }

        //Variables for Statistics
        int numSampleHands = 0;
        int numPlayerWins = 0;
        int numComputerWins = 0;
        int numOfTies = 0;

        //Make an arraylist of CommunityCardSetFlop
        ArrayList<CommunityCardSetFlop> communityCardArray = new ArrayList<>();

        //This creates an array of Pre-Flop community cards along with all 990 combinations
        for(int i = 0; i < numberOfCommunityCombinations; i++)
        {
            CommunityCardSetFlop newSet = new CommunityCardSetFlop(fortySevenCardsLeft);
            communityCardArray.add(newSet);
        }

        //Outer Loop go through  each combination
        for(int indexComb = 0; indexComb < numberOfCommunityCombinations; indexComb++)
        {
            //Get new CommunityCardSetFlop
            CommunityCardSetFlop newSet = communityCardArray.get(indexComb);
            //Get the list of all pocket card combinations
            ArrayList<ComputerPocketCards> pocketCombinations = newSet.getList();
            //Create Players Hold-em Hand
            HoldemHand playerHoldemHand = new HoldemHand(playerHand.getCard1(),playerHand.getCard2());

            //Shared Cards
            ArrayList<Card> playerCommunityCards = playerHand.getSharedCards();


            //Add Community Cards
            playerHoldemHand.addSharedCard(playerCommunityCards.get(0));
            playerHoldemHand.addSharedCard(playerCommunityCards.get(1));
            playerHoldemHand.addSharedCard(playerCommunityCards.get(2));
            //Get the randomly generated cards
            playerHoldemHand.addSharedCard(newSet.getUnknownCommunityCard1());
            playerHoldemHand.addSharedCard(newSet.getUnknownCommunityCard2());

            //Go through every pocket combination
            for(int indexPocket = 0; indexPocket < pocketCombinations.size(); indexPocket++)
            {
                //Get next Pocket Cards
                ComputerPocketCards thisPocketCard = pocketCombinations.get(indexPocket);

                //Create Computer Hold em Hand
                HoldemHand computerHand = new HoldemHand(thisPocketCard.getPocketCard1(),thisPocketCard.getPocketCard2());
                //Add Community Cards
                computerHand.addSharedCard(playerCommunityCards.get(0));
                computerHand.addSharedCard(playerCommunityCards.get(1));
                computerHand.addSharedCard(playerCommunityCards.get(2));
                //Add the randomly generated cards
                computerHand.addSharedCard(newSet.getUnknownCommunityCard1());
                computerHand.addSharedCard(newSet.getUnknownCommunityCard2());

                int result = playerHoldemHand.getBestPossibleHand().compareTo(computerHand.getBestPossibleHand());


                // System.out.println("Players Best Hand: " + playerHoldemHand.getBestPossibleHand());
                // System.out.println("Computers Best Hand: " + computerHand.getBestPossibleHand());
                // System.out.println(result);


                if (result > 0) {
                    // System.out.println("Player wins!");
                    numPlayerWins++;
                } else if (result == 0) {
                    // System.out.println("Draw!");
                    numOfTies++;
                } else {
                    // System.out.println("Computer wins!");
                    numComputerWins++;
                }
                numSampleHands++;
            }
        }
        double winningOdds = (double) numPlayerWins/numSampleHands;
        double oddsToTie = (double) numOfTies/numSampleHands;
        double oddsToLose = (double) numComputerWins/numSampleHands;

        DecimalFormat df = new DecimalFormat("##.##%");




        String strNumPlayerWins = NumberFormat.getInstance().format(numPlayerWins);
        String strNumComputerWins = NumberFormat.getInstance().format(numComputerWins);
        String strNumTies = NumberFormat.getInstance().format(numOfTies);
        String strNumSampleHands = NumberFormat.getInstance().format(numSampleHands);

        System.out.printf("-----------------------------------------------------%n");
        System.out.printf("| %10s | %10s | %10s | %10s |%n", "WIN","LOSS", "TIE", "TOTAL HANDS");
        System.out.printf("-----------------------------------------------------%n");
        System.out.printf("| %10s | %10s | %10s | %10s |%n", strNumPlayerWins, strNumComputerWins, strNumTies,  strNumSampleHands);
        System.out.printf("| %10s | %10s | %10s |%n", df.format(winningOdds), df.format(oddsToLose), df.format(oddsToTie));
        System.out.printf("-----------------------------------------------------%n");

    //    System.out.println("Win# "+numPlayerWins + " ; Tie# " + numOfTies + " ; Lose# " + numComputerWins +" = "+ numSampleHands +" Simulations");
    //    System.out.println("Win:"+ df.format(winningOdds) + " ; Tie: " + df.format(oddsToTie) + " ; Lose: " + df.format(oddsToLose));
    }
    /* */
    public static void calcTurnStats(Deck deck,HoldemHand playerHand)
    {
        //These arraylists contain the remaining unknown cards.
        ArrayList<Card> fortySixCardsLeft = new ArrayList<>();
        System.out.println("");
        System.out.println("------ TURN ------    ");

        //Display the current hand at the Turn
        displayHandAtTurn(playerHand);

        //Make a copy of 46 cards left
        for (int i = 0; i < deck.numberOfCardsLeftInTheDeck(); i++) {
            fortySixCardsLeft.add(deck.accessCardAtIndex(i));
        }

        //Variables for Statistics
        int numSampleHands = 0;
        int numPlayerWins = 0;
        int numComputerWins = 0;
        int numOfTies = 0;

        //Make an arraylist of CommunityCardSetFlop
        ArrayList<CommunityCardSetTurn> communityCardArray = new ArrayList<>();

        //This creates an array of Turn community cards along with all 990 combinations
        for(int i = 0; i < fortySixCardsLeft.size(); i++)
        {
            CommunityCardSetTurn newSet = new CommunityCardSetTurn(fortySixCardsLeft,fortySixCardsLeft.get(i));
            communityCardArray.add(newSet);
        }

        //Outer Loop go through  each combination
        for(int indexComb = 0; indexComb < fortySixCardsLeft.size(); indexComb++)
        {
            //Get new CommunityCardSetFlop
            CommunityCardSetTurn newSet = communityCardArray.get(indexComb);
            //Get the list of all pocket card combinations
            ArrayList<ComputerPocketCards> pocketCombinations = newSet.getList();
            //Create Players Hold-em Hand
            HoldemHand playerHoldemHand = new HoldemHand(playerHand.getCard1(),playerHand.getCard2());

            //Shared Cards
            ArrayList<Card> playerCommunityCards = playerHand.getSharedCards();

            //Add Community Cards
            playerHoldemHand.addSharedCard(playerCommunityCards.get(0));
            playerHoldemHand.addSharedCard(playerCommunityCards.get(1));
            playerHoldemHand.addSharedCard(playerCommunityCards.get(2));
            playerHoldemHand.addSharedCard(playerCommunityCards.get(3));
            //Get the randomly generated cards
            playerHoldemHand.addSharedCard(newSet.getUnknownCommunityCard1());

            //Go through every pocket combination
            for(int indexPocket = 0; indexPocket < pocketCombinations.size(); indexPocket++)
            {
                //Get next Pocket Cards
                ComputerPocketCards thisPocketCard = pocketCombinations.get(indexPocket);

                //Create Computer Hold em Hand
                HoldemHand computerHand = new HoldemHand(thisPocketCard.getPocketCard1(),thisPocketCard.getPocketCard2());
                //Add Community Cards
                computerHand.addSharedCard(playerCommunityCards.get(0));
                computerHand.addSharedCard(playerCommunityCards.get(1));
                computerHand.addSharedCard(playerCommunityCards.get(2));
                computerHand.addSharedCard(playerCommunityCards.get(3));
                //Add the randomly generated cards
                computerHand.addSharedCard(newSet.getUnknownCommunityCard1());


                int result = playerHoldemHand.getBestPossibleHand().compareTo(computerHand.getBestPossibleHand());

                if (result > 0) {
                    // System.out.println("Player wins!");
                    numPlayerWins++;
                } else if (result == 0) {
                    // System.out.println("Draw!");
                    numOfTies++;
                } else {
                    // System.out.println("Computer wins!");
                    numComputerWins++;
                }
                numSampleHands++;
            }
        }

        double winningOdds = (double) numPlayerWins/numSampleHands;
        double oddsToTie = (double) numOfTies/numSampleHands;
        double oddsToLose = (double) numComputerWins/numSampleHands;

        DecimalFormat df = new DecimalFormat("##.##%");



        String strNumPlayerWins = NumberFormat.getInstance().format(numPlayerWins);
        String strNumComputerWins = NumberFormat.getInstance().format(numComputerWins);
        String strNumTies = NumberFormat.getInstance().format(numOfTies);
        String strNumSampleHands = NumberFormat.getInstance().format(numSampleHands);

        System.out.printf("-----------------------------------------------------%n");
        System.out.printf("| %10s | %10s | %10s | %10s |%n", "WIN","LOSS", "TIE", "TOTAL HANDS");
        System.out.printf("-----------------------------------------------------%n");
        System.out.printf("| %10s | %10s | %10s | %10s |%n", strNumPlayerWins, strNumComputerWins, strNumTies,  strNumSampleHands);
        System.out.printf("| %10s | %10s | %10s |%n", df.format(winningOdds), df.format(oddsToLose), df.format(oddsToTie));
        System.out.printf("-----------------------------------------------------%n");

     //   System.out.println("Win# "+numPlayerWins + " ; Tie# " + numOfTies + " ; Lose# " + numComputerWins +" = "+ numSampleHands +" Simulations");
     //   System.out.println("Win:"+ df.format(winningOdds) + " ; Tie: " + df.format(oddsToTie) + " ; Lose: " + df.format(oddsToLose));
    }
    /* */
    public static void calcRiverStats(Deck deck,HoldemHand playerHand)
    {
        //Make an arraylist of CommunityCardSetFlop
        ArrayList<PossibleHandAtRiver> riverCardOptions;
        System.out.println("");
        System.out.println("------ RIVER ------");

        //Display the current hand at the Turn
        displayHandAtRiver(playerHand);

        //Variables for Statistics
        int numSampleHands = 0;
        int numPlayerWins = 0;
        int numComputerWins = 0;
        int numOfTies = 0;

        //These arraylists contain the remaining unknown cards.
        ArrayList<Card> fortyFiveCardsLeft = new ArrayList<>();

        //Make a copy of 45 cards left
        for (int i = 0; i < deck.numberOfCardsLeftInTheDeck(); i++) {
            fortyFiveCardsLeft.add(deck.accessCardAtIndex(i));
        }

        //Create a PossibleHandAtRiver that has all possible hands
        PossibleHandAtRiver riverHandObj = new PossibleHandAtRiver(fortyFiveCardsLeft);
        //Player hand is the same for all iterations.
        //Create Players Hold-em Hand
        HoldemHand playerHoldemHand = new HoldemHand(playerHand.getCard1(),playerHand.getCard2());
        //Shared Cards
        ArrayList<Card> playerCommunityCards = playerHand.getSharedCards();
        //Add Community Cards
        playerHoldemHand.addSharedCard(playerCommunityCards.get(0));
        playerHoldemHand.addSharedCard(playerCommunityCards.get(1));
        playerHoldemHand.addSharedCard(playerCommunityCards.get(2));
        playerHoldemHand.addSharedCard(playerCommunityCards.get(3));
        playerHoldemHand.addSharedCard(playerCommunityCards.get(4));

        //Get the list of all pocket card combinations
        ArrayList<ComputerPocketCards> pocketCombinations = riverHandObj.getList();

        //Go through every pocket combination
        for(int indexPocket = 0; indexPocket < pocketCombinations.size(); indexPocket++)
        {
                //Get next Pocket Cards
                ComputerPocketCards thisPocketCard = pocketCombinations.get(indexPocket);
                //Create Computer Hold em Hand
                HoldemHand computerHand = new HoldemHand(thisPocketCard.getPocketCard1(),thisPocketCard.getPocketCard2());
                //Add Community Cards
                computerHand.addSharedCard(playerCommunityCards.get(0));
                computerHand.addSharedCard(playerCommunityCards.get(1));
                computerHand.addSharedCard(playerCommunityCards.get(2));
                computerHand.addSharedCard(playerCommunityCards.get(3));
                computerHand.addSharedCard(playerCommunityCards.get(4));

                int result = playerHoldemHand.getBestPossibleHand().compareTo(computerHand.getBestPossibleHand());

                if (result > 0) {
                    // System.out.println("Player wins!");
                    numPlayerWins++;
                } else if (result == 0) {
                    // System.out.println("Draw!");
                    numOfTies++;
                } else {
                    // System.out.println("Computer wins!");
                    numComputerWins++;
                }
                numSampleHands++;
            }

        double winningOdds = (double) numPlayerWins/numSampleHands;
        double oddsToTie = (double) numOfTies/numSampleHands;
        double oddsToLose = (double) numComputerWins/numSampleHands;

        DecimalFormat df = new DecimalFormat("##.##%");

        String strNumPlayerWins = NumberFormat.getInstance().format(numPlayerWins);
        String strNumComputerWins = NumberFormat.getInstance().format(numComputerWins);
        String strNumTies = NumberFormat.getInstance().format(numOfTies);
        String strNumSampleHands = NumberFormat.getInstance().format(numSampleHands);

        System.out.printf("-----------------------------------------------------%n");
        System.out.printf("| %10s | %10s | %10s | %10s |%n", "WIN","LOSS", "TIE", "TOTAL HANDS");
        System.out.printf("-----------------------------------------------------%n");
        System.out.printf("| %10s | %10s | %10s | %10s |%n", strNumPlayerWins, strNumComputerWins, strNumTies,  strNumSampleHands);
        System.out.printf("| %10s | %10s | %10s |%n", df.format(winningOdds), df.format(oddsToLose), df.format(oddsToTie));
        System.out.printf("-----------------------------------------------------%n");
      //  System.out.println("Win# "+numPlayerWins + " ; Tie# " + numOfTies + " ; Lose# " + numComputerWins +" = "+ numSampleHands +" Simulations");
      //  System.out.println("Win:"+ df.format(winningOdds) + " ; Tie: " + df.format(oddsToTie) + " ; Lose: " + df.format(oddsToLose));
        System.out.println("Hit any key to view the winner.");
        keyboard.nextLine();
    }
    /** FUNCTIONS FOR POKER GAME**/
    private static boolean bettingRoundDidSomeoneFold(int raiseAmount) {

        if (playerBetsFirst) {

                System.out.println("Computer calls");
                computerMoney -= raiseAmount;
                pot += raiseAmount;

        } else {
            String choice = promptForCallRaiseOrFold();
            if (choice.equalsIgnoreCase("fold")) {
                System.out.println("You fold!");
                computerMoney += pot;
                return true; // ends the round, starts the loop again
            } else if (choice.equalsIgnoreCase("call")) {
                pot++;
                playerMoney -= raiseAmount;
                System.out.println("You call!");
            }
            else {
                pot += raiseAmount;
                playerMoney -= raiseAmount;
                raiseAmount = getRaiseAmount();
                pot += raiseAmount;
                playerMoney -= raiseAmount;


                System.out.println("Computer calls");
                computerMoney -= raiseAmount;
                pot += raiseAmount;


         /*
                if (random.nextInt(2) == 0) {
                    System.out.println("Computer calls");
                    computerMoney -= raiseAmount;
                    pot += raiseAmount;
                } else {
                    System.out.println("Computer folds!");
                    playerMoney += pot;
                    return true; // ends the round, starts the loop again
                }
            */

            }
        }
        return false;
    }
    private static int getRaiseAmount() {

        System.out.println("How much do you want to raise?");
        int raise = Integer.parseInt(keyboard.nextLine());

        while (raise > computerMoney || raise > playerMoney) {
            System.out.println("You can't raise more than $" + Math.min(computerMoney, playerMoney));
            System.out.println("How much do you want to raise?");
            raise = Integer.parseInt(keyboard.nextLine());
        }

        return raise;
    }
    private static String promptForCallRaiseOrFold() {
        String choice = "";

        while (!choice.equalsIgnoreCase("call")
                && !choice.equalsIgnoreCase("raise")
                && !choice.equalsIgnoreCase("fold")) {
            System.out.println("Do you want to call, raise or fold?");
            choice = keyboard.nextLine();
        }
        return choice;

    }
}

