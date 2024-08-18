package Shooter.Managers;

import Shooter.model.Enemy;
import Shooter.model.Personnage;
import Shooter.model.Piege;
import Shooter.model.Player;
import Shooter.model.Bullet;
import Shooter.model.A4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectilesManager {

    protected PlayerManager m;
    protected EnnemiManager ennemiManager;
    protected Player player;
    protected ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
    protected ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
    protected ArrayList<Piege> pieges;
    protected List<Personnage> enemies;
    protected GameManager gameManager;
    protected int[][] level_tab;

    public ProjectilesManager(Player player, GameManager gameManager) {
        this.player = player;
        this.gameManager = gameManager;
        this.level_tab = gameManager.getGamePlateau().getLevel_tab();
        this.pieges = gameManager.getGamePlateau().getPieges();
    }

    public void reset (){
        playerBullets.clear();
        enemyBullets.clear();
        this.level_tab = gameManager.getGamePlateau().getLevel_tab();
        this.pieges = gameManager.getGamePlateau().getPieges();
    }

    private void removeOutOfBoundsBullets(ArrayList<Bullet> bullets) {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (bullet.isOutOfBounds() || bullet.getSize() == 0
                    || bullet.getDistanceTraveled() >= player.getArmes().get(player.getCurrentArme()).getDistance()) {
                it.remove();
            }
        }
    }

    private void handleBulletCollisions() {
        for (Bullet bullet : playerBullets) {
            handleWallCollisions(bullet);

            for (Personnage enemy : gameManager.getEnnemiManager().getPerso_list()) {
                if (enemy instanceof Enemy) {
                    if (enemy.detectCollision(bullet.getX(), bullet.getY(), bullet.getSize())) {
                        enemy.infligerDegats(bullet.getDegats());
                        bullet.setSize(0);
                    }
                }
            }
        }

        for (Bullet bullet : enemyBullets) {
            if (player.detectCollision(bullet.getX(), bullet.getY(), bullet.getSize()) && bullet.getSize() != 0) {
                player.infligerDegats(bullet.getDegats());
                bullet.setSize(0);
            }
        }
    }

    // public void suppBulletEnnemi() {
    //     // Supprimer les projectiles hors des limites
    //     Iterator<Bullet> it = enemyBullets.iterator();
    //     while (it.hasNext()) {
    //         Bullet bullet = it.next();
    //         // Vérifiez la collision avec le mur
    //         if (bullet.isOutOfBounds() || bullet.getSize() == 0 ) {
    //             it.remove();
    //         }
    //     }
    // }

    public void grenadeDegat(List<Personnage> ennemis, A4 grenade) {
        if (grenade.getIsActivated() == true) {

            for (Personnage personnage : enemies) {
                double distance = Math.sqrt(Math.pow(personnage.getX() - grenade.getX(), 2)
                        + Math.pow(personnage.getY() - grenade.getY(), 2));
                if (distance <= 75) {
                    personnage.infligerDegats(10);
                }
            }
            
        }
    }

    public void suppPieges() {
        Iterator<Piege> it = pieges.iterator();
        while (it.hasNext()) {
            Piege piege = it.next();
            if (piege.getDimension() == 0) {
                it.remove();
            }
        }
    }

    public void update() {

        handleBulletCollisions();
        // suppBulletEnnemi();
        removeOutOfBoundsBullets(playerBullets);
        removeOutOfBoundsBullets(enemyBullets);
        suppPieges();
    }

    // Méthode pour gérer les collisions avec les murs
    private void handleWallCollisions(Bullet bullet) {
        int currentXIndex = (int) (bullet.getX() / 40);
        int currentYIndex = (int) (bullet.getY() / 40);

        if (currentXIndex >= 0 && currentXIndex < level_tab[0].length &&
                currentYIndex >= 0 && currentYIndex < level_tab.length) {

            int caseID = level_tab[currentYIndex][currentXIndex];
            int caseType = ManagerCase.getCaseType(caseID);
                
            // Si la balle entre en collision avec un mur
            if (caseType == ManagerCase.MUR && bullet.getRebond()) {
                // Calculer les coordonnées du mur
                float wallX = currentXIndex * 40 + bullet.size;
                float wallY = currentYIndex * 40 + bullet.size;
                if (wallVertical(currentXIndex, currentYIndex)) {

                    bullet.reboundD(wallX, wallY, wallX, wallY + bullet.size, player); // Pour les rebonds hauts et bas
                }
                if (wallHorizontal(currentXIndex, currentYIndex)) {
                    // méthode rebound avec les coordonnées du mur
                    bullet.reboundB(wallX, wallY, wallX + bullet.size, wallY, player); // Pour les rebonds à droite et à
                                                                                       // gauche
                }

            } else if (bullet.getCassant() &&(caseType == ManagerCase.CASSANT ||caseType == ManagerCase.MUR_CASSANT) ) {
            // } else if (caseType == ManagerCase.MUR_CASSANT || caseType == ManagerCase.CASSANT) {
                // Casser le mur cassable
                level_tab[currentYIndex][currentXIndex] = ManagerCase.SOL;
                bullet.setSize(0); // Bloquer la balle
                gameManager.getGamePlateau().debris(currentXIndex*40,currentYIndex*40);
            } else if (caseType == ManagerCase.MUR || caseType == ManagerCase.MUR_CASSANT || caseType == ManagerCase.CASSANT) {
                bullet.setSize(0);
            }
        }
    }

    private boolean wallVertical(int xIndex, int yIndex) {
        if (yIndex + 1 < level_tab.length) {
            int caseID1 = level_tab[yIndex + 1][xIndex];
            if (ManagerCase.getCaseType(caseID1) == ManagerCase.MUR) {
                return true; // mur vertical
            }

        }

        if (yIndex - 1 >= 0) {
            int caseID1 = level_tab[yIndex - 1][xIndex];
            if (ManagerCase.getCaseType(caseID1) == ManagerCase.MUR) {
                return true; // mur vertical
            }
        }
        return false; // mur horizontal
    }

    private boolean wallHorizontal(int xIndex, int yIndex) {
        if (xIndex + 1 < level_tab[yIndex].length) {
            int caseID1 = level_tab[yIndex][xIndex + 1];
            if (ManagerCase.getCaseType(caseID1) == ManagerCase.MUR) {
                return true; // mur horizontal
            }
        }

        if (xIndex - 1 >= 0) {
            int caseID1 = level_tab[yIndex][xIndex - 1];
            if (ManagerCase.getCaseType(caseID1) == ManagerCase.MUR) {
                return true; // mur horizontal
            }
        }

        return false; // pas de mur horizontal adjacent
    }

    // ------------------- Getters and Setters -------------------

    public void addBulletPlayer(Bullet b) {
        playerBullets.add(b);
    }

    public void addBulletEnemy(Bullet b) {
        enemyBullets.add(b);
    }

    public ArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public ArrayList<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ArrayList<Piege> getPieges() {
        return pieges;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int[][] getLevel_tab() {
        return level_tab;
    }

    public void setLevel_tab(int[][] level_tab) {
        this.level_tab = level_tab;
    }
}
