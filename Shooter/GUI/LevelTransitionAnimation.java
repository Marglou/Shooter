package Shooter.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LevelTransitionAnimation extends JFrame {
    private JPanel transitionPanel;
    private Timer timer;
    private float alpha = 1.0f;

    public LevelTransitionAnimation() {
        setTitle("Level Transition Animation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        transitionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, alpha));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        getContentPane().add(transitionPanel);

        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.02f;
                if (alpha <= 0.0f) {
                    alpha = 0.0f;
                    timer.stop();
                    
                }
                transitionPanel.repaint();
            }
        });
    }

    public void startTransition() {
        alpha = 1.0f;
        timer.start();
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             LevelTransitionAnimation animation = new LevelTransitionAnimation();
    //             animation.setVisible(true);

    //         }
    //     });
    // }
}
