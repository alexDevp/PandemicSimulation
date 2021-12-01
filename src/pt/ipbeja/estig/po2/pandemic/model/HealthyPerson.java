//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;


public class HealthyPerson extends Person {
    private Color color = Color.GREEN;
    private boolean infected = false;
    private boolean immune = false;


    public HealthyPerson(CellPosition cellPosition, int rectangleCode) {
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

    @Override
    public boolean isImmune() {
        return immune;
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

