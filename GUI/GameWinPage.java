package Shooter.GUI;


import java.awt.*;
import javax.swing.*;

import Shooter.model.Game;

public class GameWinPage extends GameScene {
        
    public GameWinPage(Game game) {
        super(game); 
        initUI();
        
    }

    private void initUI() {
        loadBackgroundImage(1);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1; 
        JLabel gameWinLabel = new JLabel("Game Win !", SwingConstants.CENTER);
        gameWinLabel.setFont(shooterFont);
        gameWinLabel.setForeground(Color.WHITE);
        add(gameWinLabel, gbc);
        JPanel buttonPanel=initButtons();
        gbc.gridy = 1; 
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
