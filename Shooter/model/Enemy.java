package Shooter.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import Shooter.Managers.FloodFillManager;
import Shooter.Managers.GameManager;
import Shooter.Managers.ManagerCase;
import Shooter.Managers.ProjectilesManager;

public abstract class Enemy extends Personnage {

	protected int id;
	protected int power; // faire des dégats de collision et de tir
	protected int collisionPower;
	protected int destX;
	protected int destY;
	protected float differenceX;
	protected float differenceY;
	protected int frequency;
	protected long lastShotTime;
	protected int detectionRadius; // Rayon de détection du joueur
	protected int tailleBar = 40;
	protected int vieTotal = 100;
	protected int currentSpeed = 0;
	public double direction; // direction pour ajouter le halo de vision
	protected ImageIcon sprite;

	public Color color;

	protected int prevX = 0;
	protected int prevY = 0;

	protected int[][] direction2 = { { 1, 0 }, { 0, 1 }, { 0, -1 }, { -1, 0 } };

	private ImageIcon[] walkSprites;
    private static final int NUM_WALK_FRAMES = 3;
	private int currentDirection;
	public int typeMunitionArme;
    public int animationIndexE;
    public int tickE;
    public int ANIMATION_SPEED_E = 60;


	public Enemy(int x, int y, int size, int sante, int maxSpeed, int id, int power, int collisionPower, int frequency,
			int detectionRadius, Color color, int typeMunitionArme) {
		super(x, y, size, sante, maxSpeed);
		this.id = id;
		this.power = power;
		this.collisionPower = collisionPower;
		this.frequency = frequency;
		this.detectionRadius = detectionRadius;
		this.color = color;
		this.typeMunitionArme=typeMunitionArme;
		loadWalkSprites(id);
	}

	public Enemy(int x, int y, int size, int sante, int maxSpeed, int id, int power, int collisionPower,
			int detectionRadius, Color color, int typeMunitionArme) {
		super(x, y, size, sante, maxSpeed);
		this.id = id;
		this.power = power;
		this.collisionPower = collisionPower;
		this.detectionRadius = detectionRadius;
		this.color = color;
		this.typeMunitionArme=typeMunitionArme;
		loadWalkSprites(id);
	}

	public void loadSprite() {
		URL imageUrl = getClass().getResource("we_2.png");
		ImageIcon originalImage = new ImageIcon(imageUrl);

		double scale = 5;
		int newWidth = (int) (size * scale);
		int newHeight = (int) (size * scale);

		Image resizedImage = originalImage.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		ImageIcon enemyImage = new ImageIcon(resizedImage);
		this.sprite = enemyImage;
	}

	private void loadWalkSprites(int id) {
        walkSprites = new ImageIcon[NUM_WALK_FRAMES]; // NUM_WALK_FRAMES est le nombre total de frames de marche
        for (int i = 0; i < NUM_WALK_FRAMES; i++) {
            String filename = "../res/we_"+id+"_" + i + ".png"; // Nom du fichier de l'image de marche
            
            URL imageUrl = getClass().getResource(filename);
            ImageIcon walkSprite = new ImageIcon(imageUrl);

            double scale = 5;
            int newWidth = (int) (size * scale);
            int newHeight = (int) (size * scale);

            Image resizedImage = walkSprite.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon enemyImage = new ImageIcon(resizedImage);
            walkSprites[i] = enemyImage;
			this.sprite = walkSprites[0];
        }
    }


	public abstract void updateBehavior(Player player, FloodFillManager floodFillManager, ProjectilesManager projectilesManager, int xTile, int yTile);

	public void shootBehavior(Player player, ProjectilesManager projectilesManager){}


	public boolean isPlayerDetected(Player player) {
		// Calculer la distance entre l'ennemi et le joueur
		double distance = Math.sqrt(Math.pow(player.x - this.x, 2) + Math.pow(player.y - this.y, 2));

		// Vérifier si le joueur est dans le rayon de détection
		if (distance <= detectionRadius) {
			// Le joueur est détecté
			return true;
		}
		return false;
	}

	public void ralentissement (GameManager gameManager, int xTile, int yTile) {
		int[][] map = gameManager.getGamePlateau().getLevel_tab();
		
		int caseType = ManagerCase.getCaseType(map[yTile][xTile]);
		if (caseType == ManagerCase.OBSTACLE) {
			this.maxSpeed = 1;
		} else {
			this.maxSpeed = 2;
		}
	}
	

	public Image getRotImg(Image image, double angle) {
		// Créez une image buffer pour dessiner l'image pivotée
		BufferedImage bufImg = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufImg.createGraphics();
	
		// Appliquez la transformation de rotation à l'image buffer
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angle), image.getWidth(null) / 2, image.getHeight(null) / 2);
		g2d.setTransform(tx);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	
		// Retournez l'image pivotée
		return bufImg;
	}

	public void updateTickE() {
        tickE++;
        // cela régule la vitesse de l'animation, plus le nombre est élevé, plus
        // l'animation est lente
        if (tickE >= ANIMATION_SPEED_E) {
            tickE = 0;
            animationIndexE++;
            if (animationIndexE >= 3) {
                animationIndexE = 0;
            }
        }
    }

	public void drawEnemy(Graphics g,int animationIndex) {
		 // Récupérez l'image à dessiner
		 Image image = walkSprites[animationIndex].getImage();

		 if (currentDirection == 0) {
			
			g.drawImage(getRotImg(image, 270), x-28, y-36, size + 10, size + 10, null);
		} else if (currentDirection == 2) {
			
			g.drawImage(getRotImg(image, 90), x-25, y-15, size + 10, size + 10, null);
		} else if (currentDirection == 3) {
			
			g.drawImage(getRotImg(image, 180), x-36, y-24, size + 10, size + 10, null);
		}else{
			g.drawImage(getRotImg(image, 0), x-15, y-25, size + 10, size + 10, null);
		}
        
    }


	// floodfill = distance entre l'ennemi et le joueur
	public void moveTowardsPlayer(int[][] distances, Player player, int xTile, int yTile) {
		int nextX = xTile;
		int nextY = yTile;

		if (prevX > this.x){
			nextX += 1; // Convertir la position X de l'ennemi en coordonnées de tableau
		} 

		if (prevY > this.y){
			nextY += 1; // Convertir la position Y de l'ennemi en coordonnées de tableau
		}
		
		// Déterminer la direction vers la case avec la distance la plus courte
		int minDistance = distances[nextY][nextX];
		int dirX = 0;
		int dirY = 0;

		// Parcourir les directions pour trouver la case avec une distance plus courte
		for (int[] dir : direction2) {
			int newX = nextX + dir[0];
			int newY = nextY + dir[1];

			// Vérifier si la case est valide et si la distance est plus courte
			if (newX >= 0 && newX < distances[0].length && newY >= 0 && newY < distances.length && distances[newY][newX] < minDistance && distances[newY][newX] != 100){

				minDistance = distances[newY][newX];
				dirX = dir[0];
				dirY = dir[1];
				break;
			}
		}
	
		// Déplacer l'ennemi dans la direction choisie
		move(dirX, dirY);
	}

	public void move (int x, int y){
		prevX = this.x;
		prevY = this.y;
		
		if (x == 1) {
			this.x += this.maxSpeed;
			currentDirection = 1;
		} else if (x == -1) {
			this.x -= this.maxSpeed;
			currentDirection = 3;
		}
	
		if (y == 1) {
			this.y += this.maxSpeed;
			currentDirection = 2;
		} else if (y == -1) {
			this.y -= this.maxSpeed;
			currentDirection = 0; 
		}
		updateTickE();
	}


    public void shoot(Player player, ProjectilesManager projectilesManager) {
        // Implémentez la logique de tir spécifique pour cet ennemi
        // Par exemple, tirer une balle vers le joueur
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime > getFrequency()) {
            Bullet bullet = new Bullet(x, y, player.x, player.y, power, typeMunitionArme);
            projectilesManager.getEnemyBullets().add(bullet);
            lastShotTime = currentTime;
        }
    }

	public void infligerDegatsCollision(Player player) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastShotTime > getFrequency()) {
			player.infligerDegats(collisionPower);
			lastShotTime = currentTime;
		}
	}

	public boolean isWallBetween(int xCible, int yCible, int[][] floodfill, int xTile, int yTile) {
		int xPlayer = convertPositionToTile(xCible);
		int yPlayer = convertPositionToTile(yCible);
		int xEnnemi = xTile;
		int yEnnemi = yTile;

        // Déterminer les bornes du parcours du floodfill entre les deux points
        int startX = Math.min(xPlayer, xEnnemi);
        int endX = Math.max(xPlayer, xEnnemi);
        int startY = Math.min(yPlayer, yEnnemi);
        int endY = Math.max(yPlayer, yEnnemi);
    
        // Parcourir le floodfill entre les deux points
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                // Vérifier si la case est un mur ou un obstacle
                if (floodfill[y][x] == 1000) {
                    // Un mur ou un obstacle bloque le chemin
                    return true;
                }
            }
        }
        // Aucun mur ou obstacle trouvé entre les deux points
        return false;
    }


	public int convertPositionToTile(int position) {
		// Convertit une position en coordonnées de tableau
		return position / 40;
	}

	
    protected boolean isWall(int xPos, int yPos, int[][] map) {
        // Vérifie si une case est un mur
        int x = convertPositionToTile(xPos);
        int y = convertPositionToTile(yPos);
        return map[y][x] == ManagerCase.MUR || map[y][x] == ManagerCase.CASSANT;
    }

	public void drawBarVie(Graphics g) {
		int barWidth = 40; // Largeur fixe de la barre de vie
		int barHeight = 7; // Hauteur fixe de la barre de vie
		int barX = this.x - (barWidth / 2) + 5;
		int barY = this.y - 30;
	
		// Calculez la proportion de vie restante
		float lifePercentage = (float) this.sante / this.vieTotal;
	
		// Calculez la largeur actuelle de la barre de vie en fonction de la vie restante
		int currentBarWidth = (int) (barWidth * lifePercentage);
	
		// Dessinez le fond de la barre de vie (rouge)
		g.setColor(Color.RED);
		g.fillRect(barX, barY, barWidth, barHeight);
	
		// Dessinez la barre de vie actuelle (verte) par-dessus
		g.setColor(Color.GREEN);
		g.fillRect(barX, barY, Math.min(currentBarWidth, barWidth), barHeight);
	
		// Ajoutez une bordure noire autour de la barre de vie
		g.setColor(Color.BLACK);
		g.drawRect(barX, barY, barWidth, barHeight);
	}
	



	// ------------- Getters et setters ---------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getCollisionPower() {
		return collisionPower;
	}

	public void setCollisionPower(int collisionPower) {
		this.collisionPower = collisionPower;
	}

	public int getSpeed() {
		return maxSpeed;
	}

	public void setSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getDestX() {
		return destX;
	}

	public void setDestX(int destX) {
		this.destX = destX;
	}

	public int getDestY() {
		return destY;
	}

	public void setDestY(int destY) {
		this.destY = destY;
	}

	public float getDifferenceX() {
		return differenceX;
	}

	public void setDifferenceX(float differenceX) {
		this.differenceX = differenceX;
	}

	public float getDifferenceY() {
		return differenceY;
	}

	public void setDifferenceY(float differenceY) {
		this.differenceY = differenceY;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public long getLastShotTime() {
		return lastShotTime;
	}

	public int getDetectionRadius() {
		return detectionRadius;
	}

	public float getDurerVie() {
		return this.sante / (float) this.vieTotal;
	}

	public ImageIcon getSprite(){
		return this.sprite;
	}

	public int getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(int currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public int getanimationIndexE(){
        return this.animationIndexE;
    }


}
