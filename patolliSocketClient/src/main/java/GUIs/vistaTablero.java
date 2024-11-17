/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUIs;

import logica.PatolliPanel;
import logica.PatolliPanel;
import javax.swing.*;
import java.awt.*;

public class vistaTablero extends JFrame {

    public vistaTablero(int jugadores) throws Exception {
        initComponents();
        setLocationRelativeTo(null);

        // Crea un JLayeredPane para permitir la superposición
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(Tablero.getIcon().getIconWidth(), Tablero.getIcon().getIconHeight()));

        // Agrega el JLabel con la imagen al fondo
        Tablero.setBounds(0, 0, Tablero.getIcon().getIconWidth(), Tablero.getIcon().getIconHeight());
        layeredPane.add(Tablero, Integer.valueOf(0)); // Capa inferior

        // Crea e integra el PatolliPanel en la capa superior
        PatolliPanel tablero = new PatolliPanel(jugadores, Tablero.getIcon().getIconWidth(), Tablero.getIcon().getIconHeight());
        tablero.setOpaque(false); // Haz el tablero transparente para ver el fondo
        tablero.setBounds(0, 0, Tablero.getIcon().getIconWidth(), Tablero.getIcon().getIconHeight());
        layeredPane.add(tablero, Integer.valueOf(1)); // Capa superior

        // Agrega los botones al layeredPane en una capa intermedia
        Tirar.setBounds(340, 670, 170, 60);
        layeredPane.add(Tirar, Integer.valueOf(2));

        btnVolverInicio.setBounds(10, 670, 100, 60);
        layeredPane.add(btnVolverInicio, Integer.valueOf(2));

        // Agrega el layeredPane al JFrame
        setContentPane(layeredPane);
        pack();
        repaint(); // Re-dibuja para asegurarse que el tablero se muestre correctamente
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Nombre1 = new javax.swing.JLabel();
        Dinero1 = new javax.swing.JLabel();
        Nombre2 = new javax.swing.JLabel();
        Dinero2 = new javax.swing.JLabel();
        Nombre3 = new javax.swing.JLabel();
        Dinero3 = new javax.swing.JLabel();
        Nombre4 = new javax.swing.JLabel();
        Dinero4 = new javax.swing.JLabel();
        Turno = new javax.swing.JLabel();
        Tirar = new javax.swing.JButton();
        btnVolverInicio = new javax.swing.JButton();
        Tablero = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Nombre1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Nombre1.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Nombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 140, 50));

        Dinero1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Dinero1.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Dinero1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 130, 40));

        Nombre2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Nombre2.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Nombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 170, 50));

        Dinero2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Dinero2.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Dinero2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 150, 40));

        Nombre3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Nombre3.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Nombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 140, 40));

        Dinero3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Dinero3.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Dinero3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 620, 140, 40));

        Nombre4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Nombre4.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Nombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 570, 160, 40));

        Dinero4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Dinero4.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Dinero4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 620, 150, 40));

        Turno.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Turno.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(Turno, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 50));

        Tirar.setBorderPainted(false);
        Tirar.setContentAreaFilled(false);
        Tirar.setFocusPainted(false);
        Tirar.setFocusable(false);
        Tirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TirarActionPerformed(evt);
            }
        });
        getContentPane().add(Tirar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 670, 170, 60));

        btnVolverInicio.setBorderPainted(false);
        btnVolverInicio.setContentAreaFilled(false);
        btnVolverInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverInicioActionPerformed(evt);
            }
        });
        getContentPane().add(btnVolverInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, 100, 60));

        Tablero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Tablero.png"))); // NOI18N
        getContentPane().add(Tablero, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverInicioActionPerformed
        vistaInicio inicio = new vistaInicio();
        inicio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverInicioActionPerformed

    private void TirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TirarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TirarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Dinero1;
    private javax.swing.JLabel Dinero2;
    private javax.swing.JLabel Dinero3;
    private javax.swing.JLabel Dinero4;
    private javax.swing.JLabel Nombre1;
    private javax.swing.JLabel Nombre2;
    private javax.swing.JLabel Nombre3;
    private javax.swing.JLabel Nombre4;
    private javax.swing.JLabel Tablero;
    private javax.swing.JButton Tirar;
    private javax.swing.JLabel Turno;
    private javax.swing.JButton btnVolverInicio;
    // End of variables declaration//GEN-END:variables
}
