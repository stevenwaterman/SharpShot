package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.INode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public class App extends Application {
    private Grid grid = new Grid(new Supplier<INode>(){
        @Override
        public INode get() {
            return nodeCreator.getNode();
        }
    });
    private NodeCreator nodeCreator = new NodeCreator();
    private BorderPane borderPane = new BorderPane();

    @Override
    public void start(Stage primaryStage) {
        nodeCreator.prefHeightProperty().bind(grid.prefHeightProperty());

        borderPane.setCenter(grid);
        borderPane.setLeft(nodeCreator);

        Button resetButton = new Button("Reset");
        Button runButton = new Button("Run");
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
