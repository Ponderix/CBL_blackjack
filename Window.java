import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window {
    int width;
    int height;
    Player player;
    Dealer dealer;

    Color green = new Color(78, 106, 84);

    public Window(int w, int h, Player p, Dealer d) {
        this.width = w;
        this.height = h;
        this.player = p;
        this.dealer = d;
    }

    public void generate() {
        JFrame frame = new JFrame("Blackjack");

        JTextArea betInput = new JTextArea();
        JButton betBtn = new JButton("Bet");
        betBtn.setFocusable(false);
        JButton hitBtn = new JButton("Hit");
        hitBtn.setFocusable(false);
        hitBtn.setEnabled(false);
        JButton standBtn = new JButton("Stand");
        standBtn.setFocusable(false);
        standBtn.setEnabled(false);
        JButton surrenderBtn = new JButton("Surrender");
        surrenderBtn.setFocusable(false);
        surrenderBtn.setEnabled(false);
        JButton doubleDownBtn = new JButton("Double Down");
        doubleDownBtn.setFocusable(false);
        doubleDownBtn.setEnabled(false);
        JButton nextRoundBtn = new JButton("Next round");
        nextRoundBtn.setFocusable(false);
        nextRoundBtn.setEnabled(false);

        JLabel betLabel = new JLabel();
        JLabel moneyLabel = new JLabel(player.money + "");

        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel(player, dealer);
        JPanel buttonPanel = new JPanel();
        JPanel moneyPanel = new JPanel();

        gamePanel.setBackground(green);
        gamePanel.setLayout(new BorderLayout());

        buttonPanel.add(betInput);
        buttonPanel.add(betBtn);
        buttonPanel.add(hitBtn);
        buttonPanel.add(standBtn);
        buttonPanel.add(surrenderBtn);
        buttonPanel.add(doubleDownBtn);
        buttonPanel.add(nextRoundBtn);

        moneyPanel.setLayout(new BorderLayout());
        moneyPanel.add(moneyLabel);
        moneyPanel.add(betLabel);

        frame.add(gamePanel);
        frame.add(moneyPanel, BorderLayout.SOUTH); // FIX POSITIONING
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);

        // the bet itself initiates the cycle
        betBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                betBtn.setEnabled(false);
                betInput.setEnabled(false);
                hitBtn.setEnabled(true);
                standBtn.setEnabled(true);
                surrenderBtn.setEnabled(true);
                doubleDownBtn.setEnabled(true);
                // validate bet
                // player.bet(amount)
                gamePanel.repaint();
            }
        });

        hitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.hit();
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                // recalculate player
                    // if hand.value >= 21 then 
                    // hitbutton disabled
                    // dealer.draw();
                    gamePanel.reveal = true;
                    // calculate winner
                    // payout bets
                    nextRoundBtn.setEnabled(true);
                gamePanel.repaint();
            }
        });
        standBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                // dealer.draw();
                gamePanel.reveal = true;
                // calculate winner
                // payout bets
                nextRoundBtn.setEnabled(true);
                gamePanel.repaint();
            }
        });
        surrenderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                // player surrender
                // payout bets
                nextRoundBtn.setEnabled(true);
                gamePanel.repaint();
            }
        });
        doubleDownBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                // player.doubleDown();
                // dealer.draw();
                gamePanel.reveal = true;
                // calculate winner
                // payout bets
                nextRoundBtn.setEnabled(true);
                gamePanel.repaint();
            }
        });

        nextRoundBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // shuffle deck
                // new dealer hand
                // new player hand

                gamePanel.reveal = false;
                betBtn.setEnabled(true);
                betInput.setEnabled(true);
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                surrenderBtn.setEnabled(false);
                doubleDownBtn.setEnabled(false);
                nextRoundBtn.setEnabled(false);

                gamePanel.removeAll();
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();
    }

    public static void main(String[] args) {
        Window game = new Window(800, 600, new Player(), new Dealer());

        SwingUtilities.invokeLater(() -> {
            game.generate(); 
        });
    }
}

