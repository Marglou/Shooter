package Shooter.GUI;

import javax.swing.*;

import Shooter.model.Game;
import Shooter.model.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FirstPage extends GameScene {

    private Game game;
    private JLabel title;
    private JTextField textField;
    private JButton nouvellePartie;
    private JButton partieCharge;

    public FirstPage(Game game) {
        super(game);
        this.game = game;
        initUI();

    }

    public void initUI() {
        loadBackgroundImage(3);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);

        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        backgroundPanel.setBackground(new Color(0, 0, 0, 0)); // Make the panel completely transparent
        gbc.anchor = GridBagConstraints.CENTER;
        add(backgroundPanel, gbc);

        gbc.insets = new Insets(5, 0, 5, 0); // Reduce spacing between components
        gbc.anchor = GridBagConstraints.CENTER;

        title = new JLabel("Entrez votre pseudo :");
        title.setForeground(colourL3);
        title.setFont(shooterFont);
        gbc.gridy++;
        backgroundPanel.add(title, gbc);

        textField = new JTextField(20);
        textField.setMaximumSize(new Dimension(330, 30));
        textField.setBackground(colourD1);
        textField.setForeground(colourL2);
        textField.setFont(shooterFont2.deriveFont(30f));
        gbc.gridy++;
        backgroundPanel.add(textField, gbc);

        nouvellePartie = new JButtonStyled("  Nouvelle Partie  ", shooterFont); // ne pas enlever les espaces dans le
                                                                                // string
        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        gbc.gridy++;
        backgroundPanel.add(nouvellePartie, gbc);

        partieCharge = new JButtonStyled("  Charger Partie  ", shooterFont);
        partieCharge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
        gbc.gridy++;
        backgroundPanel.add(partieCharge, gbc);

        gbc.gridy++;
        gbc.weighty = 1; // Add space at the bottom
        backgroundPanel.add(Box.createVerticalGlue(), gbc);

        setVisible(true);
    }

    private void startNewGame() {
        String name = textField.getText();
        if (!name.isEmpty()) {

            if (loadPlayer(name) != null) {

                int dialogResult = JOptionPane.CLOSED_OPTION;  // empêche de fermer la fenêtre sans répondre à la question
                while (dialogResult == JOptionPane.CLOSED_OPTION) {
                    dialogResult = JOptionPane.showConfirmDialog(this,
                            "Une sauvegarde existe déjà pour ce joueur. Voulez-vous l'écraser ?", "Attention",
                            JOptionPane.YES_NO_OPTION);
                }

                if (dialogResult == JOptionPane.NO_OPTION) {
                    return;
                } else {
                    emptyFile();
                    Player player = new Player(name); // Remplacer par le nom réel du joueur
                    eraseFiles(player);
                    game.setPlayer(player);
                    initPlayer(player);
                    game.cardLayout.show(game.cardPanel, "Menu");
                }

            } else {
                emptyFile();
                Player player = new Player(name);
                game.setPlayer(player);
                initPlayer(player);
                game.cardLayout.show(game.cardPanel, "Menu");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom valide.");
        }
    }

    private void loadGame() {
        String name = textField.getText();
        if (!name.isEmpty()) {
            Player playerExistant = loadPlayer(name);
            if (playerExistant != null && playerExistant.isPseudoIdentique(name)) {
                game.setPlayer(playerExistant);
                copyFile(playerExistant.getCreativeLevel(), "Shooter/factory/creativeTmp.txt");
                copyFile(playerExistant.getEnemyLevel(), "Shooter/factory/enemyTmp.txt");
                initPlayer(playerExistant);
                game.cardLayout.show(game.cardPanel, "Menu");
            } else {
                JOptionPane.showMessageDialog(this, "Aucune sauvegarde trouvée pour le player ou pseudo incorrect.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom valide.");
        }
    }

    public static Player loadPlayer(String name) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("Shooter/Sauvegardes/" + name + ".ser"))) {
            return (Player) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
            return null;
        }
    }

    public void initPlayer(Player playerExistant) {
        game.getPersoList().add(playerExistant);
        game.gameManager.updatePlayer(playerExistant);
    }


}
