import java.util.Arrays;

public class Deck {
    public static int LENGTH = 52;

// buildDeck() 
// Creates a deck of 52 cards with 4 suits and 13 values each. 
// Adds cards to a list. 

// shuffleDeck() 
// Shuffles the deck randomly. Uses simple swapping for shuffling. 

    public static void main(String[] args) {
        Card foo = new Card("A", "H");
        System.out.println(foo.generateName());
        System.out.println(foo.value);
        foo.switchAceValue();
        System.out.println(foo.value);
        foo.switchAceValue();
        System.out.println(foo.value);
    }
}
