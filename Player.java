// Class: Player (extends Person) 
// Represents a player. 
// Has methods for betting, hitting, and deciding when to stand. 
public class Player extends Person {
    public float money;
    public float bet;

    public Player(float startMoney) {
        this.money = startMoney;
    }

    public void betMoney(float amount) {
        if (amount > money || !currentTurn) {
            return;
        }
        money -= amount;
    }

    public void stand() {
        if (!currentTurn) {
            return;
        }
    }

    public void hit(Deck deck) {
        if (!currentTurn) {
            return;
        }
        hand.add(deck.getNextCard());
        calculateHandValue();
    }

    public void surrender() {
        if (!currentTurn) {
            return;
        }
    }

    public void doubleDown(Deck deck) {
        hand.add(deck.getNextCard());
        calculateHandValue();
    }
}
