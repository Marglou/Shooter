package Shooter.model;

// import java.awt.Color;

// Tire des balles rebondissantes
public class A6 extends Armes {

    public A6(){
        super("Gun",40, true, 20, 5,2000,400,true, 500);
    }

    // @Override
    // public Bullet tirer(int playerX, int playerY, int destX, int destY, int power, int typeMunition, Color color) {
    //     return new Bullet(playerX, playerY, destX, destY, power, typeMunition, color);
    // }

    @Override
    public Bullet tirer(int playerX, int playerY, int destX, int destY) {
        return new Bullet(playerX, playerY, destX, destY, this.power, this.typeMunition, this.color, this.detruitMur);
    }
}
