package Tablero;

import Tablero.TableroBuilder;
import Entidades.Ficha;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PatolliPanel extends JPanel {

    private List<JLabel> casillas;
    private JPanel casillasArriba;
    private JPanel casillasAbajo;
    private JPanel casillasIzq;
    private JPanel casillasDer;
    private JPanel casillasCentrales;
    private int jugadores;
    private int imagenAncho;
    private int imagenAlto;
    private List<List<Ficha>> fichasJugadores;

    /**
     * Constructor que inicializa el tablero de juego.
     *
     * @param jugadores Número de jugadores en el juego.
     * @param imagenAncho Ancho de la imagen de fondo del tablero.
     * @param imagenAlto Alto de la imagen de fondo del tablero.
     * @throws Exception Si el número de jugadores no es válido.
     */
    public PatolliPanel(int jugadores, int imagenAncho, int imagenAlto) throws Exception {
        this.jugadores = jugadores;
        this.fichasJugadores = new ArrayList<>();
        for (int i = 0; i < jugadores; i++) {
            this.fichasJugadores.add(new ArrayList<>());
        }

        this.casillas = new ArrayList<>();
        this.imagenAlto = imagenAlto;
        this.imagenAncho = imagenAncho;
        this.casillasAmarillas = new ArrayList<>();

        setPreferredSize(new Dimension(imagenAncho, imagenAlto));

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inicializarPaneles();
        configurarTablero();
    }

    /**
     * Inicializa los paneles en el layout absoluto.
     */
    private void inicializarPaneles() {
        casillasArriba = new JPanel();
        casillasAbajo = new JPanel();
        casillasIzq = new JPanel();
        casillasDer = new JPanel();
        casillasCentrales = new JPanel();

        int centerX = (imagenAncho / 2) - 50;
        int centerY = (imagenAlto / 2) - 50;

        casillasArriba.setOpaque(false);
        casillasAbajo.setOpaque(false);
        casillasIzq.setOpaque(false);
        casillasDer.setOpaque(false);
        casillasCentrales.setOpaque(false);

        add(casillasArriba, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX, // x
                centerY - 240, // y
                100, // ancho
                240 // alto
        ));
        revalidate();
        repaint();
        add(casillasAbajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX, // x
                centerY + 100, // y
                100, // ancho
                240 // alto
        ));
        revalidate();
        repaint();
        add(casillasIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX - 250, // x
                centerY, // y
                250, // ancho
                100 // alto
        ));
        revalidate();
        repaint();
        add(casillasDer, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                centerX + 100, // x
                centerY, // y
                250, // ancho
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
     * Configura el tablero basado en el número de jugadores.
     *
     * @throws Exception Si el número de jugadores no es válido.
     */
    private void configurarTablero() throws Exception {
        int casillasPorAspa = switch (jugadores) {
            case 2 ->
                8;
            case 3 ->
                10;
            case 4 ->
                14;
            default ->
                throw new Exception("Número de jugadores inválido");
        };
        int columnasPorAspa = 2;

        configurarPanel(casillasArriba, casillasPorAspa, columnasPorAspa, TableroBuilder.TipoTablero.ARRIBA);
        configurarPanel(casillasAbajo, casillasPorAspa, columnasPorAspa, TableroBuilder.TipoTablero.ABAJO);
        configurarPanel(casillasIzq, columnasPorAspa, casillasPorAspa, TableroBuilder.TipoTablero.IZQUIERDA);
        configurarPanel(casillasDer, columnasPorAspa, casillasPorAspa, TableroBuilder.TipoTablero.DERECHA);
        configurarPanel(casillasCentrales, 2, 2, TableroBuilder.TipoTablero.CENTRAL);
    }
    List<Integer> casillasAmarillas;

    /**
     * Configura un panel específico del tablero.
     *
     * @param panel Panel a configurar.
     * @param filas Número de filas en el panel.
     * @param columnas Número de columnas en el panel.
     * @param tipo Tipo de tablero asociado al panel.
     */
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

    /**
     * Devuelve las posiciones de las casillas amarillas.
     *
     * @return Lista de posiciones de casillas amarillas.
     */
    public List<Integer> getCasillasAmarillas() {
        return casillasAmarillas;
    }

    /**
     * Establece las posiciones de las casillas amarillas.
     *
     * @param casillasAmarillas Lista de posiciones de casillas amarillas.
     */
    public void setCasillasAmarillas(List<Integer> casillasAmarillas) {
        this.casillasAmarillas = casillasAmarillas;
    }

    /**
     * Mueve una ficha a una nueva posición en el tablero.
     *
     * @param posicionActual Posición actual de la ficha.
     * @param pasos Número de pasos a mover.
     * @param colorFicha Color de la ficha.
     * @return Nueva posición de la ficha.
     */
    public int moverFicha(int posicionActual, int pasos, String colorFicha) {
        // Asegurarse de que la posición está dentro del rango válido
        if (posicionActual >= 0 && posicionActual <= casillas.size()) {
            // Calcula la nueva posición
            int nuevaPosicion = posicionActual + pasos;
            if (nuevaPosicion == posicionActual) {
                return posicionActual;
            }
            int antiguaPosicion = 0;
            if (nuevaPosicion > casillas.size()) {
                nuevaPosicion = nuevaPosicion - casillas.size();
            }
            for (int i = 0; i < casillas.size(); i++) {
                if (casillas.get(i).getIcon() == null) {
                    continue;
                } else if ((int) casillas.get(i).getClientProperty("numero") == (posicionActual)) {
                    antiguaPosicion = i;
                }
            }

            for (JLabel c : casillas) {
                if ((int) c.getClientProperty("numero") == (nuevaPosicion)) {
                    c.setIcon(obtenerImagenFicha(colorFicha));
                    c.setHorizontalAlignment(JLabel.CENTER);
                    c.setVerticalAlignment(JLabel.CENTER);
                    casillas.get(antiguaPosicion).setIcon(null);
                    repaint();
                    return nuevaPosicion;
                }
            }
        }
        return 0;
    }

    /**
     * Remueve una ficha de una posición específica.
     *
     * @param posicion Posición de la ficha a remover.
     */
    public void removerFicha(int posicion) {
        for (JLabel c : casillas) {
            if ((int) c.getClientProperty("numero") == (posicion)) {
                c.setIcon(null);
                repaint();
            }
        }
    }

    /**
     * Coloca una ficha inicial en una posición específica.
     *
     * @param posicionInical Posición inicial de la ficha.
     * @param color Color de la ficha.
     */
    public void colocarFichaInicial(int posicionInical, String color) {
        for (JLabel c : casillas) {
            if ((int) c.getClientProperty("numero") == (posicionInical)) {
                c.setIcon(obtenerImagenFicha(color));
                c.setHorizontalAlignment(JLabel.CENTER);
                c.setVerticalAlignment(JLabel.CENTER);
                repaint();
            }
        }
    }

    /**
     * Devuelve una imagen representando la ficha de un jugador.
     *
     * @param colorFicha Color de la ficha.
     * @return Icono de la ficha o null si el color no es válido.
     */
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

    /**
     * Devuelve la lista de casillas del tablero.
     *
     * @return Lista de casillas representadas como JLabel.
     */
    public List<JLabel> getCasillas() {
        return casillas;
    }

    /**
     * Establece la lista de casillas del tablero.
     *
     * @param casillas Lista de casillas a establecer.
     */
    public void setCasillas(List<JLabel> casillas) {
        this.casillas = casillas;
    }

    /**
     * Actualiza la posición de una ficha en el tablero.
     *
     * @param jugador Número del jugador que mueve la ficha.
     * @param posicion Nueva posición de la ficha.
     */
    public void actualizarPosicionFicha(int jugador, int posicion) {
        JLabel casilla = casillas.get(posicion); // Obtén la casilla por posición
        casilla.setIcon(new ImageIcon("/img/fichaJugador" + jugador + ".png")); // Cambia el ícono de la casilla
        repaint(); // Asegura que el cambio sea visible
        revalidate();
    }
}
