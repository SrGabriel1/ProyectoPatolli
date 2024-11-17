package tablero;

import java.awt.Color;
import java.io.Serializable;

public class TableroExtraGrandeBuilder extends TableroBuilder implements Serializable{

    @Override
    public void establecerColor() {
        this.tablero.setColor(Color.red);
    }

    @Override
    public void establecerCasillas() {
        this.tablero.setCasillasAspa(16);
    }
    
}
