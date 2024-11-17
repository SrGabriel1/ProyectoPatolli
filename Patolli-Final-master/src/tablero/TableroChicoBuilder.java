package tablero;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class TableroChicoBuilder extends TableroBuilder implements Serializable {

    @Override
    public void establecerColor() {
        this.tablero.setColor(Color.green);
    }

    @Override
    public void establecerCasillas() {
        this.tablero.setCasillasAspa(10);
    }

}
