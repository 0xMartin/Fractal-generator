/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal_generator;

import fractal_generator.Generator.Fractal;
import fractal_generator.Generator.Julius;
import fractal_generator.Generator.Mandelbroth;
import fractal_generator.Generator.Range;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author Marti
 */
public class Window extends javax.swing.JFrame {

    //fractal
    public Fractal fractal;
    public BufferedImage FractalImage;
    public int ITERATIONS;
    public Range REAL, IMAGINAR;
    public Dimension IMAGESIZE;

    //render engine
    public Font font = new Font("Tahoma", Font.BOLD + Font.ITALIC, 12);
    public int X = 0, Y = 0;
    public float scale = 1.0f;
    private final int[] rect = new int[3];

    private boolean grids = true;
    private Point cursor;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.000");

    /**
     * Creates new form Window
     */
    public Window() {
        initComponents();
        jPanel9.setVisible(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2-this.getWidth()/2, screenSize.height/2-this.getHeight()/2);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    public void renderingThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Fractal_Generator.window.canvas1.createBufferStrategy(4);
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        render();
                    } catch (Exception ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            private void render() {
                BufferStrategy buffer = canvas1.getBufferStrategy();
                Graphics2D g2 = (Graphics2D) buffer.getDrawGraphics();

                //background
                g2.fillRect(
                        0,
                        0,
                        canvas1.getWidth(),
                        canvas1.getHeight()
                );

                //scale
                g2.scale(scale, scale);

                //fractal
                g2.drawImage(FractalImage,
                        X,
                        Y,
                        canvas1
                );

                //scale back
                g2.scale(1f / scale, 1f / scale);

                //cross
                if (jCheckBoxMenuItem1.isSelected()) {
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(4));
                    g2.drawLine(canvas1.getWidth() / 2 - 5, canvas1.getHeight() / 2, canvas1.getWidth() / 2 + 5, canvas1.getHeight() / 2);
                    g2.drawLine(canvas1.getWidth() / 2, canvas1.getHeight() / 2 - 5, canvas1.getWidth() / 2, canvas1.getHeight() / 2 + 5);
                    g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(canvas1.getWidth() / 2 - 5, canvas1.getHeight() / 2, canvas1.getWidth() / 2 + 5, canvas1.getHeight() / 2);
                    g2.drawLine(canvas1.getWidth() / 2, canvas1.getHeight() / 2 - 5, canvas1.getWidth() / 2, canvas1.getHeight() / 2 + 5);
                    try {
                        if (rect[0] != -1) {
                            g2.drawRect(
                                    (int) ((X + rect[0] * IMAGESIZE.width / (10f * rect[2])) * scale),
                                    (int) ((Y + rect[1] * IMAGESIZE.height / (10f * rect[2])) * scale),
                                    (int) (IMAGESIZE.width / (10f * rect[2]) * scale),
                                    (int) (IMAGESIZE.height / (10f * rect[2]) * scale)
                            );
                        }
                    } catch (Exception ex) {
                    }
                    g2.setStroke(new BasicStroke(1));
                }

                //coordinates
                if (grids) {
                    try {
                        int n = 1;
                        if (scale > 2) {
                            n = 2;
                        }
                        if (scale > 3) {
                            n = 3;
                        }
                        if (scale > 4) {
                            n = 4;
                        }
                        if (scale > 5) {
                            n = 5;
                        }
                        if (scale > 6) {
                            n = 6;
                        }

                        //selected
                        if (rect[0] != -1) {
                            g2.setColor(Color.YELLOW);
                            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                            try {
                                g2.fillRect(
                                        (int) ((X + rect[0] * IMAGESIZE.width / (10f * rect[2])) * scale),
                                        (int) ((Y + rect[1] * IMAGESIZE.height / (10f * rect[2])) * scale),
                                        (int) (IMAGESIZE.width / (10f * rect[2]) * scale),
                                        (int) (IMAGESIZE.height / (10f * rect[2]) * scale)
                                );
                                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                                g2.setStroke(new BasicStroke(2));
                                g2.setColor(Color.ORANGE);
                                g2.drawRect(
                                        (int) ((X + rect[0] * IMAGESIZE.width / (10f * rect[2])) * scale),
                                        (int) ((Y + rect[1] * IMAGESIZE.height / (10f * rect[2])) * scale),
                                        (int) (IMAGESIZE.width / (10f * rect[2]) * scale),
                                        (int) (IMAGESIZE.height / (10f * rect[2]) * scale)
                                );
                                g2.setStroke(new BasicStroke(1));
                            } catch (Exception ex) {
                            }
                            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                        }

                        g2.setFont(font);
                        double RealPerOne = REAL.getDistance() / (10d * n);
                        double ImaginarPerOne = IMAGINAR.getDistance() / (10d * n);
                        g2.setColor(Color.gray);
                        for (int j = 0; j <= 10 * n; j++) {
                            //horizontal
                            g2.setColor(Color.gray);
                            double s = (IMAGINAR.START + ImaginarPerOne * j) * (-1.0d);
                            g2.drawLine(
                                    0,
                                    (int) ((Y + j * IMAGESIZE.height / (10f * n)) * scale),
                                    (int) (canvas1.getWidth()),
                                    (int) ((Y + j * IMAGESIZE.height / (10f * n)) * scale)
                            );
                            g2.setColor(Color.white);
                            if (Math.abs(ImaginarPerOne) < 0.00001d) {
                                g2.drawString(s + "", 1, (Y + j * IMAGESIZE.height / (10f * n)) * scale);
                            } else {
                                g2.drawString(decimalFormat.format(s), 1, (Y + j * IMAGESIZE.height / (10f * n)) * scale);
                            }
                            //vertical
                            g2.setColor(Color.gray);
                            s = REAL.START + RealPerOne * j;
                            g2.drawLine(
                                    (int) ((X + j * IMAGESIZE.width / (10f * n)) * scale),
                                    0,
                                    (int) ((X + j * IMAGESIZE.width / (10f * n)) * scale),
                                    (int) (canvas1.getHeight())
                            );
                            g2.setColor(Color.white);
                            int textLenght = g2.getFontMetrics().stringWidth(s + "");
                            if (textLenght < IMAGESIZE.width / (10f * n)) {
                                g2.drawString(s + "", (X + j * IMAGESIZE.width / (10f * n)) * scale, 15);
                            } else {
                                g2.drawString(decimalFormat.format(s), (X + j * IMAGESIZE.width / (10f * n)) * scale, 15);
                            }
                        }
                    } catch (Exception ex) {
                    }
                }
                //show buffer
                buffer.show();
            }

        });
        t.start();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        image_width = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        image_height = new javax.swing.JTextField();
        image_type = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        Imaginar_start = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Imaginar_end = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        Real_start = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Real_end = new javax.swing.JTextField();
        fractal_type = new javax.swing.JComboBox<>();
        jCheckBox3 = new javax.swing.JCheckBox();
        cursorPositionLabel = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        Data_Re = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        Data_Im = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        coloring_cb = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        iterations_box = new javax.swing.JTextField();
        canvas1 = new java.awt.Canvas();
        jPanel11 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fractal Generator");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(103, 103, 113));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(103, 103, 113));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Image", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(183, 183, 183))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(203, 203, 203));

        image_width.setBackground(new java.awt.Color(83, 83, 93));
        image_width.setForeground(new java.awt.Color(163, 163, 163));
        image_width.setText("800");
        image_width.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        image_width.setCaretColor(new java.awt.Color(255, 255, 255));
        image_width.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        image_width.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                image_widthActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(203, 203, 203));
        jLabel1.setText("Width:");

        jLabel2.setForeground(new java.awt.Color(203, 203, 203));
        jLabel2.setText("Height:");

        image_height.setBackground(new java.awt.Color(83, 83, 93));
        image_height.setForeground(new java.awt.Color(163, 163, 163));
        image_height.setText("800");
        image_height.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        image_height.setCaretColor(new java.awt.Color(255, 255, 255));
        image_height.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        image_height.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                image_heightActionPerformed(evt);
            }
        });

        image_type.setBackground(new java.awt.Color(83, 83, 93));
        image_type.setForeground(new java.awt.Color(183, 183, 183));
        image_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ARGB", "3 BYTE BGR", "4 BYTE ABGR", "4 BYTE ABGR PRE", "BYTE BINARY", "BYTE GRAY", "BYTE INDEXED", "ARGB PRE", "BGR" }));
        image_type.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setForeground(new java.awt.Color(203, 203, 203));
        jLabel10.setText("Type");

        jCheckBox1.setBackground(new java.awt.Color(103, 103, 113));
        jCheckBox1.setForeground(new java.awt.Color(61, 63, 65));
        jCheckBox1.setSelected(true);
        jCheckBox1.setToolTipText("Set this item as static");

        jCheckBox2.setBackground(new java.awt.Color(103, 103, 113));
        jCheckBox2.setForeground(new java.awt.Color(61, 63, 65));
        jCheckBox2.setSelected(true);
        jCheckBox2.setToolTipText("Set this item as static");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(image_height, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(image_width))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(image_type, 0, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel10))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(image_width)
                    .addComponent(image_type, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(image_height))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setForeground(new java.awt.Color(203, 203, 203));
        jLabel3.setText("Iterations");

        jPanel5.setBackground(new java.awt.Color(103, 103, 113));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Imaginar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(183, 183, 183))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(203, 203, 203));

        Imaginar_start.setBackground(new java.awt.Color(83, 83, 93));
        Imaginar_start.setForeground(new java.awt.Color(163, 163, 163));
        Imaginar_start.setText("2.0");
        Imaginar_start.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Imaginar_start.setCaretColor(new java.awt.Color(255, 255, 255));
        Imaginar_start.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        Imaginar_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Imaginar_startActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(203, 203, 203));
        jLabel6.setText("Start:");

        jLabel7.setForeground(new java.awt.Color(203, 203, 203));
        jLabel7.setText("End:");

        Imaginar_end.setBackground(new java.awt.Color(83, 83, 93));
        Imaginar_end.setForeground(new java.awt.Color(163, 163, 163));
        Imaginar_end.setText("-2.0");
        Imaginar_end.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Imaginar_end.setCaretColor(new java.awt.Color(255, 255, 255));
        Imaginar_end.setSelectedTextColor(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Imaginar_end)
                    .addComponent(Imaginar_start)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(84, 84, 84)))
                .addGap(2, 2, 2))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(Imaginar_start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(Imaginar_end, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(103, 103, 113));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Real", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(183, 183, 183))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(203, 203, 203));

        Real_start.setBackground(new java.awt.Color(83, 83, 93));
        Real_start.setForeground(new java.awt.Color(163, 163, 163));
        Real_start.setText("2.0");
        Real_start.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Real_start.setCaretColor(new java.awt.Color(255, 255, 255));
        Real_start.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        Real_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Real_startActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(203, 203, 203));
        jLabel8.setText("Start:");

        jLabel9.setForeground(new java.awt.Color(203, 203, 203));
        jLabel9.setText("End:");

        Real_end.setBackground(new java.awt.Color(83, 83, 93));
        Real_end.setForeground(new java.awt.Color(163, 163, 163));
        Real_end.setText("-2.0");
        Real_end.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Real_end.setCaretColor(new java.awt.Color(255, 255, 255));
        Real_end.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        Real_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Real_endActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Real_start)
                    .addComponent(Real_end)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(81, 81, 81))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(84, 84, 84)))
                .addGap(2, 2, 2))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel8)
                .addGap(1, 1, 1)
                .addComponent(Real_start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel9)
                .addGap(1, 1, 1)
                .addComponent(Real_end, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fractal_type.setBackground(new java.awt.Color(83, 83, 93));
        fractal_type.setForeground(new java.awt.Color(183, 183, 183));
        fractal_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mandelbroth set", "Julius set" }));
        fractal_type.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        fractal_type.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fractal_typeItemStateChanged(evt);
            }
        });

        jCheckBox3.setBackground(new java.awt.Color(103, 103, 113));
        jCheckBox3.setForeground(new java.awt.Color(61, 63, 65));
        jCheckBox3.setToolTipText("Set this item as static");

        cursorPositionLabel.setForeground(new java.awt.Color(177, 177, 187));
        cursorPositionLabel.setText("X: 0.0 ; Y: 0.0");

        jPanel9.setBackground(new java.awt.Color(103, 103, 113));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(183, 183, 183))); // NOI18N
        jPanel9.setForeground(new java.awt.Color(203, 203, 203));

        Data_Re.setBackground(new java.awt.Color(83, 83, 93));
        Data_Re.setForeground(new java.awt.Color(163, 163, 163));
        Data_Re.setText("-0.8");
        Data_Re.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Data_Re.setCaretColor(new java.awt.Color(255, 255, 255));
        Data_Re.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        Data_Re.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Data_ReActionPerformed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(203, 203, 203));
        jLabel11.setText("Re:");

        Data_Im.setBackground(new java.awt.Color(83, 83, 93));
        Data_Im.setForeground(new java.awt.Color(163, 163, 163));
        Data_Im.setText("0.156");
        Data_Im.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Data_Im.setCaretColor(new java.awt.Color(255, 255, 255));
        Data_Im.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        Data_Im.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Data_ImActionPerformed(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(203, 203, 203));
        jLabel12.setText("Im:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(1, 1, 1)
                .addComponent(Data_Re)
                .addGap(1, 1, 1)
                .addComponent(jLabel12)
                .addGap(1, 1, 1)
                .addComponent(Data_Im)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Data_Re, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Data_Im, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2))
        );

        jPanel8.setBackground(new java.awt.Color(103, 103, 113));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Color", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(183, 183, 183))); // NOI18N
        jPanel8.setForeground(new java.awt.Color(203, 203, 203));

        coloring_cb.setBackground(new java.awt.Color(83, 83, 93));
        coloring_cb.setForeground(new java.awt.Color(183, 183, 183));
        coloring_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "Palette 1", "Palette 2", "Palette 3", "Palette 4", "Palette 5", "Palette 6" }));
        coloring_cb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setForeground(new java.awt.Color(203, 203, 203));
        jLabel15.setText("Coloring:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coloring_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel15)
                .addComponent(coloring_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        iterations_box.setEditable(true);
        iterations_box.setBackground(new java.awt.Color(83, 83, 93));
        iterations_box.setForeground(new java.awt.Color(163, 163, 163));
        iterations_box.setText("512");
        iterations_box.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        iterations_box.setCaretColor(new java.awt.Color(255, 255, 255));
        iterations_box.setSelectionColor(new java.awt.Color(93, 93, 113));
        iterations_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iterations_boxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cursorPositionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fractal_type, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(iterations_box)
                        .addGap(1, 1, 1)
                        .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(fractal_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel3)
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iterations_box))
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cursorPositionLabel)
                .addGap(0, 0, 0))
        );

        canvas1.setMinimumSize(new java.awt.Dimension(300, 300));
        canvas1.setPreferredSize(new java.awt.Dimension(600, 600));
        canvas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                canvas1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                canvas1MouseReleased(evt);
            }
        });
        canvas1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                canvas1MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                canvas1MouseMoved(evt);
            }
        });
        canvas1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                canvas1MouseWheelMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(133, 133, 143));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel11.setForeground(new java.awt.Color(137, 137, 147));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fractal_generator/src/render.png"))); // NOI18N
        jButton1.setToolTipText("Render");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setDefaultCapable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fractal_generator/src/coordination.png"))); // NOI18N
        jButton2.setToolTipText("Grids");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fractal_generator/src/print.png"))); // NOI18N
        jButton5.setToolTipText("Render");
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fractal_generator/src/m1.png"))); // NOI18N
        jButton6.setToolTipText("Render");
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fractal_generator/src/m2.png"))); // NOI18N
        jButton9.setToolTipText("Render");
        jButton9.setBorder(null);
        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(93, 93, 103));
        jMenuBar1.setForeground(new java.awt.Color(183, 183, 183));

        jMenu1.setForeground(new java.awt.Color(183, 183, 183));
        jMenu1.setText("File");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setBackground(new java.awt.Color(93, 93, 103));
        jMenuItem3.setForeground(new java.awt.Color(183, 183, 183));
        jMenuItem3.setText("New");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setBackground(new java.awt.Color(93, 93, 103));
        jMenuItem1.setForeground(new java.awt.Color(183, 183, 183));
        jMenuItem1.setText("Save");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setBackground(new java.awt.Color(93, 93, 103));
        jMenuItem2.setForeground(new java.awt.Color(183, 183, 183));
        jMenuItem2.setText("Print");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setForeground(new java.awt.Color(183, 183, 183));
        jMenu3.setText("Tools");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setBackground(new java.awt.Color(93, 93, 103));
        jMenuItem4.setForeground(new java.awt.Color(183, 183, 183));
        jMenuItem4.setText("Render");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setBackground(new java.awt.Color(93, 93, 103));
        jMenuItem5.setForeground(new java.awt.Color(183, 183, 183));
        jMenuItem5.setText("Grids");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jCheckBoxMenuItem1.setBackground(new java.awt.Color(93, 93, 103));
        jCheckBoxMenuItem1.setForeground(new java.awt.Color(183, 183, 183));
        jCheckBoxMenuItem1.setText("Auto render");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu3);

        jMenu4.setBackground(new java.awt.Color(93, 93, 103));
        jMenu4.setForeground(new java.awt.Color(183, 183, 183));
        jMenu4.setText("About");

        jMenuItem6.setBackground(new java.awt.Color(93, 93, 103));
        jMenuItem6.setForeground(new java.awt.Color(183, 183, 183));
        jMenuItem6.setText("Program");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized

    }//GEN-LAST:event_formComponentResized

    private void Imaginar_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Imaginar_startActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Imaginar_startActionPerformed

    private void Real_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Real_startActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Real_startActionPerformed

    private void Real_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Real_endActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Real_endActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.rect[0] = -1;
        this.X = 0;
        this.Y = 0;
        this.scale = 1.0f;

        render();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void render() {
        //render fractal
        Thread render = new Thread() {
            @Override
            public void run() {
                renderFractal();
            }
        };
        render.start();

        //lock form
        Fractal_Generator.window.setEnabled(false);

        //show dialog
        JDialog dlg = new JDialog(this, "Rendering", false);
        dlg.setResizable(false);
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JProgressBar jPB = new JProgressBar(0, 100);
        dlg.add(BorderLayout.CENTER, jPB);
        JLabel jL = new JLabel("0.0%");
        dlg.add(BorderLayout.NORTH, jL);
        dlg.setSize(300, 75);
        dlg.setLocationRelativeTo(this);
        Thread t1 = new Thread() {
            @Override
            public void run() {
                dlg.setVisible(true);
            }
        };
        t1.start();
        Thread t = new Thread() {
            @Override
            public void run() {
                dlg.setVisible(true);
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    DecimalFormat df = new DecimalFormat("0.000");
                    jL.setText(
                            df.format(Fractal_Generator.window.fractal.getStatus()) + " %"
                    );
                    jPB.setValue(
                            (int) Fractal_Generator.window.fractal.getStatus()
                    );
                    if (jPB.getValue() == 100) {
                        Fractal_Generator.window.setEnabled(true);
                        dlg.dispose();
                        rect[0] = -1;
                        return;
                    }
                }
            }
        };
        t.start();
    }

    private void renderFractal() {
        try {
            int iterations = Integer.parseInt(this.iterations_box.getText());
            this.ITERATIONS = iterations;
        } catch (NumberFormatException ex) {
            return;
        }
        try {
            Range real = new Range(
                    Double.parseDouble(this.Real_start.getText()) * (-1.0d),
                    Double.parseDouble(this.Real_end.getText()) * (-1.0d)
            );
            this.REAL = real;
        } catch (NumberFormatException ex) {
            return;
        }
        try {
            Range imaginar = new Range(
                    Double.parseDouble(this.Imaginar_start.getText()) * (-1.0d),
                    Double.parseDouble(this.Imaginar_end.getText()) * (-1.0d)
            );
            this.IMAGINAR = imaginar;
        } catch (NumberFormatException ex) {
            return;
        }
        int imageType = BufferedImage.TYPE_INT_ARGB;
        switch (this.image_type.getSelectedIndex()) {
            case 0:
                imageType = BufferedImage.TYPE_INT_ARGB;
                break;
            case 1:
                imageType = BufferedImage.TYPE_3BYTE_BGR;
                break;
            case 2:
                imageType = BufferedImage.TYPE_4BYTE_ABGR;
                break;
            case 3:
                imageType = BufferedImage.TYPE_4BYTE_ABGR_PRE;
                break;
            case 4:
                imageType = BufferedImage.TYPE_BYTE_BINARY;
                break;
            case 5:
                imageType = BufferedImage.TYPE_BYTE_GRAY;
                break;
            case 6:
                imageType = BufferedImage.TYPE_BYTE_INDEXED;
                break;
            case 7:
                imageType = BufferedImage.TYPE_INT_ARGB_PRE;
                break;
            case 8:
                imageType = BufferedImage.TYPE_INT_BGR;
                break;
        }

        if (jCheckBox1.isSelected() && !jCheckBox2.isSelected()) {
            this.image_height.setText(
                    (int) (Float.parseFloat(this.image_width.getText()) / REAL.getDistance() * IMAGINAR.getDistance()) + ""
            );
        }
        if (jCheckBox2.isSelected() && !jCheckBox1.isSelected()) {
            this.image_width.setText(
                    (int) (Float.parseFloat(this.image_height.getText()) / IMAGINAR.getDistance() * REAL.getDistance()) + ""
            );
        }

        try {
            Dimension size = new Dimension(
                    (int) Float.parseFloat(this.image_width.getText()),
                    (int) Float.parseFloat(this.image_height.getText())
            );
            this.IMAGESIZE = size;
        } catch (NumberFormatException ex) {
            return;
        }
        switch (this.fractal_type.getSelectedIndex()) {
            case 0:
                //Mandelbroth 
                this.fractal = new Mandelbroth(
                        this.ITERATIONS,
                        this.REAL,
                        this.IMAGINAR,
                        this.IMAGESIZE
                );
                try {
                    this.FractalImage = this.fractal.renderImage(
                            new double[]{
                                imageType,
                                this.coloring_cb.getSelectedIndex()
                            }
                    );
                } catch (Exception ex) {
                }
                break;
            case 1:
                //Julius set
                this.fractal = new Julius(
                        this.ITERATIONS,
                        this.REAL,
                        this.IMAGINAR,
                        this.IMAGESIZE
                );
                try {
                    this.FractalImage = this.fractal.renderImage(
                            new double[]{
                                imageType,
                                Double.parseDouble(this.Data_Re.getText()),
                                Double.parseDouble(this.Data_Im.getText()),
                                this.coloring_cb.getSelectedIndex()
                            }
                    );
                } catch (Exception ex) {
                }
                break;

        }
    }

    private void canvas1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_canvas1MouseWheelMoved
        if (jCheckBoxMenuItem1.isSelected()) {
            if (evt.getWheelRotation() < 0) {
                autoRender();
            }
            return;
        }
        this.scale -= (float) evt.getWheelRotation() / (10f * 1f / this.scale);
        if (this.scale < 0.02f) {
            this.scale = 0.02f;
        }
        autoRender();
    }//GEN-LAST:event_canvas1MouseWheelMoved

    int canvasMouseDraggedTimeOut = 0;
    private void canvas1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas1MouseDragged
        if (evt.getButton() == 0) {
            if (canvasMouseDraggedTimeOut == 0) {
                cursor = evt.getPoint();
            }
            if (canvasMouseDraggedTimeOut > 1) {
                canvasMouseDraggedTimeOut = 0;
                Point f = new Point(cursor.x - evt.getPoint().x, cursor.y - evt.getPoint().y);
                if (Math.abs(f.x) > 50 || Math.abs(f.y) > 50) {
                    return;
                }
                int m = (int) (-f.x * (1f / (this.scale)));
                if (Math.abs(m) < 1) {
                    m = 1 * -f.x;
                }
                this.X += m;
                m = (int) (-f.y * (1f / (this.scale)));
                if (Math.abs(m) < 1) {
                    m = 1 * -f.y;
                }
                this.Y += m;
            } else {
                canvasMouseDraggedTimeOut++;
            }
        }
    }//GEN-LAST:event_canvas1MouseDragged

    private void canvas1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas1MousePressed
        if (evt.getButton() == 1) {
            this.canvas1.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            canvasMouseDraggedTimeOut = 0;
        }
        //segment select
        if (evt.getButton() == 3&&!this.jCheckBoxMenuItem1.isSelected()) {
            if (this.grids) {
                try {
                    int n = 1;
                    if (this.scale > 2) {
                        n = 2;
                    }
                    if (this.scale > 3) {
                        n = 3;
                    }
                    if (this.scale > 4) {
                        n = 4;
                    }
                    if (this.scale > 5) {
                        n = 5;
                    }
                    if (this.scale > 6) {
                        n = 6;
                    }
                    double RealPerOne = REAL.getDistance() / (10d * n);
                    double ImaginarPerOne = IMAGINAR.getDistance() / (10d * n);

                    for (int k = 0; k <= 10 * n; k++) {
                        for (int j = 0; j <= 10 * n; j++) {
                            double x = (X + k * IMAGESIZE.width / (10f * n)) * this.scale;
                            double y = (Y + j * IMAGESIZE.height / (10f * n)) * this.scale;
                            if (evt.getX() > x && x < evt.getX() + RealPerOne && evt.getY() > y && y < evt.getY() + ImaginarPerOne) {
                                this.Imaginar_start.setText((IMAGINAR.START + ImaginarPerOne * j) * (-1.0d) + "");
                                this.Imaginar_end.setText((IMAGINAR.START + ImaginarPerOne * (j + 1)) * (-1.0d) + "");
                                this.Real_start.setText((REAL.START + RealPerOne * k) * (-1.0d) + "");
                                this.Real_end.setText((REAL.START + RealPerOne * (k + 1)) * (-1.0d) + "");
                                this.rect[0] = k;
                                this.rect[1] = j;
                                this.rect[2] = n;
                            }
                        }
                    }
                } catch (Exception ex) {
                }
            }
        }
    }//GEN-LAST:event_canvas1MousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.grids) {
            jButton2.setBorder(null);
            this.grids = false;
        } else {
            jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            this.grids = true;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                ImageIO.write(this.FractalImage, "png", file);
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void canvas1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas1MouseReleased
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        this.canvas1.setCursor(cursor);
    }//GEN-LAST:event_canvas1MouseReleased

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new ImagePrintable(printJob, this.FractalImage));

        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException prt) {
                prt.printStackTrace();
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void image_heightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_image_heightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_image_heightActionPerformed

    private void image_widthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_image_widthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_image_widthActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new ImagePrintable(printJob, this.FractalImage));
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException prt) {
                prt.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.scale -= (float) 1.0f / (10f * 1f / this.scale);
        if (this.scale < 0.02f) {
            this.scale = 0.02f;
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        this.scale -= (float) -1.0f / (10f * 1f / this.scale);
        if (this.scale < 0.02f) {
            this.scale = 0.02f;
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to create a new fractal?", "New", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.X = 0;
            this.Y = 0;
            this.scale = 1.0f;
            this.FractalImage = null;
            this.REAL = null;
            this.IMAGINAR = null;
            this.Imaginar_start.setText("2.0");
            this.Imaginar_end.setText("-2.0");
            this.Real_start.setText("2.0");
            this.Real_end.setText("-2.0");
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        this.rect[0] = -1;
        this.X = 0;
        this.Y = 0;
        this.scale = 1.0f;
        render();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if (this.grids) {
            jButton2.setBorder(null);
            this.grids = false;
        } else {
            jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            this.grids = true;
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void canvas1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas1MouseMoved
        this.cursorPositionLabel.setText("X: " + evt.getX() + " ; Y: " + evt.getY());
    }//GEN-LAST:event_canvas1MouseMoved

    private void Data_ReActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Data_ReActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Data_ReActionPerformed

    private void Data_ImActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Data_ImActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Data_ImActionPerformed

    private void fractal_typeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fractal_typeItemStateChanged
         switch (this.fractal_type.getSelectedIndex()) {
            case 0: jPanel9.setVisible(false); break;
            case 1: jPanel9.setVisible(true); break;
        }
    }//GEN-LAST:event_fractal_typeItemStateChanged

    private About about = new About();
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
       about.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void iterations_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iterations_boxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_iterations_boxActionPerformed

    public class ImagePrintable implements Printable {

        private double x, y, width;

        private int orientation;

        private BufferedImage image;

        public ImagePrintable(PrinterJob printJob, BufferedImage image) {
            PageFormat pageFormat = printJob.defaultPage();
            this.x = pageFormat.getImageableX();
            this.y = pageFormat.getImageableY();
            this.width = pageFormat.getImageableWidth();
            this.orientation = pageFormat.getOrientation();
            this.image = image;
        }

        @Override
        public int print(Graphics g, PageFormat pageFormat, int pageIndex)
                throws PrinterException {
            if (pageIndex == 0) {
                int pWidth = 0;
                int pHeight = 0;
                if (orientation == PageFormat.PORTRAIT) {
                    pWidth = (int) Math.min(width, (double) image.getWidth());
                    pHeight = pWidth * image.getHeight() / image.getWidth();
                } else {
                    pHeight = (int) Math.min(width, (double) image.getHeight());
                    pWidth = pHeight * image.getWidth() / image.getHeight();
                }
                g.drawImage(image, (int) x, (int) y, pWidth, pHeight, null);
                return PAGE_EXISTS;
            } else {
                return NO_SUCH_PAGE;
            }
        }

    }

    int lastIter;

    private void autoRender() {
        if (!jCheckBoxMenuItem1.isSelected()) {
            return;
        }

        int mx = this.canvas1.getWidth() / 2;
        int my = this.canvas1.getHeight() / 2;

        //segment select
        try {
            int n = 1;
            if (this.scale > 2) {
                n = 2;
            }
            if (this.scale > 3) {
                n = 3;
            }
            if (this.scale > 4) {
                n = 4;
            }
            if (this.scale > 5) {
                n = 5;
            }
            if (this.scale > 6) {
                n = 6;
            }
            double RealPerOne = REAL.getDistance() / (10d * n);
            double ImaginarPerOne = IMAGINAR.getDistance() / (10d * n);

            for (int k = 0; k <= 10 * n; k++) {
                for (int j = 0; j <= 10 * n; j++) {
                    double x = (X + k * IMAGESIZE.width / (10f * n)) * this.scale;
                    double y = (Y + j * IMAGESIZE.height / (10f * n)) * this.scale;
                    if (mx > x && x < mx + RealPerOne && my > y && y < my + ImaginarPerOne) {
                        this.Imaginar_start.setText((IMAGINAR.START + ImaginarPerOne * j) * (-1.0d) + "");
                        this.Imaginar_end.setText((IMAGINAR.START + ImaginarPerOne * (j + 1)) * (-1.0d) + "");
                        this.Real_start.setText((REAL.START + RealPerOne * k) * (-1.0d) + "");
                        this.Real_end.setText((REAL.START + RealPerOne * (k + 1)) * (-1.0d) + "");
                        this.rect[0] = k;
                        this.rect[1] = j;
                        this.rect[2] = n;
                    }
                }
            }
        } catch (Exception ex) {
        }
        lastIter = (int) Double.parseDouble(this.iterations_box.getText());
        int i = (int) (lastIter * Math.sqrt(2));
        if (i > Double.parseDouble(this.iterations_box.getText())) {
            if (!jCheckBox3.isSelected()) {
                this.iterations_box.setText(i + "");
            }
        }

        render();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Data_Im;
    private javax.swing.JTextField Data_Re;
    private javax.swing.JTextField Imaginar_end;
    private javax.swing.JTextField Imaginar_end1;
    private javax.swing.JTextField Imaginar_start;
    private javax.swing.JTextField Imaginar_start1;
    private javax.swing.JTextField Real_end;
    private javax.swing.JTextField Real_start;
    private java.awt.Canvas canvas1;
    private javax.swing.JComboBox<String> coloring_cb;
    private javax.swing.JLabel cursorPositionLabel;
    private javax.swing.JComboBox<String> fractal_type;
    private javax.swing.JTextField image_height;
    private javax.swing.JComboBox<String> image_type;
    private javax.swing.JTextField image_width;
    private javax.swing.JTextField iterations_box;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
