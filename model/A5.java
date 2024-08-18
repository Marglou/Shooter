package Shooter.model;

// import java.awt.Color;

public class A5 extends Armes {
  
    public A5(){
        super("Fusil d'asaut",15, true, 10, 1,2000,200,true, 500);
   
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
