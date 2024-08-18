package Shooter.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class CustomComboBox extends DefaultListCellRenderer {
    private Font font;
    Font loadShooterFont(int i) {
        try {
            InputStream fontStream = getClass().getResourceAsStream("../../fonts/Font"+i+".otf");
            if (fontStream == null) {
                throw new FileNotFoundException("Fichier de police introuvable.");
            }
            return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(30f);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 14);
        }
    }
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        
        
        font=loadShooterFont(1);
        label.setFont(font); 
        label.setForeground(new Color(32, 53, 96)); 

        return label;
    }
}
