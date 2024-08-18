package Shooter.GUI;


import java.awt.*;
import javax.swing.*;

import Shooter.model.Game;

public class GameOverPage extends GameScene {
    
    public GameOverPage(Game game) {
        super(game); 
        initUI();
    }

    private void initUI() {
        loadBackgroundImage(2);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1; 
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(shooterFont);
        gameOverLabel.setForeground(Color.RED);
        add(gameOverLabel, gbc);
        JPanel buttonPanel=initButtons();
        gbc.gridy = 1; // Position sous le titre
		gbc.weighty=80;
        add(buttonPanel, gbc);


    }

    private JPanel initButtons() {
        JPanel buttonPanel=new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(createButton("Menu", "Menu"));
        return buttonPanel;
    }


}
