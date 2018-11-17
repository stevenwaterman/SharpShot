package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Grid extends Application {
    private GridPane pane = new GridPane();
    private Container container;

    public Grid() {
        container = new Container(50, 30);
        container.getBullets().put(new Coordinate(1, 3), new Bullet(Direction.DOWN, BigInteger.ONE));
        container.getBullets().put(new Coordinate(1, 4), new Bullet(Direction.DOWN, BigInteger.ONE));
        Map<Coordinate, INode> nodes = container.getNodes();
        nodes.put(new Coordinate(1, 2), new NodeAdd());
        nodes.put(new Coordinate(2, 2), new NodeBranch());
        nodes.put(new Coordinate(3, 2), new NodeConstant(new BigInteger("11")));
        NodeDiv div = new NodeDiv();
        div.rotateClockwise();
        nodes.put(new Coordinate(4, 2), div);
        nodes.put(new Coordinate(5, 2), new NodeIf0());
        nodes.put(new Coordinate(6, 2), new NodeIfPositive());
        nodes.put(new Coordinate(7, 2), new NodeIn());
        nodes.put(new Coordinate(8, 2), new NodeMult());
        nodes.put(new Coordinate(9, 2), new NodeOut());
        nodes.put(new Coordinate(10, 2), new NodeRotateAnticlockwise());
        nodes.put(new Coordinate(11, 2), new NodeRotateClockwise());
        nodes.put(new Coordinate(12, 2), new NodeSplitter());
        nodes.put(new Coordinate(13, 2), new NodeSub());
        nodes.put(new Coordinate(14, 2), new NodeVoid());


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
