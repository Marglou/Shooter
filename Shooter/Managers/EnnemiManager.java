package Shooter.Managers;

import java.util.Iterator;
import java.util.List;

import Shooter.model.Enemy;
import Shooter.model.Personnage;
import Shooter.model.Player;

public class EnnemiManager {

    private GameManager gameManager;
    private List<Personnage> perso_list;
    private Player player;

    public EnnemiManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.perso_list = gameManager.getPersoList();
        this.player = gameManager.getPlayer();
    }

    public void reset() {
        perso_list.clear();
        this.player = gameManager.getPlayer();
        this.perso_list = gameManager.getPersoList();  
    }

    public void update() {
        for (Personnage perso : perso_list) {
            if (perso instanceof Enemy) {
                Enemy ennemi = (Enemy) perso;
                if (ennemi.getSize() >= 0) {
                    int newX = ennemi.convertPositionToTile(ennemi.getX());
                    int newY = ennemi.convertPositionToTile(ennemi.getY());
                    ennemi.ralentissement(gameManager, newX, newY);

                    ennemi.updateBehavior(player, gameManager.getGamePlateau().getFloodFillManager(), gameManager.getProjectilesManager(), newX, newY);
                    if (ennemi.detectCollision(player.getX(), player.getY(), player.getSize())) {
                        ennemi.infligerDegatsCollision(player);
                    }
                }

            }
        }
        

    }

    public void suppEnnemi() {

        Iterator<Personnage> it = perso_list.iterator();

        while (it.hasNext()) {
            Personnage perso = it.next();
            if (perso instanceof Enemy) {
                Enemy ennemi = (Enemy) perso;
                if (ennemi.getSize() <= 0 || ennemi.getSante() <= 0) {
                    it.remove();
                }
            }
        }
    }

    // ------------- Getters et setters ---------------------------
    public List<Personnage> getPerso_list() {
        return perso_list;
    }

    public void setPerso_list(List<Personnage> perso_list) {
        this.perso_list = perso_list;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}
