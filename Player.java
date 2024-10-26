public class Player extends Person {
    float money = 100f;

    public void hit() {
        hand.add(new Card());
    }
    public void reset() {
        for (int i = 2; i < hand.size(); i++) {
            hand.remove(i);
        }
    }
}
