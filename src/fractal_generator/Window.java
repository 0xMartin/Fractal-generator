// 
// Decompiled by Procyon v0.5.36
// 

package fractal_generator;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import javax.swing.JColorChooser;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import java.awt.Cursor;
import fractal_generator.Generator.Julius;
import fractal_generator.Generator.Mandelbroth;
import fractal_generator.Generator.FractalManager;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JProgressBar;
import javax.swing.BoxLayout;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.table.TableModel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.LayoutStyle;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.GroupLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.image.ImageObserver;
import java.awt.Image;
import FractalColoring.ColorPoint;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Graphics2D;
import FractalColoring.Data;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JInternalFrame;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Canvas;
import javax.swing.JTextField;
import tools.IterationAnalyzator;
import FractalColoring.ColoringManager;
import java.text.DecimalFormat;
import java.awt.Point;
import java.awt.Font;
import java.awt.Dimension;
import fractal_generator.Generator.Range;
import java.awt.image.BufferedImage;
import fractal_generator.Generator.Fractal;
import javax.swing.JFrame;

public class Window extends JFrame
{
    public Fractal fractal;
    public BufferedImage FractalImage;
    public int ITERATIONS;
    public Range REAL;
    public Range IMAGINAR;
    public Dimension IMAGESIZE;
    public Font font;
    public int X;
    public int Y;
    public float scale;
    private final int[] rect;
    private boolean grids;
    private Point cursor;
    private final DecimalFormat decimalFormat;
    public static ColoringManager coloringManager;
    private IterationAnalyzator IterationAnalyzator;
    int canvasMouseDraggedTimeOut;
    private About about;
    private boolean newColor;
    int lastIter;
    private JTextField Data_Im;
    private JTextField Data_Re;
    private JTextField Imaginar_end;
    private JTextField Imaginar_start;
    private JTextField Real_end;
    private JTextField Real_start;
    private Canvas canvas1;
    private Canvas canvas_coloring;
    private JComboBox<String> coloring_cb;
    private JLabel cursorPositionLabel;
    private JComboBox<String> fractal_type;
    private JTextField image_height;
    private JComboBox<String> image_type;
    private JTextField image_width;
    private JTextField iterations_box;
    private JButton jButton1;
    private JButton jButton10;
    private JButton jButton11;
    private JButton jButton12;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton9;
    private JCheckBox jCheckBox1;
    private JCheckBox jCheckBox2;
    private JCheckBox jCheckBox3;
    private JCheckBoxMenuItem jCheckBoxMenuItem1;
    private JInternalFrame jInternalFrame1;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel15;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JMenu jMenu1;
    private JMenu jMenu3;
    private JMenu jMenu4;
    private JMenu jMenu5;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem3;
    private JMenuItem jMenuItem4;
    private JMenuItem jMenuItem5;
    private JMenuItem jMenuItem6;
    private JMenuItem jMenuItem7;
    private JMenuItem jMenuItem8;
    private JMenuItem jMenuItem9;
    private JPanel jPanel1;
    private JPanel jPanel11;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JScrollPane jScroll_table;
    private JTable jTable1;
    
    public Window() {
        this.ITERATIONS = 512;
        this.font = new Font("Tahoma", 3, 9);
        this.X = 0;
        this.Y = 0;
        this.scale = 1.0f;
        this.rect = new int[3];
        this.grids = true;
        this.decimalFormat = new DecimalFormat("0.000");
        this.IterationAnalyzator = new IterationAnalyzator();
        this.canvasMouseDraggedTimeOut = 0;
        this.about = new About();
        this.newColor = false;
        this.initComponents();
        this.jPanel9.setVisible(false);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height / 2 - this.getHeight() / 2);
        this.setExtendedState(this.getExtendedState() | 0x6);
    }
    
    public void coloringPanel() {
        final Thread t = new Thread() {
            @Override
            public void run() {
                Fractal_Generator.window.canvas_coloring.createBufferStrategy(4);
                while (true) {
                    try {
                        Thread.sleep(30L);
                    }
                    catch (InterruptedException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.refreshTable();
                    final int[] colors = Data.colors;
                    final float perOne = Window.this.canvas_coloring.getWidth() / (float)colors.length;
                    final BufferStrategy buffer = Window.this.canvas_coloring.getBufferStrategy();
                    final Graphics2D g2 = (Graphics2D)buffer.getDrawGraphics();
                    g2.setColor(Color.black);
                    g2.fillRect(0, 0, Window.this.canvas_coloring.getWidth(), Window.this.canvas_coloring.getHeight());
                    for (int i = 0; i < colors.length; ++i) {
                        g2.setColor(Color.getColor("", colors[i]));
                        g2.fillRect((int)(i * perOne), 0, (int)(i * perOne + perOne), Window.this.canvas_coloring.getHeight());
                    }
                    g2.setColor(Color.black);
                    g2.drawRect(0, 0, Window.this.canvas_coloring.getWidth(), Window.this.canvas_coloring.getHeight());
                    buffer.show();
                }
            }
            
            private void refreshTable() {
                final DefaultTableModel model = (DefaultTableModel)Window.this.jTable1.getModel();
                while (Window.coloringManager.colorPoints.size() > model.getRowCount()) {
                    model.addRow(new Object[0]);
                }
                while (Window.coloringManager.colorPoints.size() < model.getRowCount()) {
                    model.removeRow(model.getRowCount() - 1);
                }
                for (int i = 0; i < Window.coloringManager.colorPoints.size(); ++i) {
                    final ColorPoint cp = Window.coloringManager.colorPoints.get(i);
                    try {
                        if (Integer.parseInt(Window.this.jTable1.getValueAt(i, 0).toString()) != cp.X && Window.this.jTable1.getValueAt(i, 0).toString().length() != 0) {
                            cp.X = Integer.parseInt(Window.this.jTable1.getValueAt(i, 0).toString());
                        }
                        if (Integer.parseInt(Window.this.jTable1.getValueAt(i, 1).toString()) != cp.range && Window.this.jTable1.getValueAt(i, 1).toString().length() != 0) {
                            cp.range = Integer.parseInt(Window.this.jTable1.getValueAt(i, 1).toString());
                        }
                    }
                    catch (Exception ex) {}
                    Window.this.jTable1.setValueAt(cp.X, i, 0);
                    Window.this.jTable1.setValueAt(cp.range, i, 1);
                    Window.this.jTable1.setValueAt(cp.color, i, 2);
                }
            }
        };
        t.start();
    }
    
    public void renderingThread() {
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Fractal_Generator.window.canvas1.createBufferStrategy(4);
                while (true) {
                    try {
                        Thread.sleep(10L);
                    }
                    catch (InterruptedException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Window.this.jLabel4.setText("CPU: ");
                    try {
                        this.render();
                    }
                    catch (Exception ex2) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                }
            }
            
            private void render() {
                final BufferStrategy buffer = Window.this.canvas1.getBufferStrategy();
                final Graphics2D g2 = (Graphics2D)buffer.getDrawGraphics();
                g2.fillRect(0, 0, Window.this.canvas1.getWidth(), Window.this.canvas1.getHeight());
                g2.scale(Window.this.scale, Window.this.scale);
                g2.drawImage(Window.this.FractalImage, Window.this.X, Window.this.Y, Window.this.canvas1);
                g2.scale(1.0f / Window.this.scale, 1.0f / Window.this.scale);
                if (Window.this.jCheckBoxMenuItem1.isSelected()) {
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(4.0f));
                    g2.drawLine(Window.this.canvas1.getWidth() / 2 - 5, Window.this.canvas1.getHeight() / 2, Window.this.canvas1.getWidth() / 2 + 5, Window.this.canvas1.getHeight() / 2);
                    g2.drawLine(Window.this.canvas1.getWidth() / 2, Window.this.canvas1.getHeight() / 2 - 5, Window.this.canvas1.getWidth() / 2, Window.this.canvas1.getHeight() / 2 + 5);
                    g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(2.0f));
                    g2.drawLine(Window.this.canvas1.getWidth() / 2 - 5, Window.this.canvas1.getHeight() / 2, Window.this.canvas1.getWidth() / 2 + 5, Window.this.canvas1.getHeight() / 2);
                    g2.drawLine(Window.this.canvas1.getWidth() / 2, Window.this.canvas1.getHeight() / 2 - 5, Window.this.canvas1.getWidth() / 2, Window.this.canvas1.getHeight() / 2 + 5);
                    try {
                        if (Window.this.rect[0] != -1) {
                            g2.drawRect((int)((Window.this.X + Window.this.rect[0] * Window.this.IMAGESIZE.width / (10.0f * Window.this.rect[2])) * Window.this.scale), (int)((Window.this.Y + Window.this.rect[1] * Window.this.IMAGESIZE.height / (10.0f * Window.this.rect[2])) * Window.this.scale), (int)(Window.this.IMAGESIZE.width / (10.0f * Window.this.rect[2]) * Window.this.scale), (int)(Window.this.IMAGESIZE.height / (10.0f * Window.this.rect[2]) * Window.this.scale));
                        }
                    }
                    catch (Exception ex) {}
                    g2.setStroke(new BasicStroke(1.0f));
                }
                if (Window.this.grids) {
                    try {
                        int n = 1;
                        if (Window.this.scale > 2.0f) {
                            n = 2;
                        }
                        if (Window.this.scale > 3.0f) {
                            n = 3;
                        }
                        if (Window.this.scale > 4.0f) {
                            n = 4;
                        }
                        if (Window.this.scale > 5.0f) {
                            n = 5;
                        }
                        if (Window.this.scale > 6.0f) {
                            n = 6;
                        }
                        if (Window.this.rect[0] != -1) {
                            g2.setColor(Color.YELLOW);
                            g2.setComposite(AlphaComposite.getInstance(3, 0.3f));
                            try {
                                g2.fillRect((int)((Window.this.X + Window.this.rect[0] * Window.this.IMAGESIZE.width / (10.0f * Window.this.rect[2])) * Window.this.scale), (int)((Window.this.Y + Window.this.rect[1] * Window.this.IMAGESIZE.height / (10.0f * Window.this.rect[2])) * Window.this.scale), (int)(Window.this.IMAGESIZE.width / (10.0f * Window.this.rect[2]) * Window.this.scale), (int)(Window.this.IMAGESIZE.height / (10.0f * Window.this.rect[2]) * Window.this.scale));
                                g2.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                g2.setStroke(new BasicStroke(2.0f));
                                g2.setColor(Color.ORANGE);
                                g2.drawRect((int)((Window.this.X + Window.this.rect[0] * Window.this.IMAGESIZE.width / (10.0f * Window.this.rect[2])) * Window.this.scale), (int)((Window.this.Y + Window.this.rect[1] * Window.this.IMAGESIZE.height / (10.0f * Window.this.rect[2])) * Window.this.scale), (int)(Window.this.IMAGESIZE.width / (10.0f * Window.this.rect[2]) * Window.this.scale), (int)(Window.this.IMAGESIZE.height / (10.0f * Window.this.rect[2]) * Window.this.scale));
                                g2.setStroke(new BasicStroke(1.0f));
                            }
                            catch (Exception ex2) {}
                            g2.setComposite(AlphaComposite.getInstance(3, 1.0f));
                        }
                        g2.setFont(Window.this.font);
                        final double RealPerOne = Window.this.REAL.getDistance() / (10.0 * n);
                        final double ImaginarPerOne = Window.this.IMAGINAR.getDistance() / (10.0 * n);
                        g2.setColor(Color.gray);
                        for (int j = 0; j <= 10 * n; ++j) {
                            g2.setColor(Color.gray);
                            double s = (Window.this.IMAGINAR.START + ImaginarPerOne * j) * -1.0;
                            g2.drawLine(0, (int)((Window.this.Y + j * Window.this.IMAGESIZE.height / (10.0f * n)) * Window.this.scale), Window.this.canvas1.getWidth(), (int)((Window.this.Y + j * Window.this.IMAGESIZE.height / (10.0f * n)) * Window.this.scale));
                            g2.setColor(Color.white);
                            int textLenght = g2.getFontMetrics().stringWidth(s + "");
                            if (textLenght < Window.this.IMAGESIZE.width / (10.0f * n)) {
                                g2.drawString(s + "", 5.0f, (Window.this.Y + j * Window.this.IMAGESIZE.height / (10.0f * n)) * Window.this.scale);
                            }
                            else {
                                g2.drawString(Window.this.decimalFormat.format(s), 5.0f, (Window.this.Y + j * Window.this.IMAGESIZE.height / (10.0f * n)) * Window.this.scale);
                            }
                            g2.setColor(Color.gray);
                            s = Window.this.REAL.START + RealPerOne * j;
                            g2.drawLine((int)((Window.this.X + j * Window.this.IMAGESIZE.width / (10.0f * n)) * Window.this.scale), 0, (int)((Window.this.X + j * Window.this.IMAGESIZE.width / (10.0f * n)) * Window.this.scale), Window.this.canvas1.getHeight());
                            g2.setColor(Color.white);
                            textLenght = g2.getFontMetrics().stringWidth(s + "");
                            if (textLenght < Window.this.IMAGESIZE.width / (10.0f * n)) {
                                g2.drawString(s + "", (Window.this.X + j * Window.this.IMAGESIZE.width / (10.0f * n)) * Window.this.scale, 15.0f);
                            }
                            else {
                                g2.drawString(Window.this.decimalFormat.format(s), (Window.this.X + j * Window.this.IMAGESIZE.width / (10.0f * n)) * Window.this.scale, 15.0f);
                            }
                        }
                    }
                    catch (Exception ex3) {}
                }
                buffer.show();
            }
        });
        t.start();
    }
    
    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jPanel2 = new JPanel();
        this.image_width = new JTextField();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.image_height = new JTextField();
        this.image_type = new JComboBox<String>();
        this.jLabel10 = new JLabel();
        this.jCheckBox1 = new JCheckBox();
        this.jCheckBox2 = new JCheckBox();
        this.jLabel3 = new JLabel();
        this.jPanel5 = new JPanel();
        this.Imaginar_start = new JTextField();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.Imaginar_end = new JTextField();
        this.jPanel6 = new JPanel();
        this.Real_start = new JTextField();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.Real_end = new JTextField();
        this.fractal_type = new JComboBox<String>();
        this.jCheckBox3 = new JCheckBox();
        this.cursorPositionLabel = new JLabel();
        this.jPanel9 = new JPanel();
        this.Data_Re = new JTextField();
        this.jLabel11 = new JLabel();
        this.Data_Im = new JTextField();
        this.jLabel12 = new JLabel();
        this.jPanel8 = new JPanel();
        this.coloring_cb = new JComboBox<String>();
        this.jLabel15 = new JLabel();
        this.canvas_coloring = new Canvas();
        this.jButton3 = new JButton();
        this.jButton4 = new JButton();
        this.jButton7 = new JButton();
        this.jScroll_table = new JScrollPane();
        this.jTable1 = new JTable();
        this.iterations_box = new JTextField();
        this.canvas1 = new Canvas();
        this.jInternalFrame1 = new JInternalFrame();
        this.jPanel11 = new JPanel();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.jButton5 = new JButton();
        this.jButton6 = new JButton();
        this.jButton9 = new JButton();
        this.jButton10 = new JButton();
        this.jButton11 = new JButton();
        this.jButton12 = new JButton();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jMenuBar1 = new JMenuBar();
        this.jMenu1 = new JMenu();
        this.jMenuItem3 = new JMenuItem();
        this.jMenuItem1 = new JMenuItem();
        this.jMenuItem2 = new JMenuItem();
        this.jMenu3 = new JMenu();
        this.jMenuItem4 = new JMenuItem();
        this.jMenuItem5 = new JMenuItem();
        this.jCheckBoxMenuItem1 = new JCheckBoxMenuItem();
        this.jMenuItem9 = new JMenuItem();
        this.jMenu5 = new JMenu();
        this.jMenuItem7 = new JMenuItem();
        this.jMenuItem8 = new JMenuItem();
        this.jMenu4 = new JMenu();
        this.jMenuItem6 = new JMenuItem();
        this.setDefaultCloseOperation(3);
        this.setTitle("Fractal Generator");
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent evt) {
                Window.this.formComponentResized(evt);
            }
        });
        this.jPanel3.setBackground(new Color(103, 103, 113));
        this.jPanel3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jPanel2.setBackground(new Color(103, 103, 113));
        this.jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), "Image", 0, 0, new Font("Dialog", 1, 12), new Color(183, 183, 183)));
        this.jPanel2.setForeground(new Color(203, 203, 203));
        this.image_width.setBackground(new Color(83, 83, 93));
        this.image_width.setForeground(new Color(163, 163, 163));
        this.image_width.setText("800");
        this.image_width.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.image_width.setCaretColor(new Color(255, 255, 255));
        this.image_width.setSelectedTextColor(new Color(255, 255, 255));
        this.jLabel1.setForeground(new Color(203, 203, 203));
        this.jLabel1.setText("Width:");
        this.jLabel2.setForeground(new Color(203, 203, 203));
        this.jLabel2.setText("Height:");
        this.image_height.setBackground(new Color(83, 83, 93));
        this.image_height.setForeground(new Color(163, 163, 163));
        this.image_height.setText("800");
        this.image_height.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.image_height.setCaretColor(new Color(255, 255, 255));
        this.image_height.setSelectedTextColor(new Color(255, 255, 255));
        this.image_type.setBackground(new Color(83, 83, 93));
        this.image_type.setForeground(new Color(183, 183, 183));
        this.image_type.setModel(new DefaultComboBoxModel<String>(new String[] { "ARGB", "3 BYTE BGR", "4 BYTE ABGR", "4 BYTE ABGR PRE", "BYTE BINARY", "BYTE GRAY", "BYTE INDEXED", "ARGB PRE", "BGR" }));
        this.image_type.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jLabel10.setForeground(new Color(203, 203, 203));
        this.jLabel10.setText("Type");
        this.jCheckBox1.setBackground(new Color(103, 103, 113));
        this.jCheckBox1.setForeground(new Color(61, 63, 65));
        this.jCheckBox1.setSelected(true);
        this.jCheckBox1.setToolTipText("Set this item as static");
        this.jCheckBox2.setBackground(new Color(103, 103, 113));
        this.jCheckBox2.setForeground(new Color(61, 63, 65));
        this.jCheckBox2.setSelected(true);
        this.jCheckBox2.setToolTipText("Set this item as static");
        final GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(1, 1, 1).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.image_height, -1, 97, 32767).addComponent(this.jLabel1, GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.LEADING).addComponent(this.image_width)).addGap(1, 1, 1).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jCheckBox1, -2, 24, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel10).addGap(0, 0, 32767)).addComponent(this.image_type, 0, 0, 32767))).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jCheckBox2, -2, 24, -2).addGap(0, 0, 32767))).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel10)).addGap(1, 1, 1).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jCheckBox1, -2, 0, 32767).addComponent(this.image_width).addComponent(this.image_type, -2, 18, -2)).addGap(1, 1, 1).addComponent(this.jLabel2).addGap(1, 1, 1).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jCheckBox2, -2, 0, 32767).addComponent(this.image_height)).addContainerGap(-1, 32767)));
        this.jLabel3.setForeground(new Color(203, 203, 203));
        this.jLabel3.setText("Iterations");
        this.jPanel5.setBackground(new Color(103, 103, 113));
        this.jPanel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), "Imaginar", 0, 0, new Font("Dialog", 1, 12), new Color(183, 183, 183)));
        this.jPanel5.setForeground(new Color(203, 203, 203));
        this.Imaginar_start.setBackground(new Color(83, 83, 93));
        this.Imaginar_start.setForeground(new Color(163, 163, 163));
        this.Imaginar_start.setText("2.0");
        this.Imaginar_start.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.Imaginar_start.setCaretColor(new Color(255, 255, 255));
        this.Imaginar_start.setSelectedTextColor(new Color(255, 255, 255));
        this.Imaginar_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.Imaginar_startActionPerformed(evt);
            }
        });
        this.jLabel6.setForeground(new Color(203, 203, 203));
        this.jLabel6.setText("Start:");
        this.jLabel7.setForeground(new Color(203, 203, 203));
        this.jLabel7.setText("End:");
        this.Imaginar_end.setBackground(new Color(83, 83, 93));
        this.Imaginar_end.setForeground(new Color(163, 163, 163));
        this.Imaginar_end.setText("-2.0");
        this.Imaginar_end.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.Imaginar_end.setCaretColor(new Color(255, 255, 255));
        this.Imaginar_end.setSelectedTextColor(new Color(255, 255, 255));
        final GroupLayout jPanel5Layout = new GroupLayout(this.jPanel5);
        this.jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addGap(1, 1, 1).addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel6, -1, -1, 32767).addComponent(this.Imaginar_end, GroupLayout.Alignment.LEADING).addComponent(this.Imaginar_start, GroupLayout.Alignment.LEADING).addComponent(this.jLabel7, GroupLayout.Alignment.LEADING, -1, -1, 32767)).addGap(2, 2, 2)));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addComponent(this.jLabel6, -1, -1, 32767).addGap(2, 2, 2).addComponent(this.Imaginar_start, -2, -1, -2).addGap(1, 1, 1).addComponent(this.jLabel7, -1, -1, 32767).addGap(1, 1, 1).addComponent(this.Imaginar_end, -2, -1, -2).addContainerGap()));
        this.jPanel6.setBackground(new Color(103, 103, 113));
        this.jPanel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), "Real", 0, 0, new Font("Dialog", 1, 12), new Color(183, 183, 183)));
        this.jPanel6.setForeground(new Color(203, 203, 203));
        this.Real_start.setBackground(new Color(83, 83, 93));
        this.Real_start.setForeground(new Color(163, 163, 163));
        this.Real_start.setText("2.0");
        this.Real_start.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.Real_start.setCaretColor(new Color(255, 255, 255));
        this.Real_start.setSelectedTextColor(new Color(255, 255, 255));
        this.Real_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.Real_startActionPerformed(evt);
            }
        });
        this.jLabel8.setForeground(new Color(203, 203, 203));
        this.jLabel8.setText("Start:");
        this.jLabel9.setForeground(new Color(203, 203, 203));
        this.jLabel9.setText("End:");
        this.Real_end.setBackground(new Color(83, 83, 93));
        this.Real_end.setForeground(new Color(163, 163, 163));
        this.Real_end.setText("-2.0");
        this.Real_end.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.Real_end.setCaretColor(new Color(255, 255, 255));
        this.Real_end.setSelectedTextColor(new Color(255, 255, 255));
        this.Real_end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.Real_endActionPerformed(evt);
            }
        });
        final GroupLayout jPanel6Layout = new GroupLayout(this.jPanel6);
        this.jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addGap(1, 1, 1).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.Real_start).addComponent(this.Real_end).addComponent(this.jLabel8, -1, -1, 32767).addComponent(this.jLabel9, -1, -1, 32767)).addGap(2, 2, 2)));
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.jLabel8).addGap(1, 1, 1).addComponent(this.Real_start, -2, -1, -2).addGap(1, 1, 1).addComponent(this.jLabel9).addGap(1, 1, 1).addComponent(this.Real_end, -2, -1, -2).addContainerGap(-1, 32767)));
        this.fractal_type.setBackground(new Color(83, 83, 93));
        this.fractal_type.setForeground(new Color(183, 183, 183));
        this.fractal_type.setModel(new DefaultComboBoxModel<String>(new String[] { "Mandelbroth set", "Julius set" }));
        this.fractal_type.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.fractal_type.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent evt) {
                Window.this.fractal_typeItemStateChanged(evt);
            }
        });
        this.jCheckBox3.setBackground(new Color(103, 103, 113));
        this.jCheckBox3.setForeground(new Color(61, 63, 65));
        this.jCheckBox3.setToolTipText("Set this item as static");
        this.cursorPositionLabel.setForeground(new Color(177, 177, 187));
        this.cursorPositionLabel.setText("X: 0.0 ; Y: 0.0");
        this.jPanel9.setBackground(new Color(103, 103, 113));
        this.jPanel9.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), "", 0, 0, new Font("Dialog", 1, 12), new Color(183, 183, 183)));
        this.jPanel9.setForeground(new Color(203, 203, 203));
        this.Data_Re.setBackground(new Color(83, 83, 93));
        this.Data_Re.setForeground(new Color(163, 163, 163));
        this.Data_Re.setText("-0.8");
        this.Data_Re.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.Data_Re.setCaretColor(new Color(255, 255, 255));
        this.Data_Re.setSelectedTextColor(new Color(255, 255, 255));
        this.jLabel11.setForeground(new Color(203, 203, 203));
        this.jLabel11.setText("Re:");
        this.Data_Im.setBackground(new Color(83, 83, 93));
        this.Data_Im.setForeground(new Color(163, 163, 163));
        this.Data_Im.setText("0.156");
        this.Data_Im.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.Data_Im.setCaretColor(new Color(255, 255, 255));
        this.Data_Im.setSelectedTextColor(new Color(255, 255, 255));
        this.jLabel12.setForeground(new Color(203, 203, 203));
        this.jLabel12.setText("Im:");
        final GroupLayout jPanel9Layout = new GroupLayout(this.jPanel9);
        this.jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel11).addGap(1, 1, 1).addComponent(this.Data_Re).addGap(1, 1, 1).addComponent(this.jLabel12).addGap(1, 1, 1).addComponent(this.Data_Im).addContainerGap()));
        jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel9Layout.createSequentialGroup().addGap(2, 2, 2).addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel11, -1, -1, 32767).addComponent(this.Data_Re, -2, 16, -2)).addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel12, -1, -1, 32767).addComponent(this.Data_Im, -2, 16, -2))).addGap(2, 2, 2)));
        this.jPanel8.setBackground(new Color(103, 103, 113));
        this.jPanel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), "Color", 0, 0, new Font("Dialog", 1, 12), new Color(183, 183, 183)));
        this.jPanel8.setForeground(new Color(203, 203, 203));
        this.coloring_cb.setBackground(new Color(83, 83, 93));
        this.coloring_cb.setForeground(new Color(183, 183, 183));
        this.coloring_cb.setModel(new DefaultComboBoxModel<String>(new String[] { "Default", "Palette 1", "Palette 2", "Palette 3", "Palette 4", "Palette 5", "Palette 6", "Custom" }));
        this.coloring_cb.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.coloring_cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent evt) {
                Window.this.coloring_cbItemStateChanged(evt);
            }
        });
        this.jLabel15.setForeground(new Color(203, 203, 203));
        this.jLabel15.setText("Coloring:");
        this.canvas_coloring.setMinimumSize(new Dimension(291, 40));
        this.canvas_coloring.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent evt) {
                Window.this.canvas_coloringMousePressed(evt);
            }
        });
        this.jButton3.setBackground(new Color(103, 103, 113));
        this.jButton3.setForeground(new Color(188, 187, 187));
        this.jButton3.setText("Clear");
        this.jButton3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jButton3.setEnabled(false);
        this.jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton3ActionPerformed(evt);
            }
        });
        this.jButton4.setBackground(new Color(103, 103, 113));
        this.jButton4.setForeground(new Color(188, 187, 187));
        this.jButton4.setText("Add new");
        this.jButton4.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jButton4.setEnabled(false);
        this.jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton4ActionPerformed(evt);
            }
        });
        this.jButton7.setBackground(new Color(103, 103, 113));
        this.jButton7.setForeground(new Color(188, 187, 187));
        this.jButton7.setText("Delete last");
        this.jButton7.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jButton7.setEnabled(false);
        this.jButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton7ActionPerformed(evt);
            }
        });
        this.jScroll_table.getViewport().setBackground(new Color(60, 63, 65));
        this.jScroll_table.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jTable1.setBackground(new Color(60, 63, 61));
        this.jTable1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jTable1.setForeground(new Color(203, 203, 203));
        this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[] { "X", "Range", "Color" }) {
            Class[] types = { Integer.class, Integer.class, Object.class };
            
            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }
        });
        this.jTable1.setSelectionBackground(new Color(103, 103, 103));
        this.jTable1.setSelectionForeground(new Color(204, 204, 204));
        this.jTable1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                Window.this.jTable1PropertyChange(evt);
            }
        });
        this.jScroll_table.setViewportView(this.jTable1);
        this.jTable1.getTableHeader().setBackground(new Color(60, 63, 75));
        this.jTable1.getTableHeader().setForeground(new Color(205, 205, 205));
        final GroupLayout jPanel8Layout = new GroupLayout(this.jPanel8);
        this.jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.canvas_coloring, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addGroup(jPanel8Layout.createSequentialGroup().addGap(1, 1, 1).addComponent(this.jLabel15, -1, 128, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.coloring_cb, -2, 155, -2).addGap(1, 1, 1)).addGroup(jPanel8Layout.createSequentialGroup().addComponent(this.jButton3, -1, -1, 32767).addGap(0, 0, 0).addComponent(this.jButton4, -1, -1, 32767).addGap(0, 0, 0).addComponent(this.jButton7, -1, -1, 32767)).addComponent(this.jScroll_table, -2, 0, 32767));
        jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel8Layout.createSequentialGroup().addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel15).addComponent(this.coloring_cb, -2, 18, -2)).addGap(1, 1, 1).addComponent(this.canvas_coloring, -2, 31, -2).addGap(1, 1, 1).addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton3, -2, 20, -2).addComponent(this.jButton4, -2, 20, -2).addComponent(this.jButton7, -2, 20, -2)).addGap(1, 1, 1).addComponent(this.jScroll_table, -2, 0, 32767)));
        this.iterations_box.setEditable(true);
        this.iterations_box.setBackground(new Color(83, 83, 93));
        this.iterations_box.setForeground(new Color(163, 163, 163));
        this.iterations_box.setText("512");
        this.iterations_box.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.iterations_box.setCaretColor(new Color(255, 255, 255));
        this.iterations_box.setSelectionColor(new Color(93, 93, 113));
        this.iterations_box.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent evt) {
                Window.this.iterations_boxKeyReleased(evt);
            }
        });
        final GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.cursorPositionLabel, -1, -1, 32767).addGroup(jPanel3Layout.createSequentialGroup().addGap(1, 1, 1).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.jLabel3, -1, -1, 32767).addContainerGap()).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jPanel8, -1, -1, 32767).addComponent(this.fractal_type, GroupLayout.Alignment.LEADING, 0, -1, 32767).addComponent(this.jPanel2, -1, -1, 32767).addGroup(GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup().addComponent(this.iterations_box).addGap(1, 1, 1).addComponent(this.jCheckBox3, -2, 24, -2)).addComponent(this.jPanel6, -1, -1, 32767).addComponent(this.jPanel5, GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(this.jPanel9, GroupLayout.Alignment.LEADING, -1, -1, 32767)).addGap(1, 1, 1)))));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.fractal_type, -2, -1, -2).addGap(1, 1, 1).addComponent(this.jLabel3).addGap(1, 1, 1).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jCheckBox3, -2, 18, -2).addComponent(this.iterations_box)).addGap(1, 1, 1).addComponent(this.jPanel2, -2, -1, -2).addGap(2, 2, 2).addComponent(this.jPanel6, -2, -1, -2).addGap(1, 1, 1).addComponent(this.jPanel5, -2, -1, -2).addGap(1, 1, 1).addComponent(this.jPanel9, -2, -1, -2).addGap(1, 1, 1).addComponent(this.jPanel8, -1, -1, 32767).addGap(1, 1, 1).addComponent(this.cursorPositionLabel)));
        this.canvas1.setMinimumSize(new Dimension(300, 300));
        this.canvas1.setPreferredSize(new Dimension(600, 600));
        this.canvas1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent evt) {
                Window.this.canvas1MousePressed(evt);
            }
            
            @Override
            public void mouseReleased(final MouseEvent evt) {
                Window.this.canvas1MouseReleased(evt);
            }
        });
        this.canvas1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent evt) {
                Window.this.canvas1MouseDragged(evt);
            }
            
            @Override
            public void mouseMoved(final MouseEvent evt) {
                Window.this.canvas1MouseMoved(evt);
            }
        });
        this.canvas1.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(final MouseWheelEvent evt) {
                Window.this.canvas1MouseWheelMoved(evt);
            }
        });
        this.jInternalFrame1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jInternalFrame1.setClosable(true);
        this.jInternalFrame1.setIconifiable(true);
        this.jInternalFrame1.setResizable(true);
        this.jInternalFrame1.setVisible(false);
        this.jInternalFrame1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent evt) {
                Window.this.jInternalFrame1MouseDragged(evt);
            }
        });
        this.jInternalFrame1.addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameActivated(final InternalFrameEvent evt) {
            }
            
            @Override
            public void internalFrameClosed(final InternalFrameEvent evt) {
            }
            
            @Override
            public void internalFrameClosing(final InternalFrameEvent evt) {
            }
            
            @Override
            public void internalFrameDeactivated(final InternalFrameEvent evt) {
            }
            
            @Override
            public void internalFrameDeiconified(final InternalFrameEvent evt) {
            }
            
            @Override
            public void internalFrameIconified(final InternalFrameEvent evt) {
                Window.this.jInternalFrame1InternalFrameIconified(evt);
            }
            
            @Override
            public void internalFrameOpened(final InternalFrameEvent evt) {
            }
        });
        final GroupLayout jInternalFrame1Layout = new GroupLayout(this.jInternalFrame1.getContentPane());
        this.jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, 32767));
        jInternalFrame1Layout.setVerticalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, 32767));
        final GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jInternalFrame1, -2, 0, 32767).addGap(1, 1, 1).addComponent(this.canvas1, -1, 489, 32767).addGap(0, 0, 0).addComponent(this.jPanel3, -2, -1, -2)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel3, -1, -1, 32767).addComponent(this.canvas1, -1, 665, 32767).addComponent(this.jInternalFrame1, -2, 0, 32767));
        this.jPanel11.setBackground(new Color(133, 133, 143));
        this.jPanel11.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jPanel11.setForeground(new Color(137, 137, 147));
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/fractal_generator/src/render.png")));
        this.jButton1.setToolTipText("Render");
        this.jButton1.setBorder(null);
        this.jButton1.setBorderPainted(false);
        this.jButton1.setContentAreaFilled(false);
        this.jButton1.setDefaultCapable(false);
        this.jButton1.setFocusPainted(false);
        this.jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton1ActionPerformed(evt);
            }
        });
        this.jButton2.setIcon(new ImageIcon(this.getClass().getResource("/fractal_generator/src/coordination.png")));
        this.jButton2.setToolTipText("Grids");
        this.jButton2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jButton2.setContentAreaFilled(false);
        this.jButton2.setFocusPainted(false);
        this.jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton2ActionPerformed(evt);
            }
        });
        this.jButton5.setIcon(new ImageIcon(this.getClass().getResource("/fractal_generator/src/print.png")));
        this.jButton5.setToolTipText("Print");
        this.jButton5.setBorder(null);
        this.jButton5.setBorderPainted(false);
        this.jButton5.setContentAreaFilled(false);
        this.jButton5.setFocusPainted(false);
        this.jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton5ActionPerformed(evt);
            }
        });
        this.jButton6.setIcon(new ImageIcon(this.getClass().getResource("/fractal_generator/src/m1.png")));
        this.jButton6.setToolTipText("");
        this.jButton6.setBorder(null);
        this.jButton6.setBorderPainted(false);
        this.jButton6.setContentAreaFilled(false);
        this.jButton6.setFocusPainted(false);
        this.jButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton6ActionPerformed(evt);
            }
        });
        this.jButton9.setIcon(new ImageIcon(this.getClass().getResource("/fractal_generator/src/m2.png")));
        this.jButton9.setToolTipText("");
        this.jButton9.setBorder(null);
        this.jButton9.setBorderPainted(false);
        this.jButton9.setContentAreaFilled(false);
        this.jButton9.setFocusPainted(false);
        this.jButton9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton9ActionPerformed(evt);
            }
        });
        this.jButton10.setIcon(new ImageIcon(this.getClass().getResource("/fractal_generator/src/iterations.png")));
        this.jButton10.setToolTipText("Iteration analyzator");
        this.jButton10.setBorder(null);
        this.jButton10.setBorderPainted(false);
        this.jButton10.setContentAreaFilled(false);
        this.jButton10.setFocusPainted(false);
        this.jButton10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton10ActionPerformed(evt);
            }
        });
        this.jButton11.setIcon(new ImageIcon(this.getClass().getResource("/fractal_generator/src/new.png")));
        this.jButton11.setToolTipText("New");
        this.jButton11.setBorder(null);
        this.jButton11.setBorderPainted(false);
        this.jButton11.setContentAreaFilled(false);
        this.jButton11.setFocusPainted(false);
        this.jButton11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton11ActionPerformed(evt);
            }
        });
        this.jButton12.setIcon(new ImageIcon(this.getClass().getResource("/fractal_generator/src/defaultPosition.png")));
        this.jButton12.setToolTipText("Set default position");
        this.jButton12.setBorder(null);
        this.jButton12.setBorderPainted(false);
        this.jButton12.setContentAreaFilled(false);
        this.jButton12.setFocusPainted(false);
        this.jButton12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jButton12ActionPerformed(evt);
            }
        });
        this.jLabel4.setFont(new Font("Dialog", 1, 10));
        this.jLabel4.setForeground(new Color(187, 187, 197));
        this.jLabel4.setText("CPU:");
        this.jLabel5.setFont(new Font("Dialog", 1, 10));
        this.jLabel5.setText("Memory:");
        final GroupLayout jPanel11Layout = new GroupLayout(this.jPanel11);
        this.jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel11Layout.createSequentialGroup().addComponent(this.jButton1, -2, 32, -2).addGap(1, 1, 1).addComponent(this.jButton2, -2, 32, -2).addGap(1, 1, 1).addComponent(this.jButton5, -2, 32, -2).addGap(1, 1, 1).addComponent(this.jButton6, -2, 32, -2).addGap(1, 1, 1).addComponent(this.jButton9, -2, 32, -2).addGap(0, 0, 0).addComponent(this.jButton10, -2, 32, -2).addGap(0, 0, 0).addComponent(this.jButton11, -2, 32, -2).addGap(0, 0, 0).addComponent(this.jButton12, -2, 32, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel4, -1, -1, 32767).addComponent(this.jLabel5, -1, 125, 32767))));
        jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButton1, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.jButton2, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.jButton5, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.jButton6, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.jButton9, -1, -1, 32767).addComponent(this.jButton10, -1, -1, 32767).addComponent(this.jButton11, -1, -1, 32767).addComponent(this.jButton12, -1, -1, 32767).addGroup(jPanel11Layout.createSequentialGroup().addComponent(this.jLabel4).addGap(0, 0, 0).addComponent(this.jLabel5)));
        this.jMenuBar1.setBackground(new Color(93, 93, 103));
        this.jMenuBar1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jMenuBar1.setForeground(new Color(183, 183, 183));
        this.jMenu1.setForeground(new Color(183, 183, 183));
        this.jMenu1.setText("File");
        this.jMenuItem3.setAccelerator(KeyStroke.getKeyStroke(78, 2));
        this.jMenuItem3.setBackground(new Color(93, 93, 103));
        this.jMenuItem3.setForeground(new Color(183, 183, 183));
        this.jMenuItem3.setText("New");
        this.jMenuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem3ActionPerformed(evt);
            }
        });
        this.jMenu1.add(this.jMenuItem3);
        this.jMenuItem1.setAccelerator(KeyStroke.getKeyStroke(83, 2));
        this.jMenuItem1.setBackground(new Color(93, 93, 103));
        this.jMenuItem1.setForeground(new Color(183, 183, 183));
        this.jMenuItem1.setText("Save");
        this.jMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem1ActionPerformed(evt);
            }
        });
        this.jMenu1.add(this.jMenuItem1);
        this.jMenuItem2.setAccelerator(KeyStroke.getKeyStroke(80, 2));
        this.jMenuItem2.setBackground(new Color(93, 93, 103));
        this.jMenuItem2.setForeground(new Color(183, 183, 183));
        this.jMenuItem2.setText("Print");
        this.jMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem2ActionPerformed(evt);
            }
        });
        this.jMenu1.add(this.jMenuItem2);
        this.jMenuBar1.add(this.jMenu1);
        this.jMenu3.setForeground(new Color(183, 183, 183));
        this.jMenu3.setText("Tools");
        this.jMenuItem4.setAccelerator(KeyStroke.getKeyStroke(82, 2));
        this.jMenuItem4.setBackground(new Color(93, 93, 103));
        this.jMenuItem4.setForeground(new Color(183, 183, 183));
        this.jMenuItem4.setText("Render");
        this.jMenuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem4ActionPerformed(evt);
            }
        });
        this.jMenu3.add(this.jMenuItem4);
        this.jMenuItem5.setAccelerator(KeyStroke.getKeyStroke(71, 2));
        this.jMenuItem5.setBackground(new Color(93, 93, 103));
        this.jMenuItem5.setForeground(new Color(183, 183, 183));
        this.jMenuItem5.setText("Grids");
        this.jMenuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem5ActionPerformed(evt);
            }
        });
        this.jMenu3.add(this.jMenuItem5);
        this.jCheckBoxMenuItem1.setBackground(new Color(93, 93, 103));
        this.jCheckBoxMenuItem1.setForeground(new Color(183, 183, 183));
        this.jCheckBoxMenuItem1.setText("Auto render");
        this.jMenu3.add(this.jCheckBoxMenuItem1);
        this.jMenuItem9.setBackground(new Color(93, 93, 103));
        this.jMenuItem9.setForeground(new Color(183, 183, 183));
        this.jMenuItem9.setText("Iteration analyzator");
        this.jMenuItem9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem9ActionPerformed(evt);
            }
        });
        this.jMenu3.add(this.jMenuItem9);
        this.jMenuBar1.add(this.jMenu3);
        this.jMenu5.setBackground(new Color(93, 93, 103));
        this.jMenu5.setForeground(new Color(183, 183, 183));
        this.jMenu5.setText("View");
        this.jMenuItem7.setAccelerator(KeyStroke.getKeyStroke(107, 2));
        this.jMenuItem7.setBackground(new Color(93, 93, 103));
        this.jMenuItem7.setForeground(new Color(183, 183, 183));
        this.jMenuItem7.setText("Zoom +");
        this.jMenuItem7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem7ActionPerformed(evt);
            }
        });
        this.jMenu5.add(this.jMenuItem7);
        this.jMenuItem8.setAccelerator(KeyStroke.getKeyStroke(109, 2));
        this.jMenuItem8.setBackground(new Color(93, 93, 103));
        this.jMenuItem8.setForeground(new Color(183, 183, 183));
        this.jMenuItem8.setText("Zoom -");
        this.jMenuItem8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem8ActionPerformed(evt);
            }
        });
        this.jMenu5.add(this.jMenuItem8);
        this.jMenuBar1.add(this.jMenu5);
        this.jMenu4.setBackground(new Color(93, 93, 103));
        this.jMenu4.setForeground(new Color(183, 183, 183));
        this.jMenu4.setText("About");
        this.jMenuItem6.setBackground(new Color(93, 93, 103));
        this.jMenuItem6.setForeground(new Color(183, 183, 183));
        this.jMenuItem6.setText("Program");
        this.jMenuItem6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                Window.this.jMenuItem6ActionPerformed(evt);
            }
        });
        this.jMenu4.add(this.jMenuItem6);
        this.jMenuBar1.add(this.jMenu4);
        this.setJMenuBar(this.jMenuBar1);
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767).addComponent(this.jPanel11, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel11, -2, -1, -2).addGap(0, 0, 0).addComponent(this.jPanel1, -1, -1, 32767).addGap(0, 0, 0)));
        this.pack();
    }
    
    private void formComponentResized(final ComponentEvent evt) {
    }
    
    private void Real_startActionPerformed(final ActionEvent evt) {
    }
    
    private void Real_endActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton1ActionPerformed(final ActionEvent evt) {
        this.rect[0] = -1;
        this.render();
    }
    
    private void render() {
        final Thread render = new Thread() {
            @Override
            public void run() {
                Window.this.renderFractal();
            }
        };
        render.start();
        Fractal_Generator.window.setEnabled(false);
        final JDialog dlg = new JDialog(this, "Rendering", false);
        dlg.setResizable(false);
        dlg.setSize(300, 111);
        dlg.setLocationRelativeTo(this);
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 3));
        panel.setBackground(new Color(60, 63, 64));
        dlg.add(panel);
        final JProgressBar jPB1 = new JProgressBar(0, 100);
        jPB1.setBackground(new Color(90, 93, 94));
        jPB1.setForeground(new Color(40, 43, 54));
        jPB1.setStringPainted(true);
        panel.add(jPB1);
        final JProgressBar jPB2 = new JProgressBar(0, 100);
        jPB2.setBackground(new Color(90, 93, 94));
        jPB2.setForeground(new Color(40, 43, 54));
        jPB2.setStringPainted(true);
        panel.add(jPB2);
        final JProgressBar jPB3 = new JProgressBar(0, 100);
        jPB3.setBackground(new Color(90, 93, 94));
        jPB3.setForeground(new Color(40, 43, 54));
        jPB3.setStringPainted(true);
        panel.add(jPB3);
        final JProgressBar jPB4 = new JProgressBar(0, 100);
        jPB4.setBackground(new Color(90, 93, 94));
        jPB4.setForeground(new Color(40, 43, 54));
        jPB4.setStringPainted(true);
        panel.add(jPB4);
        final Thread t1 = new Thread() {
            @Override
            public void run() {
                dlg.setVisible(true);
            }
        };
        t1.start();
        final Thread t2 = new Thread() {
            @Override
            public void run() {
                dlg.setVisible(true);
                do {
                    try {
                        Thread.sleep(15L);
                    }
                    catch (InterruptedException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        jPB1.setValue((int)Fractal_Generator.window.fractal.getStatusT1());
                    }
                    catch (Exception ex2) {}
                    try {
                        jPB2.setValue((int)Fractal_Generator.window.fractal.getStatusT2());
                    }
                    catch (Exception ex3) {}
                    try {
                        jPB3.setValue((int)Fractal_Generator.window.fractal.getStatusT3());
                    }
                    catch (Exception ex4) {}
                    try {
                        jPB4.setValue((int)Fractal_Generator.window.fractal.getStatusT4());
                    }
                    catch (Exception ex5) {}
                } while ((int)Fractal_Generator.window.fractal.getStatus() != 100);
                Fractal_Generator.window.setEnabled(true);
                dlg.dispose();
                Window.this.rect[0] = -1;
            }
        };
        t2.start();
        dlg.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                t2.stop();
                render.stop();
                dlg.dispose();
                Fractal_Generator.window.fractal.stopRendering();
                Fractal_Generator.window.setEnabled(true);
            }
        });
    }
    
    private void renderFractal() {
        if (this.coloring_cb.getSelectedIndex() == 7) {
            Window.coloringManager.computeColoring(Integer.parseInt(this.iterations_box.getText()));
        }
        try {
            this.ITERATIONS = Integer.parseInt(this.iterations_box.getText());
        }
        catch (NumberFormatException ex) {
            return;
        }
        try {
            final Range real = new Range(Double.parseDouble(this.Real_start.getText()) * -1.0, Double.parseDouble(this.Real_end.getText()) * -1.0);
            this.REAL = real;
        }
        catch (NumberFormatException ex) {
            return;
        }
        try {
            final Range imaginar = new Range(Double.parseDouble(this.Imaginar_start.getText()) * -1.0, Double.parseDouble(this.Imaginar_end.getText()) * -1.0);
            this.IMAGINAR = imaginar;
        }
        catch (NumberFormatException ex) {
            return;
        }
        if (this.jCheckBox1.isSelected() && !this.jCheckBox2.isSelected()) {
            this.image_height.setText((int)(Float.parseFloat(this.image_width.getText()) / this.REAL.getDistance() * this.IMAGINAR.getDistance()) + "");
        }
        if (this.jCheckBox2.isSelected() && !this.jCheckBox1.isSelected()) {
            this.image_width.setText((int)(Float.parseFloat(this.image_height.getText()) / this.IMAGINAR.getDistance() * this.REAL.getDistance()) + "");
        }
        try {
            final Dimension size = new Dimension((int)Float.parseFloat(this.image_width.getText()), (int)Float.parseFloat(this.image_height.getText()));
            this.IMAGESIZE = size;
        }
        catch (NumberFormatException ex) {
            return;
        }
        final int imageType = FractalManager.getImageType(this.image_type.getSelectedIndex());
        FractalManager.createColorSpectrum(this.ITERATIONS, this.coloring_cb.getSelectedIndex());
        switch (this.fractal_type.getSelectedIndex()) {
            case 0: {
                this.fractal = new Mandelbroth(this.ITERATIONS, this.REAL, this.IMAGINAR, this.IMAGESIZE);
                try {
                    this.FractalImage = this.fractal.renderImage(new double[] { imageType });
                }
                catch (Exception ex2) {}
                break;
            }
            case 1: {
                this.fractal = new Julius(this.ITERATIONS, this.REAL, this.IMAGINAR, this.IMAGESIZE);
                try {
                    this.FractalImage = this.fractal.renderImage(new double[] { imageType, Double.parseDouble(this.Data_Re.getText()), Double.parseDouble(this.Data_Im.getText()) });
                }
                catch (Exception ex3) {}
                break;
            }
        }
    }
    
    private void canvas1MouseWheelMoved(final MouseWheelEvent evt) {
        if (this.jCheckBoxMenuItem1.isSelected()) {
            if (evt.getWheelRotation() < 0) {
                this.autoRender();
            }
            return;
        }
        this.scale -= evt.getWheelRotation() / (10.0f / this.scale);
        if (this.scale < 0.02f) {
            this.scale = 0.02f;
        }
        this.autoRender();
    }
    
    private void canvas1MouseDragged(final MouseEvent evt) {
        if (evt.getButton() == 0) {
            if (this.canvasMouseDraggedTimeOut == 0) {
                this.cursor = evt.getPoint();
            }
            if (this.canvasMouseDraggedTimeOut > 1) {
                this.canvasMouseDraggedTimeOut = 0;
                final Point f = new Point(this.cursor.x - evt.getPoint().x, this.cursor.y - evt.getPoint().y);
                if (Math.abs(f.x) > 50 || Math.abs(f.y) > 50) {
                    return;
                }
                int m = (int)(-f.x * (1.0f / this.scale));
                if (Math.abs(m) < 1) {
                    m = 1 * -f.x;
                }
                this.X += m;
                m = (int)(-f.y * (1.0f / this.scale));
                if (Math.abs(m) < 1) {
                    m = 1 * -f.y;
                }
                this.Y += m;
            }
            else {
                ++this.canvasMouseDraggedTimeOut;
            }
        }
    }
    
    private void canvas1MousePressed(final MouseEvent evt) {
        if (evt.getButton() == 1) {
            this.canvas1.setCursor(Cursor.getPredefinedCursor(13));
            this.canvasMouseDraggedTimeOut = 0;
        }
        if (evt.getButton() == 3 && !this.jCheckBoxMenuItem1.isSelected() && this.grids) {
            try {
                int n = 1;
                if (this.scale > 2.0f) {
                    n = 2;
                }
                if (this.scale > 3.0f) {
                    n = 3;
                }
                if (this.scale > 4.0f) {
                    n = 4;
                }
                if (this.scale > 5.0f) {
                    n = 5;
                }
                if (this.scale > 6.0f) {
                    n = 6;
                }
                final double RealPerOne = this.REAL.getDistance() / (10.0 * n);
                final double ImaginarPerOne = this.IMAGINAR.getDistance() / (10.0 * n);
                for (int k = 0; k < 10 * n; ++k) {
                    for (int j = 0; j < 10 * n; ++j) {
                        final double x = (this.X + k * this.IMAGESIZE.width / (10.0f * n)) * this.scale;
                        final double y = (this.Y + j * this.IMAGESIZE.height / (10.0f * n)) * this.scale;
                        if (evt.getX() > x && x < evt.getX() + RealPerOne && evt.getY() > y && y < evt.getY() + ImaginarPerOne) {
                            this.Imaginar_start.setText((this.IMAGINAR.START + ImaginarPerOne * j) * -1.0 + "");
                            this.Imaginar_end.setText((this.IMAGINAR.START + ImaginarPerOne * (j + 1)) * -1.0 + "");
                            this.Real_start.setText((this.REAL.START + RealPerOne * k) * -1.0 + "");
                            this.Real_end.setText((this.REAL.START + RealPerOne * (k + 1)) * -1.0 + "");
                            this.rect[0] = k;
                            this.rect[1] = j;
                            this.rect[2] = n;
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private void jButton2ActionPerformed(final ActionEvent evt) {
        if (this.grids) {
            this.jButton2.setBorder(null);
            this.grids = false;
        }
        else {
            this.jButton2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
            this.grids = true;
        }
    }
    
    private void jMenuItem1ActionPerformed(final ActionEvent evt) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG", new String[] { "png" }));
        if (fileChooser.showSaveDialog(this) == 0) {
            try {
                final File file = fileChooser.getSelectedFile();
                ImageIO.write(this.FractalImage, "png", file);
            }
            catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void canvas1MouseReleased(final MouseEvent evt) {
        final Cursor cursor = Cursor.getPredefinedCursor(0);
        this.canvas1.setCursor(cursor);
    }
    
    private void jMenuItem2ActionPerformed(final ActionEvent evt) {
        final PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new ImagePrintable(printJob, this.FractalImage));
        if (printJob.printDialog()) {
            try {
                printJob.print();
            }
            catch (PrinterException prt) {
                prt.printStackTrace();
            }
        }
    }
    
    private void jButton5ActionPerformed(final ActionEvent evt) {
        final PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new ImagePrintable(printJob, this.FractalImage));
        if (printJob.printDialog()) {
            try {
                printJob.print();
            }
            catch (PrinterException prt) {
                prt.printStackTrace();
            }
        }
    }
    
    private void jButton6ActionPerformed(final ActionEvent evt) {
        this.scale -= 1.0f / (10.0f / this.scale);
        if (this.scale < 0.02f) {
            this.scale = 0.02f;
        }
    }
    
    private void jButton9ActionPerformed(final ActionEvent evt) {
        this.scale -= -1.0f / (10.0f / this.scale);
        if (this.scale < 0.02f) {
            this.scale = 0.02f;
        }
    }
    
    private void jMenuItem3ActionPerformed(final ActionEvent evt) {
        final int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to create a new fractal?", "New", 0);
        if (dialogResult == 0) {
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
    }
    
    private void jMenuItem4ActionPerformed(final ActionEvent evt) {
        this.rect[0] = -1;
        this.render();
    }
    
    private void jMenuItem5ActionPerformed(final ActionEvent evt) {
        if (this.grids) {
            this.jButton2.setBorder(null);
            this.grids = false;
        }
        else {
            this.jButton2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
            this.grids = true;
        }
    }
    
    private void canvas1MouseMoved(final MouseEvent evt) {
        this.cursorPositionLabel.setText("X: " + evt.getX() + " ; Y: " + evt.getY());
    }
    
    private void fractal_typeItemStateChanged(final ItemEvent evt) {
        switch (this.fractal_type.getSelectedIndex()) {
            case 0: {
                this.jPanel9.setVisible(false);
                break;
            }
            case 1: {
                this.jPanel9.setVisible(true);
                break;
            }
        }
    }
    
    private void jMenuItem6ActionPerformed(final ActionEvent evt) {
        this.about.setVisible(true);
    }
    
    private void jMenuItem7ActionPerformed(final ActionEvent evt) {
        this.scale -= -1.0f / (10.0f / this.scale);
        if (this.scale < 0.02f) {
            this.scale = 0.02f;
        }
    }
    
    private void jMenuItem8ActionPerformed(final ActionEvent evt) {
        this.scale -= 1.0f / (10.0f / this.scale);
        if (this.scale < 0.02f) {
            this.scale = 0.02f;
        }
    }
    
    private void jButton4ActionPerformed(final ActionEvent evt) {
        this.newColor = true;
    }
    
    private void coloring_cbItemStateChanged(final ItemEvent evt) {
        if (this.coloring_cb.getSelectedIndex() == 7) {
            this.jButton3.setEnabled(true);
            this.jButton4.setEnabled(true);
            this.jButton7.setEnabled(true);
            this.jTable1.setEnabled(true);
        }
        else {
            this.jButton3.setEnabled(false);
            this.jButton4.setEnabled(false);
            this.jButton7.setEnabled(false);
            this.jTable1.setEnabled(false);
        }
        FractalManager.createColorSpectrum(this.ITERATIONS, this.coloring_cb.getSelectedIndex());
    }
    
    private void jButton3ActionPerformed(final ActionEvent evt) {
        Window.coloringManager.clear(Integer.parseInt(this.iterations_box.getText()));
        Window.coloringManager.colorPoints = new ArrayList<ColorPoint>();
    }
    
    private void jButton7ActionPerformed(final ActionEvent evt) {
        Window.coloringManager.removeLast();
        Window.coloringManager.computeColoring(Integer.parseInt(this.iterations_box.getText()));
    }
    
    private void canvas_coloringMousePressed(final MouseEvent evt) {
        if (!this.newColor) {
            return;
        }
        this.newColor = false;
        final int i = (int)(evt.getX() / (float)this.canvas_coloring.getWidth() * Integer.parseInt(this.iterations_box.getText()));
        Window.coloringManager.addNewColorPoint(i, 1.0f, 100, JColorChooser.showDialog(this, "Color", new Color(0, 0, 0)));
        Window.coloringManager.computeColoring(Integer.parseInt(this.iterations_box.getText()));
    }
    
    private void jTable1PropertyChange(final PropertyChangeEvent evt) {
        try {
            Window.coloringManager.computeColoring(Integer.parseInt(this.iterations_box.getText()));
        }
        catch (Exception ex) {}
    }
    
    private void jMenuItem9ActionPerformed(final ActionEvent evt) {
        try {
            this.IterationAnalyzator.stop();
            this.IterationAnalyzator.setVisible(false);
        }
        catch (Exception ex) {}
        (this.IterationAnalyzator = new IterationAnalyzator()).refresh();
        this.IterationAnalyzator.run();
        this.jInternalFrame1.setTitle("Iteration Analyzator");
        this.jInternalFrame1.setVisible(true);
        this.jInternalFrame1.setContentPane(this.IterationAnalyzator.jPanel1);
    }
    
    private void iterations_boxKeyReleased(final KeyEvent evt) {
    }
    
    private void Imaginar_startActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton10ActionPerformed(final ActionEvent evt) {
        try {
            this.IterationAnalyzator.stop();
            this.IterationAnalyzator.setVisible(false);
        }
        catch (Exception ex) {}
        (this.IterationAnalyzator = new IterationAnalyzator()).refresh();
        this.IterationAnalyzator.run();
        this.jInternalFrame1.setTitle("Iteration Analyzator");
        this.jInternalFrame1.setVisible(true);
        this.jInternalFrame1.setContentPane(this.IterationAnalyzator.jPanel1);
    }
    
    private void jButton11ActionPerformed(final ActionEvent evt) {
        final int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to create a new fractal?", "New", 0);
        if (dialogResult == 0) {
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
    }
    
    private void jButton12ActionPerformed(final ActionEvent evt) {
        this.X = 0;
        this.Y = 0;
    }
    
    private void jInternalFrame1MouseDragged(final MouseEvent evt) {
    }
    
    private void jInternalFrame1InternalFrameIconified(final InternalFrameEvent evt) {
        this.jInternalFrame1.setVisible(false);
        (this.IterationAnalyzator = new IterationAnalyzator()).setVisible(true);
        this.IterationAnalyzator.refresh();
        this.IterationAnalyzator.run();
    }
    
    private void autoRender() {
        if (!this.jCheckBoxMenuItem1.isSelected()) {
            return;
        }
        final int mx = this.canvas1.getWidth() / 2;
        final int my = this.canvas1.getHeight() / 2;
        try {
            int n = 1;
            if (this.scale > 2.0f) {
                n = 2;
            }
            if (this.scale > 3.0f) {
                n = 3;
            }
            if (this.scale > 4.0f) {
                n = 4;
            }
            if (this.scale > 5.0f) {
                n = 5;
            }
            if (this.scale > 6.0f) {
                n = 6;
            }
            final double RealPerOne = this.REAL.getDistance() / (10.0 * n);
            final double ImaginarPerOne = this.IMAGINAR.getDistance() / (10.0 * n);
            for (int k = 0; k <= 10 * n; ++k) {
                for (int j = 0; j <= 10 * n; ++j) {
                    final double x = (this.X + k * this.IMAGESIZE.width / (10.0f * n)) * this.scale;
                    final double y = (this.Y + j * this.IMAGESIZE.height / (10.0f * n)) * this.scale;
                    if (mx > x && x < mx + RealPerOne && my > y && y < my + ImaginarPerOne) {
                        this.Imaginar_start.setText((this.IMAGINAR.START + ImaginarPerOne * j) * -1.0 + "");
                        this.Imaginar_end.setText((this.IMAGINAR.START + ImaginarPerOne * (j + 1)) * -1.0 + "");
                        this.Real_start.setText((this.REAL.START + RealPerOne * k) * -1.0 + "");
                        this.Real_end.setText((this.REAL.START + RealPerOne * (k + 1)) * -1.0 + "");
                        this.rect[0] = k;
                        this.rect[1] = j;
                        this.rect[2] = n;
                    }
                }
            }
        }
        catch (Exception ex) {}
        this.lastIter = (int)Double.parseDouble(this.iterations_box.getText());
        final int i = this.lastIter + 500;
        if (i > Double.parseDouble(this.iterations_box.getText()) && !this.jCheckBox3.isSelected()) {
            this.iterations_box.setText(i + "");
        }
        this.render();
    }
    
    static {
        Window.coloringManager = new ColoringManager();
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
