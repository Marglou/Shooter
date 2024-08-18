package Shooter.factory;
import java.util.AbstractMap;
import java.util.Map.Entry;

import Shooter.model.*;

public class Enemy1Factory implements PersonnageFactory {
    private int x;
    private int y;

    public Enemy1Factory(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public Personnage createPersonnage(int x, int y){
        return new EnemyBasique(x,y);
    }
    @Override
    public Entry<Integer, Integer> getCoordinates() {
        return new AbstractMap.SimpleEntry<>(x, y);
    } 
}
