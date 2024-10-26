import java.util.ArrayList;

public class Person {
    protected ArrayList<Card> hand = new ArrayList<>();

    public Person() {
        hand.add(new Card());
        hand.add(new Card());
    }
}
