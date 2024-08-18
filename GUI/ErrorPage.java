package Shooter.GUI;


import java.awt.*;
import javax.swing.*;

import Shooter.model.Game;

public class ErrorPage extends GameScene {
    
    public ErrorPage(Game game, int n) {
        super(game); 
        initUI(n);
    }

    private void initUI(int n) {
        loadBackgroundImage(2);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1; 
        JLabel mainLabel = new JLabel("Erreur", SwingConstants.CENTER);
        mainLabel.setFont(shooterFont);
        mainLabel.setForeground(Color.RED);
        String msg=errorMessage(n);
        JLabel errorLabel=new JLabel(msg, SwingConstants.CENTER);
        errorLabel.setForeground(Color.WHITE);
        errorLabel.setFont(shooterFont2.deriveFont(Font.PLAIN, 45));
        add(mainLabel, gbc);
        
        gbc.gridy++;
        add(errorLabel, gbc);
        
        JPanel buttonPanel=initButtons();
        gbc.gridy++; // Position sous le titre
		gbc.weighty=80;
        add(buttonPanel, gbc);


    }
    
    
    
    private String errorMessage(int n){
        switch(n){
            case 1: return "Tu n'as pas encore crée un niveau personalisé! Clique sur Editing pour créer ton propre niveau!";
        }
        return " ";
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