package Shooter.model;

import java.awt.Color;

import Shooter.Managers.FloodFillManager;
import Shooter.Managers.ManagerCase;
import Shooter.Managers.ProjectilesManager;

public class EnemyRebond extends Enemy{

    public EnemyRebond(int x, int y) {
        super(x, y, 50, 100, 1, 1, 20, 50, 350, 200, new Color(255, 0, 255),1);
    }

    @Override
    public void updateBehavior(Player player, FloodFillManager floodFillManager, ProjectilesManager projectilesManager, int xTile, int yTile) {
        if (isPlayerDetected(player)) {
            // Vérifier si l'ennemi peut tirer directement sur le joueur
            if (canShootDirectly(player, xTile, yTile, floodFillManager)) {
                shootBehavior(player, projectilesManager);
            } else {
                // Si l'ennemi ne peut pas tirer directement, chercher un mur pour rebondir la balle
                moveTowardsWall(player, xTile, yTile, floodFillManager);
            }
        } else {
            // Si le joueur n'est pas détecté, déplacer l'ennemi vers le joueur
            moveTowardsPlayer(floodFillManager.getFloodfill(), player, xTile, yTile);
        }
    }
    
    private boolean canShootDirectly(Player player, int xTile, int yTile, FloodFillManager floodFillManager) {
        // Vérifier s'il y a un mur entre l'ennemi et le joueur
        return !isWallBetween(player.getX(), player.getY(), floodFillManager.getFloodfill(), xTile, yTile);
    }
    
    private void moveTowardsWall(Player player, int xTile, int yTile, FloodFillManager floodFillManager) {
        // Trouver un mur le plus proche pour rebondir la balle
        int[][] floodFill = floodFillManager.getFloodfill();
        int nearestWallX = -1;
        int nearestWallY = -1;
        double minDistance = Double.MAX_VALUE;
    
        for (int x = 0; x < floodFill[0].length; x++) {
            for (int y = 0; y < floodFill.length; y++) {
                if (floodFill[y][x] == 1000) { // Représentation d'un mur dans le floodfill
                    double distance = Math.sqrt(Math.pow(xTile - x, 2) + Math.pow(yTile - y, 2));
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestWallX = x;
                        nearestWallY = y;
                    }
                }
            }
        }
    
        // Déplacer l'ennemi vers le mur le plus proche
        if (nearestWallX != -1 && nearestWallY != -1) {
            moveTowardsTile(nearestWallX, nearestWallY, xTile, yTile);
        }
    }
    
    private void moveTowardsTile(int targetX, int targetY, int currentX, int currentY) {
        int dx = Integer.compare(targetX, currentX);
        int dy = Integer.compare(targetY, currentY);
        move(dx, dy);
    }
        

    @Override
    public void shootBehavior(Player player, ProjectilesManager projectilesManager) {

        // Calcul de l'angle entre l'ennemi et le joueur
        double dx = player.getX() - getX();
        double dy = player.getY() - getY();
        double angle = Math.atan2(dy, dx);

        // Calcul de la composante horizontale de la trajectoire de la balle
        double vx = Math.cos(angle);
        
        // Calcul de la composante verticale de la trajectoire de la balle
        double vy = Math.sin(angle);

        int x = (int) vx;
        int y = (int) vy;
        // Création de la balle
        // projectilesManager.getEnemyBullets().add(new Bullet(getX(), getY(), vx, vy, getPower()));
        projectilesManager.getEnemyBullets().add(new Bullet(getX(), getY(), x, y, getPower()));
    }

    // Pour rebondir sur un mur :
    // verifier si mur à distance
    //  vertical ou horizontal
    // déterminer le point d'impact de la balle sur le mur pour toucher le joueur
    // tirer si cela est possible

    public boolean detectWall (int [][] plateau, int x, int y){
        int caseID = ManagerCase.getCaseType(plateau[y][x]);
        if (caseID == ManagerCase.MUR || caseID == ManagerCase.CASSANT){
            return true;
        }
        return false;
    }

    public boolean wallVertical(int xIndex, int yIndex, int [][] plateau){
        if (yIndex + 1 < plateau.length) {
            int caseID1 = ManagerCase.getCaseType(plateau[yIndex+1][xIndex]);
            if (caseID1 == ManagerCase.MUR){
                return true;  // mur vertical
            }

        }

        if (yIndex -1 >= 0) {
            int caseID1 = ManagerCase.getCaseType(plateau[yIndex-1][xIndex]);
            if (caseID1 == ManagerCase.MUR){
                return true;  // mur vertical
            }
        }   
        return false; // mur horizontal
    }

    public int pointImpactX(Player player, int xIndex, int yIndex, int[][] plateau) {
        // Calcul de l'angle entre l'ennemi et le joueur
        double dx = player.getX() - getX();
        double dy = player.getY() - getY();
        double angle = Math.atan2(dy, dx);
    
        // Calcul de la composante horizontale de la trajectoire de la balle
        // double vx = getProjectileSpeed() * Math.cos(angle);
        double vx = Math.cos(angle);
        
        // Déterminer si le mur est vertical ou horizontal
        boolean verticalWall = wallVertical(xIndex, yIndex, plateau);
    
        // Calculer le point d'impact sur l'axe X
        if (verticalWall) {
            // Si le mur est vertical, la composante horizontale ne change pas
            return (int)(getX() + vx); // Point d'impact sur l'axe X
        } else {
            // Si le mur est horizontal, la composante horizontale est nulle
            // donc le point d'impact sera la position X du joueur
            return player.getX();
        }
    }
    
    public int pointImpactY(Player player, int xIndex, int yIndex, int[][] plateau) {
        // Calcul de l'angle entre l'ennemi et le joueur
        double dx = player.getX() - getX();
        double dy = player.getY() - getY();
        double angle = Math.atan2(dy, dx);
    
        // Calcul de la composante verticale de la trajectoire de la balle
        // double vy = getProjectileSpeed() * Math.sin(angle);
        double vy = Math.sin(angle);
    
        // Déterminer si le mur est vertical ou horizontal
        boolean verticalWall = wallVertical(xIndex, yIndex, plateau);
    
        // Calculer le point d'impact sur l'axe Y
        if (verticalWall) {
            // Si le mur est vertical, la composante verticale est nulle
            // donc le point d'impact sera la position Y de l'ennemi
            return getY();
        } else {
            // Si le mur est horizontal, la composante verticale ne change pas
            return (int)(getY() + vy); // Point d'impact sur l'axe Y
        }
    }
    
}
