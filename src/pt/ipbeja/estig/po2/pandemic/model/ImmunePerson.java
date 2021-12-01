//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public abstract class ImmunePerson extends Person {

    private boolean immune = true;

    public ImmunePerson(CellPosition cellPosition, int rectangleCode) {
        super(cellPosition, rectangleCode);
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
