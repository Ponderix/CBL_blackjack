import javax.swing.*;

public class Blackjack { 
    private Deck deck; 
    private Player player; 
    private Dealer dealer; 
    
    public void startGame(float startingMoney) { 
        deck = new Deck(); 
        deck.shuffle(); 
        
        player = new Player(startingMoney); 
        dealer = new Dealer(); 
        
        // Deal initial hands
        player.dealHand(deck); 
        dealer.dealHand(deck); 

        Window game = new Window(800, 600, player, dealer, deck);
        SwingUtilities.invokeLater(() -> {
            game.generate(); 
        });
    } 

    // main() 
    // Runs the game loop and displays win/lose/tie results. 
    public static void main(String[] args) { 
        Blackjack game = new Blackjack(); 
        game.startGame(100); 
    }
}