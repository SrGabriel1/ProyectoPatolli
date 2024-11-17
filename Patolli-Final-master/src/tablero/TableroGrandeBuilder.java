package tablero;

import java.awt.Color;
import java.io.Serializable;

public class TableroGrandeBuilder extends TableroBuilder implements Serializable{

    @Override
    public void establecerColor() {
        this.tablero.setColor(Color.orange);
    }

    @Override
    public void establecerCasillas() {
        this.tablero.setCasillasAspa(14);
    }

}
