package Shooter.factory;
import java.util.Map.Entry;

import Shooter.model.*;

public interface PersonnageFactory {
    Personnage createPersonnage(int x, int y);

    Entry<Integer, Integer> getCoordinates();
  
}
