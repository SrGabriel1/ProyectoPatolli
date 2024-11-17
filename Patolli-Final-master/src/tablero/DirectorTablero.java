package tablero;

import java.io.Serializable;

public class DirectorTablero implements Serializable{
    
    private TableroBuilder tableroBuilder;
    
    /**
     * Establece el tipo de tablero contructor dado por parametro
     * @param tb El tipo de tablero constructor a construir
     */
    public void setTableroBuilder(TableroBuilder tb){
        tableroBuilder = tb;
    }
    
    /**
     * Construye el tablero con el tamaño dado por parametro
     * @param ancho el ancho del tablero
     * @param alto el alto del tablero
     */
    public void construirTablero(int ancho, int alto){
        tableroBuilder.crearTablero(ancho, alto);
        tableroBuilder.establecerCasillas();
        tableroBuilder.establecerColor();
    }
    
    /**
     * Obtiene el tablero ya construido
     * @return Objeto Tablero con las características pre-definidas
     */
    public Tablero getTablero(){
        return tableroBuilder.getTablero();
    }
    
}
