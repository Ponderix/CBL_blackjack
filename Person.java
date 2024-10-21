import java.util.ArrayList;
import java.util.Arrays;

public class Person {
    protected int handValue = -1;
    protected ArrayList<Card> hand = new ArrayList<>();

    // Class: Person 
    // Base class for both players and dealer. 
    // Stores hand, hand value, and some basic methods for managing hands. 

    public void calculateHandValue() {
        int value = 0;
        ArrayList<Card> aces = new ArrayList<>();

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).rank.equals("A")) {
                aces.add(hand.get(i));
            }
        }

        for (Card card : hand) {
            value += card.value;
        }

        if (aces.size() > 0 && value > 21) {
            for (Card ace : aces) {
                ace.switchAceValue();
            }
            calculateHandValue();
        } else {
            handValue = value;
        }
    }

    public void dealHand(Deck deck) {
        hand.add(deck.getNextCard());
        hand.add(deck.getNextCard());

        calculateHandValue();
    }

    // DEBUG FUNCTION
    public static void main(String[] args) {
        ArrayList<Card> list = new ArrayList<>();
        list.add(new Card("2", "H"));
        list.add(new Card("3", "d"));

        Person joe = new Person();
        Deck deck = new Deck();
        deck.shuffle();

        joe.dealHand(deck);
        System.out.println(joe.handValue);
    }
}
