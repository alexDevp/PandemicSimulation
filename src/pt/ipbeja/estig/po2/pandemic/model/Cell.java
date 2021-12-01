//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public abstract class Cell {
    private CellPosition cellPosition;
    private int dx;
    private int dy;

    //Construtor na Cell
    public Cell(CellPosition cellPosition) {
        this.cellPosition = cellPosition;
    }

    //Devolve a Cor da Cell
    public abstract Color getColor();

    //Verifica se Cell não tem ninguem
    public abstract boolean isEmpty();

    //Verifica se é uma Cell infetada
    public abstract boolean isInfected();

    //Verifica se é uma Cell infetada
    public abstract boolean isImmune();

    //Verifica se Cell será curada
    public abstract boolean isHealed(int probability);

    //Verifica se tem sintomas
    public abstract boolean hasSymptoms();

    //Verifica rectangulo que representa a celula
    public abstract int getRectangleCode();

    //Define um movimento aleatório dentro dos limites da grelha para a Cell andar
    public void randomMove() {

        //array para usar na probabilidade de fazer a Cell mudar de direção
        final int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        //array para decidir as direções em que a Cell se vai mexer
        final int[] v = {-1, 0, 1};

        //Entra na condição para mudar de direção se o número aleatorio que calhar no array for menor ou igual a 1
        if (a[World.rand.nextInt(10)] <= 1) {
            this.dx = v[World.rand.nextInt(3)];
            this.dy = v[World.rand.nextInt(3)];

            // para forçar um movimento caso os valores fiquem a 0
            if (dx == 0 && dy == 0) {
                dx = 1;
            }
        }
    }

    public void applyMove() {
        //muda os valores da posição
        this.cellPosition = new CellPosition(this.cellPosition.getLine() + dx, this.cellPosition.getCol() + dy);
    }

    //Devolve a o Objeto com a posição da Cell
    public CellPosition cellPosition() {
        return cellPosition;
    }

    //Devolve o numero de espaços na horizontal que a Cell se movimenta
    public int dx() {
        return this.dx;
    }

    //Devolve o numero de espaços na vertical que a Cell se movimenta
    public int dy() {
        return this.dy;
    }
}
