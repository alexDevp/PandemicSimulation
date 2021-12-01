//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public class SickPersonSymptoms extends SickPerson {
    private boolean symptoms = true;
    private Color color = Color.RED;

    public SickPersonSymptoms(CellPosition cellPosition, int rectangleCode, int chance, int minTimeSick, int maxTimeSick) {
        super(cellPosition, rectangleCode, chance, minTimeSick, maxTimeSick);
    }

    @Override
    public boolean hasSymptoms() {
        return symptoms;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
