package Shooter.factory;
import Shooter.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EnemyLevelLoader {
    private Map<Integer, List<PersonnageFactory>> levelEnemyFactories;
    private int level;

    public EnemyLevelLoader(int level) {
        levelEnemyFactories = new HashMap<>();
        this.level = level;
    }

    public void loadLevelEnemies(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    int read_level = Integer.parseInt(parts[0].trim());
                    if (read_level == level) {
                        String enemyData = parts[1].trim();
                        List<PersonnageFactory> factories = createFactoriesForEnemyTypes(enemyData);
                        levelEnemyFactories.put(level, factories);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Personnage> createEnemiesForLevel() {
        List<PersonnageFactory> factories = levelEnemyFactories.get(level);
        List<Personnage> enemies = new ArrayList<>();

        if (factories != null) {
            for (PersonnageFactory factory : factories) {
                Entry<Integer, Integer> coordinates = factory.getCoordinates();
                enemies.add(factory.createPersonnage(coordinates.getKey(), coordinates.getValue()));
            }
        }

        return enemies;
    }

    private List<PersonnageFactory> createFactoriesForEnemyTypes(String enemyData) {
        List<PersonnageFactory> factories = new ArrayList<>();

        String[] enemyList = enemyData.split(";");
        for (String enemy : enemyList) {
            String[] typeAndCoordinates = enemy.split(",");
            if (typeAndCoordinates.length == 3) {
                String enemyType = typeAndCoordinates[0].trim();
                String xCoordinate = typeAndCoordinates[1].replaceAll("[^0-9]", "").trim();
                String yCoordinate = typeAndCoordinates[2].replaceAll("[^0-9]", "").trim();

                factories.add(createFactoryForEnemyType(enemyType, xCoordinate, yCoordinate));
            } else {
              //  System.out.println("Invalid format: " + enemy);
            }
        }

        return factories;
    }


    private PersonnageFactory createFactoryForEnemyType(String enemyType, String xCoordinate, String yCoordinate) {
        switch (enemyType) {
            case "Enemy 1":
                return new Enemy1Factory(Integer.parseInt(xCoordinate), Integer.parseInt(yCoordinate)); //EnemyBasique
            case "Enemy 2":
                return new Enemy2Factory(Integer.parseInt(xCoordinate), Integer.parseInt(yCoordinate));//EnemyMedium
            case "Enemy 3":
                return new Enemy3Factory(Integer.parseInt(xCoordinate), Integer.parseInt(yCoordinate));//Guardien
            case "Enemy 4":
                return new Enemy4Factory(Integer.parseInt(xCoordinate), Integer.parseInt(yCoordinate));//EnemyIA
            case "Enemy 5":
                return new Enemy5Factory(Integer.parseInt(xCoordinate), Integer.parseInt(yCoordinate));//EnemyRebond
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + enemyType);
        }
    }

    public static ArrayList<String> EnnemiDuNiveau(String filePath, int level){
        ArrayList<String> ennemis = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    int read_level = Integer.parseInt(parts[0].trim());
                    if (read_level == level) {
                        String enemyData = parts[1].trim();
                        String[] enemyList = enemyData.split(";");
                        for (String enemy : enemyList) {
                            ennemis.add(enemy+";");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ennemis;
    }

    public static String EnnemiNiveau(String filepath, int level){
        String ennemi = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    int read_level = Integer.parseInt(parts[0].trim());
                    if (read_level == level) {
                        ennemi = read_level + ": ";
                        ennemi += parts[1].trim();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ennemi += "\n";
        return ennemi;
    }


    public void setLevel (int level){
        this.level = level;
    }
}
