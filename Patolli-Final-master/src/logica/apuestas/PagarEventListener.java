package logica.apuestas;

import java.io.Serializable;
import java.util.EventListener;
import logica.Jugador;
import logica.Reglamento;

public class PagarEventListener implements EventListener, Serializable{

    private Reglamento juego;

    public PagarEventListener(Reglamento juego) {
        this.juego = juego;
    }

    /**
     * Actualiza la economía de todos los jugadores, pagando apuestas
     *
     * @param event evento pagar que contiene la info de la apuesta
     * @param jugador jugador que debe de realizar la apuesta
     */
    public void onPagar(EventPagar event, Jugador jugador) {
        //Se le agrega a todos los jugadores el dinero de la apuesta a
        //excepcion del jugador que paga
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            if (!juego.getJugadores().get(i).equals(jugador)) {
                juego.getJugadores().get(i).setDinero(juego.getJugadores().get(i).getDinero()
                        + (event.getCantidad() / (juego.getJugadores().size()-1)));
            }
        }

        //Se le descuenta al jugador que paga la respectiva apuesta
        juego.getJugadores().get(jugador.getNumJugador()-1).setDinero(
                juego.getJugadores().get(jugador.getNumJugador()-1).getDinero() - event.getCantidad());

        //Determina si el jugador debe ser eliminado en caso de que se quede sin
        //dinero
        if (juego.getJugadores().get(jugador.getNumJugador()-1).getDinero() <= 0) {
            juego.getJugadores().get(jugador.getNumJugador()-1).setEliminado(true);
        }
    }

    public void setJuego(Reglamento juego) {
        this.juego = juego;
    }

    public Reglamento getJuego() {
        return juego;
    }

}
