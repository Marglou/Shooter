package Shooter.Managers;

import Shooter.model.Armes;

public class ManagerArmes {
    //contient fonctions pour les actions de Armes
    
    public GameManager gameManager;

    public ManagerArmes( GameManager gameManager){
        //ajouter les armes
        this.gameManager = gameManager;
       
    }

    public void changeArme(){
        //changer l'arme
        if (gameManager.getPlayer().getCurrentArme() == gameManager.getPlayer().getArmes().size() - 1){
            gameManager.getPlayer().setCurrentArme(0);
        } else {
            gameManager.getPlayer().setCurrentArme(gameManager.getPlayer().getCurrentArme() + 1);
        }
    }

    public void recharge(){
        //recharger les armes
        for (Armes arme : gameManager.getPlayer().getArmes()) {
            arme.reinitialiser();
            
        }
    }

    
    
}
