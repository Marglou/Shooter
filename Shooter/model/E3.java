package Shooter.model;

import java.awt.Color;

import Shooter.Managers.FloodFillManager;
import Shooter.Managers.ProjectilesManager;

public class E3 extends Enemy {

    // Attributs spécifiques à E3
    protected boolean playerDetected = false;
    protected int yTmp = 150;
    protected int maxDistance = 200;
    protected double initialX;
    protected double initialY;

    // Constructeur
    public E3() {
        super(1200, 150, 50, 100, 1, 1, 20, 50, 1000, 200, new Color(255, 255, 0), 1);
        initialX = getX();
        initialY = getY();
    }

    @Override
    public void updateBehavior(Player player, FloodFillManager floodFillManager, ProjectilesManager projectilesManager,
            int xTile,
            int yTile) {

        if (isPlayerDetected(player)) {
            playerDetected = true;

            if (playerDetected) {
                if (calculateDistanceToBase() < maxDistance) {
                    moveTowardsPlayer(player);
                }
            }

        } else {
            move();
        }
    }

    // Méthode pour déplacer l'ennemi vers le joueur
    private void moveTowardsPlayer(Player player) {
        if (player.getX() < this.getX()) {
            this.setX(this.getX() - this.getSpeed());
        } else {
            this.setX(this.getX() + this.getSpeed());
        }
        if (player.getY() < this.getY()) {
            this.setY(this.getY() - this.getSpeed());
        } else {
            this.setY(this.getY() + this.getSpeed());
        }
    }

    // Méthode pour calculer la distance par rapport à la position de base
    private double calculateDistanceToBase() {
        return Math.sqrt(Math.pow(this.getX() - initialX, 2) + Math.pow(this.getY() - initialY, 2));
    }

    // Méthode de déplacement vertical
    public void move() {
        y += 2;
        if (y > 400) {
            y = -30;
        }
    }

}
