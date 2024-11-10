/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUIs;

import control.PatolliPanel;
import javax.swing.*;
import java.awt.*;

public class vistaTablero extends JFrame {

    public vistaTablero(int jugadores) {
        initComponents();
        setLocationRelativeTo(null);

         // Crea un JLayeredPane para permitir la superposición
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(jLabel1.getIcon().getIconWidth(), jLabel1.getIcon().getIconHeight()));

        // Agrega el JLabel con la imagen al fondo
        jLabel1.setBounds(0, 0, jLabel1.getIcon().getIconWidth(), jLabel1.getIcon().getIconHeight());
        layeredPane.add(jLabel1, Integer.valueOf(0)); // Capa inferior

        // Crea e integra el PatolliPanel en la capa superior
        PatolliPanel tablero = new PatolliPanel(jugadores, jLabel1.getIcon().getIconWidth(), jLabel1.getIcon().getIconHeight());
        tablero.setOpaque(false); // Haz el tablero transparente para ver el fondo
        tablero.setBounds(0, 0, jLabel1.getIcon().getIconWidth(), jLabel1.getIcon().getIconHeight());
        layeredPane.add(tablero, Integer.valueOf(1)); // Capa superior

        // Agrega el layeredPane al JFrame
        setContentPane(layeredPane);
        pack();
        repaint(); // Re-dibuja para asegurarse que el tablero se muestre correctamente
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Tablero.png"))); // NOI18N
        getContentPane().add(jLabel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
