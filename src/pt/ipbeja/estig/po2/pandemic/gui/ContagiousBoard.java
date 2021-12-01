//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pt.ipbeja.estig.po2.pandemic.model.CellPosition;
import pt.ipbeja.estig.po2.pandemic.model.View;
import pt.ipbeja.estig.po2.pandemic.model.World;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ContagiousBoard extends VBox implements View {
    public static World world;
    private WorldBoard pane;

    private int Width = 300;
    private int lines = 30;
    private int cols = 30;
    private final int SIZE = 10;
    private final int SCALE = 3; //Valor utilizado para corrigir erro de coordenadas
    private int nStartingPeople = 6; //Valor Default
    private int prob = 90; //Valor Default
    private int minSick = 200; //Valor Default
    private int maxSick = 400; //Valor Default
    private int chartvalue = 5;
    private List<CellPosition> healthy = new ArrayList<>();
    private List<CellPosition> immuneNotContagious = new ArrayList<>();
    private List<CellPosition> immuneContagious = new ArrayList<>();
    private List<CellPosition> sickSymptoms = new ArrayList<>();
    private List<CellPosition> sickNoSymptoms = new ArrayList<>();


    //JAVAFX GRAPH REFERENCE: https://docs.oracle.com/javafx/2/charts/bar-chart.htm#CIHIIIGE
    final static String infected = "Sick";
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String, Number> infectedBar = new BarChart<String, Number>(xAxis, yAxis);
    //JAVAFX GRAPH END

    /**
     * @param primaryStage
     * @param filePath     Construtor da Board - Onde se definem todas os TextFields, Labels, Buttons e as suas definições e eventos.
     */
    public ContagiousBoard(Stage primaryStage, File filePath) {

        //JAVAFX GRAPH REFERENCE: https://docs.oracle.com/javafx/2/charts/bar-chart.htm#CIHIIIGE
        yAxis.setLabel("Number Of People");
        XYChart.Series data = new XYChart.Series();
        data.getData().add(new XYChart.Data(infected, chartvalue));
        infectedBar.setPrefWidth(Width);
        infectedBar.getData().addAll(data);
        //JAVAFX GRAPH END

        TextField txtLines = new TextField();
        TextField txtLinesRestart = new TextField();
        TextField txtCols = new TextField();
        TextField txtColsRestart = new TextField();
        TextField txtNumberOfStartingPeople = new TextField();
        TextField txtNumberOfRestartingPeople = new TextField();
        TextField txtProbHeal = new TextField();
        TextField txtProbHealRestart = new TextField();
        TextField txtMinTimeSick = new TextField();
        TextField txtMinTimeSickRestart = new TextField();
        TextField txtMaxTimeSick = new TextField();
        TextField txtMaxTimeSickRestart = new TextField();
        Label lblLines = new Label("Lines:");
        Label lblCols = new Label("Columns:");
        Label suggestion = new Label("People number:");
        Label lblProb = new Label("Probability of Heal(1-100):");
        Label lblMinSick = new Label("Min Time Sick");
        Label lblMaxSick = new Label("Max Time Sick:");
        Label lblLines2 = new Label("Lines:");
        Label lblCols2 = new Label("Columns:");
        Label lblProb2 = new Label("Probability of Heal(1-100):");
        Label lblMinSick2 = new Label("Min Time Sick");
        Label lblMaxSick2 = new Label("Max Time Sick:");
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button restartButton = new Button("Start");
        Button fileButton = new Button("Load File");
        Button saveButton = new Button("Save Game");

        VBox vBox = new VBox(suggestion, txtNumberOfRestartingPeople, lblLines2, txtLinesRestart, lblCols2, txtColsRestart, lblProb2, txtProbHealRestart, lblMinSick2, txtMinTimeSickRestart, lblMaxSick2, txtMaxTimeSickRestart, restartButton, stopButton, infectedBar, saveButton);
        HBox hBox = new HBox();

        restartButton.setDisable(true);

        startButton.setPrefWidth(Width);
        stopButton.setPrefWidth(Width);
        fileButton.setPrefWidth(Width);
        saveButton.setPrefWidth(Width);
        restartButton.setPrefWidth(Width);
        stopButton.setPrefWidth(Width);
        txtNumberOfRestartingPeople.setPrefWidth(Width);
        txtNumberOfStartingPeople.setPrefWidth(Width);
        txtProbHeal.setPrefWidth(Width);
        txtMinTimeSick.setPrefWidth(Width);
        txtMaxTimeSick.setPrefWidth(Width);
        suggestion.setPrefWidth(Width);

        this.getChildren().addAll(suggestion, txtNumberOfStartingPeople, lblLines, txtLines, lblCols, txtCols, lblProb, txtProbHeal, lblMinSick, txtMinTimeSick, lblMaxSick, txtMaxTimeSick, startButton, fileButton);

        //Se carregar um ficheiro com o jar começa a simulação com os dados do ficheiro diretamente
        if (filePath != null) {
            this.read(filePath);
            this.nStartingPeople = healthy.size() + immuneNotContagious.size() + immuneContagious.size() + sickSymptoms.size() + sickNoSymptoms.size();
            world = new World(this, this.lines, this.cols, this.nStartingPeople, this.prob, this.minSick, this.maxSick);
            this.pane = new WorldBoard(this.world, SIZE, nStartingPeople);
            startButton.setPrefWidth(pane.getPrefWidth());
            pane.setPrefSize(SIZE * cols * SCALE, SIZE * lines * SCALE);
            this.getChildren().removeAll(startButton, txtNumberOfStartingPeople, fileButton, lblLines, txtLines, lblCols, txtCols, lblProb, txtProbHeal, lblMinSick, txtMinTimeSick, lblMaxSick, txtMaxTimeSick);
            hBox.getChildren().addAll(vBox, pane);
            this.getChildren().add(hBox);
            world.startFile(healthy, immuneNotContagious, immuneContagious, sickSymptoms, sickNoSymptoms);
        }

        //Quando o botão "stopButton" é clicado ele pára os movimentos das pessoas
        stopButton.setOnMouseClicked((e) -> {
            stopButton.setDisable(true);
            restartButton.setDisable(false);
            chartvalue = 5;
            world.stop();
        });

        //Quando o botão "restartButton" é clicado o movimento volta ao inicio
        restartButton.setOnMouseClicked((e) -> {
            stopButton.setDisable(false);
            restartButton.setDisable(true);
            this.nStartingPeople = Integer.parseInt(txtNumberOfRestartingPeople.getText());
            this.lines = Integer.parseInt(txtLinesRestart.getText());
            this.cols = Integer.parseInt(txtColsRestart.getText());
            this.prob = Integer.parseInt(txtProbHealRestart.getText());
            this.minSick = Integer.parseInt(txtMinTimeSickRestart.getText());
            this.maxSick = Integer.parseInt(txtMaxTimeSickRestart.getText());
            hBox.getChildren().removeAll(vBox, pane);
            this.getChildren().removeAll(hBox);
            world = new World(this, this.lines, this.cols, this.nStartingPeople, this.prob, this.minSick, this.maxSick);
            this.pane = new WorldBoard(this.world, SIZE, nStartingPeople);
            pane.setPrefSize(SIZE * cols * SCALE, SIZE * lines * SCALE);
            hBox.getChildren().addAll(vBox, pane);
            this.getChildren().addAll(hBox);
            this.getScene().getWindow().centerOnScreen();
            world.start();
        });

        //Quando o botão "startButton" é clicado ele retira o botão e adiciona a grelha com as pessoas
        startButton.setOnMouseClicked((e) -> {
            this.lines = Integer.parseInt(txtLines.getText());
            this.cols = Integer.parseInt(txtCols.getText());
            this.nStartingPeople = Integer.parseInt(txtNumberOfStartingPeople.getText());
            this.nStartingPeople = Integer.parseInt(txtNumberOfStartingPeople.getText());
            this.prob = Integer.parseInt(txtProbHeal.getText());
            this.minSick = Integer.parseInt(txtMinTimeSick.getText());
            this.maxSick = Integer.parseInt(txtMaxTimeSick.getText());
            world = new World(this, this.lines, this.cols, this.nStartingPeople, this.prob, this.minSick, this.maxSick);
            this.pane = new WorldBoard(this.world, SIZE, nStartingPeople);
            startButton.setPrefWidth(pane.getPrefWidth());
            pane.setPrefSize(SIZE * cols * SCALE, SIZE * lines * SCALE);
            this.getChildren().removeAll(startButton, txtNumberOfStartingPeople, fileButton, lblLines, txtLines, lblCols, txtCols, lblProb, txtProbHeal, lblMinSick, txtMinTimeSick, lblMaxSick, txtMaxTimeSick);
            hBox.getChildren().addAll(vBox, pane);
            this.getChildren().add(hBox);
            this.getScene().getWindow().sizeToScene();
            this.getScene().getWindow().centerOnScreen();
            world.start();
        });

        //Quando o botão "fileButton" é clicado ele abre um filechooser para escolher um ficheiro inicializar a simulação com os parametros do ficheiro
        fileButton.setOnMouseClicked((e) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open text File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt", ".text"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                if (!this.read(file).equals("")) {
                    this.nStartingPeople = healthy.size() + immuneNotContagious.size() + immuneContagious.size() + sickSymptoms.size() + sickNoSymptoms.size();
                    world = new World(this, this.lines, this.cols, this.nStartingPeople, this.prob, this.minSick, this.maxSick);
                    this.pane = new WorldBoard(this.world, SIZE, nStartingPeople);
                    startButton.setPrefWidth(pane.getPrefWidth());
                    pane.setPrefSize(SIZE * cols * SCALE, SIZE * lines * SCALE);
                    this.getChildren().removeAll(startButton, txtNumberOfStartingPeople, fileButton, lblLines, txtLines, lblCols, txtCols, lblProb, txtProbHeal, lblMinSick, txtMinTimeSick, lblMaxSick, txtMaxTimeSick);
                    hBox.getChildren().addAll(vBox, pane);
                    this.getChildren().add(hBox);
                    this.getScene().getWindow().sizeToScene();
                    this.getScene().getWindow().centerOnScreen();
                    world.startFile(healthy, immuneNotContagious, immuneContagious, sickSymptoms, sickNoSymptoms);
                }
            }
        });

        //Guarda o que está no momento na grelha em um ficheiro
        saveButton.setOnMouseClicked((e) -> {
            String s = world.getSave();
            try {
                FileWriter myWriter = new FileWriter("save.txt");
                myWriter.write(s);
                myWriter.close();
            } catch (IOException ioException) {
                System.out.println("Ocurreu um Erro");
                ioException.printStackTrace();
            }
        });


        //JAVAFX GRAPH REFERENCE: https://docs.oracle.com/javafx/2/charts/bar-chart.htm#CIHIIIGE
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(
                new KeyFrame(Duration.millis(500),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                for (XYChart.Series<String, Number> series : infectedBar.getData()) {
                                    for (XYChart.Data<String, Number> data : series.getData()) {
                                        data.setYValue(chartvalue);
                                    }
                                }
                            }
                        }
                ));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.setAutoReverse(true);
        tl.play();
        //JAVAFX GRAPH END

    }

    /**
     * Lê o ficheiro e devolve uma string com o seu conteodo caso este cumpra com os requisitos de ter mais de 3 linhas e colunas
     *
     * @param file
     * @return String
     */
    public String read(File file) {
        String s = "";
        chartvalue = 0;
        String[] colLineTemp = {};
        try (Scanner scanner = new Scanner(file)) {

            this.lines = (Integer.parseInt(scanner.nextLine()));
            this.cols = (Integer.parseInt(scanner.nextLine()));

            if (lines > 3 && cols > 3) {

                this.prob = (Integer.parseInt(scanner.nextLine()));
                this.minSick = (Integer.parseInt(scanner.nextLine()));
                this.maxSick = (Integer.parseInt(scanner.nextLine()));
                scanner.nextLine(); /* passa a word "healthy" */

                do {
                    s = scanner.nextLine();
                    if (!s.equals("immuneNotContagious")) {
                        colLineTemp = s.split(" ");
                        CellPosition cellPosition = new CellPosition(Integer.parseInt(String.valueOf(colLineTemp[0])), Integer.parseInt(String.valueOf(colLineTemp[1])));
                        healthy.add(cellPosition);

                    }

                } while (!s.equals("immuneNotContagious"));

                do {
                    s = scanner.nextLine();
                    if (!s.equals("immuneContagious")) {
                        colLineTemp = s.split(" ");
                        CellPosition cellPosition = new CellPosition(Integer.parseInt(String.valueOf(colLineTemp[0])), Integer.parseInt(String.valueOf(colLineTemp[1])));
                        immuneNotContagious.add(cellPosition);

                    }

                } while (!s.equals("immuneContagious"));

                do {
                    s = scanner.nextLine();
                    if (!s.equals("sickSymptoms")) {
                        colLineTemp = s.split(" ");
                        CellPosition cellPosition = new CellPosition(Integer.parseInt(String.valueOf(colLineTemp[0])), Integer.parseInt(String.valueOf(colLineTemp[1])));
                        immuneContagious.add(cellPosition);

                    }

                } while (!s.equals("sickSymptoms"));

                do {
                    s = scanner.nextLine();
                    if (!s.equals("sickNoSymptoms")) {
                        colLineTemp = s.split(" ");
                        CellPosition cellPosition = new CellPosition(Integer.parseInt(String.valueOf(colLineTemp[0])), Integer.parseInt(String.valueOf(colLineTemp[1])));
                        sickSymptoms.add(cellPosition);
                        chartvalue++;
                    }

                } while (!s.equals("sickNoSymptoms"));

                do {
                    if (scanner.hasNextLine()) {
                        s = scanner.nextLine();
                        colLineTemp = s.split(" ");
                        CellPosition cellPosition = new CellPosition(Integer.parseInt(String.valueOf(colLineTemp[0])), Integer.parseInt(String.valueOf(colLineTemp[1])));
                        sickNoSymptoms.add(cellPosition);
                        chartvalue++;
                    }

                } while (scanner.hasNextLine());

            } else {
                s = "";
            }

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File not found!");
            alert.setContentText("Error opening file ");
            alert.showAndWait();
            Platform.exit(); // System.exit(1);


        }
        return s;
    }

    /**
     * Preenche o retangulo na grelha gráfica
     *
     * @param position
     * @param color
     * @param rectangleCod
     */
    @Override
    public void populate(CellPosition position, Color color, int rectangleCod) {
        pane.populate(position, color, rectangleCod);
    }

    /**
     * Muda a cor do retangulo na grelha gráfica para uma cor de SickPerson
     *
     * @param color
     * @param rectangleCod
     */
    @Override
    public void infectRectangle(Color color, int rectangleCod) {
        pane.infectRectangle(color, rectangleCod);
        chartvalue++;
    }

    /**
     * Muda a cor do retangulo na grelha gráfica para uma cor de ImmunePerson
     *
     * @param color
     * @param rectangleCod
     */
    @Override
    public void healRectangle(Color color, int rectangleCod) {
        pane.healRectangle(color, rectangleCod);
        chartvalue--;
    }

    /**
     * Atualiza a posição do retangulo na grelha gráfica
     *
     * @param dx
     * @param dy
     * @param rectangleCod
     */
    @Override
    public void updatePosition(int dx, int dy, int rectangleCod) {
        Platform.runLater(() -> {
            pane.updatePosition(dx, dy, rectangleCod);
        });
    }

}