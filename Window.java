import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Represents the main game window for the Blackjack game, 
 * managing user interactions and game logic.
 */
public class Window {
    int width;
    int height;
    Player player;
    Dealer dealer;
    Deck deck;

    Color green = new Color(78, 106, 84); // Background color of the game panel

    /**
     * Custom exception for handling invalid betting situations.
     */
    class InvalidBetException extends Exception {
        public InvalidBetException(String message) {
            super(message);
        }
    }

    /**
     * Constructs a Window with specified dimensions, player, dealer, and deck.
     * 
     * @param w the width of the window
     * @param h the height of the window
     * @param p the player participating in the game
     * @param d the dealer managing the game
     * @param dk the deck of cards used in the game
     */
    public Window(int w, int h, Player p, Dealer d, Deck dk) {
        this.width = w;
        this.height = h;
        this.player = p;
        this.dealer = d;
        this.deck = dk;
    }

    /**
     * Generates and displays the main game window and its components.
     */
    public void generate() {
        JFrame frame = new JFrame("Blackjack");

        // Initialize buttons for game actions
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

        // Text area for bet input
        JTextArea betInput = new JTextArea();
        JButton betBtn = new JButton("Bet");
        betBtn.setFocusable(false);

        // Labels to display bet and money information
        JLabel betLabel = new JLabel();
        JLabel moneyLabel = new JLabel("Your money: " + player.money);
        JLabel infoLabel = new JLabel("Your turn");

        // Set up the frame properties
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panels to organize the layout
        JPanel controlPanel = new JPanel();
        JPanel labelPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel moneyPanel = new JPanel();

        // Create the game panel and set its properties
        GamePanel gamePanel = new GamePanel(player, dealer, labelPanel);
        gamePanel.setBackground(green);
        gamePanel.setLayout(new BorderLayout());
        controlPanel.setLayout(new BorderLayout());

        // Add components to their respective panels
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

        // Add the game panel and control panel to the frame
        frame.add(gamePanel);
        frame.add(controlPanel, BorderLayout.SOUTH);
        controlPanel.add(labelPanel, BorderLayout.NORTH);
        controlPanel.add(moneyPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        gamePanel.repaint(); // Refresh the game panel
        frame.setVisible(true); // Make the frame visible

        // Action listener for the betting button
        betBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String betAmountText = betInput.getText().trim();

                try {
                    float betAmount = Float.parseFloat(betAmountText);

                    // Validate the bet amount
                    if (betAmount > player.money || betAmount <= 0) {
                        throw new InvalidBetException(
                            "Bet amount exceeds limit or is non-positive.");
                    } else {
                        gamePanel.start = true;
                    }

                    player.bet(betAmount); // Process the bet
                    player.updateLabels(betLabel, moneyLabel);
                    gamePanel.updateScores();

                    // Enable double down button if possible
                    if (!(player.money - betAmount < 0)) {
                        doubleDownBtn.setEnabled(true);
                    }
                    betBtn.setEnabled(false);
                    betInput.setEnabled(false);
                    hitBtn.setEnabled(true);
                    standBtn.setEnabled(true);
                    surrenderBtn.setEnabled(true);
                } catch (NumberFormatException ex) {
                    // Show error dialog for invalid input
                    JOptionPane.showMessageDialog(frame, 
                        "Please enter a valid number for the bet.", "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (InvalidBetException ex) {
                    // Show error dialog for invalid bet
                    JOptionPane.showMessageDialog(frame, 
                        ex.getMessage(), "Invalid Bet", 
                        JOptionPane.ERROR_MESSAGE);
                }

                gamePanel.repaint(); // Refresh the game panel
            }
        });

        // Action listener for the hit button
        hitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);

                player.hit(deck); // Player takes a hit
                gamePanel.repaint();
                gamePanel.updateScores();

                // Check if player has bust or reached blackjack
                if (player.handValue > 21) {                    
                    hitBtn.setEnabled(false);
                    standBtn.setEnabled(false);
                    nextRoundBtn.setEnabled(true);
                    infoLabel.setText("You lost. Next round.");
                    player.payout(0);
                    
                    gamePanel.reveal = true;
                    player.updateLabels(betLabel, moneyLabel);
                    gamePanel.updateScores();
                    gamePanel.repaint();
                } else if (player.handValue == 21) {
                    hitBtn.setEnabled(false);
                    standBtn.setEnabled(false);
                    nextRoundBtn.setEnabled(true);
                    dealer.draw(deck);
                    calculateWinner(infoLabel);
                    
                    gamePanel.reveal = true;
                    player.updateLabels(betLabel, moneyLabel);
                    gamePanel.updateScores();
                    gamePanel.repaint();
                }                    
            }
        });

        // Action listener for the stand button
        standBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                nextRoundBtn.setEnabled(true);

                dealer.draw(deck); // Dealer takes a turn
                calculateWinner(infoLabel);
                
                gamePanel.reveal = true;
                player.updateLabels(betLabel, moneyLabel);
                gamePanel.updateScores();
                gamePanel.repaint();
            }
        });

        // Action listener for the surrender button
        surrenderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                nextRoundBtn.setEnabled(true);

                infoLabel.setText("Surrender, you get back 0.5x bet. Next round.");
                player.payout(0.5f); // Process surrender payout
                player.updateLabels(betLabel, moneyLabel);
                gamePanel.repaint(); // Refresh the game panel
            }
        });

        // Action listener for the double down button
        doubleDownBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                nextRoundBtn.setEnabled(true);

                player.doubleDown(deck); // Player doubles down
                dealer.draw(deck); // Dealer takes a turn
                calculateWinner(infoLabel);
                
                gamePanel.reveal = true;
                player.updateLabels(betLabel, moneyLabel);
                gamePanel.updateScores();
                gamePanel.repaint();
            }
        });

        // Action listener for the next round button
        nextRoundBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.returnCard(deck); // Return player's cards to the deck
                dealer.returnCard(deck); // Return dealer's cards to the deck
                player.dealHand(deck); // Deal new hands
                dealer.dealHand(deck);

                gamePanel.reveal = false; // Reset game state for the next round
                gamePanel.start = false;
                betBtn.setEnabled(true);
                betInput.setEnabled(true);
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                nextRoundBtn.setEnabled(false);
                infoLabel.setText("Your turn.");

                gamePanel.removeAll(); // Clear the game panel for new round
                gamePanel.repaint();
                gamePanel.updateScores();
                player.updateLabels(betLabel, moneyLabel);
                deck.shuffle(); // Shuffle the deck for the next round

                if (player.money <= 0) { // Check for game over condition
                    gamePanel.start = false;
                    deck.shuffle(); 
                    notifyGameOver(betLabel, moneyLabel);
                }
            }
        });

        gamePanel.repaint(); // Refresh the game panel
    }

    /**
     * Calculates the winner of the round and updates the info label accordingly.
     * 
     * @param label the label to update with the outcome of the round
     */
    private void calculateWinner(JLabel label) {
        int playerDiff = 21 - player.handValue; // Difference from 21 for player
        int dealerDiff = 21 - dealer.handValue; // Difference from 21 for dealer

        // Determine the outcome of the game based on the hand values
        if (playerDiff == 0 && dealerDiff != 0) {
            label.setText("BlackJack, you win 1.5x your bet! Next round.");
            player.payout(2.5f);
        } else if (playerDiff < 0) {
            label.setText("You lost. Next round.");
            player.payout(0);
        } else if (playerDiff == dealerDiff) {
            label.setText("You tie. Next round.");
            player.payout(1);
        } else if (playerDiff > 0) {
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

    /**
     * Notifies the player that the game is over and asks if they want to restart.
     * 
     * @param betLabel the label displaying the current bet
     * @param moneyLabel the label displaying the current money
     */
    private void notifyGameOver(JLabel betLabel, JLabel moneyLabel) {
        int response = JOptionPane.showConfirmDialog(null,
            "You've lost all your money! Would you like to restart the game?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            player.reset(); // Reset player for a new game
            player.updateLabels(betLabel, moneyLabel);
        } else {
            System.exit(0); // Exit the game if the player chooses not to restart
        }
    }
}
