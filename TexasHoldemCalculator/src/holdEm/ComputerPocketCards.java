package holdEm;

public class ComputerPocketCards {

    //Definitions
    private Card pocketCard1;
    private Card pocketCard2;

    public ComputerPocketCards(Card pocketCard1, Card pocketCard2) {
        //Set the two pocket Cards
        this.pocketCard1 = pocketCard1;
        this.pocketCard2 = pocketCard2;
    }

    //Functions
    public Card getPocketCard1() {
        return pocketCard1;
    }
    public Card getPocketCard() {
        return pocketCard1;
    }
    public void setPocketCard1(Card pocketCard1) {
        this.pocketCard1 = pocketCard1;
    }
    public Card getPocketCard2() {
        return pocketCard2;
    }
    public void setPocketCard2(Card pocketCard2) {
        this.pocketCard2 = pocketCard2;
    }
}
