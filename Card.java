


/**
 * Represents a playing card with a rank, suite, and value for use in blackjack.
 * Provides methods to retrieve the card's name, get its value, toggle the 
 * value of an Ace between 1 and 11, and retrieve the image path of the card.
 * 
 * This class includes constants for valid ranks and suites. The card's value 
 * is set according to blackjack rules, with face cards (jack, queen, king) 
 * valued at 10 and Aces defaulting to 11 unless modified by the switchAceValue 
 * method.
 */
public class Card {

    public String rank;
    public String suite;
    public int value;

    public static String[] RANK_LIST = {"2", "3", "4", "5", "6", "7", 
                                        "8", "9", "10", "j", "q", "k", 
                                        "a"};
    public static String[] SUITE_LIST = {"h", "c", "s", "d"};

    /**
     * Constructs a card with a specified rank and suite.
     * Initializes the card's value according to blackjack rules.
     *
     * @param r the rank of the card (e.g., "2", "j", "a")
     * @param s the suite of the card (e.g., "h" for hearts)
     */
    public Card(String r, String s) {
        String lowerCaseSuite = s.toLowerCase();
        String lowerCaseRank = r.toLowerCase();

        for (String str : RANK_LIST) {
            if (str.equals(lowerCaseRank)) {
                this.rank = lowerCaseRank;
                break;
            }
        }

        for (String str : SUITE_LIST) {
            if (str.equals(lowerCaseSuite)) {
                this.suite = lowerCaseSuite;
                break;
            }
        }

        this.value = calculateValue();
    }

    /**
     * Returns the card's name, combining rank and suite (e.g., "ah" for Ace of Hearts).
     *
     * @return the name of the card in the format "rank + suite"
     */
    public String name() {
        return rank + suite;
    }

    /**
     * Switches the value of the card if it is an Ace. By default, an Ace
     * is valued at 11; this method changes its value to 1 if it is currently
     * 11, and back to 11 if it is currently 1.
     */
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

    /**
     * Retrieves the image path of the card for display purposes.
     * The path is based on the card's name (rank and suite).
     *
     * @return a string representing the file path to the card's image
     */
    public String getImagePath() {
        return "./cards/" + name() + ".png";
    }

    /**
     * Returns the value of the card according to the blackjack rules.
     * Face cards (jack, queen, king) are valued at 10, numeric cards 
     * are valued according to their rank, and Aces default to 11 unless 
     * modified by the switchAceValue function.
     *
     * @return an integer value between 2 and 11, representing the card's value
     */
    private int calculateValue() {
        for (int i = 0; i < RANK_LIST.length; i++) {
            if (rank.equals(RANK_LIST[i]) && i < 8) {
                return i + 2;
            } else if (rank.equals("a")) {
                return 11;
            } else if (i >= 8) {
                return 10;
            }
        }
        return 0;
    }
}
