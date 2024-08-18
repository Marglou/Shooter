package Shooter.factory;
import java.util.AbstractMap;
import java.util.Map.Entry;

import Shooter.model.*;

public class Enemy3Factory implements PersonnageFactory {
    private int x;
    private int y;

    public Enemy3Factory(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public Personnage createPersonnage(int x, int y){
        return new EnemySniper(x,y);
    }
    @Override
    public Entry<Integer, Integer> getCoordinates() {
        return new AbstractMap.SimpleEntry<>(x, y);
    } 
}
