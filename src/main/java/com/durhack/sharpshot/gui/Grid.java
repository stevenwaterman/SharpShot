package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.*;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Supplier;

public class Grid extends GridPane {
    private final Supplier<INode> nodeSupplier;
    private Container container;

    private boolean systemActive = false;
    private Timer timer = new Timer();

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    private List<BigInteger> input = new ArrayList<>();

    public Grid(Supplier<INode> nodeSupplier) {
        this.nodeSupplier = nodeSupplier;
        container = new Container(40, 25);
        render();
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public void render() {
        getChildren().clear();

        for (int x = 0; x < container.getWidth(); x++) {
            for (int y = 0; y < container.getHeight(); y++) {
                add(emptyGraphic(new Coordinate(x, y)), x, y);
            }
        }

        for (Map.Entry<Coordinate, INode> nodeLocation : container.getNodes().entrySet()) {
            Coordinate coordinate = nodeLocation.getKey();
            INode node = nodeLocation.getValue();
            add(node.toGraphic(), coordinate.getX(), coordinate.getY());
        }

        for (Map.Entry<Coordinate, Bullet> bulletLocations : container.getBullets().entrySet()) {
            Coordinate coordinate = bulletLocations.getKey();
            Bullet bullet = bulletLocations.getValue();
            add(bullet.toGraphic(), coordinate.getX(), coordinate.getY());
        }

        for (Node n : getChildren()) {
            n.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    nodeLeftClicked((int) n.getLayoutX() / 32, (int) n.getLayoutY() / 32);
                if (mouseEvent.getButton() == MouseButton.SECONDARY)
                    nodeRightClicked((int) n.getLayoutX() / 32, (int) n.getLayoutY() / 32);

            });
        }
    }

    private void nodeRightClicked(int x, int y) {
        INode node = container.getNodes().get(new Coordinate(x, y));
        if (systemActive && node != null)
            if (node instanceof NodeIn) {
                inputers.remove(((NodeIn) node).getIndex());
            }
        container.getNodes().remove(new Coordinate(x, y));
        render();
    }

    private void nodeLeftClicked(int x, int y) {
        if (systemActive) {
            return;
        }
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
            if (result.isPresent()) {
                String choice = result.get();
                INode newNode;

                switch (choice) {
                    case "in":
                        int var = getNumberInput("Enter an input index: ", getNextIndex()).intValue();
                        inputers.add(var + "");
                        newNode = new NodeIn(var);
                        break;
                    case "out":
                        newNode = new NodeOut();
                        break;

                    case "add":
                        newNode = new NodeAdd();
                        break;
                    case "sub":
                        newNode = new NodeSub();
                        break;
                    case "mul":
                        newNode = new NodeMult();
                        break;
                    case "div":
                        newNode = new NodeDiv();
                        break;

                    case "branch":
                        newNode = new NodeBranch();
                        break;
                    case "splitter":
                        newNode = new NodeSplitter();
                        break;

                    case "const":
                        newNode = new NodeConstant(getNumberInput("Enter a constant: ", "0"));
                        break;

                    case "void":
                        newNode = new NodeVoid();
                        break;

                    case "if_positive":
                        newNode = new NodeIfPositive();
                        break;
                    case "if_0":
                        newNode = new NodeIfZero();
                        break;

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

    private ArrayList<String> inputers = new ArrayList<>();

    private String getNextIndex() {
        int counter = 0;
        while (counter <= inputers.size()) {
            if (inputers.contains(counter + "")) {
                counter++;
            } else {
                return (counter + "");
            }
        }
        return (counter + "");
    }

    private BigInteger getNumberInput(String header, String counter) {
        TextInputDialog dialog = new TextInputDialog(counter);
        dialog.setTitle("New Node");
        dialog.setHeaderText(header);
        dialog.setContentText("");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return new BigInteger(result.get());
        } else {
            throw new RuntimeException("No result");
        }
    }

    public void tick() {
        container.tick();
        render();
    }

    public void reset() {
        timer.cancel();
        container.reset();
        systemActive = false;
        render();
    }

    private Node emptyGraphic(Coordinate coordinate) {
        Rectangle rectangle = new Rectangle(32.0, 32.0, Color.WHITE);
        rectangle.setStroke(Color.GRAY);
        rectangle.setStrokeWidth(0.5);
        rectangle.setStrokeType(StrokeType.CENTERED);
        rectangle.setOnMousePressed(mouseEvent -> {
            INode currentNode = container.getNodes().get(coordinate);
            if (currentNode == null) {
                INode newNode = nodeSupplier.get();
                if (newNode != null && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    container.getNodes().put(coordinate, newNode);
                    Grid.this.render();
                }
            } else {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    currentNode.rotateClockwise();
                    Grid.this.render();
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    container.getNodes().remove(coordinate);
                    Grid.this.render();
                }
            }
        });
        return rectangle;
    }

    public List<BigInteger> getInput() {
        return input;
    }
}
