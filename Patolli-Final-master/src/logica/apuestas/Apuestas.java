package logica.apuestas;

import java.io.Serializable;
import javax.swing.JOptionPane;
import logica.Jugador;
import logica.Reglamento;

public class Apuestas implements Serializable{

    private PagarEventListener escuchador;
    private EventDispatcher despachcador;
    private Reglamento juego;
    private float porcentajeApuesta, fondoInicial, porcentaje;

    public Apuestas(Reglamento juego, float fondoInicial) {
        this.juego = juego;
        this.escuchador = new PagarEventListener(juego);
        this.despachcador = new EventDispatcher();
        this.despachcador.registrarListener(escuchador);
        this.fondoInicial = fondoInicial;
    }

    /**
     * Paga una apuesta a todos lo jugadores y se le resta al que paga, si el
     * jugador que paga se queda sin dinero, es eliminado
     *
     * @param jugador el jugador que debe de pagar la apuesta
     */
    public void pagarApuesta(Jugador jugador) {
        this.despachcador.actualizarListener(juego);
        juego = this.despachcador.despacharEvento(porcentajeApuesta, jugador);
    }

    /**
     * Establece que porcentaje del dinero que tiene cada jugador al iniciar el
     * juego se paga en cada apuesta
     *
     * @param porcentajeApuesta porcentaje que se pagará en cada apuesta que va
     * desde un minimo del 5% hasta un máximo del 30%
     */
    public void establecerPorcentajeApuesta(float porcentajeApuesta) {
        if (!(porcentajeApuesta >= 5 && porcentajeApuesta <= 30)) {
            JOptionPane.showMessageDialog(null, "El porcentaje debe ser minimo de 10% y máximo de 30%"
                    + "", "Error!!", 0);
            return;
        }
        this.porcentaje = porcentajeApuesta;
        this.porcentajeApuesta = fondoInicial*(porcentajeApuesta/100);
    }

    public float getPorcentajeApuesta() {
        return porcentajeApuesta;
    }

    public Reglamento getJuego() {
        return juego;
    }

    public void setJuego(Reglamento juego) {
        this.juego = juego;
    }

    public float getPorcentaje() {
        return porcentaje;
    }
    
}
