package Shooter.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Armes implements Serializable {

    protected int power;
    protected boolean type; // true pour arme portée à la main et false pour arme de sol
    protected int munition; // nombre de munitons que comporte l'arme
    protected int distance; // la distance a laquelle l'arme tire
    protected int recharge; // recharge de l'arme
    protected int dureeRecharge; // durée de la recharge
    protected boolean detruitMur;
    protected int typeMunition;

    public Color color;
    protected String nom;
    protected int frequenceTir;

    public Armes(String nom, int power, boolean type, int munition, int typeMunition, Color color) {
        this.power = power;
        this.type = type;
        this.munition = munition;
        this.typeMunition = typeMunition;
        this.nom = nom;
    }

    public Armes(String nom, int power, boolean type, int munition, int typeMunition, int dureeRecharge, int distance,
            boolean detruitMur) {
        this.nom = nom;
        this.power = power;
        this.type = type;
        this.munition = munition;
        this.distance = distance;
        this.recharge = munition;
        this.dureeRecharge = dureeRecharge;
        this.typeMunition = typeMunition;
        this.detruitMur = detruitMur;

    }

    public Armes(String nom, int power, boolean type, int munition, int typeMunition, int dureeRecharge, int distance,
            boolean detruitMur, int freq) {
        this.nom = nom;
        this.power = power;
        this.type = type;
        this.munition = munition;
        this.distance = distance;
        this.recharge = munition;
        this.dureeRecharge = dureeRecharge;
        this.typeMunition = typeMunition;
        this.detruitMur = detruitMur;
        this.frequenceTir = freq;
    }

    public void shoot() {
        if (this.munition > 0) {
            this.munition -= 1;
        }
    }

    // public Bullet tirer(int playerX, int playerY, int destX, int destY, int
    // power, int typeMunition, Color color) {
    // return null;
    // }

    public abstract Bullet tirer(int playerX, int playerY, int destX, int destY);

    public void besoinRecharge() {
        if (this.munition == 0) {
            // Définir la durée du timer en millisecondes
            long dureeTimer = this.dureeRecharge; // Par exemple, 1 minute

            // Créer un objet Timer
            Timer timer = new Timer();

            final Armes arme = this;

            // Créer une tâche à exécuter lorsque le timer se déclenche
            TimerTask tache = new TimerTask() {

                @Override
                public void run() {
                    // Code à exécuter à la fin du timer
                    arme.munition = arme.recharge;

                    // Arrêter le timer après l'exécution de la tâche
                    timer.cancel();
                }
            };

            // Planifier la tâche pour s'exécuter après la durée spécifiée
            timer.schedule(tache, dureeTimer);

        }
    }

    public void reinitialiser() {
        this.munition = this.recharge;
    }

    // ---------- Getters & Setters ----------

    public String getNom() {
        return nom;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getMunition() {
        return munition;
    }

    public void setMunition(int munition) {
        this.munition = munition;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getRecharge() {
        return recharge;
    }

    public void setRecharge(int recharge) {
        this.recharge = recharge;
    }

    public int getDureeRecharge() {
        return dureeRecharge;
    }

    public void setDureeRecharge(int dureeRecharge) {
        this.dureeRecharge = dureeRecharge;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean getDetruitMur() {
        return detruitMur;
    }

    public void setDetruitMur(boolean detruitMur) {
        this.detruitMur = detruitMur;
    }

    public int getTypeMunition() {
        return typeMunition;
    }

    public void setTypeMunition(int typeMunition) {
        this.typeMunition = typeMunition;
    }

    public int getFrequenceTir() {
        return this.frequenceTir;
    }
}
