//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public class ImmunePersonNoInfectious extends ImmunePerson {
    private Color color = Color.BLUE;
    private boolean infected = false;

    public ImmunePersonNoInfectious(CellPosition cellPosition, int rectangleCode) {
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
