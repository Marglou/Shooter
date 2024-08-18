package Shooter.GUI;

import Shooter.model.Game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoixLevel extends GameScene{

    //protected Player player;
    protected int nbLevel;

    public ChoixLevel(Game game) {
        super(game);
        //this.player = player;
        this.nbLevel = game.nbLevel;
        initUI();
    }


    private void initUI() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1; 

        JLabel titleLabel = new JLabel("Home Invasion");
        titleLabel.setFont(shooterFont);
        titleLabel.setForeground(colourL2);
        add(titleLabel, gbc);

  
        JPanel buttonPanel = createButtonPanel();
        gbc.gridy = 1; 
		gbc.weighty=80;
        add(buttonPanel, gbc);
    }

    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); 
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.insets.top = 10;
        gbc.insets.right = 15;

        for (int i = 1; i <= nbLevel; i++) {
            JButton levelButton = createButton("Level " + i, "Play");
            levelButton.addActionListener(new LevelButtonListener(i, game));
            buttonPanel.add(levelButton, gbc);
            gbc.gridx++;
        }

        buttonPanel.add(createButton("Menu", "Menu"), gbc);
        return buttonPanel;
    }

    
    private class LevelButtonListener implements ActionListener {
        private int level;
        private Game game;

        public LevelButtonListener(int level, Game game) {
            this.level = level;
            this.game = game;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (level >= 0) {
                game.rejouer = true;
                game.rejouerLevel = level;
                game.gameManager.reset();
                game.begin = true;
                game.cardLayout.show(game.cardPanel, "Play");

            } 
        
        }

    }
  
}





    
      

  