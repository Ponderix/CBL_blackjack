import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Blackjack {
    private class Card {
        String value;
        String type;

        Card(String value, String type) {
            this.value = value;
            this.type = type;
        }

        public int getValue() {
            return "a".equals(value) ? 11 : Integer.parseInt(value);
        }

        public String getImagePath() {
            return "./Cards/" + value.toLowerCase() + type.toLowerCase().charAt(0) + ".png";
        }
    }

    ArrayList<Card> deck;
    Random random = new Random();
    float startingMoney = 100;
    float currentBet = 10;

    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    int boardWidth = 700;
    int boardHeight = 500;
    int cardWidth = 90;
    int cardHeight = 126;

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
    JButton playAgainButton = new JButton("Play Again");

    Blackjack() {
        startGame();
        setupGUI();
    }

    private void setupGUI() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
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
        playAgainButton.setFocusable(false);
        playAgainButton.setVisible(false);  // Initially hidden
        buttonPanel.add(playAgainButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(e -> {
            Card card = deck.remove(deck.size() - 1);
            playerSum += card.getValue();
            playerAceCount += card.value.equals("a") ? 1 : 0;
            playerHand.add(card);
            if (reducePlayerAce() > 21) {
                hitButton.setEnabled(false);
            }
            gamePanel.repaint();
        });

        stayButton.addActionListener(e -> {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
            playDealerTurn();
            gamePanel.repaint();
        });

        playAgainButton.addActionListener(e -> {
            resetGame();
            gamePanel.repaint();
        });

        gamePanel.repaint();
    }

    private void drawCards(Graphics g) {
        try {
            Image hiddenCardImg = new ImageIcon("./Cards/back.png").getImage();
            if (!stayButton.isEnabled()) {
                hiddenCardImg = new ImageIcon(hiddenCard.getImagePath()).getImage();
            }
            g.drawImage(hiddenCardImg, 15, 20, cardWidth, cardHeight, null);

            for (int i = 0; i < dealerHand.size(); i++) {
                Card card = dealerHand.get(i);
                Image cardImg = new ImageIcon(card.getImagePath()).getImage();
                g.drawImage(cardImg, (cardWidth + 20) + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
            }

            for (int i = 0; i < playerHand.size(); i++) {
                Card card = playerHand.get(i);
                Image cardImg = new ImageIcon(card.getImagePath()).getImage();
                g.drawImage(cardImg, 15 + (cardWidth + 5) * i, 260, cardWidth, cardHeight, null);
            }

            if (!stayButton.isEnabled()) {
                String message = calculateWinner();
                g.setFont(new Font("Arial", Font.PLAIN, 24));
                g.setColor(Color.white);
                g.drawString(message, 270, 200);

                g.setFont(new Font("Arial", Font.PLAIN, 18));
                g.drawString("Current Balance: $" + startingMoney, 480, 30);
                g.drawString("Bet Amount: $" + currentBet, 480, 60);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        buildDeck();
        shuffleDeck();

        dealerHand = new ArrayList<>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size() - 1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.value.equals("a") ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerSum += card.getValue();
        dealerAceCount += card.value.equals("a") ? 1 : 0;
        dealerHand.add(card);

        playerHand = new ArrayList<>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            playerSum += card.getValue();
            playerAceCount += card.value.equals("a") ? 1 : 0;
            playerHand.add(card);
        }

        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        playAgainButton.setVisible(false);
    }

    public void buildDeck() {
        deck = new ArrayList<>();
        String[] values = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] suits = {"c", "d", "h", "s"};

        for (String type : suits) {
            for (String value : values) {
                deck.add(new Card(value, type));
            }
        }
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, currCard);
        }
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount--;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount--;
        }
        return dealerSum;
    }

    private void playDealerTurn() {
        while (dealerSum < 17) {
            Card card = deck.remove(deck.size() - 1);
            dealerSum += card.getValue();
            dealerAceCount += card.value.equals("a") ? 1 : 0;
            dealerHand.add(card);
        }
        playAgainButton.setVisible(true);
    }

    private String calculateWinner() {
        if (playerSum > 21) {
            startingMoney -= currentBet;
            return "You Lose!";
        } else if (dealerSum > 21 || playerSum > dealerSum) {
            startingMoney += currentBet;
            return "You Win!";
        } else if (playerSum == dealerSum) {
            return "Tie!";
        } else {
            startingMoney -= currentBet;
            return "Dealer Wins!";
        }
    }

    private void resetGame() {
        playerHand.clear();
        dealerHand.clear();
        playerSum = 0;
        playerAceCount = 0;
        dealerSum = 0;
        dealerAceCount = 0;
        startGame();
    }

    public static void main(String[] args) {
        new Blackjack();
    }
}
