package Shooter.model;

import java.io.Serializable;

public class Personnage implements Serializable {

    protected int x, y;
    protected int xSpeed, ySpeed;
    protected int size;
    protected int maxSpeed;
    protected int sante; // deux foncitons infligerDegats et soigner

    public Personnage(int x, int y, int xSpeed, int ySpeed, int size, int maxSpeed, int sante) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        this.maxSpeed = maxSpeed;
        this.sante = sante;
    }

    public Personnage(int x, int y, int size, int sante, int maxSpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.sante = sante;
        this.maxSpeed = maxSpeed;
    }

    public boolean detectCollision(float newX, float newY, int newSize) {
        if (x - newSize < newX && x + size > newX && y - newSize < newY && y + size > newY) {
            return true;
        }
        return false;
    }


    // ------------- Getters et setters ---------------------------
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getSante() {
        return sante;
    }

    public void setSante(int sante) {
        this.sante = sante;
    }

    public void infligerDegats(int degats) {
        this.sante -= degats;
    }

    public void soigner(int soin) {
        this.sante += soin;
    }

}
