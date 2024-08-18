package Shooter.Managers;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Shooter.factory.EnemyLevelLoader;
import Shooter.model.A2;
import Shooter.model.A3;
import Shooter.model.A4;
import Shooter.model.A5;
import Shooter.model.A6;
import Shooter.model.Armes;
import Shooter.model.Crosshair;
import Shooter.model.Plateau;
import Shooter.model.Player;
import Shooter.model.Game;
import Shooter.model.Personnage;

public class GameManager {

	private Plateau gamePlateau;
	private Player player;
	private PlayerManager playerManager;
	private ProjectilesManager projectilesManager;
	private MyMouseListener myMouseListener;
	private EnnemiManager ennemiManager;
	public ManagerArmes managerArmes;
	public FloodFillManager floodFillManager;
	protected Game game;

	public GameManager(Game g, Plateau gamePlateau) {

		this.game = g;
		this.gamePlateau = gamePlateau;
		this.player = g.getPlayer();
		this.playerManager = new PlayerManager(this);
		this.ennemiManager = new EnnemiManager(this);
		this.projectilesManager = new ProjectilesManager(player, this);
		this.myMouseListener = new MyMouseListener(player, new Crosshair(), projectilesManager, this);
		this.managerArmes = new ManagerArmes(this);
		this.floodFillManager = gamePlateau.getFloodFillManager();

	}

	public void update() {

		ennemiManager.update();
		ennemiManager.suppEnnemi();
		playerManager.update();
		projectilesManager.update();

	}

	public void updatePlayer(Player player) {
		this.player = game.getPlayer();
		playerManager.setPlayer(player);
		ennemiManager.setPlayer(player);
		myMouseListener.setPlayer(player);
		projectilesManager.setPlayer(player);
	}

	public void reset() {
		player.reset();
		gamePlateau.reset();
		managerArmes.recharge();
		projectilesManager.reset();
		gamePlateau.getFloodFillManager().reset(gamePlateau);
		clearArrayList();
		ennemiManager.reset();
	}


	public void isGameOver() {
		if (getPlayer().getSante() <= 0 || (weaponsEmpty() && projectilesManager.getPlayerBullets().isEmpty() && game.perso_list.size() > 1) && game.gameManager.getProjectilesManager().getPieges().isEmpty()) {
			reset();
			game.begin = false;
			game.cardLayout.show(game.cardPanel, "GameOver");
		}
	}

	private boolean weaponsEmpty() {
		int c = 0;
		for (Armes arme : getPlayer().getArmes()) {
			if (arme.getMunition() == 0) {
				c++;
			}
		}
		return (c == getPlayer().getArmes().size());
	}

	private void clearArrayList() {

		List<Personnage> tmp = new ArrayList<>();
		tmp.add(game.perso_list.get(0));
		if(!game.gameTxtSource){
			EnemyLevelLoader enemyLoader = new EnemyLevelLoader(getPlayer().getLevel()); 
			if (game.rejouer) {
			enemyLoader = new EnemyLevelLoader(game.rejouerLevel);
			}
			enemyLoader.loadLevelEnemies("Shooter/factory/EnemiesForLevels.txt");
			tmp.addAll(enemyLoader.createEnemiesForLevel());
		}
		else{
			EnemyLevelLoader enemyLoader = new EnemyLevelLoader(getPlayer().getLevelCreatif()); 
			enemyLoader.loadLevelEnemies("Shooter/factory/enemyTmp.txt");
			tmp.addAll(enemyLoader.createEnemiesForLevel());
		}


		// Vérifier si le premier élément est une instance de Player avant la conversion
		if (!(game.perso_list.isEmpty())) {
			// Vider la liste
			game.perso_list.clear();
			game.perso_list = tmp;
		}

		// getEnnemiManager().setPerso_list(game.perso_list);

	}

	public void win() {

		if (game.perso_list.size() == 1 && game.begin) {
			game.begin = false;

			Player player = (Player) game.perso_list.get(0);
			if (player.getLevel() == game.nbLevel && !game.rejouer || game.rejouerLevel == game.nbLevel || (player.getLevelCreatif()==game.nbLevelCreatif && game.gameTxtSource)) {
				if (game.rejouer && game.rejouerLevel == game.nbLevel) {
					game.rejouerLevel = -1;
				}
				game.cardLayout.show(game.cardPanel, "GameWin");
				if(game.gameTxtSource){
					player.resetLevelCreatif();
				}

			} else {
				if (game.gameTxtSource){
					game.setLabel("");
					game.textLabel.setText(game.getLabel());
				}
				showBlackScreenForDelay(1000);
			}
		}
	}

	private void nextLevel() {
		if (game.rejouer) {
			game.rejouerLevel += 1;
		} else {
			if(!game.gameTxtSource) {
				ajouteArmes();
				getPlayer().setLevel();
			}
		}
		if(game.gameTxtSource){
			getPlayer().setLevelCreatif();
		}
		reset();
	}

	private void ajouteArmes() {
		Player player = (Player) game.perso_list.get(0);
		int lvl = player.getLevel();
		String arme = "";
		switch (lvl) {
			case 1:
				player.addArme(new A5());
				arme ="fusil d'assault";
				break;
			case 2:
				player.addArme(new A2());
				arme ="bazooka";
				break;
			case 3:
				player.addArme(new A3());
				arme ="mine";
				break;
			case 4:
				player.addArme(new A4());
				arme ="grenade";
				break;
			case 5:
				player.addArme(new A6());
				arme ="A6";
				break;

			default:
				lvl = -1;
				break;
		}
		if (lvl != -1) {
			// if(lvl==5||lvl==1){
				// game.setLabel("Bravo ! Vous avez débloqué une nouvelle arme : " + player.getArmes().get(player.getArmes().size()-1).getNom() + " !");
			// }else{
				// game.setLabel("Bravo ! Vous avez débloqué une nouvelle arme : A " + player.getLevel() + " !");
				game.setLabel("Bravo ! Vous avez débloqué une nouvelle arme : " + player.getArmes().get(player.getArmes().size()-1).getNom() + " !");
			// }
		} else {
			game.setLabel("");
		}
		game.textLabel.setText(game.getLabel());

	}

	private void showBlackScreenForDelay(int delay) {
		JFrame blackFrame = new JFrame();
		blackFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		blackFrame.setUndecorated(true);
		blackFrame.getContentPane().setBackground(Color.BLACK);
		blackFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		blackFrame.setLocationRelativeTo(null);
		blackFrame.setAlwaysOnTop(true);

		// Ajout du texte
		game.textLabel.setForeground(Color.WHITE); // Couleur du texte
		game.textLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Police et taille du texte
		game.textLabel.setHorizontalAlignment(SwingConstants.CENTER); // Alignement du texte au centre
		blackFrame.add(game.textLabel);

		blackFrame.setOpacity(0.0f);
		blackFrame.setVisible(true);

		Timer timer = new Timer(25, new ActionListener() {
			float opacity = 0.0f;
			float step1 = 0.06f; // assombrit
			float step2 = 0.02f; // éclaircit
			boolean expanding = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (expanding) {
					opacity += step2;
					if (opacity >= 1.0f) {
						expanding = false;
						try {
							Thread.sleep(delay);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						nextLevel();
						game.begin = true;
					}
				} else {
					opacity -= step1;
					if (opacity <= 0.0f) {
						((Timer) e.getSource()).stop();
						blackFrame.dispose();
					}
				}
				opacity = Math.min(Math.max(opacity, 0.0f), 1.0f);
				blackFrame.setOpacity(opacity);
			}
		});
		timer.start();
	}

	// ----------------- Getters et setters ---------------------------

	public Plateau getGamePlateau() {
		return gamePlateau;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGamePlateau(Plateau gamePlateau) {
		this.gamePlateau = gamePlateau;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public void setPlayerManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	public ProjectilesManager getProjectilesManager() {
		return projectilesManager;
	}

	public void setProjectilesManager(ProjectilesManager projectilesManager) {
		this.projectilesManager = projectilesManager;
	}

	public MyMouseListener getMyMouseListener() {
		return myMouseListener;
	}

	public void setMyMouseListener(MyMouseListener myMouseListener) {
		this.myMouseListener = myMouseListener;
	}

	public EnnemiManager getEnnemiManager() {
		return ennemiManager;
	}

	public List<Personnage> getPersoList() {
		return game.getPersoList();
	}

	public void setEnnemiManager(EnnemiManager ennemiManager) {
		this.ennemiManager = ennemiManager;
	}

	public boolean getSound() {
		return game.getSound();
	}

	public FloodFillManager getFloodFillManager() {
		return floodFillManager;
	}

	



}
