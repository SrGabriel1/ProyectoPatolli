/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import GUIs.vistaPartidaGanada;
import GUIs.vistaTablero;
import logica.ControladorVentanas;

/**
 *
 * @author Gabriel
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        ControladorVentanas controlador = null;
        vistaTablero p = new vistaTablero(4, "xxx", controlador);
                p.setVisible(true);
    }
    
}
