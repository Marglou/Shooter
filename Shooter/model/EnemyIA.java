package Shooter.model;

import java.awt.Color;

import Shooter.Managers.FloodFillManager;
import Shooter.Managers.ProjectilesManager;

public class EnemyIA extends Enemy {

    public EnemyIA(int x, int y) {
        super(x, y, 50, 300, 1, 5, 40, 20, 350, 200, new Color(255, 0, 255), 1);
    }

    @Override
    public void updateBehavior(Player player, FloodFillManager floodFillManager, ProjectilesManager projectilesManager,
            int xTile,
            int yTile) {
        moveTowardsPlayer(floodFillManager.getFloodfill(), player, xTile, yTile);
    }

}
