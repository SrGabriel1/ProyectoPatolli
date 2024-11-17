package tablero;

import java.awt.Color;
import java.io.Serializable;

public class TableroMedianoBuilder extends TableroBuilder implements Serializable{

    @Override
    public void establecerColor() {
        this.tablero.setColor(Color.blue);
    }

    @Override
    public void establecerCasillas() {
        this.tablero.setCasillasAspa(12);
    }

}
