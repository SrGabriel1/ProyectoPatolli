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

        // Configura el layout de la ventana
        setLayout(new BorderLayout());
        
        // Crea e integra el PatolliPanel con el número de jugadores
        PatolliPanel tablero = new PatolliPanel(jugadores);
        add(tablero, BorderLayout.CENTER); // Añade el tablero al centro
        
        pack(); // Ajusta el tamaño de la ventana al contenido
        repaint(); // Re-dibuja para asegurarse que el tablero se muestre correctamente
    }

    public static void main(String[] args) {
        int jugadores = 4; // Cambia este valor para probar con 2 o 4 jugadores
        SwingUtilities.invokeLater(() -> {
            new vistaTablero(jugadores).setVisible(true);
        });
    }








    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
