public class Player extends Person {

    // Constructor that initializes the player with a specific amount of money
    public Player(float money) {
        this.money = money; // Set the player's starting money
    }

    // Method to add a card to the player's hand (this invokes the addCardToHand() from Person)
    public void hit(Deck deck) {
        Card card = deck.getNextCard();  // Deal the next card from the deck
        addCardToHand(card);  // Add the card to the player's hand and recalculate hand value
        System.out.println("Player draws: " + card.value + "-" + card.type);
    }

    // Method for dealing the initial hand (two cards) to the player
    public void dealHand(Deck deck) {
        Card firstCard = deck.getNextCard();
        Card secondCard = deck.getNextCard();
        addCardToHand(firstCard);  // Add the first card
        addCardToHand(secondCard); // Add the second card
        System.out.println("Player's initial hand: " + firstCard.value + "-" + firstCard.type + ", " + secondCard.value + "-" + secondCard.type);
    }

    // Method for betting money, adjusting the player's balance
    public void bet(float amount) {
        if (amount > money) {
            System.out.println("Not enough money to bet that amount.");
        } else {
            money -= amount;
            System.out.println("Player bets: $" + amount);
        }
    }

    // Method to update the player's balance when they win a round
    public void win(float amount) {
        money += amount;
        System.out.println("Player wins: $" + amount);
    }

    // Method to return the player's current balance
    public float getMoney() {
        return money;
    }

    // Method to reset the player's hand for a new round (inherits clearHand() from Person)
    public void resetHand() {
        clearHand();  // Clear the player's hand and reset the hand value
    }
}
