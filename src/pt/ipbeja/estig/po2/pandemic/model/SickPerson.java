//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public abstract class SickPerson extends Person {

    private int counter = 0;
    private int maxTimeSick;
    private int minTimeSick;
    private int chance;
    private boolean infected = true;
    private boolean immune = false;

    public SickPerson(CellPosition cellPosition, int rectangleCode, int chance, int minTimeSick, int maxTimeSick) {
        super(cellPosition, rectangleCode);
        this.chance = chance;
        this.minTimeSick = minTimeSick;
        this.maxTimeSick = maxTimeSick;
    }

    @Override
    public boolean isInfected() {
        return infected;
    }

    @Override
    public boolean isImmune() {
        return immune;
    }

    /**
     * Verifica se a cell se curou ou não
     * @param probability Numero aleatório gerado ateriormente
     * @return
     */
    @Override
    public boolean isHealed(int probability) {
        counter++;
        if (maxTimeSick == counter || (probability > chance && counter > minTimeSick)) {
            return true;
        }
        return false;
    }

}
