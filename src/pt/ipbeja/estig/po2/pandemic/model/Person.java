//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

public abstract class Person extends Cell {
    private boolean empty = false;
    private int rectangleCode;

    public Person(CellPosition cellPosition, int rectangleCode) {
        super(cellPosition);
        this.rectangleCode = rectangleCode;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public int getRectangleCode() {
        return rectangleCode;
    }
}
