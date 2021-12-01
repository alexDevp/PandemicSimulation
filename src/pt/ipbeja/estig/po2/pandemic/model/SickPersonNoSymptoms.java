//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public class SickPersonNoSymptoms extends SickPerson {
    private boolean symptoms = false;
    private Color color = Color.ORANGE;

    public SickPersonNoSymptoms(CellPosition cellPosition, int rectangleCode, int chance, int minTimeSick, int maxTimeSick) {
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
