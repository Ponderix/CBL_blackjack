public class Blackjack { 
    
    private Deck deck; 
    private Player player; 
    private Dealer dealer; 
    // startGame() 
    // Sets up the game: builds the deck, shuffles it, and deals initial cards 
    // to both player and dealer. 
    
    public void startGame(float startingMoney) { 
        deck = new Deck(); 
        deck.shuffle(); 
        
        player = new Player(startingMoney); 
        dealer = new Dealer(); 
        
        // Deal initial hands
        player.dealHand(deck); 
        dealer.dealHand(deck); 

        player.currentTurn = true; 
        System.out.println("Starting game with player money: " + player.money); 
        System.out.println("Player hand value: " + player.handValue); 
        System.out.println("Dealer hand value: " + dealer.handValue); 
    } 
        
    // hit()
    // Adds a new card to the player's hand. If the hand value is > 21, the player busts. 
        
    public void hit() { 
        if (player.currentTurn) { 
            player.hit(deck); 
            System.out.println("Player hand value: " + player.handValue); 
            if (player.handValue > 21) { 
                System.out.println("Player busts!"); 
                endGame(); 
            } 
        } 
    } 
        
    // stay() 
    // Player stops taking cards. Dealer starts playing according to its rules. 
    public void stay() { 
        if (player.currentTurn) { 
            player.currentTurn = false; 
            dealer.currentTurn = true; 
                
            dealer.draw(deck); 
            System.out.println("Dealer hand value: " + dealer.handValue); 
            endGame(); 
        } 
    } 
        
    // endGame() 
    // Determine the winner based on the final hand values of the player and dealer. 
    private void endGame() { 
        player.currentTurn = false; 
        dealer.currentTurn = false; 
            
        if (player.handValue > 21) { 
            System.out.println("Player busts, dealer wins.");
        } else if (dealer.handValue > 21 || player.handValue > dealer.handValue) { 
            System.out.println("Player wins!"); 
        } else if (dealer.handValue == player.handValue) { 
            System.out.println("It's a tie!"); 
        } else { 
            System.out.println("Dealer wins."); 
        } 
    } 
            
    // main() 
    // Runs the game loop and displays win/lose/tie results. 
    public static void main(String[] args) { 
        Blackjack game = new Blackjack(); 
        game.startGame(100); 
        // Player starts with $100 game.hit(); 
        // Player hits game.stay(); 
        // Player stays, dealer plays 
    } 
}