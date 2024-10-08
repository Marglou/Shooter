package Shooter.factory;
import java.util.AbstractMap;
import java.util.Map.Entry;

import Shooter.model.*;

public class Enemy5Factory implements PersonnageFactory {
    private int x;
    private int y;

    public Enemy5Factory(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Personnage createPersonnage(int x, int y){
        return new Enemy5(x,y);
    } 
     @Override
    public Entry<Integer, Integer> getCoordinates() {
        return new AbstractMap.SimpleEntry<>(x, y);
    } 
}
