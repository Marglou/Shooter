package Shooter.GUI;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Shooter.model.Game;
import java.awt.*;



public class MenuPage extends GameScene {

    public MenuPage(Game g1) {
        super(g1);
        setPanelSize();
        initUI();
        loadBackgroundImage(1);
    }

    private void setPanelSize() {
        setMinimumSize(game.size_screen);
        setPreferredSize(game.size_screen);
        setMaximumSize(game.size_screen);
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

    

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); 
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.insets.top = 10;
        gbc.insets.right = 15;
    
        buttonPanel.add(createButton("Play", "Play"), gbc);
        
        gbc.gridx++; 
        buttonPanel.add(createButton("Creative", "Play"), gbc);
        
        gbc.gridx++; 
        buttonPanel.add(createButton("Settings", "Settings"), gbc);
        
        gbc.gridx++; 
        buttonPanel.add(createButton("Editing", "Editing"), gbc);
        
        gbc.gridx++; 
        gbc.insets.right = 0; 
        buttonPanel.add(createButtonExit("Quit"), gbc);
    
        return buttonPanel;
    }
    
    

}

