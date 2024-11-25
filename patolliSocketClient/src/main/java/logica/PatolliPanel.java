package logica;

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
        this.casillasAmarillas = new ArrayList<>();

        // Configura el tamaño del panel según el tamaño de la imagen
        setPreferredSize(new Dimension(imagenAncho, imagenAlto));

        // Configura el layout absoluto
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
        revalidate();
        repaint();
        add(casillasAbajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX, // x
                centerY + 100, // y
                100, // ancho
                200 // alto
        ));
        revalidate();
        repaint();
        add(casillasIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX - 200, // x
                centerY, // y
                200, // ancho
                100 // alto
        ));
        revalidate();
        repaint();
        add(casillasDer, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX + 100, // x
                centerY, // y
                200, // ancho
                100 // alto
        ));
        revalidate();
        repaint();
        add(casillasCentrales, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX, // x
                centerY, // y
                100, // ancho
                100 // alto
        ));
        revalidate();
        repaint();
    }

    /**
     * Configura el tablero en forma de "X" y ajusta el tamaño según el número
     * de jugadores.
     */
    private void configurarTablero() throws Exception {
        int casillasPorAspa = 7; // Siempre 7 filas por aspa
        int columnasPorAspa = 2; // Siempre 2 columnas

        configurarPanel(casillasArriba, casillasPorAspa, columnasPorAspa, TableroBuilder.TipoTablero.ARRIBA);
        configurarPanel(casillasAbajo, casillasPorAspa, columnasPorAspa, TableroBuilder.TipoTablero.ABAJO);
        configurarPanel(casillasIzq, columnasPorAspa, casillasPorAspa, TableroBuilder.TipoTablero.IZQUIERDA);
        configurarPanel(casillasDer, columnasPorAspa, casillasPorAspa, TableroBuilder.TipoTablero.DERECHA);
        configurarPanel(casillasCentrales, 2, 2, TableroBuilder.TipoTablero.CENTRAL); // Mantén el central como 2x2
    }
    List<Integer> casillasAmarillas;

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
        for (Integer c : tablero.getCasillasAmarillas()) {
            casillasAmarillas.add(c);
        }
    }

    public List<Integer> getCasillasAmarillas() {
        return casillasAmarillas;
    }

    public void setCasillasAmarillas(List<Integer> casillasAmarillas) {
        this.casillasAmarillas = casillasAmarillas;
    }

    public int moverFicha(int posicionActual, int pasos, String colorFicha) {
        // Asegurarse de que la posición está dentro del rango válido
        if (posicionActual >= 0 && posicionActual <= casillas.size()) {
            // Calcula la nueva posición
            int nuevaPosicion = posicionActual + pasos;
            if(nuevaPosicion==posicionActual){
                return posicionActual;
            }
            int antiguaPosicion = 0;
            if(nuevaPosicion>casillas.size()){
                nuevaPosicion=nuevaPosicion-casillas.size();
            }
            for (int i = 0; i < casillas.size(); i++) {
                if(casillas.get(i).getIcon()==null){
                    continue;
                }else if (casillas.get(i).getText().equalsIgnoreCase(""+posicionActual)) {
                    antiguaPosicion=i;
                }
            }

            for (JLabel c : casillas) {
                if (c.getText().equalsIgnoreCase("" + nuevaPosicion)) {
                    c.setIcon(obtenerImagenFicha(colorFicha));
                    c.setHorizontalAlignment(JLabel.CENTER);
                    c.setVerticalAlignment(JLabel.CENTER);
                    casillas.get(antiguaPosicion).setIcon(null);
                    repaint();
                    return nuevaPosicion;
                }
            }
//            // Limpia la casilla anterior (quita solo la imagen)
//            JLabel casillaAnterior = casillas.get(posicionActual);
//            casillaAnterior.setIcon(null);
//
//            // Marca la nueva casilla con la imagen correspondiente
//            JLabel nuevaCasilla = casillas.get(nuevaPosicion);
//            nuevaCasilla.setIcon(obtenerImagenFicha(colorFicha));
//            nuevaCasilla.setHorizontalAlignment(JLabel.CENTER);
//            nuevaCasilla.setVerticalAlignment(JLabel.CENTER);
//            repaint();

//        // Opcional: lógica adicional según tipo de casilla
//        if (nuevaCasilla.getBackground().equals(Color.RED)) {
//            System.out.println("¡Detente en la casilla roja!");
//        }
//        if (nuevaCasilla.getBackground().equals(Color.YELLOW)) {
//            System.out.println("¡Casilla amarilla, juega de nuevo!");
//        }
        }
        return 0;
    }
    
    public void colocarFichaInicial(int posicionInical,String color){
        for (JLabel c : casillas) {
                if (c.getText().equalsIgnoreCase("" + posicionInical)) {
                    c.setIcon(obtenerImagenFicha(color));
                    c.setHorizontalAlignment(JLabel.CENTER);
                    c.setVerticalAlignment(JLabel.CENTER);
                    repaint();
                }
            }
    }

    private ImageIcon obtenerImagenFicha(String colorFicha) {
        String ruta = switch (colorFicha.toLowerCase()) {
            case "naranja" ->
                "img/FichaNaranja.png";
            case "roja" ->
                "img/FichaRoja.png";
            case "amarilla" ->
                "img/FichaAmarilla.png";
            case "morada" ->
                "img/FichaMorada.png";
            default ->
                null;
        };
        if (ruta != null) {
            return new ImageIcon(getClass().getClassLoader().getResource(ruta));
        } else {
            System.err.println("Color de ficha no reconocido: " + colorFicha);
            return null;
        }
    }

    public List<JLabel> getCasillas() {
        return casillas;
    }

    public void setCasillas(List<JLabel> casillas) {
        this.casillas = casillas;
    }

    public void actualizarPosicionFicha(int jugador, int posicion) {
        JLabel casilla = casillas.get(posicion); // Obtén la casilla por posición
        casilla.setIcon(new ImageIcon("/img/fichaJugador" + jugador + ".png")); // Cambia el ícono de la casilla
    }
}
