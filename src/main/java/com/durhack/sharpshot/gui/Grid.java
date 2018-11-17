package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;

public class Grid extends Application {
    private GridPane pane = new GridPane();
    private Container container;

    public Grid() {
        container = new Container(50, 30);
        container.getBullets().put(new Coordinate(1, 3), new Bullet(Direction.DOWN, BigInteger.ONE));
        container.getBullets().put(new Coordinate(1, 4), new Bullet(Direction.DOWN, BigInteger.ONE));
        container.getNodes().put(new Coordinate(1, 2), new NodeAdd());
        container.getNodes().put(new Coordinate(2, 2), new NodeSub());
        container.getNodes().put(new Coordinate(3, 2), new NodeMult());
        container.getNodes().put(new Coordinate(4, 2), new NodeDiv());
    }

    private void render() {
        pane.getChildren().clear();

        for (int x = 0; x < container.getWidth(); x++) {
            for (int y = 0; y < container.getHeight(); y++) {
                pane.add(emptyGraphic(), x, y);
            }
        }

        for (Map.Entry<Coordinate, INode> nodeLocation : container.getNodes().entrySet()) {
            Coordinate coordinate = nodeLocation.getKey();
            INode node = nodeLocation.getValue();
            pane.add(node.toGraphic(), coordinate.getX(), coordinate.getY());
        }

        for (Map.Entry<Coordinate, Bullet> bulletLocations : container.getBullets().entrySet()) {
            Coordinate coordinate = bulletLocations.getKey();
            Bullet bullet = bulletLocations.getValue();
            pane.add(bullet.toGraphic(), coordinate.getX(), coordinate.getY());
        }
    }

    private void tick() {
        container.tick();
        render();
    }

    private Node emptyGraphic(){
        return new Rectangle(32.0, 32.0, Color.WHITE);
    }

    public static void main(String[] args) {
        launch(Grid.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        render();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(pane);

        Button tickButton = new Button("Tick");
        tickButton.setOnAction(actionEvent -> tick());
        pane.add(tickButton, 0, container.getHeight());

        Button runButton = new Button("Run");
        runButton.setOnAction(actionEvent -> {
            runButton.setDisable(true);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> tick());
                }
            }, 0, 250);
        });

        HBox hBox = new HBox(tickButton, runButton);
        borderPane.setBottom(hBox);

        Scene rootScene = new Scene(borderPane);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }
}
