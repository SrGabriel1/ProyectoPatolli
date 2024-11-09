package control;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PatolliPanel extends JPanel {

    private List<JLabel> casillas; // Lista para almacenar las casillas
    private JPanel casillasArriba;
    private JPanel casillasAbajo;
    private JPanel casillasIzq;
    private JPanel casillasDer;
    private JPanel casillasCentrales;
    private int jugadores; // Número de jugadores
    private Image fondoImagen;
    private int imagenAncho;
    private int imagenAlto;

    // Constructor que acepta el número de jugadores
    public PatolliPanel(int jugadores, int imagenAncho, int imagenAlto) {
        this.jugadores = jugadores;
        this.casillas = new ArrayList<>();
        this.imagenAlto = imagenAlto;
        this.imagenAncho = imagenAncho;
        // Configura el tamaño del panel según el tamaño de la imagen
        setPreferredSize(new Dimension(imagenAncho, imagenAlto));

        // Configura el layout absoluto
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        // Configura el layout absoluto para el JPanel
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inicializarPaneles(); // Inicializa los paneles
        configurarTablero(); // Configura el tablero en forma de "X"
    }

    /**
     * Inicializa los paneles en el layout absoluto.
     */
    private void inicializarPaneles() {
        // Inicializa los paneles para las secciones del tablero
        casillasArriba = new JPanel();
        casillasAbajo = new JPanel();
        casillasIzq = new JPanel();
        casillasDer = new JPanel();
        casillasCentrales = new JPanel();

        // Calcula el punto central basado en el tamaño de la imagen
        int centerX = (imagenAncho / 2) - 50;  // 50 es la mitad del ancho del panel central
        int centerY = (imagenAlto / 2) - 50;   // 50 es la mitad del alto del panel central
        // Hacer transparentes los subpaneles
        casillasArriba.setOpaque(false);
        casillasAbajo.setOpaque(false);
        casillasIzq.setOpaque(false);
        casillasDer.setOpaque(false);
        casillasCentrales.setOpaque(false);
        
        // Añade los paneles centrados relativos al punto central
        add(casillasArriba, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX, // x
                centerY - 200, // y
                100, // ancho
                200 // alto
        ));

        add(casillasAbajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX, // x
                centerY + 100, // y
                100, // ancho
                200 // alto
        ));

        add(casillasIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX - 200, // x
                centerY, // y
                200, // ancho
                100 // alto
        ));

        add(casillasDer, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX + 100, // x
                centerY, // y
                200, // ancho
                100 // alto
        ));

        add(casillasCentrales, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX, // x
                centerY, // y
                100, // ancho
                100 // alto
        ));
    }

    /**
     * Configura el tablero en forma de "X" y ajusta el tamaño según el número
     * de jugadores.
     */
    private void configurarTablero() {
        int casillasPorAspa = (jugadores == 2) ? 8 : (jugadores == 3) ? 10 : 14; // Número de casillas según el número de jugadores

        switch (casillasPorAspa) {
            case 8:
                // Configura cada panel con la forma de X
                pintarTablero2(casillasArriba, casillasPorAspa, 2, false);
                pintarTablero2(casillasAbajo, casillasPorAspa, 2, true);
                pintarTablero2(casillasDer, 2, casillasPorAspa, true);
                pintarTablero2(casillasIzq, 2, casillasPorAspa, false);
                pintarTablero2(casillasCentrales, 2, 2, true); // Centro de la "X"
                break;
            case 10:
                pintarTablero3(casillasArriba, casillasPorAspa, 2, false);
                pintarTablero3(casillasAbajo, casillasPorAspa, 2, true);
                pintarTablero3(casillasDer, 2, casillasPorAspa, true);
                pintarTablero3(casillasIzq, 2, casillasPorAspa, false);
                pintarTablero3(casillasCentrales, 2, 2, true); // Centro de la "X"
                break;
            case 14:
                pintarTablero4(casillasArriba, casillasPorAspa, 2, false);
                pintarTablero4(casillasAbajo, casillasPorAspa, 2, true);
                pintarTablero4(casillasDer, 2, casillasPorAspa, true);
                pintarTablero4(casillasIzq, 2, casillasPorAspa, false);
                pintarTablero4(casillasCentrales, 2, 2, true); // Centro de la "X"
                break;
        }

    }

    /**
     * Método para pintar cada sección del tablero con las casillas y colores
     * necesarios.
     */
    private void pintarTablero3(JPanel tablero, int filas, int columnas, boolean invertir) {
        tablero.setLayout(new GridLayout(filas, columnas, 5, 5)); // Espacio entre casillas
        tablero.setPreferredSize(tablero.getSize());

        for (int i = 1; i <= filas * columnas; i++) {
            JLabel label = new JLabel("");
            label.setBorder(new LineBorder(Color.BLACK, 1));
            label.setOpaque(true);
            label.setBackground(Color.WHITE);

            // Casillas amarillas para el centro (2x2) no se dibujan
            if (tablero == casillasCentrales) {
                // No hacemos nada para las casillas centrales
            } // Casillas rojas para las aspas (en paralelo)
            else if ((tablero == casillasArriba) && (i == 5 || i == 6)) {
                label.setBackground(Color.RED); // Rojo en la parte superior e inferior
            } else if ((tablero == casillasAbajo) && (i == 15 || i == 16)) {
                label.setBackground(Color.RED); // Rojo en la parte superior e inferior
            } else if ((tablero == casillasIzq) && (i == 3 || i == 13)) {
                label.setBackground(Color.RED); // Rojo en la parte izquierda y derecha
            } else if ((tablero == casillasDer) && (i == 8 || i == 18)) {
                label.setBackground(Color.RED); // Rojo en la parte izquierda y derecha
            } else if ((tablero == casillasArriba) && (i == 20)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasDer) && (i == 11)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasAbajo) && (i == 1)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasIzq) && (i == 10)) {
                label.setBackground(Color.YELLOW);
            }

            tablero.add(label);
            casillas.add(label); // Añade la casilla a la lista
        }
    }

    private void pintarTablero2(JPanel tablero, int filas, int columnas, boolean invertir) {
        tablero.setLayout(new GridLayout(filas, columnas, 5, 5)); // Espacio entre casillas
        tablero.setPreferredSize(tablero.getSize());

        for (int i = 1; i <= filas * columnas; i++) {
            JLabel label = new JLabel("");
            label.setBorder(new LineBorder(Color.BLACK, 1));
            label.setOpaque(true);
            label.setBackground(Color.WHITE);

            // Casillas amarillas para el centro (2x2) no se dibujan
            if (tablero == casillasCentrales) {
                // No hacemos nada para las casillas centrales
            } // Casillas rojas para las aspas (en paralelo)
            else if ((tablero == casillasArriba) && (i == 5 || i == 6)) {
                label.setBackground(Color.RED); // Rojo en la parte superior e inferior
            } else if ((tablero == casillasAbajo) && (i == 11 || i == 12)) {
                label.setBackground(Color.RED); // Rojo en la parte superior e inferior
            } else if ((tablero == casillasIzq) && (i == 3 || i == 11)) {
                label.setBackground(Color.RED); // Rojo en la parte izquierda y derecha
            } else if ((tablero == casillasDer) && (i == 6 || i == 14)) {
                label.setBackground(Color.RED); // Rojo en la parte izquierda y derecha
            } else if ((tablero == casillasArriba) && (i == 16)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasDer) && (i == 9)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasAbajo) && (i == 1)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasIzq) && (i == 8)) {
                label.setBackground(Color.YELLOW);
            }

            tablero.add(label);
            casillas.add(label); // Añade la casilla a la lista
        }
    }

    /**
     * Método para pintar cada sección del tablero con las casillas y colores
     * necesarios.
     */
    private void pintarTablero4(JPanel tablero, int filas, int columnas, boolean invertir) {
        tablero.setLayout(new GridLayout(filas, columnas, 5, 5)); // Espacio entre casillas
        tablero.setPreferredSize(tablero.getSize());

        for (int i = 1; i <= filas * columnas; i++) {
            JLabel label = new JLabel("");
            label.setBorder(new LineBorder(Color.BLACK, 1));
            label.setOpaque(true);
            label.setBackground(Color.WHITE);

            // Casillas amarillas para el centro (2x2) no se dibujan
            if (tablero == casillasCentrales) {
                // No hacemos nada para las casillas centrales
            } // Casillas rojas para las aspas (en paralelo)
            else if ((tablero == casillasArriba) && (i == 5 || i == 6)) {
                label.setBackground(Color.RED); // Rojo en la parte superior e inferior
            } else if ((tablero == casillasAbajo) && (i == 23 || i == 24)) {
                label.setBackground(Color.RED); // Rojo en la parte superior e inferior
            } else if ((tablero == casillasIzq) && (i == 3 || i == 17)) {
                label.setBackground(Color.RED); // Rojo en la parte izquierda y derecha
            } else if ((tablero == casillasDer) && (i == 12 || i == 26)) {
                label.setBackground(Color.RED); // Rojo en la parte izquierda y derecha
            } else if ((tablero == casillasArriba) && (i == 28)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasDer) && (i == 15)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasAbajo) && (i == 1)) {
                label.setBackground(Color.YELLOW);
            } else if ((tablero == casillasIzq) && (i == 14)) {
                label.setBackground(Color.YELLOW);
            }

            tablero.add(label);
            casillas.add(label); // Añade la casilla a la lista
        }
    }
}
