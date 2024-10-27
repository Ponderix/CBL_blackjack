import javax.swing.*;

/**
 * The main class that initializes and starts the Blackjack game. 
 * It manages the game components including the deck, player, and dealer.
 */
public class Blackjack { 
    private Deck deck; 
    private Player player; 
    private Dealer dealer; 
    
    /**
     * Initializes the game components and starts the game.
     * A new deck is created and shuffled, and the player and dealer
     * are initialized. The initial hands are dealt to both players.
     * 
     * @param startingMoney the initial amount of money the player starts with
     */
    public void startGame(float startingMoney) { 
        deck = new Deck(); 
        deck.shuffle(); 
        
        player = new Player(startingMoney); 
        dealer = new Dealer(); 
        
        // Deal initial hands
        player.dealHand(deck); 
        dealer.dealHand(deck); 

        Window game = new Window(1000, 600, player, dealer, deck);
        SwingUtilities.invokeLater(() -> {
            game.generate(); 
        });
    } 

    /**
     * The entry point of the application. It creates a new instance of 
     * the Blackjack game and starts it with a specified starting amount 
     * of money for the player.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) { 
        Blackjack game = new Blackjack(); 
        game.startGame(100); 
    }
}