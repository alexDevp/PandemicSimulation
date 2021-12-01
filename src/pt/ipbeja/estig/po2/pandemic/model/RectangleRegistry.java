//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.shape.Rectangle;

public class RectangleRegistry {
    private int rectangleCod;
    private Rectangle rectangle;

    public RectangleRegistry(Rectangle rectangle, int rectangleCod) {
        this.rectangle = rectangle;
        this.rectangleCod = rectangleCod;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getRectangleCod() {
        return rectangleCod;
    }

}
