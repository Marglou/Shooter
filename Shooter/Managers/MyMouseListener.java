package Shooter.Managers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Shooter.model.Player;
import Shooter.model.SoundPlayer;
import Shooter.model.Armes;
import Shooter.model.Bullet;
import Shooter.model.Crosshair;
import Shooter.model.Piege;

public class MyMouseListener implements MouseListener, MouseMotionListener {

    private Player player;
    private Crosshair crosshair;
    private ProjectilesManager projectilesManager;
    public GameManager gameManager;

    protected boolean mouseActive = true;
    protected SoundPlayer soundPlayer = new SoundPlayer();

    private Timer shootingTimer;
    private boolean isShooting;

    public MyMouseListener(Player player, Crosshair crosshair, ProjectilesManager projectilesManager,
            GameManager gameManager) {
        this.player = player;
        this.crosshair = crosshair;
        this.projectilesManager = projectilesManager;
        this.gameManager = gameManager;

        // Initialize the shooting timer
        shootingTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && mouseActive) {
            isShooting = true;

            if (player.getArmes().get(player.getCurrentArme()) instanceof Piege) {
                shoot();
                isShooting = false;
            } else {
                shoot(); // Initial shot
                startShootingTimer(); // Start continuous shooting with a slight delay
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (isShooting) {
                isShooting = false;
                stopShootingTimer(); // Stop continuous shooting
            }
        }
    }

    private void shoot() {
        if (!isShooting)
            return; // Prevent shooting if not in shooting mode

        Armes currentArme = player.getArmes().get(player.getCurrentArme());
        if (currentArme.getMunition() > 0) {
            if (currentArme.getType()) {

                // Bullet b = currentArme.tirer(player.getX(), player.getY(), crosshair.getX() +
                // crosshair.getCushion(),
                // crosshair.getY() + crosshair.getCushion(), currentArme.getPower(),
                // currentArme.getTypeMunition(), currentArme.color);
                // b.setCassant(currentArme.getDetruitMur());
                Bullet b = currentArme.tirer(player.getX(), player.getY(), crosshair.getX() + crosshair.getCushion(),
                        crosshair.getY() + crosshair.getCushion());

                if (b != null) {
                    projectilesManager.addBulletPlayer(b);
                    currentArme.shoot();
                }

                // if (soundPlayer != null && gameManager.getSound()) {
                // soundPlayer.playSound("Shooter/res/tir_sound.wav");
                // }
            } else {

                Piege piege = (Piege) player.getArmes().get(player.getCurrentArme());
                int xCrosshair = (int) (crosshair.getX() / 40);
                int yCrosshair = (int) (crosshair.getY() / 40);

                xCrosshair = adjustCoordForTile(crosshair.getX());
                yCrosshair = adjustCoordForTile(crosshair.getY());

                int xPlayer = (int) (player.getX() / 40);
                int yPlayer = (int) (player.getY() / 40);

                if (piege.isInPlateau(xCrosshair, yCrosshair, gameManager.getGamePlateau().getLevel_tab()) &&
                        piege.distanceToPlayer(crosshair.getX(), crosshair.getY(), player.getX(), player.getY())) {

                    piege.posePiege(crosshair.getX(), crosshair.getY(), xPlayer, yPlayer,
                            gameManager.getGamePlateau().getLevel_tab(), gameManager.getGamePlateau());
                }
            }
        } else {
            // if (soundPlayer != null && gameManager.getSound()) {
            // soundPlayer.playSound("Shooter/res/arme_vide.wav");
            // }
        }
    }

    public int adjustCoordForTile(double coord) {
        double tileSize = 40.0;
        double halfTile = tileSize / 2.0;
        double positionInTile = coord % tileSize;

        if (positionInTile < halfTile) {
            return (int) Math.floor(coord / tileSize);
        } else {
            return (int) Math.ceil(coord / tileSize);
        }
    }

    private void startShootingTimer() {
        Armes currentArme = player.getArmes().get(player.getCurrentArme());
        int delay = currentArme.getFrequenceTir(); // Get the shooting frequency from the weapon
        shootingTimer.setInitialDelay(delay); // Set an initial delay to prevent immediate double shot
        shootingTimer.setDelay(delay);
        shootingTimer.start();
    }

    private void stopShootingTimer() {
        shootingTimer.stop();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        crosshair.setX(e.getX() - 23);
        crosshair.setY(e.getY() - 46);
        int clickX = e.getX();
        int clickY = e.getY();

        double angle = Math.atan2(clickY - player.getY(), clickX - player.getX());
        player.setDirection(angle);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // To handle dragging, call shoot once and let the timer handle the rest
        if (!shootingTimer.isRunning() && mouseActive) {
            shoot(); // Initial shot
            startShootingTimer(); // Start continuous shooting
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public Crosshair getCrosshair() {
        return this.crosshair;
    }

    public void setMouse(boolean b) {
        mouseActive = b;
    }

    public void setPlayer(Player player2) {
        this.player = player2;
    }
}
