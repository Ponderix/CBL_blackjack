import java.util.ArrayList;
import java.util.Random;

public class Deck {
    public ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        String[] values = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] suits = {"c", "d", "h", "s"};

        for (String value : values) {
            for (String suit : suits) {
                cards.add(new Card(value, suit));
            }
        }
    }

    public void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int j = rand.nextInt(cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public Card getNextCard() {
        return cards.remove(0);
    }
}
