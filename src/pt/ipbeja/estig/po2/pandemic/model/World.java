//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.model;

import java.util.List;
import java.util.Random;

public class World {
    public static final Random rand = new Random();

    private View view;
    private final int nLines;
    private final int nCols;
    private final int prob;
    private final int minSick;
    private final int maxSick;
    private boolean STOP = false;
    final int[] v = {-1, 0, 1};
    int rectangleCode = 0;
    int rectangleCounter = 0;
    int nextLine;
    int nextCol;

    private final int nStartingPeople;
    public Cell[][] cellsBoard;

    private final int INFECTED = 5;

    public World(View view, int nLines, int nCols, int nStartingPeople, int prob, int minSick, int maxSick) {
        this.view = view;
        this.nLines = nLines;
        this.nCols = nCols;
        this.nStartingPeople = nStartingPeople;
        this.prob = prob;
        this.minSick = minSick;
        this.maxSick = maxSick;
        this.cellsBoard = new Cell[nLines][nCols];
    }

    /**
     * Inicia o simulador
     */
    public void start() {
        new Thread(() -> {
            populate();
            while (!STOP) {
                simulate();
            }
            STOP = true;
        }).start();
    }

    /**
     * Começa o jogo a partir de os dados recebidos do ficheiro
     * @param healthy Lista de pessoas Saudaveis
     * @param immuneNotContagious   Lista de Pessoas Imunes nao contagiosas
     * @param immuneContagious  Lista de Pessoas Imunes Contagiosas
     * @param sickSymptoms  Lista de Pessoas Doentes com Sintomas
     * @param sickNoSymptoms    Lista de Pessoas Doentes Sem sintomas
     */
    public void startFile(List<CellPosition> healthy, List<CellPosition> immuneNotContagious, List<CellPosition> immuneContagious, List<CellPosition> sickSymptoms, List<CellPosition> sickNoSymptoms ) {
        new Thread(() -> {
            populateFromFile(healthy, immuneNotContagious, immuneContagious, sickSymptoms, sickNoSymptoms);
            while (!STOP) {
                simulate();
            }
            STOP = true;
        }).start();
    }

    /**
     * Pára a simulação
     */
    public void stop() {
        STOP = true;
    }

    /**
     * Preenche a grelha com EmptyCells
     */
    public void fillBoard() {
        for (int l = 0; l < nLines; l++) {
            for (int c = 0; c < nCols; c++) {
                cellsBoard[l][c] = new EmptyCell(new CellPosition(l, c));
            }
        }
    }

    /**
     * Preenche a população Infetada de forma aleatória
     */
    public void populateInfected() {
        int counterInfected = 0;

        do {
            int l = rand.nextInt(nLines - 1);
            int c = rand.nextInt(nCols - 1);
            if (cellsBoard[l][c].isEmpty()) {
                int r = rand.nextInt(10);
                if (r<=4){
                    cellsBoard[l][c] = new SickPersonNoSymptoms(new CellPosition(l, c), rectangleCounter, this.prob, this.minSick, this.maxSick);
                }else if(r>4){
                    cellsBoard[l][c] = new SickPersonSymptoms(new CellPosition(l, c), rectangleCounter, this.prob, this.minSick, this.maxSick);
                }

                view.populate(cellsBoard[l][c].cellPosition(), cellsBoard[l][c].getColor(), rectangleCounter);
                rectangleCounter++;
                counterInfected++;
            }

        } while (counterInfected < INFECTED);
    }
    /**
     * Preenche a população Saudavel de forma aleatória
     */
    public void populateNotInfected() {
        int notInfected = nStartingPeople - INFECTED;
        int counterNotInfected = 0;

        do {
            int l = rand.nextInt(nLines);
            int c = rand.nextInt(nCols);
            if (cellsBoard[l][c].isEmpty()) {

                cellsBoard[l][c] = new HealthyPerson(new CellPosition(l, c), rectangleCounter);
                view.populate(cellsBoard[l][c].cellPosition(), cellsBoard[l][c].getColor(), rectangleCounter);
                rectangleCounter++;
                counterNotInfected++;
            }
        } while (counterNotInfected < notInfected);
    }

    /**
     * Preenche a população Saudavel a partir da lista recebida
     * @param healthy Lista das coordenadas das pessoas saudaveis
     */
    public void populateHealthyFromFile(List<CellPosition> healthy) {
        int notInfected = healthy.size();
        int l = 0;
        int c = 0;
        for (int i = 0; i < notInfected ; i++) {
            l = healthy.get(i).getLine();
            c = healthy.get(i).getCol();
            cellsBoard[l][c] = new HealthyPerson(new CellPosition(l, c), rectangleCounter);
            view.populate(cellsBoard[l][c].cellPosition(), cellsBoard[l][c].getColor(), rectangleCounter);
            rectangleCounter++;
        }
    }

    /**
     * Preenche a população imune e não contagiosa a partir da lista recebida
     * @param immuneNotContagious Lista das coordenadas das pessoas imunes não contagiosas
     */

    private void populateImmuneNotContagiousFromFile(List<CellPosition> immuneNotContagious) {
        int immuneCount = immuneNotContagious.size();
        int l = 0;
        int c = 0;
        for (int i = 0; i < immuneCount ; i++) {
            l = immuneNotContagious.get(i).getLine();
            c = immuneNotContagious.get(i).getCol();
            cellsBoard[l][c] = new ImmunePersonNoInfectious(new CellPosition(l, c), rectangleCounter);
            view.populate(cellsBoard[l][c].cellPosition(), cellsBoard[l][c].getColor(), rectangleCounter);
            rectangleCounter++;
        }
    }

    /**
     * Preenche a população imune e contagiosa a partir da lista recebida
     * @param immuneContagious Lista das coordenasdas das pessoas imunes contagiosas
     */
    private void populateImmuneContagiousFromFile(List<CellPosition> immuneContagious) {
        int immuneCount = immuneContagious.size();
        int l = 0;
        int c = 0;
        for (int i = 0; i < immuneCount ; i++) {
            l = immuneContagious.get(i).getLine();
            c = immuneContagious.get(i).getCol();
            cellsBoard[l][c] = new ImmunePersonInfectious(new CellPosition(l, c), rectangleCounter);
            view.populate(cellsBoard[l][c].cellPosition(), cellsBoard[l][c].getColor(), rectangleCounter);
            rectangleCounter++;
        }
    }

    /**
     * Preenche a população doente sem sintomas
     * @param sickNoSymptoms Lista das coordenadas das pessoas doentes sem sintomas
     */
    private void populateSickNoSymptomsFromFile(List<CellPosition> sickNoSymptoms) {
        int counterInfected = sickNoSymptoms.size();
        int l = 0;
        int c = 0;
        for (int i = 0; i < counterInfected ; i++) {
            l = sickNoSymptoms.get(i).getLine();
            c = sickNoSymptoms.get(i).getCol();

            cellsBoard[l][c] = new SickPersonNoSymptoms(new CellPosition(l, c), rectangleCounter, this.prob, this.minSick, this.maxSick);
            view.populate(cellsBoard[l][c].cellPosition(), cellsBoard[l][c].getColor(), rectangleCounter);
            rectangleCounter++;
        }
    }

    /**
     * Preenche a população doente com sintomas
     * @param sickSymptoms Lista das coordenadas das pessoas doentes com sintomas
     */
    private void populateSickSymptomsFromFile(List<CellPosition> sickSymptoms) {
        int counterInfected = sickSymptoms.size();
        int l = 0;
        int c = 0;
        for (int i = 0; i < counterInfected ; i++) {
            l = sickSymptoms.get(i).getLine();
            c = sickSymptoms.get(i).getCol();
            cellsBoard[l][c] = new SickPersonSymptoms(new CellPosition(l, c), rectangleCounter, this.prob, this.minSick, this.maxSick);
            view.populate(cellsBoard[l][c].cellPosition(), cellsBoard[l][c].getColor(), rectangleCounter);
            rectangleCounter++;
        }
    }

    /**
     * Popula a grelha com os métodos aleatórios
     */
    private void populate() {
        fillBoard();
        populateInfected();
        populateNotInfected();
    }

    /**
     * Popula a grelha com os métodos com a informação dos ficheiros
     * @param healthy Lista das coordenadas das saudaveis
     * @param immuneNotContagious Lista das coordenadas das pessoas imunes não contagiosas
     * @param immuneContagious Lista das coordenadas das pessoas imunes contagiosas
     * @param sickSymptoms Lista das coordenadas das pessoas doentes com sintomas
     * @param sickNoSymptoms Lista das coordenadas das pessoas doentes sem sintomas
     */
    private void populateFromFile(List<CellPosition> healthy, List<CellPosition> immuneNotContagious, List<CellPosition> immuneContagious, List<CellPosition> sickSymptoms, List<CellPosition> sickNoSymptoms) {
        fillBoard();
        populateHealthyFromFile(healthy);
        populateSickSymptomsFromFile(sickSymptoms);
        populateSickNoSymptomsFromFile(sickNoSymptoms);
        populateImmuneContagiousFromFile(immuneContagious);
        populateImmuneNotContagiousFromFile(immuneNotContagious);
    }

    /**
     * Gera uma String com os dados do que está na grelha no momento
     * @return String com os dados
     */
    public String getSave() {
        StringBuilder save = new StringBuilder();

        save.append(nLines);
        save.append("\n");
        save.append(nCols);
        save.append("\n");
        save.append(prob);
        save.append("\n");
        save.append(minSick);
        save.append("\n");
        save.append(maxSick);
        save.append("\n");
        save.append("healthy");

        for (int l = 0; l < nLines; l++) {
            for (int c = 0; c < nCols; c++) {

                if(!cellsBoard[l][c].isInfected() && !cellsBoard[l][c].isImmune() && !cellsBoard[l][c].isEmpty() ){

                    save.append("\n" + l + " " + c);
                }

            }
        }
        save.append("\n");
        save.append("immuneNotContagious");
        for (int l = 0; l < nLines; l++) {
            for (int c = 0; c < nCols; c++) {

                if(cellsBoard[l][c].isImmune() && !cellsBoard[l][c].isInfected()){
                    save.append("\n" + l + " " + c);
                }

            }
        }
        save.append("\n");
        save.append("immuneContagious");
        for (int l = 0; l < nLines; l++) {
            for (int c = 0; c < nCols; c++) {

                if(cellsBoard[l][c].isImmune() && cellsBoard[l][c].isInfected()){
                    save.append("\n" + l + " " + c);
                }

            }
        }
        save.append("\n");
        save.append("sickSymptoms");
        for (int l = 0; l < nLines; l++) {
            for (int c = 0; c < nCols; c++) {

                if(cellsBoard[l][c].isInfected() && cellsBoard[l][c].hasSymptoms() ){
                    save.append("\n" + l + " " + c);
                }

            }
        }
        save.append("\n");
        save.append("sickNoSymptoms");
        for (int l = 0; l < nLines; l++) {
            for (int c = 0; c < nCols; c++) {

                if(cellsBoard[l][c].isInfected() && !cellsBoard[l][c].hasSymptoms() && !cellsBoard[l][c].isImmune()){
                    save.append("\n" + l + " " + c);
                }

            }
        }

        return save.toString();
    }

    /**
     * Começa a simulação de contágios e cura dentro dos limites da grelha
     */
    private void simulate() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int line = 0; line < nLines; line++) {
            for (int col = 0; col < nCols; col++) {

                if (!cellsBoard[line][col].isEmpty()) {
                    cellsBoard[line][col].randomMove();
                    nextLine = line + cellsBoard[line][col].dx();
                    nextCol = col + cellsBoard[line][col].dy();

                    if (checkLimits(nextLine, nextCol)) {
                        if (cellsBoard[nextLine][nextCol].isEmpty()) {
                            this.view.updatePosition(cellsBoard[line][col].dx(), cellsBoard[line][col].dy(), cellsBoard[line][col].getRectangleCode());
                            cellsBoard[line][col].applyMove();
                            if (checkInfected(line, col)) {
                                infectRectangle(line, col);
                            }
                            checkHealed(line, col);
                        }
                    }
                }
            }
        }
    }

    /**
     * Verifica que as Persons não saem dos limites da grelha
     * @param nextLine Linha seguinte
     * @param nextCol Coluna seguinte
     * @return de sim ou nao (se está dentro dos limites)
     */
    public boolean checkLimits(int nextLine, int nextCol) {

        if ((nextLine) < nLines && nextCol < nCols) {
            if (nextCol > -1 && nextLine > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se a Person se encontrou com outra infetada quando se moveu e chama o método para infetar
     * @param line Linha da posição
     * @param col Coluna da posição
     * @return devolve se infetou ou nao
     */
    public boolean checkInfected(int line, int col) {
        for (int counter = 0; counter < v.length; counter++) {
            for (int counter2 = 0; counter2 < v.length; counter2++) {
                if (checkLimits((line + v[counter]), (col + v[counter2]))) {
                    if (cellsBoard[(line + v[counter])][(col + v[counter2])].isInfected()) {
                        if (!cellsBoard[line][col].isImmune() && !cellsBoard[line][col].isInfected() ) {
                            if (counter != 0 || counter2 != 0) {
                                infectCell(line, col);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método para infetar a Person, este método apaga a cell saudavel e cria uma doente no seu lugar
     * @param line
     * @param col
     * @return
     */
    public boolean infectCell(int line, int col) {
        rectangleCode = cellsBoard[line][col].getRectangleCode();
        int r = rand.nextInt(10);
        if (r<=4){
            cellsBoard[line][col] = new SickPersonNoSymptoms(new CellPosition(line, col), rectangleCode, this.prob, this.minSick, this.maxSick);
        }else if(r>4){
            cellsBoard[line][col] = new SickPersonSymptoms(new CellPosition(line, col), rectangleCode, this.prob, this.minSick, this.maxSick);
        }
        return true;
    }

    /**
     * Chama o método para infetar a cell graficamente
     * @param line Linha das coordenadas
     * @param col Coluna das coordenadas
     */
    public void infectRectangle(int line, int col) {
        view.infectRectangle(cellsBoard[line][col].getColor(), rectangleCode);
    }

    /**
     * Método verifica se a Person se curou ou não
     * @param line Linha das coordenadas
     * @param col Coluna das coordenadas
     * @return devolve se a Persons e curou ou não (boolean)
     */
    public boolean checkHealed(int line, int col) {
        if (healCell(line, col)) {
            rectangleCode = cellsBoard[line][col].getRectangleCode();

            int r = rand.nextInt(10);
            if (r<=4){
                cellsBoard[line][col] = new ImmunePersonInfectious(new CellPosition(line, col), rectangleCode);
            }else if(r>4){
                cellsBoard[line][col] = new ImmunePersonNoInfectious(new CellPosition(line, col), rectangleCode);
            }
            view.healRectangle(cellsBoard[line][col].getColor(), rectangleCode);
            return true;
        }
        return false;
    }

    /**
     * Método que gera o número aleatório para ver se a Person se conseguiu curar
     * @param line
     * @param col
     * @return
     */
    public boolean healCell(int line, int col) {
        if (cellsBoard[line][col].isHealed(rand.nextInt(100))) {
            return true;
        }
        return false;
    }

}
