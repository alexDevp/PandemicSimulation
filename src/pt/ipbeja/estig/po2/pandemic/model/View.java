//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import javafx.scene.paint.Color;

public interface View {

    void populate(CellPosition position, Color color, int rectangleCounter);

    void infectRectangle(Color color, int rectangleCounter);

    void healRectangle(Color color, int rectangleCounter);

    void updatePosition(int dx, int dy, int rectangleCod);
}
