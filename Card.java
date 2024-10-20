// Class: Card 
// Represents a card with value, type, and image. 
// Has methods to get value, check if it’s an Ace, and get image path. 

public class Card {

    public String rank;
    public String suite;
    public int value;

    public static String[] RANK_LIST = {"2", "3", "4", "5", "6", "7", 
                                        "8", "9", "10", "J", "Q", "K", 
                                        "A"};
    public static String[] SUITE_LIST = {"H", "C", "S", "D"};

    public Card(String r, String s) {
        for (String str : RANK_LIST) {
            if (str.equals(r)) {
                this.rank = r;
                break;
            }
        }

        for (String str : SUITE_LIST) {
            if (str.equals(s)) {
                this.suite = s;
                break;
            }
        }

        this.value = calculateValue();
    }

    // Generates the shorthand string name of the card, e.g "AD" is ace of diamonds.
    public String name() {
        return rank + suite;
    }

    // If the card is an ace it switches its value to 11 or 1.
    public void switchAceValue() {
        switch (value) {
            case 11:
                value = 1;
                break;
        
            case 1:
                value = 11;
                break;
            default:
                break;
        }
    }

    // INCOMPLETE
    public String getImagePath() {
        return null;
    }

    /**
     * Returns the value of the card according to the blackjack rules.
     * Aces are by default 11 unless specified by the switchAceValue function.
     * @return an integer value between 2 and 11.
     */
    private int calculateValue() {
        for (int i = 0; i < RANK_LIST.length; i++) {
            if (rank.equals(RANK_LIST[i]) && i < 8) {
                return i + 2;
            } else if (rank.equals("A")) {
                return 11;
            } else if (i >= 8) {
                return 10;
            }
        }
        return 0;
    }
}
