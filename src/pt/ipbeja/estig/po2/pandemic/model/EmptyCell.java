//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public class EmptyCell extends Cell {
    private boolean empty = true;
    private Color color = null;

    public EmptyCell(CellPosition cellPosition) {
        super(cellPosition);

    }

    public boolean isEmpty() {
        return empty;
    }

    @Override
    public boolean isInfected() {
        return false;
    }

    @Override
    public boolean isImmune() {
        return false;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getRectangleCode() {
        return -1;
    }

    @Override
    public boolean isHealed(int probability) {
        return false;
    }

    @Override
    public boolean hasSymptoms() {
        return false;
    }
}
