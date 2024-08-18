package Shooter.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;

import Shooter.GUI.*;
import Shooter.Managers.GameManager;
import Shooter.factory.EnemyLevelLoader;
import Shooter.factory.PlateauLevelLoader;

public class Game extends JFrame implements Runnable {

	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;
	public List<Personnage> perso_list;
	public Dimension size_screen;
	public CardLayout cardLayout;
	public JPanel cardPanel;

	// Classes
	private MenuPage menu;
	private PlayPage playing;
	private SettingsPage settings;

	public boolean isRunning = true;
	public boolean begin = false;
	public boolean gameTxtSource = false; // False: mode normal //True: mode créateur
	public boolean rejouer = false;

	public int nbLevel = 6;
	public int nbLevelCreatif = 0;
	public int rejouerLevel = -1;

	public GameManager gameManager;
	public Plateau gamePlateau;

	protected boolean soundEnabled = false;

	private EnemyLevelLoader enemyLoader;
	private Player player;

	protected String label = "";
	public JLabel textLabel = new JLabel();

	public Game() {

		// initialisation:liste de perso, perso et des loaders
		this.perso_list = new ArrayList<>();
		this.player = new Player(null);
		if (!rejouer) {
			this.enemyLoader = new EnemyLevelLoader(player.getLevel());
		} else if (rejouerLevel != -1) {
			this.enemyLoader = new EnemyLevelLoader(rejouerLevel);
		}

		// initialisation du plateau (default) et du game Manager
		this.gamePlateau = new Plateau(gameTxtSource);
		this.gameManager = new GameManager(this, gamePlateau);
		this.gamePlateau.gameManager = gameManager;

		// initialisation des listeners
		this.addKeyListener(gameManager.getPlayerManager());
		setFocusable(true);
		this.addMouseMotionListener(gameManager.getMyMouseListener());
		this.addMouseListener(gameManager.getMyMouseListener());

		// initialisation du card layout
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		getContentPane().add(cardPanel);
		createPages();
		size_screen = new Dimension(1440, 840);
		setMinimumSize(size_screen);
		setPreferredSize(size_screen);
		setMaximumSize(size_screen);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void initialisation() { // initialisation quand le button play ou creative est cliqué
		// if (gameTxtSource) {
		// 	enemyLoader.setLevel(player.getLevelCreatif());
		// 	enemyLoader.loadLevelEnemies("Shooter/factory/enemyTmp.txt");
		// } else {
		// 	enemyLoader.setLevel(player.getLevel());
		// 	enemyLoader.loadLevelEnemies("Shooter/factory/EnemiesForLevels.txt");
		// }
		// this.perso_list = enemyLoader.createEnemiesForLevel();
		// this.perso_list.add(0, player);

		// // plateau et managers
		// int lvl = -1;
		// if (rejouer) {
		// 	lvl = rejouerLevel;
		// }
		// if (gameTxtSource) {
		// 	lvl = player.levelCreatif;
		// } else {
		// 	lvl = player.getLevel();
		// }
		// this.gamePlateau = new Plateau(gameTxtSource, lvl - 1);
		// this.gameManager.setGamePlateau(gamePlateau);
		// this.gameManager.getGamePlateau().getFloodFillManager().setGamePlateau(gamePlateau);
		// this.gamePlateau.gameManager = gameManager;
		// this.gamePlateau.getFloodFillManager().setGamePlateau(gamePlateau);
		// this.gamePlateau.getFloodFillManager().reset(gamePlateau);
		// this.gameManager.getProjectilesManager().setLevel_tab(gamePlateau.getLevel_tab());

		// this.gamePlateau.init(gameTxtSource, lvl - 1);
		// this.gameManager.getEnnemiManager().setPerso_list(perso_list);
		gameManager.reset();

	}

	private void createPages() {
		cardPanel.add(new MiniHistoire(this), "MiniHistoire");
		cardPanel.add(new FirstPage(this), "FirstPage");

		cardPanel.add(new MenuPage(this), "Menu");
		this.playing = new PlayPage(this);
		cardPanel.add(playing, "Play");
		cardPanel.add(new ChoixLevel(this), "ChoixLevel");
		cardPanel.add(new SettingsPage(this), "Settings");
		cardPanel.add(new EditingMode(this), "Editing");
		cardPanel.add(new GameOverPage(this), "GameOver");
		cardPanel.add(new GameWinPage(this), "GameWin");
		cardPanel.add(new ErrorPage(this, 1), "Error");
		cardPanel.add(new GameInfoPage(this), "Info");

		// cardLayout.show(cardPanel, "Menu");
		cardLayout.show(cardPanel, "MiniHistoire");

	}

	private void updateGame() {
		this.gamePlateau.repaint();
		this.gameManager.update();
		this.gameManager.isGameOver();
		this.gameManager.win();
	}

	public static void main(String[] args) {

		Game game = new Game();
		game.setVisible(true);
		game.run();

	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		// int frames = 0;
		// int updates = 0;

		long now;

		while (isRunning) {

			now = System.nanoTime();

			// Render
			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				// frames++;
			}

			// Update
			if (now - lastUpdate >= timePerUpdate) {
				// play est cliqué = début du jeu
				if (begin) {
					SwingUtilities.invokeLater(() -> {
						updateGame();
					});

				}
				lastUpdate = now;
				// updates++;
			}

			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				lastTimeCheck = System.currentTimeMillis();
			}
		}
	}

	// --------- GETTERS et SETTERS -----------
	public MenuPage getMenu() {
		return menu;
	}

	public boolean isBegin() {
		return begin;
	}

	public PlayPage getPlaying() {
		return playing;
	}

	public SettingsPage getSettings() {
		return settings;
	}

	public List<Personnage> getPersoList() {
		return this.perso_list;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean getSound() {
		return soundEnabled;
	}

	public void updateSound(boolean soundEnabled) {
		this.soundEnabled = soundEnabled;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}