package Shooter.model;

import java.awt.image.BufferedImage;

public class Case {
    private BufferedImage sprite;
	private int id, type;

	public Case(BufferedImage sprite, int id,  int type) {
		this.sprite = sprite;
		this.id = id;
        this.type = type;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return this.type;
	}
}
