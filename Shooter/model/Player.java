package Shooter.model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Shooter.Managers.MyMouseListener;

// import java.awt.*;

public class Player extends Personnage implements Serializable {

    protected String pseudo;
    protected double direction = 0;
    protected double rotationSpeed = Math.PI / 100;
    protected ArrayList<Armes> armes = new ArrayList<Armes>();
    protected int currentArme;
    private boolean rotating = true; // Variable pour indiquer si la rotation est en cours
    private int santeMax = 1000;
    protected String creativeLevel;
    protected String enemyLevel;

    protected int level = 1;
    protected int levelCreatif = 1;
    private int counter = 0;
    private static final int ROTATION_DELAY = 5; // Ajustez cette valeur pour ralentir plus ou moins

    public Player() {
        super(55, 425, 0, 0, 50, 2, 1000);

    }

    public Player(String pseudo) {
        super(55, 425, 0, 0, 50, 2, 1000);
        this.pseudo = pseudo;
        this.currentArme = 0;

        this.armes.add(new A6());
        this.armes.add(new A1());
        this.armes.add(new A2());
        this.armes.add(new A3());
        this.armes.add(new A4());
        this.armes.add(new A5());

        this.creativeLevel = "Shooter/Sauvegardes/" + pseudo + "_level.txt";
        this.enemyLevel = "Shooter/Sauvegardes/" + pseudo + "_enemy.txt";

    }

    public void reset() {
        this.x = 55;
        this.y = 425;
        this.direction = 0;
        this.sante = 1000;
        this.currentArme = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;

    }

    public void tournie(MyMouseListener m) {
        m.setMouse(false);
        if (rotating) {
            counter++;
            if (counter >= ROTATION_DELAY) {
                counter = 0;
                // Faites pivoter l'image du joueur
                direction += Math.PI / 2; // Tourne de 90 degrés (PI/2 radians)

                // Si le joueur a tourné dans les quatre directions
                if (direction >= Math.PI * 2) {
                    direction = 0; // Réinitialise la direction à zéro
                }
            }
        }
    }

    public void stopTournie(MyMouseListener m) {
        setRotationSpeed(0); // Set rotation speed to zero to stop rotation
        m.setMouse(true);
    }

    // ------------ Méthodes de sauvegarde et de chargement ------------

    public void sauvegarderJoueur(String cheminDuFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminDuFichier))) {
            oos.writeObject(this); // Sauvegarde l'objet Player
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPseudoIdentique(String playerName) {
        return this.pseudo.equals(playerName);
    }

    // ------------------ Méthodes de dessin -------------------

    public void drawBarVie(Graphics g) {
        int barWidth = 100; // Largeur de la barre de vie
        int barHeight = 10; // Hauteur de la barre de vie
        int x = 1320; // Position X de la barre de vie
        int y = 100; // Position Y de la barre de vie

        // Dessinez le contour de la barre de vie
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);

        // Calculez la largeur de la barre de vie en fonction de la santé actuelle
        int healthWidth = (int) ((double) sante / santeMax * barWidth);

        // Dessinez la barre de vie en fonction de la santé actuelle
        g.setColor(Color.RED);
        g.fillRect(x, y, healthWidth, barHeight);
    }

    // ------ Getter et Setter ---------

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    public boolean isMovingUp() {
        return ySpeed < 0; // Si ySpeed est négatif, le joueur se déplace vers le haut
    }

    public boolean isMovingDown() {
        return ySpeed > 0; // Si ySpeed est positif, le joueur se déplace vers le bas
    }

    public boolean isMovingRight() {
        return xSpeed > 0; // Si xSpeed est positif, le joueur se déplace vers la droite
    }

    public boolean isMovingLeft() {
        return xSpeed < 0; // Si xSpeed est négatif, le joueur se déplace vers la gauche
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String newPseudo) {
        this.pseudo = newPseudo;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public ArrayList<Armes> getArmes() {
        return armes;
    }

    public void setArmes(ArrayList<Armes> armes) {
        this.armes = armes;
    }

    public int getCurrentArme() {
        return currentArme;
    }

    public void setCurrentArme(int currentArme) {
        this.currentArme = currentArme;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelCreatif() {
        return levelCreatif;
    }

    public void setLevel() {
        this.level++;
    }

    public void setLevelCreatif() {
        this.levelCreatif++;
    }

    public void resetLevelCreatif() {
        this.levelCreatif = 1;
    }

    public void addArme(Armes arme) {
        this.armes.add(arme);
    }

    public boolean isRotating() {
        return rotating;
    }

    public void setCreativelLevel(String path) {
        this.creativeLevel = path;
    }

    public String getCreativeLevel() {
        return creativeLevel;
    }

    public String getEnemyLevel() {
        return enemyLevel;
    }

    public void setEnemyLevel(String path) {
        this.enemyLevel = path;
    }
}