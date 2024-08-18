package Shooter.Managers;

import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.event.KeyAdapter;

import Shooter.model.Player;

public class PlayerManager extends KeyAdapter {

    protected GameManager gameManager;
    protected Player player;
    private long obstacleTimer = 0;
    private static final long OBSTACLE_BLOCAGE = 3000; // 3 secondes en millisecondes
    private boolean isBlocked = false;
    private boolean tournie = false;
    private int nombreblocage = 0;
    private long tournieStartTime = 0;
    private static final long TOURNIE_DURATION = 3000; // 3 secondes en millisecondes
    private static final long OBSTACLE_DELAY = 1000;

    private transient ImageIcon[] walkSprites;
    private static final int NUM_WALK_FRAMES = 3;

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.player = gameManager.getPlayer();
        loadWalkSprites();
    }

    public void reset() {
        this.player.setXSpeed(0);
        this.player.setYSpeed(0);
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        if (isBlocked) {
            if (nombreblocage == 0 && !tournie) {
                // Gestion du blocage sans tournoi
                if (currentTime - obstacleTimer >= OBSTACLE_BLOCAGE) {
                    isBlocked = false;
                    player.setMaxSpeed(2); // Rétablir la vitesse normale
                } else {
                    gameManager.getMyMouseListener().setMouse(false);
                    return; // Ne pas mettre à jour la position du joueur tant que le délai n'est pas écoulé
                }
            } else if (nombreblocage == 0 && tournie) {
                // Gestion du blocage avec tournoi
                if (currentTime - obstacleTimer >= OBSTACLE_DELAY) {
                    isBlocked = false;
                    tournie = false;
                    player.setMaxSpeed(2); // Rétablir la vitesse normale
                    tournieStartTime = 0; // Réinitialiser le temps de début du tournoi
                } else {
                    // Permettre au joueur de tourner même s'il est bloqué
                    if (tournieStartTime == 0) {
                        tournieStartTime = currentTime; // Enregistrer le début du tournoi
                    }

                    if (currentTime - tournieStartTime >= TOURNIE_DURATION) {
                        player.stopTournie(gameManager.getMyMouseListener()); // Arrêter le tournoi
                    } else {
                        player.tournie(gameManager.getMyMouseListener()); // Continuer à tourner
                    }
                    return; // Ne pas mettre à jour la position du joueur tant que le délai n'est pas écoulé
                }
            }
        }

        // Mettre à jour la position du joueur en fonction de sa vitesse
        int newX = player.getX() + player.getXSpeed();
        int newY = player.getY() + player.getYSpeed();

        // Vérifier si la prochaine position est valide (sans collision avec un mur)
        if (isValidPosition(newX, newY)) {
            player.setX(newX);
            player.setY(newY);
        } else {
            // Réinitialiser la vitesse si la position n'est pas valide
            player.setXSpeed(0);
            player.setYSpeed(0);
        }

        int nextTileType = gameManager.getGamePlateau().getLevel_tab()[newY / 40][newX / 40];
        int casetype = ManagerCase.getCaseType(nextTileType);

        switch (casetype) {
            // Cas où le joueur rencontre un obstacle ralentisseur (eau)
            case ManagerCase.RALENTIE:
                player.setMaxSpeed(1);
                gameManager.getGamePlateau().setANIMATION_SPEED(50);
                break;

            case ManagerCase.ACCELERATION:
                player.setMaxSpeed(3);
                gameManager.getGamePlateau().setANIMATION_SPEED(20);
                break;

            case ManagerCase.PERTE:
                player.setSante(player.getSante() - 3);
                break;

            case ManagerCase.TOURNIE:
                player.setMaxSpeed(1);
                tournie = true;
                if (!isBlocked) {
                    tournieStartTime = System.currentTimeMillis(); // Début du tournoi
                }
                player.tournie(gameManager.getMyMouseListener());
                isBlocked = true;
                nombreblocage += 1;
                break;

            case ManagerCase.OBSTACLE:
                tournie = false;
                isBlocked = true;
                nombreblocage += 1;
                obstacleTimer = System.currentTimeMillis();
                break;

            default:
                tournie = false;
                player.setMaxSpeed(2);
                gameManager.getGamePlateau().setANIMATION_SPEED(30);
                nombreblocage = 0;
                gameManager.getMyMouseListener().setMouse(true);
                break;
        }

    }

    // Chargement des images de marche depuis des fichiers ou des ressources
    private void loadWalkSprites() {
        walkSprites = new ImageIcon[NUM_WALK_FRAMES]; // NUM_WALK_FRAMES est le nombre total de frames de marche
        for (int i = 0; i < NUM_WALK_FRAMES; i++) {
            String filename = "../res/walk_" + i + ".png"; // Nom du fichier de l'image de marche

            URL imageUrl = getClass().getResource(filename);
            ImageIcon walkSprite = new ImageIcon(imageUrl);

            double scale = 5;
            int newWidth = (int) (player.getSize() * scale);
            int newHeight = (int) (player.getSize() * scale);

            Image resizedImage = walkSprite.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon joueurImage = new ImageIcon(resizedImage);
            walkSprites[i] = joueurImage;
        }

    }

    // Méthode pour dessiner le joueur en utilisant l'image de marche actuelle
    public Image imagePlayer(int animationIndex) {
        return walkSprites[animationIndex].getImage();
    }

    private boolean isValidPosition(float x, float y) {
        // Vérifier si la position est à l'intérieur des limites du plateau de jeu
        int playerSize = player.getSize();
        int minX = playerSize;
        int minY = playerSize;
        int maxX = 1440 - playerSize;
        int maxY = 840 - playerSize;

        if (x < minX || x > maxX || y < minY || y > maxY) {
            return false;
        }

        // Obtenir les indices de la case correspondante dans le tableau du plateau de
        // jeu
        int xIndex = (int) (x / 40);
        int yIndex = (int) (y / 40);

        // Obtenir le type de la case
        int caseType = ManagerCase.getCaseType(gameManager.getGamePlateau().getLevel_tab()[yIndex][xIndex]);

        // Vérifier si la case est un mur, un mur cassant ou une case bloquante
        return (caseType != ManagerCase.MUR && caseType != ManagerCase.CASSANT && caseType != ManagerCase.BLOQUE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Mettre à jour les variables selon les touches pressées
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_Z) {
            player.setYSpeed(-player.getMaxSpeed());
        } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            player.setYSpeed(player.getMaxSpeed());
        } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_Q) {
            player.setXSpeed(-player.getMaxSpeed());
        } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            player.setXSpeed(player.getMaxSpeed());
        }

        if (code == KeyEvent.VK_SPACE) {
            gameManager.managerArmes.changeArme();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Réinitialiser la vitesse lorsque la touche est relâchée
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_Z || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            player.setYSpeed(0);
        } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_Q || code == KeyEvent.VK_RIGHT
                || code == KeyEvent.VK_D) {
            player.setXSpeed(0);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        loadWalkSprites();
    }

    public void setGameManager(GameManager gameManager2) {
        this.gameManager = gameManager2;
    }

}
