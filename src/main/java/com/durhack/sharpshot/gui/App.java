package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.Container;
import com.durhack.sharpshot.nodes.NodeIn;
import com.durhack.sharpshot.util.Ascii;
import com.durhack.sharpshot.util.ErrorBox;
import com.durhack.sharpshot.util.SaveLoadFiles;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class App extends Application {
    public static final int TICK_RATE = 150;

    private final Button runButton = new Button("Run");
    private final Button resetButton = new Button("Reset");
    private final Button loadButton = new Button("Load");
    private final Button saveButton = new Button("Save");
    private final Button clearAllButton = new Button("Clear All");
    private final TextField inputText = new TextField();

    private final BorderPane borderPane = new BorderPane();
    private final NodeCreator nodeCreator = new NodeCreator(this::firstAvailableInputIndex);
    private final Grid grid = new Grid(this::createNode, () -> runButton.setDisable(false));
    private static final TextArea programOutput = new TextArea();

    private INode createNode() {
        return nodeCreator.createNode();
    }

    private int firstAvailableInputIndex() {
        int counter = 0;
        List<Integer> inputers = grid.getContainer().getNodes()
                .values()
                .stream()
                .filter(node ->
                        node instanceof NodeIn
                ).map(node ->
                        ((NodeIn) node).getIndex()
                ).collect(Collectors.toList());

        while (true) {
            if (inputers.contains(counter)) {
                counter++;
            } else {
                return (counter);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        nodeCreator.prefHeightProperty().bind(grid.prefHeightProperty());

        borderPane.setCenter(grid);
        borderPane.setLeft(nodeCreator);

        HBox hBox = new HBox(16.0, resetButton, runButton, inputText, loadButton, saveButton, clearAllButton);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBox);

        resetButton.setOnAction(actionEvent -> {
            runButton.setDisable(false);
            grid.reset();
        });

        runButton.setOnAction(actionEvent -> {
            runButton.setDisable(true);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> grid.tick());
                }
            }, 0, TICK_RATE);
            if(grid.getTimer() != null)
                grid.getTimer().cancel();
            grid.setTimer(timer);

            List<BigInteger> input = grid.getInput();
            input.clear();
            clearOutput();
            for (String x : inputText.getText().split(" "))
                if (x.length() > 0)
                    try{
                    input.add(new BigInteger(x));}
                    catch(NumberFormatException e){
                        if (x.length() <= 1){
                        input.add(Ascii.toBig(x.charAt(0)));}
                        else{
                            ErrorBox.alert("Input not Char or BigInteger","Please try again","Input takes Char and integers only with spaces bettween them!");
                        }
                    }
            grid.getContainer().start(input);
            grid.render();
        });

        loadButton.setOnAction(actionEvent -> {
            Container container = SaveLoadFiles.loadFromFile(primaryStage, grid);
            if(container != null)
                grid.setContainer(container);
        });

        saveButton.setOnAction(actionEvent -> SaveLoadFiles.saveToFile(primaryStage, grid.getContainer()));

        clearAllButton.setOnAction(actionEvent -> {
            grid.clearAll();
        });

        programOutput.setMaxWidth(100);
        programOutput.setEditable(false);
        clearOutput();
        borderPane.setRight(programOutput);

        Scene rootScene = new Scene(borderPane);
        primaryStage.setScene(rootScene);
        primaryStage.setTitle("SharpShot 1.0");

        primaryStage.setOnCloseRequest(windowEvent -> {
            grid.getTimer().cancel();
        });

        primaryStage.show();
    }

    public static void printToOut(String s) {
        programOutput.appendText(s + "\n");
    }

    public static void clearOutput() {
        programOutput.setText("OUT:\n\n");
    }
}
