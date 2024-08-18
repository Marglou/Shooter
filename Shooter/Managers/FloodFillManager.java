package Shooter.Managers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Shooter.model.Piege;
import Shooter.model.Plateau;

public class FloodFillManager {

    private Plateau plateau;
    protected int[][] level_tab;
    protected int[][] floodfill;
    // protected int[][] floodfill2;
    // protected int[][] floodfill3;

    public FloodFillManager(Plateau plateau) {
        this.plateau = plateau;
        this.level_tab = plateau.getLevel_tab();
        floodfill = new int[level_tab.length][level_tab[0].length];
        // floodfill2 = new int[level_tab.length][level_tab[0].length];
        // floodfill3 = new int[level_tab.length][level_tab[0].length];
    }

    public void reset(Plateau plateau) {
        this.plateau = plateau;
        this.level_tab = plateau.getLevel_tab();
        floodfill = new int[level_tab.length][level_tab[0].length];
        // floodfill2 = new int[level_tab.length][level_tab[0].length];
        // floodfill3 = new int[level_tab.length][level_tab[0].length];
        
    }

    public void updateFloodFill(int xCible, int yCible) {
        floodfill = newFloodFill(xCible, yCible); // evite murs et obstacles
        // printFloodFill(floodfill);
        // floodfill2 = newFloodFill2(xCible, yCible, floodfill2);
        // floodfill3 = newFloodFill3(xCible, yCible); // evite murs, obstacles et pieges
    }

    public int[][] newFloodFill(int xCible, int yCible) {
        int[][] res = new int[this.level_tab.length][this.level_tab[0].length];
        res[xCible][yCible] = 0; // on met la case cible à 0
        res = newFloodFill2(xCible, yCible, res);

        for (int i = 0; i < this.level_tab.length; i++) {
            for (int j = 0; j < this.level_tab[i].length; j++) {
                if (ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.MUR
                        || ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.CASSANT
                        || ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.BLOQUE) {
                    res[i][j] = 1000;
                }
            }
        }

        res[xCible][yCible] = 0; // on met la case cible à 0
        return res;
    }

    // public int[][] newFloodFill(int xCible, int yCible) {
    //     int[][] res = new int[this.level_tab.length][this.level_tab[0].length];
    //     res[xCible][yCible] = 0; // on met la case cible à 0
    //     res = newFloodFill2(xCible, yCible, res);

    //     for (int i = 0; i < this.level_tab.length; i++) {
    //         for (int j = 0; j < this.level_tab[i].length; j++) {
    //             if (ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.MUR
    //                     || ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.CASSANT
    //                     || ManagerCase.getCaseType(this.level_tab[i][j]) == ManagerCase.BLOQUE
    //                     || isEnemy(i, j)) {
    //                 res[i][j] = 1000;
    //             }
    //         }
    //     }

    //     res[xCible][yCible] = 0; // on met la case cible à 0
    //     return res;
    // }

    // private boolean isEnemy(int x, int y) {
    //     for (Personnage p : plateau.getGameManager().getPersoList()) {
    //         if (p instanceof EnemyBasique) {
    //             int enemyX = p.getX() / 40;
    //             int enemyY = p.getY() / 40;
    //             if (enemyX == y && enemyY == x) {
    //                 return true;
    //             }
    //         }
    //     }
    //     return false;
    // }
    
    
    public int[][] newFloodFill3(int xCible, int yCible) {
        int[][] res = new int[this.level_tab.length][this.level_tab[0].length];
        res[xCible][yCible] = 0; // on met la case cible à 0
        res = copyTab(floodfill);

        for (int i = 0; i < this.level_tab.length; i++) {
            for (int j = 0; j < this.level_tab[i].length; j++) {
                if (isPiege(j, i)) {
                    res[i][j] = 1000;
                }
            }
        }

        res[xCible][yCible] = 1000; // on met la case cible à 0
        return res;
    }

    private int[][] copyTab (int[][] tab){
        int[][] res = new int[tab.length][tab[0].length];
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                res[i][j] = tab[i][j];
            }
        }
        return res;
    }

    private boolean isPiege(int x, int y) {
        ArrayList<Piege> pieges = plateau.getPieges();
        for (int i = 0; i < pieges.size(); i++) {
            if (pieges.get(i).getX() / 40 == y && pieges.get(i).getY() / 40 == x) {
                return true;
            }
        }
        return false;
    }

    public int[][] newFloodFill2(int x, int y, int[][] res) {
        Queue<int[]> queue = new LinkedList<>(); // Utiliser une file pour le parcours en largeur (BFS)
        queue.offer(new int[] { x, y }); // Ajouter la cellule initiale à la file
        int distance = 0;

        // Liste des directions
        int[][] directions = {
                { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }
        };
        // Tant que la file n'est pas vide, continuez le parcours en largeur
        while (!queue.isEmpty()) {
            int size = queue.size(); // Taille actuelle de la file (nombre de cellules à ce niveau de distance)
            distance++; // Incrémenter la distance pour le niveau suivant
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll(); // Récupérer la cellule de la file
                int cellX = cell[0];
                int cellY = cell[1];

                // Parcourir les cellules adjacentes
                for (int[] dir : directions) {
                    int newX = cellX + dir[0];
                    int newY = cellY + dir[1];
                    // Vérifier si la cellule adjacente est dans la grille et non visitée
                    if (newX >= 0 && newX < res.length && newY >= 0 && newY < res[0].length && res[newX][newY] == 0 &&
                            !isObstacle(newX, newY)) {
                        res[newX][newY] = distance; // Assigner la distance à la cellule adjacente
                        queue.offer(new int[] { newX, newY }); // Ajouter la cellule adjacente à la file pour
                                                               // exploration
                    }
                }
            }
        }
        return res;
    }

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
        }
    }

    // ----------------- Getters et setters ---------------------------

    public int[][] getFloodfill() {
        return floodfill;
    }

    // public int[][] getFloodfill2() {
    //     return floodfill2;
    // }

    // public int[][] getFloodfill3() {
    //     return floodfill3;
    // }

    public void setGamePlateau (Plateau plateau){
        this.plateau = plateau;
    }

    public Plateau getGamePlateau(){
        return plateau;
    }

    public int [][] getLevel_tab(){
        return level_tab;
    }
}
