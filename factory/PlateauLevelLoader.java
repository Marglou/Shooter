package Shooter.factory;
import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
// import java.util.Scanner;

public class PlateauLevelLoader{

    public static int[][] loadPlayingBoard(String filePath, int level) {
        int[][] currentBoard = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int line_level = -1;
            boolean isTargetLevel = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Level")) {
                    line_level++;
                    isTargetLevel = (line_level == level);
                }
                if (isTargetLevel && line.trim().startsWith("{")) {
                    if (currentBoard == null) {
                        currentBoard = parseLine(line);
                    } else {
                        int[][] newBoard = new int[currentBoard.length + 1][];
                        System.arraycopy(currentBoard, 0, newBoard, 0, currentBoard.length);
                        newBoard[currentBoard.length] = parseLine(line)[0];
                        currentBoard = newBoard;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentBoard;
    }

    public static String getLevelString(String filepath, int level){
        String levelString ="";
        StringBuilder texte = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            
            boolean isTargetLevel = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Level "+level)) {
                    isTargetLevel =true;
                }
                if(line.startsWith("Level "+(level+1))){
                    isTargetLevel = false;
                    break;
                }
                if (isTargetLevel) {
                    texte.append(line);
                    texte.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        levelString = texte.toString();
        return levelString;
    }

    private static int[][] parseLine(String line) {
        line = line.trim().replaceAll("[{}]", ""); // Remove curly braces
        String[] rowTokens = line.split("},?\\s*\\{?");

        int[][] board = new int[rowTokens.length][];
        for (int i = 0; i < rowTokens.length; i++) {
            String[] cellTokens = rowTokens[i].split(",\\s*");
            board[i] = new int[cellTokens.length];

            for (int j = 0; j < cellTokens.length; j++) {
                try {
                    board[i][j] = Integer.parseInt(cellTokens[j].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing integer: " + cellTokens[j].trim());
                    e.printStackTrace();
                }
            }
        }

        return board;
    }

    //nombre de niveau
    public static int levelmax(String filePath){
        int levelmax = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Level")) {
                    levelmax++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levelmax;
    }
   
}