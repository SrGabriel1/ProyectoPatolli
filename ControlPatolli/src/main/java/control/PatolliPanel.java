package control;

import javax.swing.*;
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
    public PatolliPanel(int jugadores, int imagenAncho, int imagenAlto) throws Exception {
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
       private void configurarTablero() throws Exception {
        int casillasPorAspa = switch (jugadores) {
            case 2 -> 8;
            case 3 -> 10;
            case 4 -> 14;
            default -> throw new Exception("Número de jugadores inválido");
        };

        // Configurar cada panel con su tipo correspondiente
        configurarPanel(casillasArriba, casillasPorAspa, 2, TableroBuilder.TipoTablero.ARRIBA);
        configurarPanel(casillasAbajo, casillasPorAspa, 2, TableroBuilder.TipoTablero.ABAJO);
        configurarPanel(casillasIzq, 2, casillasPorAspa, TableroBuilder.TipoTablero.IZQUIERDA);
        configurarPanel(casillasDer, 2, casillasPorAspa, TableroBuilder.TipoTablero.DERECHA);
        configurarPanel(casillasCentrales, 2, 2, TableroBuilder.TipoTablero.CENTRAL);
    }

    private void configurarPanel(JPanel panel, int filas, int columnas, TableroBuilder.TipoTablero tipo) {
        TableroBuilder tablero = TableroBuilder.builderTablero(
            filas,
            columnas,
            panel,
            casillas,
            tipo,
            jugadores
        );
        tablero.construirTablero();
    }
}