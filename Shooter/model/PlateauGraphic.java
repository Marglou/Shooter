package Shooter.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class PlateauGraphic extends JPanel {

    private Plateau plateau;

    public PlateauGraphic(Plateau p) {
        this.plateau = p;
    }

    public void render_plateau(Graphics g) {
        for (int y = 0; y < plateau.level_tab.length; y++) {
            for (int x = 0; x < plateau.level_tab[y].length; x++) {
                int id = plateau.level_tab[y][x];
                g.drawImage(plateau.tile_manager.getSprite(id), x * 40, y * 40, null);
            }
        }
        this.plateau.plateau_graphic = g;
        this.plateau.floodFillManager.updateFloodFill(this.plateau.gameManager.getPlayer().getY() / 40,
                this.plateau.gameManager.getPlayer().getX() / 40);

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);

        for (Piege piege : plateau.gameManager.getProjectilesManager().getPieges()) {
            piege.paintPiege(g, plateau.gameManager);
        }

        // Dessiner les balles du joueur
        for (Bullet playerBullet : plateau.gameManager.getProjectilesManager().getPlayerBullets()) {
            playerBullet.createBullet(g);
        }

        // Dessiner les balles des ennemis
        for (Bullet enemyBullet : plateau.gameManager.getProjectilesManager().getEnemyBullets()) {
            enemyBullet.createBullet(g);
        }

        // Create a new Graphics2D object for gameManager.getPlayer()
        Graphics2D gPlayer = (Graphics2D) g.create();

        if (plateau.playerPositions.size() > 0) {
            for (TimePoint position : plateau.playerPositions) {
                if (position.isDrawing) {
                    position.draw(g);
                }
            }
        }

        if (plateau.debris.size() > 0) {
            for (TimePoint positiondebris : plateau.debris) {
                if (positiondebris.isDrawing) {
                    positiondebris.drawDebris(g);
                }
            }
        }

        drawPlayerMovement(gPlayer);

        // Dessiner les ennemis
        for (Personnage perso : plateau.gameManager.getEnnemiManager().getPerso_list()) {
            if (perso instanceof Enemy) {
                Enemy ennemi = (Enemy) perso;
                ennemi.drawEnemy(g, ennemi.getanimationIndexE());
                ennemi.drawBarVie(g);
            }
        }

        // print arme et nombre munitions
        int currentArme = plateau.gameManager.getPlayer().currentArme;
        Armes armeCourante = plateau.gameManager.getPlayer().armes.get(currentArme);

        // Définir les polices
        Font titleFont = new Font("Arial", Font.BOLD, 18);
        Font infoFont = new Font("Arial", Font.PLAIN, 14);

        // Couleurs
        Color backgroundColor = new Color(50, 50, 50, 50); // Gris foncé semi-transparent
        Color titleColor = Color.YELLOW; // Jaune vif pour les titres
        Color infoColor = Color.WHITE; // Blanc pour les informations
        Color redColor = Color.RED; // Rouge pour les munitions à 10

        // Dessiner le fond semi-transparent pour les informations
        g.setColor(backgroundColor);
        g.fillRect(1300, 1, 200, 120);
        // Activer l'anti-aliasing pour des bordures et du texte plus lisses
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner un rectangle arrondi avec un gradient comme fond pour les
        // informations
        GradientPaint gradient = new GradientPaint(1300, 1, new Color(70, 70, 70, 200), 1500, 120,
                new Color(30, 30, 30, 200));
        g2d.setPaint(gradient);
        RoundRectangle2D roundedRect = new RoundRectangle2D.Double(1300, 1, 200, 120, 15, 15);
        g2d.fill(roundedRect);

        // Dessiner la bordure du rectangle arrondi
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(roundedRect);

        // Dessiner le titre "ARME" avec une police stylisée
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("ARME", 1310, 20);

        // Dessiner l'image de l'arme si disponible
        if (armeCourante.getTypeMunition() >= 0) {
            g.drawImage(plateau.imageArmes[armeCourante.getTypeMunition()], 1375, 29,60,60, null);
        }

        // Afficher le nom de l'arme avec une police stylisée
        g.setFont(infoFont);
        g.setColor(infoColor);
        if (currentArme >= 0 && currentArme < plateau.gameManager.getPlayer().getArmes().size()) {
            g.drawString(armeCourante.nom, 1330, 37);
            g.drawString("munitions:", 1310, 55);

            // Vérifier si le nombre de munitions est égal à 10
            if (armeCourante.getMunition() <= 10) {
                // Utiliser une temporisation pour faire clignoter le chiffre des munitions
                int blinkCounter = (int) (System.currentTimeMillis() / 500) % 2;
                if (blinkCounter == 0) {
                    g.setColor(redColor); // Couleur rouge
                } else {
                    g.setColor(infoColor); // Couleur normale
                }
            } else {
                g.setColor(infoColor); // Couleur normale
            }

            g.drawString(Integer.toString(armeCourante.getMunition()), 1330, 70);
        }

        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("VIE", 1310, 90);

        plateau.gameManager.getPlayer().drawBarVie(g);

        // Dessin crosshair
        plateau.gameManager.getMyMouseListener().getCrosshair().draw(g);

        int centerX = (int) plateau.gameManager.getPlayer().getX() - armeCourante.distance;
        int centerY = (int) plateau.gameManager.getPlayer().getY() - armeCourante.distance;

        // Définir la couleur du trait
        g2d.setColor(Color.RED);

        // Définir le style de trait discontinu
        float[] dashPattern = { 5, 5 }; // Exemple : alternance de 5 pixels pleins et 5 pixels vides
        g2d.setStroke(
                (Stroke) new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dashPattern, 0.0f));
        g2d.drawOval(centerX, centerY, armeCourante.distance * 2, armeCourante.distance * 2);

    }

    private void drawPlayerMovement(Graphics2D g) {

        double scale = 5;
        int newWidth = (int) (plateau.gameManager.getPlayer().getSize() * scale);
        int newHeight = (int) (plateau.gameManager.getPlayer().getSize() * scale);

        double x = plateau.gameManager.getPlayer().getX();
        double y = plateau.gameManager.getPlayer().getY();

        addPlayerPosition((int) x, (int) y);

        AffineTransform at = new AffineTransform();
        at.translate(x - newWidth / 2, y - newHeight / 2);
        at.rotate(plateau.gameManager.getPlayer().getDirection(), newWidth / 2, newHeight / 2);
        g.drawImage(plateau.gameManager.getPlayerManager().imagePlayer(plateau.animationIndex), at, null);
        if (plateau.gameManager.getPlayer().getXSpeed() != 0 || plateau.gameManager.getPlayer().getYSpeed() != 0) {
            updateTick();
        }
    }

    public void addPlayerPosition(int x, int y) {
        if (plateau.playerPositions.size() != 0) {
            if (System.currentTimeMillis()
                    - plateau.playerPositions.get(plateau.playerPositions.size() - 1).timing >= 500) {
                plateau.playerPositions.add(new TimePoint(x, y, plateau.isGoingRight));
                plateau.isGoingRight = !(plateau.isGoingRight);
            }
        }
    }

    protected void updateTick() {
        plateau.tick++;
        // cela régule la vitesse de l'animation, plus le nombre est élevé, plus
        // l'animation est lente
        if (plateau.tick >= plateau.ANIMATION_SPEED) {
            plateau.tick = 0;
            plateau.animationIndex++;
            if (plateau.animationIndex >= 3) {
                plateau.animationIndex = 0;
            }
        }
    }

    public void update_pleateau(int x, int y, int type_case) {
        plateau.plateau_graphic.drawImage(plateau.tile_manager.getSprite(type_case), x * 40, y * 40, null);
        plateau.clearOldPosition();
        plateau.clearOldPositionDebris();
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

}