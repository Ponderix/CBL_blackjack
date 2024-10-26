import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class GamePanel extends JPanel {
    ArrayList<Card> playerHand;
    ArrayList<Card> dealerHand;
    public boolean reveal = false;

    public static int CARD_WIDTH = 100;
    public static int CARD_HEIGHT = 143;

    public GamePanel(Player p, Dealer d) {
        this.playerHand = p.hand;
        this.dealerHand = d.hand;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            Image hiddenCardImage = getCardImage(null, "./cards/cardback.png");
            g.drawImage(hiddenCardImage, 20, 20, CARD_WIDTH, CARD_HEIGHT, null);
            
            if (reveal) {
                Image revealedCardImage = getCardImage(dealerHand.get(0), null);
                g.drawImage(revealedCardImage, 20, 20, CARD_WIDTH, CARD_HEIGHT, null);
            }

            // loop starts as one as the initial card is hidden
            for (int i = 1; i < dealerHand.size(); i++) {
                Image image = getCardImage(dealerHand.get(i), null);
                g.drawImage(image, 
                    (25 + CARD_WIDTH + (5 + CARD_WIDTH) * (i - 1)), 
                    20, CARD_WIDTH, CARD_HEIGHT, 
                    null);
            }

            for (int i = 0; i < playerHand.size(); i++) {
                Image image = getCardImage(playerHand.get(i), null);
                g.drawImage(image, 
                    (20 + (5 + CARD_WIDTH) * i), 
                    370, CARD_WIDTH, CARD_HEIGHT, 
                    null);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private Image getCardImage(Card card, String path) {
        if (card == null) {
            return new ImageIcon(getClass().getResource(path)).getImage();
        }
        return new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
    }
}