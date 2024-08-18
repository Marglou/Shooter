package Shooter.model;

import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import Shooter.Managers.*;
import Shooter.factory.PlateauLevelLoader;
import java.util.Queue;

public class Plateau extends JPanel {

    protected GameManager gameManager;
    protected ManagerCase tile_manager;
    protected FloodFillManager floodFillManager;
    protected int[][] level_tab;

    protected Graphics plateau_graphic; // on utilise pour "enregistrer" notre image graphique puis pouvoir la modifier
                                        // dans le update

    protected ArrayList<Piege> pieges = new ArrayList<Piege>();
    protected ArrayList<TimePoint> debris = new ArrayList<TimePoint>();

    protected BufferedImage[] imageArmes;

    public int animationIndex;
    public int tick;
    public int ANIMATION_SPEED = 30;
    java.util.List<TimePoint> playerPositions;
    public boolean isGoingRight = true;
    public PlateauGraphic graphismes;


    public Plateau(boolean gameMode) {
        if (!gameMode) {
            this.level_tab = PlateauLevelLoader.loadPlayingBoard("Shooter/factory/PlateauLevels.txt", 0);
        } else {
            this.level_tab = PlateauLevelLoader.loadPlayingBoard("Shooter/factory/creativeTmp.txt", 0);
        }

        this.tile_manager = new ManagerCase();
        this.floodFillManager = new FloodFillManager(this);
        this.playerPositions = new ArrayList<>();
        this.graphismes=new PlateauGraphic(this);
        loadArme();

    }

    public Plateau(boolean gameMode, int level) {

        if (!gameMode) {
            this.level_tab = PlateauLevelLoader.loadPlayingBoard("Shooter/factory/PlateauLevels.txt", level);
        } else {
            this.level_tab = PlateauLevelLoader.loadPlayingBoard("Shooter/factory/creativeTmp.txt", level);
        }

        this.floodFillManager = new FloodFillManager(this);
        this.tile_manager = new ManagerCase();
        this.playerPositions = new ArrayList<>();
        this.graphismes=new PlateauGraphic(this);
        playerPositions.add(new TimePoint(1200, 300, isGoingRight));
        loadArme();
    }

    public void clearOldPosition() {
        if (playerPositions.size() > 0) {
            for (TimePoint position : playerPositions) {
                if (!position.isDrawing) {
                    playerPositions.remove(position);
                }
            }
        }
    }

    public void clearOldPositionDebris() {
        if (debris.size() > 0) {
            for (TimePoint position : debris) {
                if (!position.isDrawing) {
                    playerPositions.remove(position);
                }
            }
        }
    }


    private void loadArme() { //Is this considered a graphic function?
        BufferedImage atlas = Enregistrement.getSpriteAtlas();
        imageArmes = new BufferedImage[6];

        for (int i = 0; i < 6; i++) {
            imageArmes[i] = atlas.getSubimage((i) * 40, 16 * 40, 40, 40);
        }
    }

    public void debris(int x,int y){
        int c=0;

        while(c<5){
            TimePoint p = new TimePoint(x, y, 10000);
            int caseType=ManagerCase.getCaseType(gameManager.getGamePlateau().getLevel_tab()[p.getY()/40][p.getX()/40]);
            if (caseType!=ManagerCase.MUR && caseType!=ManagerCase.MUR_CASSANT){
                debris.add(p);
                c++;
            }
        }
    }

    public void addPlayerPosition(int x, int y) {
        if (playerPositions.size() != 0) {
            if (System.currentTimeMillis() - playerPositions.get(playerPositions.size() - 1).timing >= 500) {
                playerPositions.add(new TimePoint(x, y, isGoingRight));
                isGoingRight = !isGoingRight;
            }
        }
    }

    public void reset() {
        pieges.clear();
        debris.clear();
        if (!gameManager.getGame().gameTxtSource) {
            int level = gameManager.getPlayer().getLevel() - 1;
            if (gameManager.getGame().rejouer) {
                level = gameManager.getGame().rejouerLevel - 1;
            }
            this.level_tab = PlateauLevelLoader.loadPlayingBoard("Shooter/factory/PlateauLevels.txt", level);
        } else {
            int level=gameManager.getPlayer().getLevelCreatif()-1;
            this.level_tab = PlateauLevelLoader.loadPlayingBoard("Shooter/factory/creativeTmp.txt", level);
        }

        playerPositions.clear();
        playerPositions.add(new TimePoint(gameManager.getPlayer().getX(), gameManager.getPlayer().getY(), isGoingRight));
    }

    // ------------- Flood fill ----------------

    // Vérifier si une cellule est un obstacle
    private boolean isObstacle(int x, int y) {
        return ManagerCase.getCaseType(this.level_tab[x][y]) == ManagerCase.MUR ||
                ManagerCase.getCaseType(this.level_tab[x][y]) == ManagerCase.CASSANT ||
                ManagerCase.getCaseType(this.level_tab[x][y]) == ManagerCase.BLOQUE;
    }

    public void printFloodFill(int[][] res) { 
        // Déterminer la largeur maximale de chaque colonne
        int numRows = res.length;
        int numCols = res[0].length;
        int[] colWidths = new int[numCols];

        for (int j = 0; j < numCols; j++) {
            int maxWidth = 0;
            for (int i = 0; i < numRows; i++) {
                int width = String.valueOf(res[i][j]).length();
                maxWidth = Math.max(maxWidth, width);
            }
            colWidths[j] = maxWidth;
        }

        // Afficher les éléments en alignant les colonnes
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // Formater chaque nombre pour s'adapter à la largeur maximale de la colonne
                System.out.printf("%-" + (colWidths[j] + 1) + "d", res[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[][] markCellsInRange(int[][] grid, int targetX, int targetY, int range) {//DANS FLOOD FILL MANAGER?
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int distance = Math.abs(targetX - i) + Math.abs(targetY - j);
                if (distance <= range) {
                    grid[i][j] = 0;
                }
            }
        }
        return grid;
    }

    public static int[][] CellsInEdge(int[][] grid, int targetX, int targetY, int range) {
        int[][] edgeCells = new int[0][2];
        int edgeCount = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int distance = Math.abs(targetX - i) + Math.abs(targetY - j);
                if (distance == range) {
                    int[][] newEdgeCells = new int[edgeCells.length + 1][2];
                    for (int k = 0; k < edgeCells.length; k++) {
                        newEdgeCells[k] = edgeCells[k];
                    }
                    newEdgeCells[edgeCount] = new int[] { i, j };
                    edgeCells = newEdgeCells;
                    edgeCount++;
                }
            }
        }

        return edgeCells;
    }

    public static void printEdgeCells(int[][] edgeCells) {//SUPRIMER ?
        System.out.println("Edge Cell Coordinates:");
        for (int i = 0; i < edgeCells.length; i++) {
            System.out.println("(" + edgeCells[i][0] + ", " + edgeCells[i][1] + ")");
        }
    }

    public int[][] sniperFloodFill(int playerX, int playerY, int shootingRange) {//SUPRIMER ? //DANS FLOOD FILL MANAGER?
        int[][] res = new int[this.level_tab.length][this.level_tab[0].length];

        res = init(res);

        markCellsInRange(res, playerX, playerY, shootingRange);

        int[][] edgeCells = CellsInEdge(res, playerX, playerY, shootingRange);
        // printEdgeCells(edgeCells);

        for (int i = 0; i < edgeCells.length; i++) {
            int edgeX = edgeCells[i][0];
            int edgeY = edgeCells[i][1];
            res = newFloodFill3(edgeX, edgeY, res);
        }

        for (int i = 0; i < this.level_tab.length; i++) {
            for (int j = 0; j < this.level_tab[i].length; j++) {
                if (ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.MUR
                        || ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.CASSANT
                        || ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.BLOQUE) {
                    res[i][j] = 1000;
                }
            }
        }
        return res;
    }

    public int[][] newFloodFill3(int x, int y, int[][] res) {//SUPRIMER ?//DANS FLOOD FILL MANAGER?
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { x, y });
        res[x][y] = 0;
        int distance = 0;

        int[][] directions = {
                { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }
        };

        while (!queue.isEmpty()) {
            int size = queue.size();
            distance++;
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int cellX = cell[0];
                int cellY = cell[1];

                for (int[] dir : directions) {
                    int newX = cellX + dir[0];
                    int newY = cellY + dir[1];

                    if (newX >= 0 && newX < res.length && newY >= 0 && newY < res[0].length && res[newX][newY] == -1 &&
                            !isObstacle(newX, newY)) {
                        res[newX][newY] = distance;
                        queue.offer(new int[] { newX, newY });
                    }
                }
            }
        }
        return res;
    }



    // --------- GETTERS et SETTERS -----------

    private static int[][] init(int[][] res) {
        for (int i = 0; i < res.length; i++) {
            for (int y = 0; y < res[0].length; y++) {
                res[i][y] = -1;
            }
        }
        return res;
    }

    public int[][] getLevel_tab() {
        return level_tab;
    }

    public ManagerCase getTile_manager() {
        return tile_manager;
    }

    public Graphics getPlateau_graphic() {
        return plateau_graphic;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public FloodFillManager getFloodFillManager() {
        return floodFillManager;
    }

    public int getANIMATION_SPEED() {
        return ANIMATION_SPEED;
    }

    public void setANIMATION_SPEED(int aNIMATION_SPEED) {
        ANIMATION_SPEED = aNIMATION_SPEED;
    }

    public java.util.List<TimePoint> getPlayerPositions() {
        return playerPositions;
    }

    public void setPlayerPositions(java.util.List<TimePoint> playerPositions) {
        this.playerPositions = playerPositions;
    }

    public ArrayList<Piege> getPieges() {
        return this.pieges;
    }


}
