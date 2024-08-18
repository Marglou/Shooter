package Shooter.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import Shooter.Managers.GameManager;
import Shooter.Managers.ManagerCase;

public class A4 extends Piege {

    private int x;
    private int y;
    private transient BufferedImage[] grenadeImage;
    private transient BufferedImage[] explosionImage;
    private boolean isActivated = false;
    private boolean isExploding = false;
    private int explosionFrameIndex = 0;
    private int check = 0;
    private Timer explosionTimer;
    private Timer explosionAnimationTimer;

    public A4(int x, int y) {
        super("grenade", x, y);
        this.distance = 400;
        this.typeMunition = 4;
        this.x = x;
        this.y = y;
        this.explosionImage = loadExplosion();
        this.grenadeImage = loadImage();
    }

    public A4() {
        super("grenade");
        this.distance = 400;
        this.typeMunition = 4;
    }

    @Override
    public BufferedImage[] loadImage() {
        BufferedImage atlas = Enregistrement.getSpriteAtlas();
        BufferedImage[] piegeImages = new BufferedImage[1];
        piegeImages[0] = atlas.getSubimage(4 * 40, 16 * 40, 40, 40);
        return piegeImages;
    }

    @Override
    public BufferedImage[] loadExplosion() {
        BufferedImage atlas = Enregistrement.getSpriteAtlas();
        BufferedImage[] piegeExplosion = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            piegeExplosion[i] = atlas.getSubimage(i * 40, 18* 40, 40, 40);
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
            // p.pieges.add(new A4(xCrosshair, yCrosshair));
            p.gameManager.getProjectilesManager().getPieges().add(new A4(xCrosshair, yCrosshair));
            p.gameManager.getPlayer().getArmes().get(p.gameManager.getPlayer().getCurrentArme()).shoot();
        }

    }

    // public void loadGrenadeImage() {
    // BufferedImage atlas = Enregistrement.getSpriteAtlas();
    // grenadeImage = new BufferedImage[1];
    // grenadeImage[0] = atlas.getSubimage(3 * 40, 8 * 40, 40, 40);
    // }

    // public void loadExplosionImage() {
    // BufferedImage atlas = Enregistrement.getSpriteAtlas();
    // explosionImage = new BufferedImage[5];
    // for (int i = 0; i < 5; i++) {
    // explosionImage[i] = atlas.getSubimage(i * 40, 9 * 40, 40, 40);
    // }
    // }

    @Override
    public void paintPiege(Graphics g, GameManager gameManager) {
        this.paintComponent(g);
        this.explosion(gameManager);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        switch (check) {
            case 0:
                g2d.drawImage(grenadeImage[0], x, y, 40, 40, null);
                explosionTimer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (explosionFrameIndex < explosionImage.length - 1) {
                            explosionFrameIndex++;
                            check = 1;
                        } else {
                            explosionTimer.stop();
                            isExploding = false;
                            check = 2;
                        }
                    }
                });
                explosionAnimationTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        explosionTimer.start();
                        explosionAnimationTimer.stop();
                    }
                });
                explosionAnimationTimer.start();

                break;

            case 1:
                isExploding = true;
                g2d.drawImage(explosionImage[explosionFrameIndex], x, y, 2 * 40, 2 * 40, null);
                if (explosionFrameIndex == explosionImage.length - 1) {
                    isExploding = false;
                    check = 2;
                }
                break;
        }

        g2d.dispose();

    }

    public void explosion(GameManager gameManager) {
        if (isExploding) {
            // degat au personnge
            for (Personnage personnage : gameManager.getEnnemiManager().getPerso_list()) {
                double distance = Math.sqrt(Math.pow(personnage.getX() - this.x, 2)
                        + Math.pow(personnage.getY() - this.y, 2));
                if (distance <= 75) {
                    personnage.infligerDegats(80);
                }
            }
            // degat au mur
            int grenadeX = (int) (this.x / 40);
            int grenadeY = (int) (this.y / 40);

            for (int x = grenadeX - 2; x <= grenadeX + 2; x++) {
                for (int y = grenadeY - 2; y <= grenadeY + 2; y++) {

                    if (x >= 0 && x < gameManager.getGamePlateau().level_tab[0].length &&
                            y >= 0 && y < gameManager.getGamePlateau().level_tab.length) {
                        double distance = Math.sqrt(Math.pow(x - grenadeX, 2) + Math.pow(y - grenadeY, 2));
                        if (distance <= 2) {
                            int caseID = gameManager.getGamePlateau().level_tab[y][x];
                            int caseType = ManagerCase.getCaseType(caseID);
                            if (caseType == ManagerCase.CASSANT || caseType == ManagerCase.MUR_CASSANT) {
                                gameManager.getGamePlateau().level_tab[y][x] = ManagerCase.SOL;
                            }
                        }
                    }

                }
            }
            setDimension(0);
        }
    }

    // -----------------GETTER & SETTER---------------------------\\\
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getIsActivated() {
        return isActivated;
    }

    public boolean getIsExploding() {
        return isExploding;
    }
}
