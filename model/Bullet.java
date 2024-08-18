package Shooter.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bullet {

    public int size = 10; // Taille du projectile
    public float distanceTraveled = 0; // Distance parcourue par le projectile
    private float slowdownFactor = 15f; // Facteur de ralentissement pour ajuster la vitesse du projectile

    public float x; // Coordonnée x du projectile
    public float y; // Coordonnée y du projectile
    public float differenceX; // Composante de mouvement en x
    public float differenceY; // Composante de mouvement en y
    private int degats;
    public Color color; // Couleur du projectile
    private BufferedImage[] mImages;
    public int typeMunitionArme;

    public boolean rebond = false;
    protected boolean cassant;

    // Constructeur de la classe Bullet
    // x, y : Coordonnées initiales du projectile
    // destX, destY : Coordonnées de la destination du projectile
    public Bullet(int x, int y, int destX, int destY, int degats, int typeMunitionArme) {
        this.x = x;
        this.y = y;
        this.degats = degats;
        this.typeMunitionArme = typeMunitionArme;
        calculateMovement(destX, destY); // Calcul des composantes de mouvement en fonction de la destination
        loadMunitionImage();
    }

    public Bullet(int x, int y, int destX, int destY, int degats, int typeMunitionArme, boolean rebond) {
        this.x = x;
        this.y = y;
        this.degats = degats;
        this.typeMunitionArme = typeMunitionArme;
        this.rebond = rebond;
        calculateMovement(destX, destY); // Calcul des composantes de mouvement en fonction de la destination
        loadMunitionImage();
    }

    public Bullet(int x, int y, int destX, int destY, int degats) {
        this.x = x;
        this.y = y;
        this.degats = degats;
        calculateMovement(destX, destY); // Calcul des composantes de mouvement en fonction de la destination
        loadMunitionImage();
    }

    public Bullet(int x, int y, int destX, int destY, int degats, int typeMunitionArme, Color color) {
        this.x = x;
        this.y = y;
        this.degats = degats;
        this.typeMunitionArme = typeMunitionArme;
        this.color = color;
        this.rebond = true;
        calculateMovement(destX, destY); // Calcul des composantes de mouvement en fonction de la destination
    }

    public Bullet(int x, int y, int destX, int destY, int degats, int typeMunitionArme, Color color, boolean cassant) {
        this.x = x;
        this.y = y;
        this.degats = degats;
        this.typeMunitionArme = typeMunitionArme;
        this.color = color;
        this.rebond = false;
        this.cassant = cassant;
        calculateMovement(destX, destY); // Calcul des composantes de mouvement en fonction de la destination
        loadMunitionImage();
    }

    // public Bullet(int x, int y, int destX, int destY, int degats, Color color) {
    // this.x = x;
    // this.y = y;
    // this.degats = degats;
    // this.color = color;
    // calculateMovement(destX, destY); // Calcul des composantes de mouvement en
    // fonction de la destination
    // loadMunitionImage();
    // }

    public void loadMunitionImage() {
        BufferedImage atlas = Enregistrement.getSpriteAtlas();
        mImages = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            mImages[i] = atlas.getSubimage( i * 40, 17 * 40, 40, 40);
        }
    }

    private void updateDistanceTraveled() {
        float diffX = differenceX * differenceX;
        float diffY = differenceY * differenceY;
        float distanceSquared = diffX + diffY + 3 * size;
        distanceTraveled += Math.sqrt(distanceSquared);
    }

    // Calcule les composantes de mouvement en fonction de la destination
    private void calculateMovement(int destX, int destY) {
        float angle = calculateAngle(destX, destY); // Calcul de l'angle entre la position actuelle et la destination
        calculateDifferences(angle); // Calcul des composantes de mouvement en fonction de l'angle
    }

    // Calcule l'angle entre la position actuelle et la destination
    private float calculateAngle(int destX, int destY) {
        return (float) Math.atan2(destY - y, destX - x);
    }

    // Calcule les composantes de mouvement en fonction de l'angle
    private void calculateDifferences(float angle) {
        // Les composantes de mouvement sont obtenues en utilisant l'angle et le facteur
        // de ralentissement
        differenceX = (float) Math.cos(angle) * slowdownFactor;
        differenceY = (float) Math.sin(angle) * slowdownFactor;
    }

    // Dessine le projectile à sa position actuelle
    public void createBullet(Graphics g) {

        updateDistanceTraveled();
        // Cast Graphics to Graphics2D
        Graphics2D g2d = (Graphics2D) g.create();

        // Translate and rotate the graphics context
        g2d.translate(x, y); // Translate to the position of the bullet
        double angle = Math.atan2(differenceY, differenceX); // Calculate angle of motion
        g2d.rotate(angle); // Rotate the image to align with the direction of motion

        // Draw the bullet image
        if (typeMunitionArme <5 ) {
            g2d.drawImage(mImages[typeMunitionArme], -size / 2, -size / 2, null); // Draw from the center of the bullet
        } else {
            g2d.setColor(color); // Définir la couleur du cercle
            // Dessiner le cercle centré sur la position actuelle du projectile
            int circleX = -(size / 2); // Coordonnée x du coin supérieur gauche du cercle
            int circleY = -(size / 2); // Coordonnée y du coin supérieur gauche du cercle
            g2d.fillOval(circleX, circleY, size, size); // Dessiner le cercle
        }
        // Dispose the graphics context to release resources
        g2d.dispose();
        // Mettre à jour les coordonnées du projectile
        this.x += differenceX;
        this.y += differenceY;
    }


    public boolean isOutOfBounds() {
        if (this.x <= this.size || this.y <= this.size || this.x >= 1400 || this.y >= 850) {
            return true;
        }
        return false;
    }

    // ---------------------- Balles rebondissantes ----------------------

    //BEAU DROITE GAUCHE
    public void reboundD(float wallX1, float wallY1, float wallX2, float wallY2, Player player) {
        this.distanceTraveled = 0;
    
        // Calculer le vecteur entre les deux points sur le mur
        float wallVectorX = wallX2 - wallX1;
        float wallVectorY = wallY2 - wallY1;
    
        // Déterminer la position de la balle par rapport au mur
        float ballCenterX = player.x + player.size / 2;
        float ballCenterY = player.y + player.size / 2;
    
        boolean ballAbove = ballCenterY < wallY1;
        boolean ballBelow = ballCenterY > wallY2;
        boolean ballLeft = ballCenterX < wallX1;
        boolean ballRight = ballCenterX > wallX2;
    
        // Calculer le vecteur normal au mur en fonction de la position de la balle
        float wallNormalX, wallNormalY;
        if (ballAbove || ballBelow) {
            // Le vecteur normal est horizontal
            wallNormalX =  -wallVectorY;
            wallNormalY = wallVectorX;
        } else if (ballLeft || ballRight) {
            // Le vecteur normal est vertical
            wallNormalX = ballLeft ? 1 : -1;
            wallNormalY = 0;
        } else {
            // La balle est exactement sur le mur, ignorer le rebond
            return;
        }
    
        // Normaliser le vecteur normal
        float length = (float) Math.sqrt(wallNormalX * wallNormalX + wallNormalY * wallNormalY);
        wallNormalX /= length;
        wallNormalY /= length;
    
        // Calculer la vitesse de la balle
        float speed = (float) Math.sqrt(differenceX * differenceX + differenceY * differenceY);
    
        // Normaliser le vecteur de vitesse de la balle
        float ballDirectionX = differenceX / speed;
        float ballDirectionY = differenceY / speed;
    
        // Calculer le produit scalaire entre la direction de la balle et la direction
        // normale du mur
        float dotProduct = ballDirectionX * wallNormalX + ballDirectionY * wallNormalY;
    
        // Calculer le vecteur de réflexion
        float reflectionX = ballDirectionX - 2 * dotProduct * wallNormalX;
        float reflectionY = ballDirectionY - 2 * dotProduct * wallNormalY;
    
        // Mettre à jour les composantes de vitesse de la balle avec le vecteur de
        // réflexion
        this.differenceX = reflectionX * speed;
        this.differenceY = reflectionY * speed;
    }

    
    //BEAU HAUT BAS
    public void reboundB(float wallX1, float wallY1, float wallX2, float wallY2, Player player) {
        this.distanceTraveled = 0;
    
        // Calculer le vecteur entre les deux points sur le mur
        // float wallVectorX = wallX2 - wallX1;
        // float wallVectorY = wallY2 - wallY1;
    
        // Déterminer la position de la balle par rapport au mur
        float ballCenterX = player.x + player.size / 2;
        float ballCenterY = player.y + player.size / 2;
    
        boolean ballAbove = ballCenterY < wallY1;
        boolean ballBelow = ballCenterY > wallY2;
        boolean ballLeft = ballCenterX < wallX1;
        boolean ballRight = ballCenterX > wallX2;
    
        // Calculer le vecteur normal au mur en fonction de la position de la balle
        float wallNormalX, wallNormalY;
        if (ballAbove || ballBelow) {
            // Le vecteur normal est horizontal
            wallNormalX = 0;
            wallNormalY = ballAbove ? -1 : 1;
        } else if (ballLeft || ballRight) {
            // Le vecteur normal est vertical
            wallNormalX = ballLeft ? -1 : 1;
            wallNormalY = 0;
        } else {
            // La balle est exactement sur le mur, ignorer le rebond
            return;
        }
    
        // Normaliser le vecteur normal
        float length = (float) Math.sqrt(wallNormalX * wallNormalX + wallNormalY * wallNormalY);
        wallNormalX /= length;
        wallNormalY /= length;
    
        // Calculer la vitesse de la balle
        float speed = (float) Math.sqrt(differenceX * differenceX + differenceY * differenceY);
    
        // Normaliser le vecteur de vitesse de la balle
        float ballDirectionX = differenceX / speed;
        float ballDirectionY = differenceY / speed;
    
        // Calculer le produit scalaire entre la direction de la balle et la direction
        // normale du mur
        float dotProduct = ballDirectionX * wallNormalX + ballDirectionY * wallNormalY;
    
        // Calculer le vecteur de réflexion
        float reflectionX = ballDirectionX - 2 * dotProduct * wallNormalX;
        float reflectionY = ballDirectionY - 2 * dotProduct * wallNormalY;
    
        // Mettre à jour les composantes de vitesse de la balle avec le vecteur de
        // réflexion
        this.differenceX = reflectionX * speed;
        this.differenceY = reflectionY * speed;
    } 
    


    // --------- Getters et Setters ---------

    public int getDegats() {
        return this.degats;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public float getSlowdownFactor() {
        return slowdownFactor;
    }

    public void setSlowdownFactor(float slowdownFactor) {
        this.slowdownFactor = slowdownFactor;
    }

    public float getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }

    public BufferedImage[] getmImages() {
        return mImages;
    }

    public void setmImages(BufferedImage[] mImages) {
        this.mImages = mImages;
    }

    public void setDistanceTraveled(float distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public int getTypeMunitionArme() {
        return typeMunitionArme;
    }

    public void setTypeMunitionArme(int typeMunitionArme) {
        this.typeMunitionArme = typeMunitionArme;
    }

    public boolean getRebond() {
        return rebond;
    }

    public void setRebond(boolean rebond) {
        this.rebond = rebond;
    }

    public boolean getCassant() {
        return cassant;
    }

    public void setCassant(boolean cassant) {
        this.cassant = cassant;
    }

}
