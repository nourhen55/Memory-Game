package presentation;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.sql.SQLException;

import metier.joueur;
import doa.gesjeuphp;
import doa.igsionjoueur;

public class gestiondejeuxGUI extends JFrame {

    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static final int TOTAL_CARDS = ROWS * COLS;
    private static final String IMAGE_FOLDER = "C:\\Users\\ASUS\\eclipse-workspace\\jeux\\src\\presentation\\jeuxjava\\im";
    private int pairsFound=0;
    private ArrayList<JButton> cards;
    private ArrayList<String> cardValues;
    private JButton firstCard;
    private JButton secondCard;
    private JTextField nomField;
    private JTextField prenomField;
    private JLabel scoreLabel;
    private int score;
    private igsionjoueur gestionJoueur;

    public gestiondejeuxGUI() {
        setTitle("Memory Game");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            gestionJoueur = new gesjeuphp();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("Nom:");
        nomField = new JTextField(10);
        JLabel prenomLabel = new JLabel("Prénom:");
        prenomField = new JTextField(10);
        topPanel.add(nameLabel);
        topPanel.add(nomField);
        topPanel.add(prenomLabel);
        topPanel.add(prenomField);

        JPanel centerPanel = new JPanel(new GridLayout(ROWS, COLS));
        cards = new ArrayList<>();
        cardValues = new ArrayList<>();
        initializeCards();
        for (JButton card : cards) {
            centerPanel.add(card);
        }

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton afficherButton = new JButton("Afficher");
        afficherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherButtonActionPerformed(e);
            }
        });
        bottomPanel.add(afficherButton);
        scoreLabel = new JLabel("Score: 0");
        bottomPanel.add(scoreLabel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initializeCards() {
        ArrayList<String> values = new ArrayList<>();
        for (int i = 1; i <= TOTAL_CARDS / 2; i++) {
            values.add(String.valueOf(i));
            values.add(String.valueOf(i));
        }
        Collections.shuffle(values);

        for (int i = 0; i < TOTAL_CARDS; i++) {
            JButton card = new JButton();
            card.setPreferredSize(new Dimension(80, 80));
            card.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (firstCard == null) {
                        firstCard = card;
                        flipCard(firstCard);
                    } else if (secondCard == null && !card.equals(firstCard)) {
                        secondCard = card;
                        flipCard(secondCard);
                        compareCards();
                    }
                }
            });
            cards.add(card);
            cardValues.add(values.get(i));
        }
    }

    private void flipCard(JButton card) {
        int index = cards.indexOf(card);
        String value = cardValues.get(index);
        try {
            String imagePath = IMAGE_FOLDER + value + ".jpg";
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                throw new IOException("Image file not found: " + imagePath);
            }
            ImageIcon icon = new ImageIcon(ImageIO.read(imageFile));
            card.setIcon(icon);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unable to load image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void compareCards() {
        int index1 = cards.indexOf(firstCard);
        int index2 = cards.indexOf(secondCard);
        
        if (cardValues.get(index1).equals(cardValues.get(index2))) {
            // Les cartes correspondent
            firstCard.setEnabled(false);
            secondCard.setEnabled(false);
            if (pairsFound == 0) {
                pairsFound++;
                updateScore(pairsFound);
            } else {
                
                pairsFound++;
                updateScore(pairsFound);
            }

            firstCard = null;
            secondCard = null;
        } else {
            pairsFound = 0;
            
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            firstCard.setIcon(null);
                            secondCard.setIcon(null);
                            
                            firstCard = null;
                            secondCard = null;
                        }
                    });
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void updateScore(int increment) {
        score += increment;
        scoreLabel.setText("Score: " + score);
    }

    private void afficherButtonActionPerformed(ActionEvent e) {
        if (nomField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le champ Nom est vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } 
        else if (prenomField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le champ Prénom est vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } 
        else {
            joueur newPlayer = new joueur(nomField.getText(), prenomField.getText(), score);

            try {
                String mess = gestionJoueur.ajouterjoueur(newPlayer);
                JOptionPane.showMessageDialog(this, mess);

                joueur j1 = gestionJoueur.afficherpremierjoueur(score);
                JOptionPane.showMessageDialog(this, "Nom: " + j1.getNom() + ", Score: " + j1.getScore());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gestiondejeuxGUI game = new gestiondejeuxGUI();
            game.setVisible(true);
        });
    }
}
