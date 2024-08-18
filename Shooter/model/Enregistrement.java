package Shooter.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
// import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

//elle permet d'avoir acces pour tout les classes la recuperation de trois_carre.png
//éviter de la recopier a chaque fois
public class Enregistrement {
    public static BufferedImage getSpriteAtlas() {

		BufferedImage img = null;
		URL fichier = Enregistrement.class.getClassLoader().getResource("Shooter/res/image_combinee.png");
		try {
			img = ImageIO.read(fichier);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}
}
