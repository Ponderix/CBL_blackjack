import java.util.ArrayList;
import java.util.Random;

public class Deck {
    public ArrayList<Card> cards = new ArrayList<>();  // List to store all cards in the deck

    // Constructor to build a deck of cards without Kings, Queens, or Jacks
    public Deck() {
        String[] VALUES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] SUITS = {"C", "D", "H", "S"};  // C = Clubs, D = Diamonds, H = Hearts, S = Spades

        // Create cards with Aces and numeric values, but no face cards (J, Q, K)
        for (String value : VALUES) {
            for (String suit : SUITS) {
                cards.add(new Card(value, suit));
            }
        }
    }

    // Method to shuffle the deck randomly
    public void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int j = rand.nextInt(cards.size());  // Generate random index
            Card temp = cards.get(i);  // Swap cards[i] with a random card in the deck
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    // Method to deal the next card from the top of the deck
    public Card getNextCard() {
        return cards.remove(0);  // Remove and return the first card in the deck
    }
}
