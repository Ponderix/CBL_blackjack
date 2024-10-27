import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a standard deck of 52 playing cards. The deck is initialized with 
 * all possible combinations of ranks and suits. It provides methods to shuffle 
 * the deck and draw the next card.
 */
public class Deck {
    public static int LENGTH = 52;

    public ArrayList<Card> list = new ArrayList<>();

    /**
     * Constructs a new deck of cards by creating instances of `Card` for 
     * each combination of rank and suit. The deck will initially contain all 
     * 52 cards.
     */
    public Deck() {
        for (int i = 0; i < Card.SUITE_LIST.length; i++) {
            for (int j = 0; j < Card.RANK_LIST.length; j++) {
                Card temp = new Card(Card.RANK_LIST[j], Card.SUITE_LIST[i]);
                this.list.add(temp);
            }
        }
    }
    
    /**
     * Shuffles the deck of cards using a randomization algorithm. The order of 
     * the cards in the deck is randomized, allowing for a new game to start 
     * with a shuffled deck.
     */
    public void shuffle() {
        int length = list.size();
        Random random = new Random();
        for (int i = 0; i < length - 1; i++) {
            int randomInt = random.nextInt(length - i) + i;
            Collections.swap(list, i, randomInt);
        }
    }

    /**
     * Draws the next card from the top of the deck. If the deck is empty, an 
     * `EmptyDeckException` is thrown. The card is removed from the deck 
     * after being drawn.
     *
     * @return the next card in the deck
     * @throws EmptyDeckException if there are no cards left in the deck
     */
    public Card getNextCard() {
        if (list.size() == 0) {
            throw new EmptyDeckException("The deck is empty. No more cards to draw.");
        }
        Card next = list.get(0);
        list.remove(0);
        return next;
    }
}

/**
 * Exception thrown when an attempt is made to draw a card from an empty deck.
 */
class EmptyDeckException extends RuntimeException {
    public EmptyDeckException(String message) {
        super(message);
    }
}

