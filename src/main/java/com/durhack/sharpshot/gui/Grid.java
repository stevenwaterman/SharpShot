package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.Container;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.function.Supplier;

public class Grid extends GridPane {
    private final Supplier<INode> nodeSupplier;
    private final Runnable enableRunButton;
    private Container container;

    private boolean systemRunning = false;
    private Timer timer = new Timer();

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    private List<BigInteger> input = new ArrayList<>();

    public Grid(Supplier<INode> nodeSupplier, Runnable enableRunButton) {
        this.enableRunButton = enableRunButton;
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
    }

    public void tick() {
        systemRunning = true;
        container.tick();
        render();
        if (container.noBullets()) {
            reset();
            enableRunButton.run();
        }
    }

    public void reset() {
        timer.cancel();
        container.reset();
        systemRunning = false;
        render();
    }

    private Node emptyGraphic(Coordinate coordinate) {
        Rectangle rectangle = new Rectangle(32.0, 32.0, Color.WHITE);
        rectangle.setStroke(Color.GRAY);
        rectangle.setStrokeWidth(0.5);
        rectangle.setStrokeType(StrokeType.CENTERED);
        rectangle.setOnMousePressed(mouseEvent -> {
            INode currentNode = container.getNodes().get(coordinate);
            if (!systemRunning) {
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
            }
        });
        return rectangle;
    }

    public List<BigInteger> getInput() {
        return input;
    }
}
