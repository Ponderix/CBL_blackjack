import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Represents the game panel in the Blackjack game, extending JPanel. 
 * Manages the display of player and dealer hands, card images, and scores.
 */
public class GamePanel extends JPanel {
    Player player;
    Dealer dealer;
    public boolean reveal = false; // Flag to indicate whether to reveal the dealer's hidden card

    /*  Flag to indicate whether the game has started.
    This is required in order to be able to deal cards 
    only after bets have been places*/ 
    public boolean start = false;  

    public static int CARD_WIDTH = 100; 
    public static int CARD_HEIGHT = 143;

    private JLabel dealerHandLabel;
    private JLabel playerHandLabel;
    private JPanel labelPanel;

    /**
     * Constructs a GamePanel with specified player, dealer, and label panel.
     * 
     * @param p the player participating in the game
     * @param d the dealer managing the game
     * @param panel the panel for displaying the hand labels
     */
    public GamePanel(Player p, Dealer d, JPanel panel) {
        this.player = p;
        this.dealer = d;

        this.dealerHandLabel = new JLabel();
        this.dealerHandLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.playerHandLabel = new JLabel();
        this.playerHandLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.labelPanel = panel;

        this.labelPanel.setLayout(new GridLayout(2, 1)); // Set layout for label panel
    }

    /**
     * Paints the component, drawing the cards and updating the display 
     * based on the current game state.
     * 
     * @param g the Graphics context to paint on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        labelPanel.add(dealerHandLabel);
        labelPanel.add(playerHandLabel);
        
        // Initially draw hidden card
        Image hiddenCardImage = getCardImage(null, "./cards/cardback.png");
        g.drawImage(hiddenCardImage, 20, 20, CARD_WIDTH, CARD_HEIGHT, null);
            
        // If reveal is true repaint hidden card
        if (reveal) {
            Image revealedCardImage = getCardImage(dealer.hand.get(0), null);
            g.drawImage(revealedCardImage, 20, 20, CARD_WIDTH, CARD_HEIGHT, null);
        }

        // If game hasn't started yet, don't draw the rest
        if (start) {
            drawHand(g, dealer.hand, 1, 20);
            drawHand(g, player.hand, 0, 320);
        }        
    }

    /**
     * Updates the score labels for the player and dealer based on the current game state.
     * If the game has not started, resets the labels; otherwise, updates with hand values.
     */
    public void updateScores() {
        if (!start) {
            playerHandLabel.setText("Your hand: ");
            dealerHandLabel.setText("Dealer hand: ");
            return;
        }

        playerHandLabel.setText("Your hand: " + player.handValue);
        if (reveal) {
            dealerHandLabel.setText("Dealer hand: " + dealer.handValue);
        } else {
            dealerHandLabel.setText("Dealer hand: " + dealer.hand.get(1).value + " and ?");
        }
        revalidate();
        repaint();
    }

    /**
     * Gets the image of a card, either from a file path or the card's image path.
     * 
     * @param card the Card object whose image is to be retrieved
     * @param path the file path for the card image if the card is null
     * @return the Image of the card
     */
    private Image getCardImage(Card card, String path) {
        if (card == null) {
            return new ImageIcon(getClass().getResource(path)).getImage();
        }
        return new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
    }

    /**
     * Draws the player's or dealer's hand on the panel.
     * 
     * @param g the Graphics context to paint on
     * @param hand the list of cards to draw
     * @param startIndex the starting index in the hand to draw from
     * @param y the y-coordinate to draw the cards
     */
    private void drawHand(Graphics g, ArrayList<Card> hand, int startIndex, int y) {
        for (int i = startIndex; i < hand.size(); i++) {
            Image image = getCardImage(hand.get(i), null);
            g.drawImage(image, 
                (20 + (5 + CARD_WIDTH) * i), 
                y, CARD_WIDTH, CARD_HEIGHT, 
                null);
        }
    }
}
