import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class GamePanel extends JPanel {
    Player player;
    Dealer dealer;
    public boolean reveal = false;
    public boolean start = false;

    public static int CARD_WIDTH = 100;
    public static int CARD_HEIGHT = 143;

    private JLabel dealerHandLabel;
    private JLabel playerHandLabel;
    private JPanel labelPanel;

    public GamePanel(Player p, Dealer d, JPanel panel) {
        this.player = p;
        this.dealer = d;

        this.dealerHandLabel = new JLabel();
        this.dealerHandLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.playerHandLabel = new JLabel();
        this.playerHandLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.labelPanel = panel;

        this.labelPanel.setLayout(new GridLayout(2, 1));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        labelPanel.add(dealerHandLabel);
        labelPanel.add(playerHandLabel);
        
        // initially draw hidden card
        Image hiddenCardImage = getCardImage(null, "./cards/cardback.png");
        g.drawImage(hiddenCardImage, 20, 20, CARD_WIDTH, CARD_HEIGHT, null);
            
        // if reveal is set to true repaint hidden card
        if (reveal) {
            Image revealedCardImage = getCardImage(dealer.hand.get(0), null);
            g.drawImage(revealedCardImage, 20, 20, CARD_WIDTH, CARD_HEIGHT, null);
        }

        // if game hasnt started yet then dont draw the rest
        if (start) {
            drawHand(g, dealer.hand, 1, 20);
            drawHand(g, player.hand, 0, 320);
        }        
    }

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

    private Image getCardImage(Card card, String path) {
        if (card == null) {
            return new ImageIcon(getClass().getResource(path)).getImage();
        }
        return new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
    }

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