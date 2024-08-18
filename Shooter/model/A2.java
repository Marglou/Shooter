package Shooter.model;

// import java.awt.Color;

// Bazooka 
public class A2 extends Armes{

    public A2(){
        super("Bazooka",35, true, 10, 2,5000,400,false, 1500);
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
