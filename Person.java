import java.util.ArrayList;

public abstract class Person {
    protected ArrayList<Card> hand = new ArrayList<>(); // List to store the person's hand (cards)
    protected int handValue = 0;  // The total value of the cards in the person's hand
    protected float money;  // The person's money (used for betting, if needed)
    protected boolean currentTurn = false;  // Whether it's this person's turn in the game

    // Method to calculate the value of the person's hand
    public void calculateHandValue() {
        int aceCount = 0;
        handValue = 0;

        // Loop through each card in the hand and add its value to the handValue
        for (Card card : hand) {
            handValue += card.getValue();

            // Count the number of aces in the hand
            if (card.value.equals("A")) {
                aceCount++;
            }
        }

        // If the hand value exceeds 21 and there are aces, treat some aces as 1 instead of 11
        while (handValue > 21 && aceCount > 0) {
            handValue -= 10;  // Adjust hand value by subtracting 10 for each ace
            aceCount--;
        }
    }

    // Method to add a card to the person's hand
    public void addCardToHand(Card card) {
        hand.add(card);
        calculateHandValue();  // Recalculate hand value after adding a card
    }

    // Method to clear the person's hand (useful for starting a new round)
    public void clearHand() {
        hand.clear();
        handValue = 0;
    }

    // Getter method to return the current hand value
    public int getHandValue() {
        return handValue;
    }

    // Getter method to return the person's hand (useful for displaying cards)
    public ArrayList<Card> getHand() {
        return hand;
    }

    // Method to set the person's current turn status
    public void setCurrentTurn(boolean turn) {
        this.currentTurn = turn;
    }

    // Method to check if it's currently the person's turn
    public boolean isCurrentTurn() {
        return currentTurn;
    }
}
