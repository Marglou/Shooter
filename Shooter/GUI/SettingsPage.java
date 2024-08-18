package Shooter.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Shooter.model.Game;
import Shooter.model.SoundPlayer;

public class SettingsPage extends GameScene {

    private SoundToggleButton soundToggleButton;
    protected SoundPlayer soundPlayer = new SoundPlayer();

    public SettingsPage(Game game) {
        super(game);
        initUI();
        loadBackgroundImage(1);
    }

    private void initUI() {
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(shooterFont);
        titleLabel.setForeground(colourL2);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        add(titleLabel, gbc);

        ImageIcon soundOnIcon = new ImageIcon("Shooter/res/soundOn.jpg");
        ImageIcon muteIcon = new ImageIcon("Shooter/res/mute.png");
        soundOnIcon = scaleImageIcon(soundOnIcon, 50, 50);
        muteIcon = scaleImageIcon(muteIcon, 50, 50);
        soundToggleButton = new SoundToggleButton("Activer son", "Désactiver son", soundOnIcon, muteIcon);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        add(soundToggleButton, gbc);

        // ------- Sauvegarde ---------------
        JButton saveButton = createButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePlayer();
            }
        });
        gbc.gridy++;
        add(saveButton, gbc);

        // Panel avec les règles du jeu
        JPanel rulesPanel = new JPanel();
        rulesPanel.setPreferredSize(new Dimension(700, 300));
        rulesPanel.setBackground(colourL3);
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.Y_AXIS)); // Vertical layout
        JLabel titleLabel2 = new JLabel("Règles");
        titleLabel2.setFont(shooterFont3.deriveFont(30f)); // Adjust font size
        titleLabel2.setForeground(colourL1);
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the title

        rulesPanel.add(titleLabel2);
        rulesPanel.add(ruleLabelMaker(
                "Pour gagner tu dois traverser toutes les pièces de la maison et éliminer tous les ennemis!", gbc,
                rulesPanel));
        rulesPanel.add(ruleLabelMaker("Pour accomplir cette mission, tu peux:", gbc, rulesPanel));
        rulesPanel.add(ruleLabelMaker("~ Te déplacer en utilisant les flèches du clavier", gbc, rulesPanel));
        rulesPanel.add(ruleLabelMaker("~ Viser ton arme avec le curseur", gbc, rulesPanel));
        rulesPanel.add(ruleLabelMaker("~ Changer d'arme avec la touche d'espace", gbc, rulesPanel));
        rulesPanel.add(ruleLabelMaker(
                "Tu peux vérifier tes stats et l'arme à main dans le carré en haut à droite de l'écran pendant la partie",
                gbc, rulesPanel));

        add(rulesPanel, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(createButton("Menu", "Menu"), gbc);
        gbc.gridy++;
        add(createButton("Game Stats", "Info"), gbc);
    }

    private void savePlayer() {
        createFile(game.getPlayer().getCreativeLevel());
        createFile(game.getPlayer().getEnemyLevel());
        copyFile("Shooter/factory/creativeTmp.txt", game.getPlayer().getCreativeLevel());
        copyFile("Shooter/factory/enemyTmp.txt", game.getPlayer().getEnemyLevel());
        game.getPlayer().sauvegarderJoueur("Shooter/Sauvegardes/" + game.getPlayer().getPseudo() + ".ser");
        System.out.println("Player saved");
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private JLabel ruleLabelMaker(String txt, GridBagConstraints gbc, JPanel rulesPanel) {
        JLabel rule1Label = new JLabel(txt);
        rule1Label.setFont(shooterFont3.deriveFont(25f)); // Adjust font size
        rule1Label.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the rule label
        rule1Label.setForeground(colourL1);
        rulesPanel.add(rule1Label); // Add the rule label to the rules panel
        gbc.gridy++;
        return rule1Label;
    }

    public class SoundToggleButton extends JButton {
        private boolean soundEnabled;
        private ImageIcon soundEnabledIcon;
        private ImageIcon soundDisabledIcon;

        public SoundToggleButton(String enableText, String disableText, ImageIcon enabledIcon, ImageIcon disabledIcon) {
            soundEnabled = false;
            soundEnabledIcon = enabledIcon;
            soundDisabledIcon = disabledIcon;
            updateIcon();

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    soundEnabled = !soundEnabled;
                    soundPlayer.manuallyStopped = false;
                    updateIcon();
                    game.updateSound(soundEnabled);
                }
            });
        }

        private void updateIcon() {
            if (soundEnabled) {
                setIcon(soundEnabledIcon);
                soundPlayer.playSound("Shooter/res/Son/geometryDash.wav");
            } else {
                setIcon(soundDisabledIcon);
                soundPlayer.stop();
            }
        }
    }
}