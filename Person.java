import java.util.ArrayList;
import java.util.Arrays;

public class Person {
    protected int handValue = 0;
    protected ArrayList<Card> hand = new ArrayList<>();

    // Class: Person 
    // Base class for both players and dealer. 
    // Stores hand, hand value, and some basic methods for managing hands. 

    public void calculateHandValue() {
        int value = 0;
        ArrayList<Card> aces = new ArrayList<>();

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).rank.equals("a")) {
                aces.add(hand.get(i));
            }
        }

        for (Card card : hand) {
            value += card.value;
        }

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

    public void dealHand(Deck deck) {
        hand.add(deck.getNextCard());
        hand.add(deck.getNextCard());

        calculateHandValue();
    }

    public void returnCard(Deck deck) {
        for (Card card : hand) {
            deck.list.add(card);
        }
        hand.clear();
    }
}
