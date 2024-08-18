package Shooter.model;

import java.awt.Color;

import Shooter.Managers.FloodFillManager;
import Shooter.Managers.ManagerCase;
import Shooter.Managers.ProjectilesManager;

// Evite les tirs des balles du joueur
public class Enemy5 extends Enemy {

    public Enemy5(int x, int y) {
        super(x, y, 50, 400, 1, 4, 50, 30, 350, 200, new Color(255, 0, 255),1);
    }

    @Override
    public void updateBehavior(Player player, FloodFillManager floodFillManager, ProjectilesManager projectilesManager, int xTile,
            int yTile) {

        eviteTir(projectilesManager);
        if (isPlayerDetected(player)) {
            shootBehavior(player, projectilesManager);
        } else {
            moveTowardsPlayer(floodFillManager.getFloodfill(), player, xTile, yTile);
        }
        
    }

    @Override
    public void shootBehavior(Player player, ProjectilesManager projectilesManager) {
        shoot(player, projectilesManager);
    }   

    public void eviteTir(ProjectilesManager projectilesManager) {
        for (Bullet b : projectilesManager.getPlayerBullets()) {
            if (detecteBullet(b)) {
                avoidBullet(b, projectilesManager);
            }
        }
    }

    public boolean detecteBullet(Bullet b) {
        if (Math.abs(b.getX() - getX()) < 100 && Math.abs(b.getY() - getY()) < 150) {
            return true;
        }
        return false;
    }

    private boolean isBlocked(int xIndex, int yIndex, int dirX, int dirY, ProjectilesManager projectilesManager) {
            xIndex = xIndex/40;
            yIndex = yIndex/40;
            if (dirX < 0){
                xIndex ++;
            }
            if (dirY < 0){
                yIndex ++;
            }
        int caseID = projectilesManager.getGameManager().getGamePlateau().level_tab[yIndex][xIndex];
        int casetype = ManagerCase.getCaseType(caseID);
        if (casetype == ManagerCase.MUR || casetype == ManagerCase.CASSANT || casetype == ManagerCase.BLOQUE) {
            return true;
        }
        return false;
    }


    public void avoidBullet(Bullet b, ProjectilesManager projectilesManager) {
        // Calculer la direction de la balle par rapport à l'ennemi
        double angleToBullet = Math.atan2(b.getY() - getY(), b.getX() - getX());

        // Calculer la direction de déplacement pour éviter la balle
        int dirX = (int) Math.signum(Math.cos(angleToBullet));
        int dirY = (int) Math.signum(Math.sin(angleToBullet));

        // Augmenter la distance pour permettre à l'ennemi de bouger plus loin
        int distance = 3; // Par exemple, vous pouvez changer cette valeur pour contrôler la distance

        int newX = x - dirX * distance;
        int newY = y - dirY * distance;

        int xTile = x - dirX * distance;
        int yTile = y - dirY * distance;

        if (!isBlocked(xTile, yTile, dirX, dirY, projectilesManager) || outOfBounds(newX, newY)) {
            x = newX;
            y = newY;
        } else {
            if (!isBlocked(x, yTile, dirX, dirY, projectilesManager) || outOfBounds(x, newY)) {
                y = newY;
            } else if (!isBlocked(xTile, y, dirX, dirY, projectilesManager) || outOfBounds(newX, y)) {
                x = newX;
            }
        }

    }

    private boolean outOfBounds(int x, int y) {
        return x < 0 || x >= 1440 || y < 0 || y >= 840;
    }


}
