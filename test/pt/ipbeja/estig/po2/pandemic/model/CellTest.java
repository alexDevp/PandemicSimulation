//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.pandemic.gui.ContagiousBoard;
import pt.ipbeja.estig.po2.pandemic.gui.WorldBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    private final int nLines = 30;
    private final int nCols = 30;
    private final int nStartingPeople = 6;
    private int prob = 90; //Valor Default
    private int minSick = 200; //Valor Default
    private int maxSick = 400; //Valor Default
    WorldBoard pane;
    ContagiousBoard contagiousBoard;
    World world = new World(contagiousBoard, nLines, nCols, nStartingPeople, prob, minSick, maxSick);

    @Test
    void movementTestLeft() {
        assertEquals(false, world.checkLimits(0, -1));
    }

    @Test
    void movementTestRight() {
        assertEquals(false, world.checkLimits(0, 31));
    }

    @Test
    void movementTestUp() {
        assertEquals(false, world.checkLimits(-1, 0));
    }

    @Test
    void movementTestDown() {
        assertEquals(false, world.checkLimits(31, 0));
    }

    @Test
    void infectionInfected() {
        world.fillBoard();

        world.cellsBoard[1][1] = new SickPersonSymptoms(new CellPosition(1, 1), 1, this.prob, this.minSick, this.maxSick);
        world.cellsBoard[1][2] = new SickPersonSymptoms(new CellPosition(1, 2), 2, this.prob, this.minSick, this.maxSick);
        world.checkInfected(1, 1);
        assertEquals(true, world.cellsBoard[1][1].isInfected());
    }

    @Test
    void infectionHealthy() {
        world.fillBoard();
        world.cellsBoard[1][1] = new SickPersonSymptoms(new CellPosition(1, 1), 1, this.prob, this.minSick, this.maxSick);
        world.cellsBoard[1][2] = new HealthyPerson(new CellPosition(1, 2), 2);
        world.checkInfected(1, 2);
        assertEquals(true, world.cellsBoard[1][2].isInfected());
    }

    @Test
    void infectionImmune() {
        world.fillBoard();
        world.cellsBoard[1][1] = new SickPersonSymptoms(new CellPosition(1, 1), 1, this.prob, this.minSick, this.maxSick);
        world.cellsBoard[1][2] = new ImmunePersonNoInfectious(new CellPosition(1, 2), 2);
        world.checkInfected(1, 2);
        assertEquals(false, world.cellsBoard[1][2].isInfected());
    }

}
