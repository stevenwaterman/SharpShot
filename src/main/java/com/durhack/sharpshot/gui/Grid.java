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
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.util.*;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Grid extends Application {
    private GridPane pane = new GridPane();
    private Container container;

    private Timer timer = new Timer();

    private List<BigInteger> input = new ArrayList<>();

    public Grid() {
        container = new Container(40, 25);
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

        for (Node n : pane.getChildren()) {
            n.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    nodeLeftClicked((int) n.getLayoutX() / 32, (int) n.getLayoutY() / 32);
                if (mouseEvent.getButton() == MouseButton.SECONDARY)
                    nodeRightClicked((int) n.getLayoutX() / 32, (int) n.getLayoutY() / 32);

            });
        }
    }

    private void nodeRightClicked(int x, int y) {
        if (container.getNodes().get(new Coordinate(x, y)) != null)
            container.getNodes().remove(new Coordinate(x, y));
        render();
    }

    private void nodeLeftClicked(int x, int y) {
        if (container.getNodes().get(new Coordinate(x, y)) != null) {
            container.getNodes().get(new Coordinate(x, y)).rotateClockwise();
        } else {
            List<String> choices = new ArrayList<>();

            choices.add("in");
            choices.add("out");

            choices.add("add");
            choices.add("sub");
            choices.add("mul");
            choices.add("div");

            choices.add("branch");
            choices.add("splitter");

            choices.add("const");

            choices.add("void");

            choices.add("if_positive");
            choices.add("if_0");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("add", choices);
            dialog.setTitle("Add Node");
            dialog.setHeaderText("Choose your node type:");
            dialog.setContentText("");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                String choice = result.get();
                INode newNode;

                switch(choice) {
                    case "in":  newNode = new NodeIn(getNumberInput("Enter an input index: ").intValue()); break;
                    case "out": newNode = new NodeOut(); break;

                    case "add": newNode = new NodeAdd(); break;
                    case "sub": newNode = new NodeSub(); break;
                    case "mul": newNode = new NodeMult(); break;
                    case "div": newNode = new NodeDiv(); break;

                    case "branch":   newNode = new NodeBranch(); break;
                    case "splitter": newNode = new NodeSplitter(); break;

                    case "const": newNode = new NodeConstant(getNumberInput("Enter a constant: ")); break;

                    case "void": newNode = new NodeVoid(); break;

                    case "if_positive": newNode = new NodeIfPositive(); break;
                    case "if_0": newNode = new NodeIf0(); break;

                    default:
                        System.err.println("This shouldn't happen");
                        throw new RuntimeException("Choice nonexistent");
                }

                container.getNodes().put(new Coordinate(x, y), newNode);
            } else {
                System.err.println("No choice made");
            }
        }


        render();
    }

    private BigInteger getNumberInput(String header) {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("New Node");
        dialog.setHeaderText(header);
        dialog.setContentText("");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return new BigInteger(result.get());
        } else {
            throw new RuntimeException("No result");
        }

    }

    private void tick() {
        container.tick();
        render();
    }

    private void reset() {
        timer.cancel();
        container.reset();
        render();
    }

    private Node emptyGraphic(){
        Rectangle rectangle = new Rectangle(32.0, 32.0, Color.WHITE);
        rectangle.setStroke(Color.GRAY);
        rectangle.setStrokeWidth(0.5);
        rectangle.setStrokeType(StrokeType.CENTERED);
        return rectangle;
    }

    @Override
    public void start(Stage primaryStage) {
        render();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(pane);

        //Button tickButton = new Button("Tick");
        //tickButton.setOnAction(actionEvent -> tick());
        //pane.add(tickButton, 0, container.getHeight());

        Button resetButton = new Button("Reset");
        Button runButton = new Button("Run");
        TextField inputText = new TextField();

        resetButton.setOnAction(actionEvent -> {
            runButton.setDisable(false);
            reset();
        });

        runButton.setOnAction(actionEvent -> {
            runButton.setDisable(true);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> tick());
                }
            }, 0, 250);

            input.clear();
            for(String x : inputText.getText().split(" "))
                if(x.length() > 0)
                    input.add(new BigInteger(x));
            container.start(input);
        });

        HBox hBox = new HBox(resetButton, runButton, inputText);
        borderPane.setBottom(hBox);

        Scene rootScene = new Scene(borderPane);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }
}
