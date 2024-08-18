package Shooter.GUI;

import Shooter.model.Game;
import Shooter.model.Player;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameScene extends JPanel {
    public Game game;

    // Constantes pour les couleurs et fonts
    protected Font shooterFont = loadShooterFont(1);
    protected Font shooterFont2=loadShooterFont(2);
    protected Font shooterFont3=loadShooterFont(3);
    protected Image backgroundImage;

    protected static final Color colourL1 = new Color(32, 53, 96); // RGB: Dark blue color
    protected static final Color colourL2 = Color.WHITE; // RGB: White color
    protected static final Color colourL3 = new Color(188, 217, 245); // RGB: Light blue color
    protected static final Color colourD1 = new Color(115, 156, 189, 200); // RGB: Semi-transparent blue color
    protected static final Color colourD2 = new Color(55, 85, 108, 200); // RGB: Semi-transparent dark blue color

    protected static final LineBorder line1 = new LineBorder(colourL1);
    protected static final LineBorder line2 = new LineBorder(colourL2);

    public GameScene(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public JButtonStyled createButton(String text, String pageName) {
        JButtonStyled button = new JButtonStyled(text, shooterFont);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = game.cardLayout;
                JPanel panel = game.cardPanel;
                if (pageName.equals("Play") && !text.equals("Creative")) {
                    if (game.gameManager.getPlayer().getLevel() < game.nbLevel && !game.rejouer
                            || game.rejouer && game.rejouerLevel != -1) {
                                game.getPlayer().resetLevelCreatif();
                        game.gameTxtSource = false;
                        game.initialisation();
                        game.begin = true;

                    } else {
                        game.begin = false;
                        game.cardLayout.show(game.cardPanel, "ChoixLevel");
                        return;
                    }
                } else if (text.equals("Creative")) {
                    game.getPlayer().resetLevelCreatif();
                    if (isFileEmpty("Shooter/factory/creativeTmp.txt")) {
                        layout.show(panel, "Error");
                        return;
                    } else {
                        game.gameTxtSource = true;
                        game.initialisation();
                        game.begin = true;
                    }
                } else if (pageName.equals("Menu") && game.isBegin()) {
                    game.gameManager.reset();
                    game.begin = false;
                    game.rejouerLevel = -1;
                }
                layout.show(panel, pageName);
            }
        });
        return button;
    }

    // -------------- Manipulation de fichiers --------------------

    protected static boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return (file.length() == 0);
    }

    protected static void emptyFile(String filePath) {
        File file = new File(filePath);

        try (FileWriter fileWriter = new FileWriter(file)) {
            // Ouvrir le fichier en mode écriture vide son contenu
            // Aucun contenu n'est écrit dans le fichier, il est donc vidé
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void eraseFiles(Player player) {
        eraseFile(player.getCreativeLevel());
        eraseFile(player.getEnemyLevel());
        eraseFile("Shooter/Sauvegardes/" + player.getPseudo() + ".ser");
    }

    protected void eraseFile(String fileName) {
        File file = new File(fileName);

        if (file.delete()) {
            // System.out.println("Fichier effacé : " + file.getName());
        } else {
            //System.out.println("Erreur lors de la suppression du fichier.");
        }
    }

    protected void emptyFile() {
        emptyFile("Shooter/factory/enemyTmp.txt");
        emptyFile("Shooter/factory/creativeTmp.txt");
    }

    protected void copyFile(String src, String dest) {
       
        try (FileInputStream fis = new FileInputStream(src);
                FileOutputStream fos = new FileOutputStream(dest)) {

            byte[] buffer = new byte[1024]; // Tampon de 1 Ko
            int bytesRead;

            // Lire depuis le fichier source et écrire dans le fichier de destination
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la copie du fichier de " + src + " à " + dest + " : " + e.getMessage());
        }

    }

    protected void createFile (String fileName){
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                // System.out.println("Fichier créé : " + file.getName());
            } else {
                // System.out.println("Le fichier existe déjà.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------------

    public JButtonStyled createButtonExit(String text) {
        JButtonStyled button = new JButtonStyled(text, shooterFont);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyFile("Shooter/factory/creativeTmp.txt");
                emptyFile("Shooter/factory/enemyTmp.txt");
                System.exit(0);
            }
        });
        return button;
    }

    public JButtonStyled createButton(String text) {
        JButtonStyled button = new JButtonStyled(text, shooterFont);
        return button;
    }

    protected Font loadShooterFont(int i) {
        try {
            InputStream fontStream = getClass().getResourceAsStream("../../fonts/Font" + i + ".otf");
            if (fontStream == null) {
                throw new FileNotFoundException("Fichier de police introuvable.");
            }
            return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(130f);
        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'erreur : police par défaut
            return new Font("SansSerif", Font.PLAIN, 14);
        }
    }

    protected void loadBackgroundImage(int i) {
        try {
            InputStream inputStream;
            switch (i) {
                case 1:
                    inputStream = getClass().getResourceAsStream("../res/background.jpg");
                    break;
                case 2:
                    inputStream = getClass().getResourceAsStream("../res/background2.jpg");
                    break;
                case 3:
                    inputStream = getClass().getResourceAsStream("../res/background3.jpg");
                    break;
                default:
                    inputStream = getClass().getResourceAsStream("../res/background.jpg");
            }
            if (inputStream != null) {
                backgroundImage = ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public class JButtonStyled extends JButton {
        private int id;

        public JButtonStyled(String text, Font font) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setForeground(colourL1);
            setBackground(colourD1);
            setFont(font.deriveFont(Font.PLAIN, 45));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(line1);
            adjustButtonSize();
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(colourD2);
                    setForeground(colourL2);
                    setBorder(line2);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(colourD1);
                    setForeground(colourL1);
                    setBorder(line1);
                }
            });
        }

        JButtonStyled(int id, Icon icon) {
            super(icon);
            this.id = id;
            setBackground(colourL3);
        }

        private void adjustButtonSize() {
            setVerticalTextPosition(SwingConstants.CENTER);
            setMargin(new Insets(0, 0, 80, 0)); // Increase bottom margin
            FontMetrics metrics = getFontMetrics(getFont());
            int textWidth = metrics.stringWidth(getText());
            int textHeight = metrics.getHeight();
            int extraWidth = 0;
            int extraHeight = -30; // Increase extra height
            int buttonWidth = Math.max(textWidth + extraWidth, 150);
            int buttonHeight = Math.max(textHeight + extraHeight, 40);
            setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
