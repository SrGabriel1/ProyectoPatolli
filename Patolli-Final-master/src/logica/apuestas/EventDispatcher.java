package logica.apuestas;

import java.io.Serializable;
import logica.Jugador;
import logica.Reglamento;

public class EventDispatcher implements Serializable{
    
    private PagarEventListener listener;
    
    /**
     * Registra un escuchador de eventos
     * 
     * @param listener el escuchador de eventos a registrar
     */
    public void registrarListener(PagarEventListener listener){
        this.listener = listener;
    }
    
    /**
     * Activa el evento de todos los escuchadores
     * 
     * @param cantidad la cantidad de dinero a pagar
     * @param jugador el jugador que debe pagar la apuesta
     * @return el juego con la info de las economías actualizada
     */
    public Reglamento despacharEvento(float cantidad, Jugador jugador){
        this.listener.onPagar(new EventPagar(cantidad), jugador);
        return this.listener.getJuego();
    }
    
    /**
     * Envia info de juego actualizado a todos los escuchadores
     * 
     * @param juego el juego con la infor actualizada
     */
    public void actualizarListener(Reglamento juego){
        this.listener.setJuego(juego);
    }
    
}
