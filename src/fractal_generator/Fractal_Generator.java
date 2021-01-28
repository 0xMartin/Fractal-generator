// 
// Decompiled by Procyon v0.5.36
// 

package fractal_generator;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Font;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JWindow;
import java.awt.Color;
import FractalColoring.Data;

public class Fractal_Generator
{
    public static final Window window;
    
    public void init() {
        final int j = 1000;
        Data.colors = new int[j];
        for (int i = 0; i < j; ++i) {
            Data.colors[i] = Color.HSBtoRGB(i / 300.0f, 1.0f, i / (i + 15.0f));
        }
    }
    
    private void showSpash() {
        final JWindow splash = new JWindow();
        splash.setSize(500, 250);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splash.setLocation(screenSize.width / 2 - splash.getWidth() / 2, screenSize.height / 2 - splash.getHeight() / 2);
        splash.setVisible(true);
        splash.createBufferStrategy(2);
        final Graphics2D g2 = (Graphics2D)splash.getGraphics();
        g2.drawImage(new ImageIcon(this.getClass().getResource("/fractal_generator/src/splashBg.png")).getImage(), 0, 0, null);
        g2.setFont(new Font("Tahoma", 1, 24));
        g2.setColor(new Color(150, 80, 150));
        g2.drawString("Fractal generator", 32, 52);
        g2.setColor(new Color(170, 130, 220));
        g2.drawString("Fractal generator", 30, 50);
        g2.setColor(new Color(220, 130, 220));
        float f = 0.0f;
        while (f < 1.0f) {
            f += 0.02f;
            try {
                Thread.sleep(15L);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(Fractal_Generator.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.fillRect(0, splash.getHeight() - 15, (int)(splash.getWidth() * f), 15);
        }
        try {
            Thread.sleep(400L);
        }
        catch (InterruptedException ex) {
            Logger.getLogger(Fractal_Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        splash.setVisible(false);
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException ex) {
            Logger.getLogger(Fractal_Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(final String[] args) {
        final Fractal_Generator fg = new Fractal_Generator();
        fg.init();
        fg.showSpash();
        Fractal_Generator.window.setVisible(true);
        Fractal_Generator.window.renderingThread();
        Fractal_Generator.window.coloringPanel();
    }
    
    static {
        window = new Window();
    }
}
