package Shooter.model;

// import java.awt.Color;

public class A1 extends Armes{

    public A1(){
        super("Pistolet",10, true, 30, 0,2000,200,false, 1000);
        
    }

    // @Override
    // public Bullet tirer(int playerX, int playerY, int destX, int destY, int power, int typeMunition, Color color) {
    //     return new Bullet(playerX, playerY, destX, destY, power, typeMunition);
    // }

    @Override
    public Bullet tirer(int playerX, int playerY, int destX, int destY) {
        return new Bullet(playerX, playerY, destX, destY, this.power, this.typeMunition, this.color, this.detruitMur);
    }

}
