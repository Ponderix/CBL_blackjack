import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a generic person in the Blackjack game, 
 * such as a player or a dealer. Holds a hand of cards 
 * and provides methods to calculate the hand's value, 
 * deal cards, and return cards to the deck.
 */
public class Person {
    protected int handValue = 0; // The total value of the person's hand
    protected ArrayList<Card> hand = new ArrayList<>(); // The cards in the person's hand

    /**
     * Calculates the total value of the hand based on Blackjack rules. 
     * Aces are initially valued at 11 and adjusted down to 1 as necessary 
     * to avoid busting (going over 21).
     */
    public void calculateHandValue() {
        int value = 0;
        ArrayList<Card> aces = new ArrayList<>();

        // Identify all aces in the hand
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).rank.equals("a")) {
                aces.add(hand.get(i));
            }
        }

        // Calculate the total value of the hand
        for (Card card : hand) {
            value += card.value;
        }

        // Reduce the value of aces if the total value is larger than 21
        if (!aces.isEmpty() && value > 21) {
            for (Card ace : aces) {
                System.out.println("ace val b4: " + ace.value);
                if (ace.value != 11) {
                    System.out.println("ace after: " + ace.value);
                    break;
                }
                ace.switchAceValue();
                value -= 10;
                if (value <= 21) {
                    System.out.println("ace after: " + ace.value);
                    break;
                }
            }
        }
        handValue = value;
    }

    /**
     * Deals two cards to the person from the deck and calculates 
     * the initial hand value.
     * 
     * @param deck the deck from which cards are drawn
     */
    public void dealHand(Deck deck) {
        hand.add(deck.getNextCard());
        hand.add(deck.getNextCard());

        calculateHandValue();
    }

    /**
     * Returns all cards in the person's hand back to the deck 
     * and clears the person's hand.
     * 
     * @param deck the deck to which the cards are returned
     */
    public void returnCard(Deck deck) {
        for (Card card : hand) {
            deck.list.add(card);
        }
        hand.clear();
    }
}
