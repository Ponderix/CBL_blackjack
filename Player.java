import javax.swing.*;

// Class: Player (extends Person) 
// Represents a player. 
// Has methods for betting, hitting, and deciding when to stand. 
public class Player extends Person {
    public float startingMoney;
    public float money;
    public float bet;

    public Player(float startMoney) {
        this.money = startMoney;
        this.startingMoney = startMoney;
    }

    public void bet(float amount) {
        bet = amount;

        if (amount > money) {
            return;
        }
        money -= amount;
    }

    public void updateLabels(JLabel betLabel, JLabel moneyLabel) {
        betLabel.setText("Your bet: " + bet);
        moneyLabel.setText("Your money: " + money);
    }

    public void hit(Deck deck) {
        hand.add(deck.getNextCard());
        calculateHandValue();
    }

    public void doubleDown(Deck deck) {
        hand.add(deck.getNextCard());
        money -= bet;
        bet *= 2;
        calculateHandValue();
    }

    public void payout(float multiplier) {
        money += bet * multiplier;
        bet = 0;
    }

    public void reset() {
        money = startingMoney;
    }
}
