/**
 * Represents a dealer in a game of blackjack. Extends the `Person` class,
 * inheriting attributes and methods for managing a hand of cards and calculating
 * their value. The dealer has a specific rule to keep drawing cards until 
 * reaching or exceeding a hand value of 17.
 */
public class Dealer extends Person {

    /**
     * Draws cards from the provided deck until the dealer's hand value reaches 
     * or exceeds 17, as per standard blackjack rules.
     * This method adds cards to the dealer's hand and recalculates the hand 
     * value after each draw.
     *
     * @param deck the deck from which cards are drawn
     */
    public void draw(Deck deck) {
        while (handValue < 17) {
            Card next = deck.getNextCard();
            hand.add(next);
            calculateHandValue();
        }
    }
}