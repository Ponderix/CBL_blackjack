import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;

public class Deck {
    public static int LENGTH = 52; // use only when looping over 52 cards

    public ArrayList<Card> list = new ArrayList<>();

    // buildDeck() 
    // Creates a deck of 52 cards with 4 suits and 13 values each. 
    // Adds cards to a list. 
    public Deck() {
        for (int i = 0; i < Card.SUITE_LIST.length; i++) {
            for (int j = 0; j < Card.RANK_LIST.length; j++) {
                Card temp = new Card(Card.RANK_LIST[j], Card.SUITE_LIST[i]);
                this.list.add(temp);
            }
        }
    }
    

    // shuffle() 
    // Shuffles the current deck randomly. Uses simple swapping for shuffling. 
    public void shuffle() {
        int length = list.size();
        Random random = new Random();
        for (int i = 0; i < length - 1; i++) {
            int randomInt = random.nextInt(length  - i) + i;

            // swap lowermost card with random card
            Collections.swap(list, i, randomInt);
        }
    }

    // removes the first card object from the deck
    // the deck length reduces by 1
    public Card getNextCard() {
        if (list.size() == 0) {
            throw new EmptyDeckException("The deck is empty. No more cards to draw.");
        }
        Card next = list.get(0);
        list.remove(0);
        return next;
    }
}

class EmptyDeckException extends RuntimeException {
    public EmptyDeckException(String message) {
        super(message);
    }
}
