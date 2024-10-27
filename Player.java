import javax.swing.*;

/**
 * Represents a player in the Blackjack game, extending the Person class. 
 * Contains attributes for managing the player's money, betting, and hand. 
 * Provides methods for betting, hitting, doubling down, and updating labels.
 */
public class Player extends Person {
    public float startingMoney; // starting money
    public float money; // current money
    public float bet;        

    /**
     * Constructs a Player with a specified starting amount of money.
     * 
     * @param startMoney the initial amount of money for the player
     */
    public Player(float startMoney) {
        this.money = startMoney;
        this.startingMoney = startMoney;
    }

    /**
     * Places a bet for the current round. The bet amount is subtracted 
     * from the player's money. If the bet exceeds available money, it is ignored.
     * 
     * @param amount the amount to bet
     */
    public void bet(float amount) {
        bet = amount;

        if (amount > money) {
            return; // Bet is ignored if it exceeds available money
        }
        money -= amount; // Deduct the bet amount from player's money
    }

    /**
     * Updates the display labels for the player's current bet and money.
     * 
     * @param betLabel the label displaying the current bet amount
     * @param moneyLabel the label displaying the current amount of money
     */
    public void updateLabels(JLabel betLabel, JLabel moneyLabel) {
        betLabel.setText("Your bet: " + bet);
        moneyLabel.setText("Your money: " + money);
    }

    /**
     * Adds a new card to the player's hand from the deck and recalculates 
     * the total hand value.
     * 
     * @param deck the deck from which to draw the next card
     */
    public void hit(Deck deck) {
        hand.add(deck.getNextCard());
        calculateHandValue();
    }

    /**
     * Doubles the player's bet, adds a new card to the hand, and adjusts 
     * the player's money accordingly.
     * 
     * @param deck the deck from which to draw the next card
     */
    public void doubleDown(Deck deck) {
        hand.add(deck.getNextCard());
        money -= bet; // Deduct the original bet amount
        bet *= 2; // Double the bet amount
        calculateHandValue();
    }

    /**
     * Calculates the player's payout based on the multiplier, resets 
     * the bet amount to zero after payout.
     * 
     * @param multiplier the multiplier to determine the payout amount
     */
    public void payout(float multiplier) {
        money += bet * multiplier; // Update money with the payout
        bet = 0; // Reset the bet amount
    }

    /**
     * Resets the player's money to the starting amount.
     */
    public void reset() {
        money = startingMoney; // Reset current money to starting amount
    }
}
