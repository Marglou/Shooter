package Shooter.GUI; 
import javax.swing.*;

import Shooter.model.Game;

import java.awt.*;
// import java.net.URL;

public class GameInfoPage extends GameScene {
    public GameInfoPage(Game game) {
        super(game);
        initUI();
        
    }

    private void initUI() {
        loadBackgroundImage(3);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); //margins
    
        // Titre
        JLabel titleLabel = new JLabel("Game Stats");
        titleLabel.setFont(shooterFont);
        titleLabel.setForeground(colourL2);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
    
        // Panel Enemies
        JPanel enemiesPanel = new JPanel();
        enemiesPanel.setLayout(new GridLayout(0, 3, 10, 10)); 
        enemiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
        
        enemiesPanel.add(createInfoPanel("Intrus", "Description:Enemi basique repére le joueur et lui tire dessus.\nPower:10", "Shooter/res/we_1_0.png"));
        enemiesPanel.add(createInfoPanel("Boss", "Description:Enemi medium qui posséde un ranyon de detection suis le joueur et lui tire dessus.\nPower:20", "Shooter/res/we_2_0.png"));
        enemiesPanel.add(createInfoPanel("Millitaire", "Description: Il poursuit le joueur dès le début de la partie et fait des dégâts en corps à corps.\nPower:30", "Shooter/res/we_3_0.png"));
        enemiesPanel.add(createInfoPanel("Policier", "Description:Il se déplace vers le joueur, dès qu’il est à portée de tir sans obstacle il s'arrete puis tire.\nPower:40", "Shooter/res/we_4_0.png"));
        enemiesPanel.add(createInfoPanel("Assasin", "Description:Lorsque le joueur lui tire dessus, il  essayer d’ éviter les balles ou il s’approche de lui et lui tire dessus.\nPower:50", "Shooter/res/we_5_0.png"));
    
        // Panel Weapons, Armes
        JPanel weaponsPanel = new JPanel();
        weaponsPanel.setLayout(new GridLayout(0, 3, 10, 10)); 
        weaponsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
        weaponsPanel.add(createInfoPanel("Pistolet", "Tire des balles classiques et cassentles objets cassants\nDegats:10\nMunitions:30", "Shooter/res/113.png"));
        weaponsPanel.add(createInfoPanel("Bazooka", "Tire des balles de feu et cassent les objets cassants\nDegats:10\nMunitions:25", "Shooter/res/114.png"));
        weaponsPanel.add(createInfoPanel("Mine", "Explosent lorsqu'on marche dessus\nDegats:30\nMunitions:5", "Shooter/res/115.png"));
        weaponsPanel.add(createInfoPanel("Grenade", "Explosent et infligent des dégât a tout ce qui l'entoure\nDegats:50\nMunitions:5", "Shooter/res/116.png"));
        weaponsPanel.add(createInfoPanel("Fusil d'assaut", "Tire des balles a longues portée \nDegats:15\nMunitions:25", "Shooter/res/117.png"));
        weaponsPanel.add(createInfoPanel("Gun", "Tire des balles rebondissantes \nDegats:10\nMunitions:30", "Shooter/res/118.png"));
    
        // Panel Cases
        JPanel casesPanel = new JPanel();
        casesPanel.setLayout(new GridLayout(0, 3, 10, 10)); 
        casesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
   
        casesPanel.add(createInfoPanel("Normale", "On peut se déplacer librement sur ces cases\nexemple: les tuiles du sol", "Shooter/res/2.png"));
        casesPanel.add(createInfoPanel("Bloque", "On peut pas passer par ces cases\nexemple: les murs", "Shooter/res/53.png"));
        casesPanel.add(createInfoPanel("Obstacle", "On peut casses ces obstacles afin de passer\nexemple: les pots de fleurs", "Shooter/res/20.png"));
        casesPanel.add(createInfoPanel("Accèlere", "Passer sur ces cases accèlere la vitesse du joueur\nexemple: les flaques d'eau", "Shooter/res/9.png"));
        casesPanel.add(createInfoPanel("Ralentit", "Passer sur ces cases ralentit la vitesse du joueur\nexemple: les tapis", "Shooter/res/13.png"));
        casesPanel.add(createInfoPanel("Tournis", "Passer sur ces cases fait tourner le joueur sur place\nexemple: les peaux de banane", "Shooter/res/10.png"));
        casesPanel.add(createInfoPanel("Stop", "Passer sur ces casses bloque momentairement le joueur\nexemple: fleurs", "Shooter/res/21.png"));
    
        // Scroll panes pour chaque pane
        JScrollPane enemiesScrollPane = new JScrollPane(enemiesPanel);
        JScrollPane weaponsScrollPane = new JScrollPane(weaponsPanel);
        JScrollPane casesScrollPane = new JScrollPane(casesPanel);
    
        // Tabbed pane 
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(shooterFont2.deriveFont(30f));
        tabbedPane.setForeground(colourL1);
        tabbedPane.addTab("Enemies", enemiesScrollPane);
        tabbedPane.addTab("Weapons", weaponsScrollPane);
        tabbedPane.addTab("Cases", casesScrollPane);
    
        add(tabbedPane, BorderLayout.CENTER);
        add(createButton("Menu", "Menu"), BorderLayout.SOUTH);
    }
    
    

    private JPanel createInfoPanel(String title, String description, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(colourD1);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(shooterFont2.deriveFont(30f));
        titleLabel.setForeground(colourL1);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
    
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath);
    
        if (icon != null && icon.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            imageLabel = new JLabel(scaledIcon);
        } else {
            // System.out.println("Failed to load image: " + imagePath);
            // imageLabel = new JLabel("Image not found");
        }
    
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);
    
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setLineWrap(true);
        descriptionArea.setForeground(colourL2);
        descriptionArea.setBackground(colourD2);
        descriptionArea.setFont(shooterFont3.deriveFont(20f));
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        panel.add(scrollPane, BorderLayout.SOUTH);
    
        return panel;
    }
    
    
    

}
