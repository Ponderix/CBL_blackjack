import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window {
    int width;
    int height;
    Player player;
    Dealer dealer;
    Deck deck;

    Color green = new Color(78, 106, 84);

    class InvalidBetException extends Exception {
        public InvalidBetException(String message) {
            super(message);
        }
    }

    public Window(int w, int h, Player p, Dealer d, Deck dk) {
        this.width = w;
        this.height = h;
        this.player = p;
        this.dealer = d;
        this.deck = dk;
    }

    public void generate() {
        JFrame frame = new JFrame("Blackjack");

        JTextArea betInput = new JTextArea();
        JButton betBtn = new JButton("Bet");
        betBtn.setFocusable(false);
        JButton hitBtn = new JButton("Hit");
        hitBtn.setEnabled(false);
        JButton standBtn = new JButton("Stand");
        standBtn.setEnabled(false);
        JButton surrenderBtn = new JButton("Surrender");
        surrenderBtn.setEnabled(false);
        JButton doubleDownBtn = new JButton("Double Down");
        doubleDownBtn.setEnabled(false);
        JButton nextRoundBtn = new JButton("Next round");
        nextRoundBtn.setEnabled(false);

        JLabel betLabel = new JLabel();
        JLabel moneyLabel = new JLabel("Your money: " + player.money);
        JLabel infoLabel = new JLabel("Your turn");

        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        JPanel labelPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel moneyPanel = new JPanel();

        GamePanel gamePanel = new GamePanel(player, dealer, labelPanel);

        gamePanel.setBackground(green);
        gamePanel.setLayout(new BorderLayout());

        controlPanel.setLayout(new BorderLayout());

        buttonPanel.add(betInput);
        buttonPanel.add(betBtn);
        buttonPanel.add(hitBtn);
        buttonPanel.add(standBtn);
        buttonPanel.add(surrenderBtn);
        buttonPanel.add(doubleDownBtn);
        buttonPanel.add(nextRoundBtn);

        moneyPanel.setLayout(new GridLayout(1, 3));
        moneyPanel.add(betLabel);
        moneyPanel.add(moneyLabel);
        moneyPanel.add(infoLabel);

        frame.add(gamePanel);
        frame.add(controlPanel, BorderLayout.SOUTH);

        controlPanel.add(labelPanel, BorderLayout.NORTH);
        controlPanel.add(moneyPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        gamePanel.repaint();
        frame.setVisible(true);

        // the bet itself initiates the cycle
        betBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String betAmountText = betInput.getText().trim();

                try {
                    float betAmount = Float.parseFloat(betAmountText);

                    if (betAmount > player.money || betAmount <= 0) {
                        throw new InvalidBetException(
                            "Bet amount exceeds limit or is non-positive.");
                    }

                    player.bet(betAmount);
                    player.updateLabels(betLabel, moneyLabel);

                    /* disable double down if subtracting the same bet amount again  
                    would result in negative money.*/ 
                    if (!(player.money - betAmount < 0)) {
                        doubleDownBtn.setEnabled(true);
                    }
                    betBtn.setEnabled(false);
                    betInput.setEnabled(false);
                    hitBtn.setEnabled(true);
                    standBtn.setEnabled(true);
                    surrenderBtn.setEnabled(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, 
                        "Please enter a valid number for the bet.", "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (InvalidBetException ex) {
                    JOptionPane.showMessageDialog(frame, 
                        ex.getMessage(), "Invalid Bet", 
                        JOptionPane.ERROR_MESSAGE);
                }

                gamePanel.repaint();
            }
        });

        hitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);

                player.hit(deck);
                gamePanel.repaint();
                gamePanel.updateScores();

                if (player.handValue > 21) {                    
                    hitBtn.setEnabled(false);
                    standBtn.setEnabled(false);
                    nextRoundBtn.setEnabled(true);

                    gamePanel.updateScores();

                    infoLabel.setText("You lost. Next round.");
                    player.payout(0);
                    
                    gamePanel.reveal = true;
                    player.updateLabels(betLabel, moneyLabel);

                    gamePanel.repaint();
                } else if (player.handValue == 21) {
                    hitBtn.setEnabled(false);
                    standBtn.setEnabled(false);
                    nextRoundBtn.setEnabled(true);

                    dealer.draw(deck);
                    gamePanel.updateScores();
                    calculateWinner(infoLabel);

                    gamePanel.reveal = true;
                    player.updateLabels(betLabel, moneyLabel);

                    gamePanel.repaint();
                }                    
            }
        });
        standBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                nextRoundBtn.setEnabled(true);

                dealer.draw(deck);
                gamePanel.updateScores();
                calculateWinner(infoLabel);

                gamePanel.reveal = true;
                player.updateLabels(betLabel, moneyLabel);

                gamePanel.repaint();
            }
        });
        surrenderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                nextRoundBtn.setEnabled(true);

                infoLabel.setText("Surrender, you get back 0.5x bet. Next round.");
                player.payout(0.5f);
                player.updateLabels(betLabel, moneyLabel);

                gamePanel.repaint();
            }
        });
        doubleDownBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);

                player.doubleDown(deck);
                dealer.draw(deck);
                calculateWinner(infoLabel);

                gamePanel.reveal = true;
                gamePanel.updateScores();
                player.updateLabels(betLabel, moneyLabel);

                nextRoundBtn.setEnabled(true);
                gamePanel.repaint();
            }
        });

        nextRoundBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.returnCard(deck);
                dealer.returnCard(deck);
                player.dealHand(deck);
                dealer.dealHand(deck);

                gamePanel.reveal = false;
                betBtn.setEnabled(true);
                betInput.setEnabled(true);
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                nextRoundBtn.setEnabled(false);
                infoLabel.setText("Your turn.");

                gamePanel.removeAll();
                gamePanel.repaint();
                gamePanel.updateScores();
                player.updateLabels(betLabel, moneyLabel);

                if (player.money <= 0) {
                    notifyGameOver();
                }

                System.out.println(deck.list.size());
            }
        });

        gamePanel.repaint();
    }

    private void calculateWinner(JLabel label) {
        int playerDiff = 21 - player.handValue;
        int dealerDiff = 21 - dealer.handValue;

        if (playerDiff == 0 && dealerDiff != 0) {
            label.setText("BlackJack, you win 1.5x your bet! Next round.");
            player.payout(2.5f);
        }
        if (playerDiff == dealerDiff) {
            label.setText("You tie. Next round.");
            player.payout(1);
        }

        if (playerDiff < 0) {
            label.setText("You lost. Next round.");
            player.payout(0);
        }

        if (playerDiff > 0) {
            if (dealerDiff < 0 || playerDiff < dealerDiff) {
                label.setText("You won! Next round.");
                player.payout(2);
            } else {
                String blackjack = "Dealer blackjack, you lose. Next round.";
                label.setText(dealerDiff == 0 ? blackjack : "You lost! Next round.");
                player.payout(0);
            }
        }
    }

    private void notifyGameOver() {
        int response = JOptionPane.showConfirmDialog(null,
            "You've lost all your money! Would you like to restart the game?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            player.reset();
        } else {
            System.exit(0);
        }
    }
}

