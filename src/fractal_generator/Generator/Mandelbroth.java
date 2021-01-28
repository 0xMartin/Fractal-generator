// 
// Decompiled by Procyon v0.5.36
// 

package fractal_generator.Generator;

import java.awt.Color;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.Graphics2D;
import fractal_generator.Data;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

public class Mandelbroth implements Fractal
{
    public float status;
    private int ITERATIONS;
    private Range REAL;
    private Range IMAGINAR;
    private Dimension SIZE;
    private SubThread t1;
    private SubThread t2;
    private SubThread t3;
    private SubThread t4;
    
    public Mandelbroth(final int iterations, final Range Real, final Range Imaginar, final Dimension size) {
        this.ITERATIONS = iterations;
        this.REAL = Real;
        this.IMAGINAR = Imaginar;
        this.SIZE = size;
    }
    
    @Override
    public void stopRendering() {
        this.t1.thread.stop();
        this.t2.thread.stop();
        this.t3.thread.stop();
        this.t4.thread.stop();
    }
    
    @Override
    public float getStatus() {
        return this.status;
    }
    
    @Override
    public float getStatusT1() {
        return this.t1.status;
    }
    
    @Override
    public float getStatusT2() {
        return this.t2.status;
    }
    
    @Override
    public float getStatusT3() {
        return this.t3.status;
    }
    
    @Override
    public float getStatusT4() {
        return this.t4.status;
    }
    
    @Override
    public BufferedImage renderImage(final double[] data) {
        Data.iterations = new ArrayList<long[][]>();
        BufferedImage image = null;
        try {
            image = new BufferedImage(this.SIZE.width, this.SIZE.height, (int)data[0]);
            Data.images.add(image);
            this.render((Graphics2D)image.getGraphics(), this.ITERATIONS, this.REAL, this.IMAGINAR, this.SIZE, data);
        }
        catch (OutOfMemoryError ex) {
            this.status = 100.0f;
            JOptionPane.showMessageDialog(null, "Out of memory");
        }
        this.status = 100.0f;
        image.flush();
        return image;
    }
    
    private void render(final Graphics2D g2, final int it, final Range re, final Range im, final Dimension size, final double[] data) {
        final double RePerTick = re.getDistance() / 2.0;
        final double ImPerTick = im.getDistance() / 2.0;
        final Range re2 = new Range(re.START, re.START + RePerTick);
        final Range re3 = new Range(re.START + RePerTick, re.START + RePerTick * 2.0);
        final Range im2 = new Range(im.START, im.START + ImPerTick);
        final Range im3 = new Range(im.START + ImPerTick, im.START + ImPerTick * 2.0);
        final Dimension s = new Dimension((int)(size.width / 2.0f), (int)(size.height / 2.0f));
        this.t1 = new SubThread(it, re2, im2, s, data);
        this.t1.thread.start();
        this.t1.threadID = 0;
        this.t2 = new SubThread(it, re2, im3, s, data);
        this.t2.thread.start();
        this.t2.threadID = 1;
        this.t3 = new SubThread(it, re3, im2, s, data);
        this.t3.thread.start();
        this.t3.threadID = 2;
        this.t4 = new SubThread(it, re3, im3, s, data);
        this.t4.thread.start();
        this.t4.threadID = 3;
        while (this.t1.status != 100.0f || this.t2.status != 100.0f || this.t3.status != 100.0f || this.t4.status != 100.0f) {
            this.status = (this.t1.status + this.t2.status + this.t3.status + this.t4.status) / 4.0f;
            try {
                Thread.sleep(20L);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(Mandelbroth.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        final ImageObserver ob = null;
        g2.drawImage(this.t1.image, 0, 0, ob);
        g2.drawImage(this.t2.image, 0, s.height, ob);
        g2.drawImage(this.t3.image, s.width, 0, ob);
        g2.drawImage(this.t4.image, s.width, s.height, ob);
    }
    
    private class SubThread implements Runnable
    {
        public float status;
        private int it;
        private Range re;
        private Range im;
        private Dimension size;
        public BufferedImage image;
        public int threadID;
        public Thread thread;
        
        public SubThread(final int iterations, final Range Real, final Range Imaginar, final Dimension size, final double[] data) {
            this.threadID = 0;
            this.image = new BufferedImage(size.width, size.height, (int)data[0]);
            this.it = iterations;
            this.re = Real;
            this.im = Imaginar;
            this.size = size;
            this.thread = new Thread(this);
        }
        
        @Override
        public void run() {
            final Graphics2D g2 = (Graphics2D)this.image.getGraphics();
            this.renderFractal(g2, this.it, this.re, this.im, this.size);
            this.status = 100.0f;
        }
        
        public void renderFractal(final Graphics2D graphics, final int iterations, final Range Real, final Range Imaginar, final Dimension size) {
            long[][] iter = null;
            try {
                iter = new long[size.width][size.height];
            }
            catch (OutOfMemoryError ex) {
                JOptionPane.showMessageDialog(null, "Iteration analizator overflow");
            }
            final int[] colors = FractalColoring.Data.colors;
            final double RealPerOne = Real.getDistance() / size.width;
            final double ImaginarPerOne = Imaginar.getDistance() / size.height;
            for (int x = 0; x < size.width; ++x) {
                this.status = x / (float)size.width * 100.0f;
                for (int y = 0; y < size.height; ++y) {
                    final double CRe = Real.START + x * RealPerOne;
                    double CIm;
                    double X;
                    double Y;
                    int iteration;
                    double x_new;
                    for (CIm = Imaginar.START + y * ImaginarPerOne, X = 0.0, Y = 0.0, iteration = 0; X * X + Y * Y <= 4.0 && iteration < iterations; Y = 2.0 * X * Y + CIm, X = x_new, ++iteration) {
                        x_new = X * X - Y * Y + CRe;
                    }
                    try {
                        iter[x][y] = iteration;
                    }
                    catch (Exception ex2) {}
                    if (iteration < iterations) {
                        try {
                            graphics.setColor(Color.getColor("", colors[iteration]));
                        }
                        catch (Exception ex3) {}
                        graphics.fillRect(x, y, 1, 1);
                    }
                    else {
                        graphics.setColor(Color.black);
                        graphics.fillRect(x, y, 1, 1);
                    }
                }
            }
            Data.iterations.add(iter);
        }
    }
}
