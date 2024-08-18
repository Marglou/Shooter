package Shooter.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TimePoint {
    protected int x;
    protected int y;
    protected int time; // Temps avant d'effacer les pas
    protected Timer timer; // Timer pour effacer les pas
    protected boolean isDrawing = true;
    protected boolean isGoingRight;
    protected int pointSize = 7;
    protected long timing; // moment où le point a été créé

    public TimePoint(int x, int y, boolean isGoingRight) {
        this.x = x;
        this.y = y;
        this.time = 5000;
        this.timer = new Timer();
        startTimer();
        this.isGoingRight = isGoingRight;
        this.timing = System.currentTimeMillis();
        
    }

    public TimePoint(int x, int y, int time) {
        Random rand = new Random();
        this.x = rand.nextInt(60) + x;
        this.y = rand.nextInt(60) + y;
        this.time = time;
        this.timer = new Timer();
        startTimer();
    }

    protected void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Le timer est expiré, donc annule le dessin
                stopDrawing();
            }
        }, time);
    }

    protected void stopDrawing() {
        // Annuler le timer
        isDrawing = false;
        timer.cancel();
        timer.purge();
    }

    public void draw(Graphics g) {
        // Dessiner un cercle à l'emplacement (x, y)
        g.setColor(Color.BLACK); 

        // // Dessiner le point en fonction de la direction
        if (isGoingRight) {
            g.fillOval(x - pointSize , y + pointSize , 4, pointSize);
        } else {
            g.fillOval(x + pointSize , y - pointSize , 4, pointSize);
        }

        if (isGoingRight) {
            g.fillOval(x + pointSize , y - pointSize , 4, pointSize);
        } else {
            g.fillOval(x - pointSize , y + pointSize , 4, pointSize);
        }
    }

    public void drawDebris(Graphics g) {
        if (isDrawing) {
            // Dégradé de transparence pour les débris
            for (int i = 0; i < 6; i++) {
                // Calculer la transparence en fonction de la position du débris
                int alpha = 255 - (i * 40); // Plus le débris est petit, plus il est transparent
    
                // Définir la couleur gris foncé avec transparence
                int grayValue = 50; // Valeur fixe pour le gris foncé
                g.setColor(new Color(grayValue, grayValue, grayValue, alpha));
    
                // Dessiner des carrés plus petits
                int size = 4 + i; // Taille de base plus petite avec une augmentation plus modeste
                g.fillRect(x - i, y - i, size, size);
            }
        }
    }
    
    

    // ------------------- Getters  et Setters -------------------

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getTime() {
        return time;
    }

}
