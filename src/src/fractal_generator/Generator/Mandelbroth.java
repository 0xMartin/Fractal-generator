/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal_generator.Generator;

import fractal_generator.Data;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marti
 */
public class Mandelbroth implements Fractal {

    public float status;
    
    private int ITERATIONS;
    private Range REAL, IMAGINAR;
    private Dimension SIZE;

    /**
     * MandelBroth
     *
     * @param iterations - number of iterations
     * @param Real - Range on real axis
     * @param Imaginar - Range on imaginar axis
     * @param size - size of image
     */
    public Mandelbroth(int iterations, Range Real, Range Imaginar, Dimension size) {
        this.ITERATIONS = iterations;
        this.REAL = Real;
        this.IMAGINAR = Imaginar;
        this.SIZE = size;
    }

    @Override
    public float getStatus() {
        return this.status;
    }
    
    /**
     * MandelBroth
     *
     * @param data DATA[0] - BufferedImage.imageType
     * @return BufferedImage of MandelBroth fractal
     */
    @Override
    public BufferedImage renderImage(double[] data) {
        /**
         * data[0] - imageType
         */
        BufferedImage image = null;
        try {
            image = new BufferedImage(this.SIZE.width, this.SIZE.height, (int)data[0]);
            Data.images.add(image);
            render(
                    (Graphics2D) image.getGraphics(),
                    this.ITERATIONS,
                    this.REAL,
                    this.IMAGINAR,
                    this.SIZE,
                    data
            );
        } catch (OutOfMemoryError ex) {
            this.status = 100;
            JOptionPane.showMessageDialog(null, "Out of memory");
        }
        this.status = 100;
        image.flush();
        return image;
    }

    private void render(Graphics2D g2, int it, Range re, Range im, Dimension size, double[] data) {

        double RePerTick = re.getDistance()/2.0d;
        double ImPerTick = im.getDistance()/2.0d;
        
        Range re1 = new Range(
                re.START,
                re.START+RePerTick
        );
        
        Range re2 = new Range(
                re.START+RePerTick,
                re.START+RePerTick*2.0d
        );
        
        Range im1 = new Range(
                im.START,
                im.START+ImPerTick
        );
        
        Range im2 = new Range(
                im.START+ImPerTick,
                im.START+ImPerTick*2.0d
        );
        
        Dimension s = new Dimension((int) (size.width/2.0f), (int) (size.height/2.0f));
        
        SubThread t1 = new SubThread(it,re1,im1,s,data);
        t1.thread.start();
        SubThread t2 = new SubThread(it,re1,im2,s,data);
        t2.thread.start();
        SubThread t3 = new SubThread(it,re2,im1,s,data);
        t3.thread.start();
        SubThread t4 = new SubThread(it,re2,im2,s,data);
        t4.thread.start();
        
        while(t1.status!=100||t2.status!=100||t3.status!=100||t4.status!=100){
            this.status = (t1.status+t2.status+t3.status+t4.status)/4f;
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Mandelbroth.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ImageObserver ob = null;
        g2.drawImage(t1.image, 0, 0, ob);
        g2.drawImage(t2.image, 0, s.height, ob);
        g2.drawImage(t3.image, s.width, 0, ob);
        g2.drawImage(t4.image, s.width, s.height, ob);
    }

    private class SubThread implements Runnable {

        public float status;
        private int it;
        private Range re, im;
        private Dimension size;
        private int coloring;
        public BufferedImage image;
        
        public Thread thread;

        public SubThread(int iterations, Range Real, Range Imaginar, Dimension size, double[] data) {
            this.image = new BufferedImage(size.width, size.height, (int)data[0]);
            this.coloring = (int)data[1];
            this.it = iterations;
            this.re = Real;
            this.im = Imaginar;
            this.size = size;
            this.thread = new Thread(this);
        }

        @Override
        public void run() {
            Graphics2D g2 = (Graphics2D)image.getGraphics();
            renderFractal(g2, this.it, this.re, this.im, this.size);
            this.status = 100;
        }

        public void renderFractal(Graphics2D graphics, int iterations, Range Real, Range Imaginar, Dimension size) {
            //create color palete
            int[] colors = new int[iterations];
            for (int i = 0; i < iterations; i++) {
                switch(this.coloring){
                    case 0:
                        colors[i] = Color.HSBtoRGB((float)i / 300, 1, i / (i + 15f));
                    break;
                    case 1:
                        colors[i] = Color.HSBtoRGB((float)i / 256, 1, i / (i + 15f));
                    break;
                    case 2:
                        colors[i] = Color.HSBtoRGB((float)i / 512, 1, i / (i + 15f));
                    break;
                    case 3:
                        colors[i] = Color.HSBtoRGB((float)i / 1024, 1, i / (i + 15f));
                    break;
                    case 4:
                        colors[i] = Color.HSBtoRGB((float)i / iterations, 1, i / (i + 15f));
                    break;
                    case 5:
                        colors[i] = Color.HSBtoRGB(1.0f-(float)i / iterations, 1, i / (i + 15f));
                    break;
                    case 6:
                        colors[i] = Color.HSBtoRGB(1.0f-(float)i / 300, 1, i / (i + 15f));
                    break;
                }
            }

            double RealPerOne = Real.getDistance() / size.width;
            double ImaginarPerOne = Imaginar.getDistance() / size.height;

            for (int x = 0; x < size.width; x++) {
                this.status = (float) x / size.width * 100;
                for (int y = 0; y < size.height; y++) {

                    double CRe = Real.START + x * RealPerOne;
                    double CIm = Imaginar.START + y * ImaginarPerOne;

                    double X = 0, Y = 0;
                    int iteration = 0;

                    while (X * X + Y * Y <= 4 && iteration < iterations) {
                        double x_new = X * X - Y * Y + CRe;
                        Y = 2 * X * Y + CIm;
                        X = x_new;
                        iteration++;
                    }

                    if (iteration < iterations) {
                        try {
                            graphics.setColor(
                                    Color.getColor(
                                            "",
                                            colors[iteration]
                                    )
                            );
                        } catch (Exception ex) {
                        }
                        graphics.fillRect(x, y, 1, 1);
                    } else {
                        graphics.setColor(Color.black);
                        graphics.fillRect(x, y, 1, 1);
                    }

                }
            }
        }

    }
}
