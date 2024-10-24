import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Blackjack {

    private Deck deck;
    private Player player;
    private Dealer dealer;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawCards(g);
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");

    int BOARD_WIDTH = 600;
    int BOARD_HEIGHT = BOARD_WIDTH;
    int CARD_WIDTH = 110;
    int CARD_HEIGHT = 154;

    public void startGame(float startingMoney) {
        deck = new Deck();
        deck.shuffle();

        player = new Player(startingMoney);
        dealer = new Dealer();

        player.dealHand(deck);
        dealer.dealHand(deck);

        player.currentTurn = true;

        setupGUI();
    }

    private void setupGUI() {
        frame.setVisible(true);
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hit();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stay();
            }
        });

        gamePanel.repaint();
    }

    private void drawCards(Graphics g) {
        try {
            Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
            if (!stayButton.isEnabled()) {
                hiddenCardImg = new ImageIcon(getClass().getResource(dealer.hiddenCard.getImagePath())).getImage();
            }
            g.drawImage(hiddenCardImg, 20, 20, CARD_WIDTH, CARD_HEIGHT, null);

            for (int i = 0; i < dealer.hand.size(); i++) {
                Card card = dealer.hand.get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, CARD_WIDTH + 25 + (CARD_WIDTH + 5) * i, 20, CARD_WIDTH, CARD_HEIGHT, null);
            }

            for (int i = 0; i < player.hand.size(); i++) {
                Card card = player.hand.get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, 20 + (CARD_WIDTH + 5) * i, 320, CARD_WIDTH, CARD_HEIGHT, null);
            }

            if (!stayButton.isEnabled()) {
                String message = calculateWinner();
                g.setFont(new Font("Arial", Font.PLAIN, 30));
                g.setColor(Color.white);
                g.drawString(message, 220, 250);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hit() {
        if (player.currentTurn) {
            player.hit(deck);
            if (player.handValue > 21) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                gamePanel.repaint();
                return;
            }
            gamePanel.repaint();
        }
    }

    public void stay() {
        if (player.currentTurn) {
            player.currentTurn = false;
            dealer.currentTurn = true;

            dealer.draw(deck);
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);

            gamePanel.repaint();
        }
    }

    private String calculateWinner() {
        if (player.handValue > 21) {
            return "You Lose!";
        } else if (dealer.handValue > 21 || player.handValue > dealer.handValue) {
            return "You Win!";
        } else if (dealer.handValue == player.handValue) {
            return "It's a Tie!";
        } else {
            return "Dealer Wins!";
        }
    }

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.startGame(100);
    }
}
