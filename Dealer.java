// Class: Dealer (extends Person) 
// Handles dealer-specific rules like drawing until 17+ and revealing hidden cards. 
public class Dealer extends Person {
    public void draw(Deck deck) {
        if (!currentTurn) {
            return;
        }

        while (handValue < 17) {
            Card next = deck.getNextCard();
            hand.add(next);
            calculateHandValue();
            System.out.println("added card: " + next.name());
        }
    }

    // DEBUG FUNCTION
    public static void main(String[] args) {
        Dealer joe = new Dealer();
        Deck deck = new Deck();
        deck.shuffle();

        joe.dealHand(deck);
        System.out.println(joe.handValue);
        joe.currentTurn = true;
        joe.draw(deck);
        System.out.println(joe.handValue);
    }
}
