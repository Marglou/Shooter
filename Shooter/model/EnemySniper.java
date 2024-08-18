package Shooter.model;

import java.awt.Color;

import Shooter.Managers.FloodFillManager;
import Shooter.Managers.ProjectilesManager;

public class EnemySniper extends Enemy {

    public EnemySniper(int x, int y) {
        super(x, y, 50, 200, 1, 3, 30, 15, 400, 250, new Color(255, 0, 255),1);
    }

    public boolean isPlayerInRange(Player player, int[][] map, int xTile, int yTile) {

        if (isPlayerDetected(player)) {
            if (!isWallBetween(player.getX(), player.getY(), map, xTile, yTile)) {
                return true;

            }
            return false;
        }
        return false;
    }

    @Override
    public void updateBehavior(Player player, FloodFillManager floodFillManager, ProjectilesManager projectilesManager,
            int xTile,
            int yTile) {

        if (isPlayerDetected(player)) {
            if (!isWallBetween(player.getX(), player.getY(), floodFillManager.getFloodfill(), xTile, yTile)) {
                shootBehavior(player, projectilesManager);
            } else {
                moveTowardsPlayer(floodFillManager.getFloodfill(), player, xTile, yTile);
            }
        } else {
            moveTowardsPlayer(floodFillManager.getFloodfill(), player, xTile, yTile);
        }
    }

    @Override
    public void shootBehavior(Player player, ProjectilesManager projectilesManager) {
        shoot(player, projectilesManager);
    }

}
