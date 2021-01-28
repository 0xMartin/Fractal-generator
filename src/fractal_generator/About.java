// 
// Decompiled by Procyon v0.5.36
// 

package fractal_generator;

import javax.swing.LayoutStyle;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.GroupLayout;
import java.awt.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;

public class About extends JFrame
{
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    
    public About() {
        this.initComponents();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height / 2 - this.getHeight() / 2);
    }
    
    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel4 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel1 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle("Program");
        this.setBackground(new Color(60, 63, 64));
        this.setResizable(false);
        this.setType(Type.UTILITY);
        this.jPanel1.setBackground(new Color(103, 103, 113));
        this.jLabel4.setForeground(new Color(197, 197, 197));
        this.jLabel4.setText("Author: Martin Kr\u010dma");
        this.jLabel3.setForeground(new Color(197, 197, 197));
        this.jLabel3.setText("Last update: 19.11.2017");
        this.jLabel2.setForeground(new Color(197, 197, 197));
        this.jLabel2.setText("Version: 2.3.0.0");
        this.jLabel1.setForeground(new Color(197, 197, 197));
        this.jLabel1.setText("Program: Fractal generator");
        final GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4)).addContainerGap(201, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel4).addContainerGap(144, 32767)));
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, GroupLayout.Alignment.TRAILING, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.jPanel1, -1, -1, 32767)));
        this.pack();
    }
}
