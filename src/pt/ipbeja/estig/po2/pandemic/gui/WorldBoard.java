//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.gui;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pt.ipbeja.estig.po2.pandemic.model.CellPosition;
import pt.ipbeja.estig.po2.pandemic.model.RectangleRegistry;
import pt.ipbeja.estig.po2.pandemic.model.World;

import javafx.scene.paint.Color;

public class WorldBoard extends Pane {

    private Rectangle rectangle;
    private final int CELL_SIZE;
    private RectangleRegistry[] rectangles;
    private final int SPEED = 200;

    /**
     * Contrutor da classe WorldBoard
     * @param world
     * @param size
     * @param startingPeople
     */
    public WorldBoard(World world, int size, int startingPeople) {
        this.CELL_SIZE = size;
        this.rectangle = null;
        this.rectangles = new RectangleRegistry[startingPeople];

    }

    /**
     * Adiciona efeticamente o retangulo á grelha gráfica
     * @param position
     * @param color
     * @return
     */
    public Rectangle addRectangle(CellPosition position, Color color) {
        int line = position.getLine() * CELL_SIZE;
        int col = position.getCol() * CELL_SIZE;

        Rectangle r = new Rectangle(col, line, CELL_SIZE, CELL_SIZE);

        r.setFill(color);
        Platform.runLater(() -> {
            this.getChildren().add(r);
        });

        return r;
    }

    /**
     * Altera a posição do retangulo na grelha gráfica
     * JAVAFX-SmoothTransitions
     * @param dx
     * @param dy
     * @param RectangleCod
     */
    public void updatePosition(int dx, int dy, int RectangleCod) {
        updateRectangle(dy, dx, RectangleCod);

        TranslateTransition tt =
                new TranslateTransition(Duration.millis(SPEED), this.rectangle);

        tt.setToX(rectangle.getX());
        tt.setToY(rectangle.getY());
        tt.play();
    }

    /**
     * Adiciona o retangulo á grelha gráfica
     * @param position
     * @param color
     * @param RectangleCod
     */
    public void populate(CellPosition position, Color color, int RectangleCod) {
        this.rectangle = this.addRectangle(position, color);
        RectangleRegistry registry = new RectangleRegistry(rectangle, RectangleCod);
        rectangles[RectangleCod] = registry;
    }

    /**
     * Troca efetivamente a cor do retangulo na grelha gráfica
     * @param color
     * @param RectangleCod
     */
    public void infectRectangle(Color color, int RectangleCod) {
        for (int i = 0; i <= (rectangles.length - 1); i++) {
            if (rectangles[i].getRectangleCod() == RectangleCod) {
                this.rectangle = rectangles[i].getRectangle();
                this.rectangle.setFill(color);
            }
        }
    }

    /**
     * Troca efetivamente a cor do retalngulo na grelha
     * @param color
     * @param RectangleCod
     */
    public void healRectangle(Color color, int RectangleCod) {
        for (int i = 0; i <= (rectangles.length - 1); i++) {
            if (rectangles[i].getRectangleCod() == RectangleCod) {
                this.rectangle = rectangles[i].getRectangle();
                this.rectangle.setFill(color);

            }
        }
    }

    /**
     * Atualiza a lista temporaria de retangulos e atulaliza o retangulo
     * @param dx Coordenada linha
     * @param dy Coordenada Coluna
     * @param cod Código do retangulo
     * @return retangulo
     */
    public Rectangle updateRectangle(int dx, int dy, int cod) {

        for (int i = 0; i <= rectangles.length; i++) {
            if (rectangles[i].getRectangleCod() == cod) {
                this.rectangle = rectangles[i].getRectangle();
                this.rectangle.setLayoutX(this.rectangle.getX() + (dx * CELL_SIZE));
                this.rectangle.setLayoutY(this.rectangle.getY() + (dy * CELL_SIZE));
                rectangles[i] = new RectangleRegistry(this.rectangle, i);
                return rectangle;
            }
        }
        return null;
    }

}
