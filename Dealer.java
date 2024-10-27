// Class: Dealer (extends Person) 
// Handles dealer-specific rules like drawing until 17+ and revealing hidden cards. 
public class Dealer extends Person {
    public void draw(Deck deck) {
        while (handValue < 17) {
            Card next = deck.getNextCard();
            hand.add(next);
            calculateHandValue();
        }
    }
}
