package Shooter.model;

import java.awt.Color;

import Shooter.Managers.FloodFillManager;
import Shooter.Managers.ProjectilesManager;

public class EnemyMedium extends Enemy {

    /*
     * peut suivre le joueur si detecté
     * position x : 550
     * position y : 100
     * taille : 50
     * sante : 100
     * speed : 2
     * dégats de tir : 20
     * dégat de collision : 50
     * fréq de tir : 1 seconde
     * radius de détection : 200 px
     */

    public EnemyMedium(int x, int y) {
        super(x, y, 50, 100, 1, 2, 20, 10, 1000, 250, new Color(255, 255, 0), 1);
    }


    @Override
    public void updateBehavior(Player player, FloodFillManager floodFillManager, ProjectilesManager projectilesManager,
            int xTile,
            int yTile) {

        if (isPlayerDetected(player)) {
            moveTowardsPlayer(floodFillManager.getFloodfill(), player, xTile, yTile);
            if (!isWallBetween(player.getX(), player.getY(), floodFillManager.getFloodfill(), xTile, yTile)) {
                shootBehavior(player, projectilesManager);
            }
        }
    }

    @Override
    public void shootBehavior(Player player, ProjectilesManager projectilesManager) {
        shoot(player, projectilesManager);
    }

}
