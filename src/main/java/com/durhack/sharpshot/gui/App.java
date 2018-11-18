package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.NodeIn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private BorderPane borderPane = new BorderPane();
    private Button runButton = new Button("Run");
    private NodeCreator nodeCreator = new NodeCreator(this::firstAvailableInputIndex);
    private Grid grid = new Grid(this::createNode, () -> runButton.setDisable(false));

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

        Button resetButton = new Button("Reset");
        TextField inputText = new TextField();
        HBox hBox = new HBox(16.0, resetButton, runButton, inputText);
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
            }, 0, 250);
            grid.setTimer(timer);

            List<BigInteger> input = grid.getInput();
            input.clear();
            for (String x : inputText.getText().split(" "))
                if (x.length() > 0)
                    input.add(new BigInteger(x));
            grid.getContainer().start(input);
            grid.render();
        });

        Scene rootScene = new Scene(borderPane);
        primaryStage.setScene(rootScene);
        primaryStage.setTitle("SharpShot 1.0");
        primaryStage.show();
    }
}
