package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.util.*;

public class Grid extends Application {
    private GridPane pane = new GridPane();
    private Container container;

    private List<BigInteger> pendingInput;

    public Grid() {
        container = new Container(30, 20);
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

        for(Node n : pane.getChildren()) {
            n.setOnMouseClicked(mouseEvent -> {
                nodeClicked((int) n.getLayoutX() / 32, (int) n.getLayoutY() / 32);
            });
        }
    }

    private void nodeClicked(int x, int y) {
        if(container.getNodes().get(new Coordinate(x, y)) != null) {
            container.getNodes().remove(new Coordinate(x, y));
        } else {
            List<String> choices = new ArrayList<>();

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
                    case "add": newNode = new NodeAdd(); break;
                    case "sub": newNode = new NodeSub(); break;
                    case "mul": newNode = new NodeMult(); break;
                    case "div": newNode = new NodeDiv(); break;

                    case "branch": newNode = new NodeBranch(); break;
                    case "splitter": newNode = new NodeSplitter(); break;

                    case "const": newNode = new NodeConstant(getNumberInput()); break;

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

    private BigInteger getNumberInput() {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("New Constant Node");
        dialog.setHeaderText("Please Enter an Integer");
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

    private Node emptyGraphic(){
        return new Rectangle(32.0, 32.0, Color.WHITE);
    }

    @Override
    public void start(Stage primaryStage) {
        render();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(pane);

        Button button = new Button("Tick");
        button.setOnAction(actionEvent -> tick());
        pane.add(button, 0, container.getHeight());
        borderPane.setBottom(button);

        Scene rootScene = new Scene(borderPane);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }
}
