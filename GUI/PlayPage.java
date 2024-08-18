package Shooter.GUI;

import Shooter.model.Game;
import java.awt.Graphics;


public class PlayPage extends GameScene {
	public Game game;

	public PlayPage(Game game) {
		super(game);
		this.game = game;
		add(createButton("Menu", "Menu"));
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		this.game.gamePlateau.graphismes.render_plateau(g); // ancien render pour gamePlateau
		this.game.gamePlateau.graphismes.paintComponent(g);

	}
}
