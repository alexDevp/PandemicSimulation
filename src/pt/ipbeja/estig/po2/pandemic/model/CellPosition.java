//Nome: Alexandre Pereira
//Nrº:  17678
package pt.ipbeja.estig.po2.pandemic.model;

public class CellPosition {
    private int line;
    private int col;

    //Construtor da Posição da Cell
    public CellPosition(int line, int col) {
        this.line = line;
        this.col = col;
    }

    //Devolve a linha da Posição da Cell
    public int getLine() {
        return line;
    }

    //Devolve a coluna da Posição da Cell
    public int getCol() {
        return col;
    }

}
