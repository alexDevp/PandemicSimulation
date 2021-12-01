//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public class ImmunePersonInfectious extends ImmunePerson {
    private Color color = Color.PURPLE;
    private boolean infected = true;

    public ImmunePersonInfectious(CellPosition cellPosition, int rectangleCode) {
        super(cellPosition, rectangleCode);
    }
    @Override
    public Color getColor() {
        return color;
    }
    @Override
    public boolean isInfected() {
        return infected;
    }

}
