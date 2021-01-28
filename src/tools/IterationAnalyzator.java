// 
// Decompiled by Procyon v0.5.36
// 

package tools;

import java.awt.print.PrinterException;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import javax.swing.LayoutStyle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.GroupLayout;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.awt.Color;
import java.awt.Graphics2D;
import fractal_generator.Data;
import fractal_generator.Fractal_Generator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Canvas;
import javax.swing.JFrame;

public class IterationAnalyzator extends JFrame
{
    public Thread thread;
    public int[] data;
    private boolean run;
    private boolean vertical;
    private Canvas canvas1;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    public JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    
    public IterationAnalyzator() {
        this.run = false;
        this.vertical = true;
        this.initComponents();
        this.canvas1.createBufferStrategy(2);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height / 2 - this.getHeight() / 2);
    }
    
    public void refresh() {
        this.canvas1.createBufferStrategy(2);
    }
    
    public void stop() {
        this.run = false;
        this.thread.stop();
    }
    
    public void run() {
        if (this.run) {
            return;
        }
        this.run = true;
        (this.thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200L);
                    }
                    catch (InterruptedException ex) {
                        Logger.getLogger(IterationAnalyzator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    IterationAnalyzator.this.data = new int[Fractal_Generator.window.ITERATIONS];
                    try {
                        for (final long[][] iter : Data.iterations) {
                            try {
                                for (int x = 0; x < iter.length; ++x) {
                                    try {
                                        for (int y = 0; y < iter.length; ++y) {
                                            final int[] data = IterationAnalyzator.this.data;
                                            final int n = (int)iter[x][y];
                                            ++data[n];
                                        }
                                    }
                                    catch (Exception ex2) {}
                                }
                            }
                            catch (Exception ex3) {}
                        }
                    }
                    catch (Exception ex4) {}
                    int max = Integer.MIN_VALUE;
                    for (int i = 0; i < IterationAnalyzator.this.data.length; ++i) {
                        if (max < IterationAnalyzator.this.data[i]) {
                            max = i;
                        }
                    }
                    double k;
                    if (IterationAnalyzator.this.vertical) {
                        k = IterationAnalyzator.this.canvas1.getWidth() / (float)max;
                    }
                    else {
                        k = IterationAnalyzator.this.canvas1.getHeight() / (float)max;
                    }
                    final BufferStrategy buffer = IterationAnalyzator.this.canvas1.getBufferStrategy();
                    try {
                        final Graphics2D g2 = (Graphics2D)buffer.getDrawGraphics();
                        g2.fillRect(0, 0, IterationAnalyzator.this.canvas1.getWidth(), IterationAnalyzator.this.canvas1.getHeight());
                        for (int j = 0; j < IterationAnalyzator.this.data.length; ++j) {
                            try {
                                g2.setColor(Color.getColor("", FractalColoring.Data.colors[j]));
                                if (IterationAnalyzator.this.vertical) {
                                    int h = (int)(IterationAnalyzator.this.canvas1.getHeight() / (float)IterationAnalyzator.this.data.length);
                                    if (h <= 0) {
                                        h = 1;
                                    }
                                    g2.fillRect(0, (int)(j * (float)IterationAnalyzator.this.canvas1.getHeight() / IterationAnalyzator.this.data.length), (int)(IterationAnalyzator.this.data[j] * (float)k), h);
                                }
                                else {
                                    int w = (int)(IterationAnalyzator.this.canvas1.getWidth() / (float)IterationAnalyzator.this.data.length);
                                    if (w <= 0) {
                                        w = 1;
                                    }
                                    g2.fillRect((int)(j * (float)IterationAnalyzator.this.canvas1.getWidth() / IterationAnalyzator.this.data.length), (int)(IterationAnalyzator.this.canvas1.getHeight() - (float)(int)(IterationAnalyzator.this.data[j] * (float)k)), w, (int)(IterationAnalyzator.this.data[j] * (float)k));
                                }
                            }
                            catch (Exception ex5) {}
                        }
                        g2.setColor(Color.WHITE);
                        if (IterationAnalyzator.this.vertical) {
                            g2.drawString("0", IterationAnalyzator.this.canvas1.getWidth() - 10, 10);
                            final String t = Fractal_Generator.window.ITERATIONS + "";
                            g2.drawString(t, IterationAnalyzator.this.canvas1.getWidth() - 10 * t.length(), IterationAnalyzator.this.canvas1.getHeight() - 10);
                        }
                        else {
                            g2.drawString("0", 1, 10);
                            final String t = Fractal_Generator.window.ITERATIONS + "";
                            g2.drawString(t, IterationAnalyzator.this.canvas1.getWidth() - 10 * t.length(), 10);
                        }
                        buffer.show();
                    }
                    catch (Exception ex6) {}
                }
            }
        }).start();
    }
    
    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jPanel3 = new JPanel();
        this.canvas1 = new Canvas();
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jButton1 = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle("Iteration analyzator");
        this.setAlwaysOnTop(true);
        this.setType(Type.UTILITY);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent evt) {
                IterationAnalyzator.this.formWindowClosing(evt);
            }
        });
        this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.canvas1.setPreferredSize(new Dimension(536, 333));
        this.canvas1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(final MouseEvent evt) {
                IterationAnalyzator.this.canvas1MouseMoved(evt);
            }
        });
        final GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.canvas1, -1, 556, 32767));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.canvas1, GroupLayout.Alignment.TRAILING, -1, -1, 32767));
        this.jPanel2.setBackground(new Color(80, 83, 96));
        this.jPanel2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jLabel1.setForeground(new Color(187, 187, 186));
        this.jLabel1.setText("Iteration:");
        this.jLabel2.setForeground(new Color(187, 187, 186));
        this.jLabel2.setText("0");
        this.jButton1.setBackground(new Color(103, 103, 113));
        this.jButton1.setForeground(new Color(197, 197, 197));
        this.jButton1.setText("Vertical");
        this.jButton1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                IterationAnalyzator.this.jButton1ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButton1, -2, 85, -2)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addGap(1, 1, 1).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButton1, -2, 17, -2).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel2)))));
        final GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jPanel3, -1, -1, 32767).addComponent(this.jPanel2, -1, -1, 32767)).addGap(0, 0, 0)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.jPanel3, -1, -1, 32767).addGap(0, 0, 0).addComponent(this.jPanel2, -2, -1, -2)));
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addGap(0, 0, 32767)));
        this.pack();
    }
    
    private void canvas1MouseMoved(final MouseEvent evt) {
        if (this.vertical) {
            this.jLabel2.setText(evt.getY() * Fractal_Generator.window.ITERATIONS / this.canvas1.getHeight() + "");
        }
        else {
            this.jLabel2.setText(evt.getX() * Fractal_Generator.window.ITERATIONS / this.canvas1.getWidth() + "");
        }
    }
    
    private void formWindowClosing(final WindowEvent evt) {
        this.thread.stop();
    }
    
    private void jButton1ActionPerformed(final ActionEvent evt) {
        if (this.vertical) {
            this.vertical = false;
            this.jButton1.setText("Horizontal");
        }
        else {
            this.vertical = true;
            this.jButton1.setText("Vertical");
        }
    }
    
    public class ImagePrintable implements Printable
    {
        private double x;
        private double y;
        private double width;
        private int orientation;
        private BufferedImage image;
        
        public ImagePrintable(final PrinterJob printJob, final BufferedImage image) {
            final PageFormat pageFormat = printJob.defaultPage();
            this.x = pageFormat.getImageableX();
            this.y = pageFormat.getImageableY();
            this.width = pageFormat.getImageableWidth();
            this.orientation = pageFormat.getOrientation();
            this.image = image;
        }
        
        @Override
        public int print(final Graphics g, final PageFormat pageFormat, final int pageIndex) throws PrinterException {
            if (pageIndex == 0) {
                int pWidth = 0;
                int pHeight = 0;
                if (this.orientation == 1) {
                    pWidth = (int)Math.min(this.width, this.image.getWidth());
                    pHeight = pWidth * this.image.getHeight() / this.image.getWidth();
                }
                else {
                    pHeight = (int)Math.min(this.width, this.image.getHeight());
                    pWidth = pHeight * this.image.getWidth() / this.image.getHeight();
                }
                g.drawImage(this.image, (int)this.x, (int)this.y, pWidth, pHeight, null);
                return 0;
            }
            return 1;
        }
    }
}
