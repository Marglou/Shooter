package Shooter.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import Shooter.model.Game;
import Shooter.model.SoundPlayer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

public class MiniHistoire extends GameScene {

    private JButton histoire;
    private static JPanel panelTexte;
    private JLabel lblTexte;
    private Image backgroundImage;
    private static Image overlayImage; // Image overlay
    private int index = 0; // Index de la phrase actuelle
    private boolean introSkip = true; // Variable pour vérifier si l'intro a été skip
    private SoundPlayer soundPlayer;
    private String soundFilePath = "Shooter/res/Son/introSon.wav"; // Chemin du fichier audio

    private String[] phrases = {
            "Sam, notre jeune héros, vivait dans un village paisible en France.",
            "Cependant,après la mort de ses parents, il quitta sa maison d'enfance...",
            "... et parta poursuivre sa vie à la découverte du monde.",
            "Après des années, il décide de revenir dans sa ville natale.",
            "Mais son retour ne se passe pas comme prévu...",
            "Il découvre que son village autrefois paisible a été envahi !",
            "Determiné,Sam décide de prendre les armes et de combattre les envahisseurs...",
            "A vous de jouer maintenant !"
    };

    private int charIndex = 0; // Index du caractère en cours d'affichage
    private Timer timer; // Timer pour afficher progressivement les caractères
    private Timer skipButtonTimer;

    public MiniHistoire(Game game) {
        super(game);

        setLayout(null);
        setPanelSize();

        loadBackgroundImage();
        loadOverlayImage();

        soundPlayer = new SoundPlayer(); // Initialiser le SoundPlayer

        // Ajout du bouton pour passer l'histoire
        JButton btnSkip = createButton("Skip intro");
        btnSkip.setBounds(1180, 20, 200, 35);
        btnSkip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MiniHistoire.this.introSkip = false; // Utilisez MiniHistoire.this pour référencer la variable de classe
                if (timer != null && timer.isRunning()) {
                    timer.stop(); // Arrêter le timer de l'affichage progressif des caractères
                }
                soundPlayer.stop(); // Arrêter la musique
                game.cardLayout.show(game.cardPanel, "FirstPage");
            }
        });
        // Démarrer un Timer pour ajouter le bouton après 5 secondes
        this.skipButtonTimer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
                add(btnSkip);
                revalidate(); // Réorganiser les composants pour refléter l'ajout du bouton
                repaint(); // Repeindre le panneau pour afficher le bouton
            }
        });
        skipButtonTimer.setRepeats(false); // Assurez-vous que le Timer ne se répète pas

        histoire = createButton("Start story");
        histoire.setBounds(600, 600, 200, 35);
        add(histoire, BorderLayout.CENTER);
        histoire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(histoire);
                MiniHistoire.overlayImage = null; // Supprimer l'image overlay
                MiniHistoire.panelTexte = createPanelTexte();
                MiniHistoire.panelTexte.setBounds(600, 680, 800, 100);
                add(MiniHistoire.panelTexte);
                afficherProchainePhrase();
                soundPlayer.playSound(soundFilePath); // Démarrer la musique
                skipButtonTimer.start();
                revalidate();
                repaint();
            }
        });
    }

    private void setPanelSize() {
        setMinimumSize(game.size_screen);
        setPreferredSize(game.size_screen);
        setMaximumSize(game.size_screen);
    }

    private JPanel createPanelTexte() {
        JPanel panelTexte = new JPanel();
        panelTexte.setLayout(null);

        // Créer une couleur semi-transparente
        panelTexte.setBackground(colourD1);

        lblTexte = new JLabel("");
        lblTexte.setBounds(10, 0, 780, 100);
        lblTexte.setForeground(colourL1);
        lblTexte.setFont(shooterFont.deriveFont(35f)); // Définir une police pour le texte
        panelTexte.add(lblTexte);

        return panelTexte;
    }

    private void updateImg() {
        loadBackgroundImage();
        repaint();
    }

    private void afficherProchainePhrase() {
        if (introSkip) {
            updateImg();
            if (index < phrases.length) {
                String phraseActuelle = phrases[index++];
                charIndex = 0; // Réinitialiser l'index du caractère
                timer = new Timer(50, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (charIndex <= phraseActuelle.length()) {
                            lblTexte.setText(phraseActuelle.substring(0, charIndex++));
                        } else {
                            ((Timer) e.getSource()).stop(); // Arrêter le timer une fois que toute la phrase est affichée

                            // Attendre quelque temps avant d'afficher la phrase suivante
                            Timer nextPhraseTimer = new Timer(2000, new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    ((Timer) e.getSource()).stop();
                                    afficherProchainePhrase(); // Afficher la phrase suivante
                                }
                            });
                            nextPhraseTimer.start(); // Attendre quelque temps avant d'afficher la phrase suivante
                        }
                    }
                });
                timer.start(); // Démarrer le timer pour afficher progressivement les caractères

            } else {
                Timer finHistoireTimer = new Timer(3000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!MiniHistoire.this.introSkip)
                            return; // Ne pas continuer si l'intro a été skippée
                        ((Timer) e.getSource()).stop();
                        soundPlayer.stop(); // Arrêter la musique
                        game.cardLayout.show(game.cardPanel, "FirstPage");
                    }
                });
                finHistoireTimer.start(); // Attendre quelque temps avant de passer à la page suivante
            }
        }
    }

    protected void loadBackgroundImage() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("../res/Story/img_hist_" + index + ".jpg");
            if (inputStream != null) {
                backgroundImage = ImageIO.read(inputStream);
            } else {
                System.out.println("Image not found at path: Shooter/res/Story/img_hist_" + index + ".jpg");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadOverlayImage() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("../res/Story/HOME.png");
            if (inputStream != null) {
                overlayImage = ImageIO.read(inputStream);
            } else {
                System.out.println("Image not found at path: Shooter/res/Story/img_hist_overlay_" + index + ".png");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        if (overlayImage != null && introSkip) {
            int x = 585; // Position x de l'image supplémentaire
            int y = 200; // Position y de l'image supplémentaire
            int width = 285; // Largeur de l'image supplémentaire
            int height = 260; // Hauteur de l'image supplémentaire
            g.drawImage(overlayImage, x, y, width, height, this);
        }
    }
}
