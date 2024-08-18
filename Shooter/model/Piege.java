package Shooter.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Shooter.Managers.GameManager;
import Shooter.Managers.ManagerCase;

public abstract class Piege extends Armes {

    protected int x;
    protected int y;
    protected int explosionFrameIndex = 0;
    protected int check = 0;
    protected int dimension = 40;
    protected transient BufferedImage[] piegeImages;
    protected transient BufferedImage[] piegeExplosion;

    public Piege(String nom) {
        super(nom, 30, false, 5, 3, 2000, 150, true);
        // piegeImages = loadImage();
        // piegeExplosion = loadExplosion();
    }

    public Piege(String nom, int x, int y) {
        super(nom, 50, false, 5, 3, 2000, 150, true);
        this.x = x;
        this.y = y;
        piegeImages = loadImage();
        piegeExplosion = loadExplosion();
        // ajouter dans le constructeur les BufferedIMage ? pb avec A4 mais pas A3
    }

    public abstract BufferedImage[] loadImage();

    public abstract BufferedImage[] loadExplosion();

    public abstract void paintPiege(Graphics g, GameManager gameManager);

    

    public abstract void posePiege(int xCrosshair, int yCrosshair, int xPlayer, int yPlayer, int[][] plateau,
            Plateau p);

    public boolean distanceToPlayer(int xPiege, int yPiege, int playerX, int playerY) {
        // Calcul de la distance euclidienne entre la position actuelle et la position
        // du joueur
        double distanceToPlayer = Math.sqrt(Math.pow(xPiege - playerX, 2) + Math.pow(yPiege - playerY, 2));

        // Vérification si la distance est inférieure au seuil spécifié
        return distanceToPlayer <= this.distance;
    }

    public boolean isInPlateau(int xCrosshair, int yCrosshair, int[][] plateau) {
        return (xCrosshair >= 0 && xCrosshair < plateau[0].length
                && yCrosshair >= 0
                && yCrosshair < plateau.length);
    }

    public boolean isWallBetween(int xCrosshair, int yCrosshair, int xPlayer, int yPlayer, int[][] plateau) {
        // Vérifier si les coordonnées du joueur et de la cible sont les mêmes
        if (xCrosshair == xPlayer && yCrosshair == yPlayer) {
            // Les coordonnées sont les mêmes, pas besoin de vérifier les murs entre elles
            return false;
        }

        // Déterminer les bornes du parcours du plateau entre les deux points
        int startX = Math.min(xPlayer, xCrosshair);
        int endX = Math.max(xPlayer, xCrosshair);
        int startY = Math.min(yPlayer, yCrosshair);
        int endY = Math.max(yPlayer, yCrosshair);

        // Parcourir le plateau entre les deux points
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                // Vérifier si la case est un mur ou un obstacle
                int caseType = ManagerCase.getCaseType(plateau[y][x]);
                if (caseType == ManagerCase.MUR || caseType == ManagerCase.MUR_CASSANT|| caseType == ManagerCase.CASSANT) {
                    return true;
                }
            }
        }
        // Aucun mur ou obstacle trouvé entre les deux points
        return false;
    }

    public int adjustCoordForTile(double coord) {
        double tileSize = 40.0;
        double halfTile = tileSize / 2.0;
        double positionInTile = coord % tileSize;

        if (positionInTile < halfTile) {
            return (int) Math.floor(coord / tileSize);
        } else {
            return (int) Math.ceil(coord / tileSize);
        }
    }

    public boolean isAWall(int x, int y, int[][] plateau) {

        int caseType = ManagerCase.getCaseType(plateau[y][x]);
        if (caseType == ManagerCase.MUR || caseType == ManagerCase.BLOQUE || caseType == ManagerCase.MUR_CASSANT
                || caseType == ManagerCase.CASSANT) {
            return true;
        }
        return false;
    }


    // ---------- Getters and Setters ----------

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

}
