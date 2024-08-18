package Shooter.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Shooter.Managers.GameManager;

// Mine => explose si un personnage passe dessus
public class A3 extends Piege {

    public A3() {
        super("Mine");
        this.typeMunition = 3;
        this.distance = 150;

    }

    public A3(int x, int y) {
        super("Mine", x, y);
        this.typeMunition = 3;
        this.distance = 150;
        this.piegeImages = loadImage();
        this.piegeExplosion = loadExplosion();
    }

    @Override
    public BufferedImage[] loadImage() {
        BufferedImage atlas = Enregistrement.getSpriteAtlas();
        BufferedImage[] piegeImages = new BufferedImage[1];
        piegeImages[0] = atlas.getSubimage(6 * 40, 16 * 40, 40, 40);
        return piegeImages;
    }

    @Override
    public BufferedImage[] loadExplosion() {
        BufferedImage atlas = Enregistrement.getSpriteAtlas();
        BufferedImage[] piegeExplosion = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            piegeExplosion[i] = atlas.getSubimage(i * 40, 18 * 40, 40, 40);
        }
        return piegeExplosion;
    }

    @Override
    public Bullet tirer(int playerX, int playerY, int destX, int destY) {
        return null;
    }

    @Override
    public void posePiege(int xCrosshair, int yCrosshair, int xPlayer, int yPlayer, int[][] plateau, Plateau p) {

        int xCrosshairTile = adjustCoordForTile(xCrosshair);
        int yCrosshairTile = adjustCoordForTile(yCrosshair);

        if (!isAWall(xCrosshairTile, yCrosshairTile, plateau)
                && !isWallBetween(xCrosshairTile, yCrosshairTile, xPlayer, yPlayer, plateau)) {
            // p.pieges.add(new A3(xCrosshair, yCrosshair));
            p.gameManager.getProjectilesManager().getPieges().add(new A3(xCrosshair, yCrosshair));
            p.gameManager.getPlayer().getArmes().get(p.gameManager.getPlayer().getCurrentArme()).shoot();
        }

    }

    @Override
    public void paintPiege(Graphics g, GameManager gameManager) {
        paintComponent(g);
        explosion(gameManager, g);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(this.piegeImages[0], x, y, 40, 40, null);
        g2d.dispose();

    }

    public void paintComponentExplosion(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        setDimension(0);

        if (explosionFrameIndex < piegeExplosion.length - 1) {
            g2d.drawImage(piegeExplosion[explosionFrameIndex], x, y, 3 * 40, 3 * 40, null);
            explosionFrameIndex++;
        }

        g2d.dispose();
    }

    public void explosion(GameManager gameManager, Graphics g) {
        for (Personnage perso : gameManager.getPersoList()) {
            if (perso.detectCollision(x, y, dimension)) {
                paintComponentExplosion(g);
                perso.infligerDegats(power);
            }
        }
    }


    // --------- GETTERS et SETTERS -----------

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

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

}
