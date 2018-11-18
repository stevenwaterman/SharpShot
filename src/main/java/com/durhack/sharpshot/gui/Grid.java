package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.Container;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.function.Supplier;

public class Grid extends Pane {
    public final int WIDTH = 40, HEIGHT = 25;

    private final Supplier<INode> nodeSupplier;
    private final Runnable enableRunButton;
    private Container container;

    private boolean systemRunning = false;
    private Timer timer = new Timer();

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Timer getTimer() { return this.timer; }

    private final List<BigInteger> input = new ArrayList<>();

    public Grid(Supplier<INode> nodeSupplier, Runnable enableRunButton) {
        this.enableRunButton = enableRunButton;
        this.nodeSupplier = nodeSupplier;
        container = new Container(this, WIDTH, HEIGHT);
        resize(WIDTH * 32, HEIGHT * 32);
        render();
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
        render();
    }

    public void render() {
        getChildren().clear();

        for (Map.Entry<Coordinate, INode> nodeLocation : container.getNodes().entrySet()) {
            Coordinate coordinate = nodeLocation.getKey();
            INode node = nodeLocation.getValue();
            Node graphic = node.toGraphic();
            graphic.relocate(coordinate.getX() * 32, coordinate.getY() * 32);
            getChildren().add(graphic);
        }

        for (Map.Entry<Coordinate, Bullet> bulletLocations : container.getBullets().entrySet()) {
            Coordinate coordinate = bulletLocations.getKey();
            Bullet bullet = bulletLocations.getValue();

            Node graphic = bullet.toGraphic();
            graphic.relocate(coordinate.getX() * 32, coordinate.getY() * 32);

            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.millis(App.TICK_RATE));
            translateTransition.setNode(graphic);

            translateTransition.setToX(bullet.getDirection().getDeltaX() * 32);
            translateTransition.setToY(bullet.getDirection().getDeltaY() * 32);

            translateTransition.setAutoReverse(false);
            getChildren().add(graphic);
            translateTransition.play();
        }

        for (int x = 0; x < container.getWidth(); x++) {
            for (int y = 0; y < container.getHeight(); y++) {
                Node eg = emptyGraphic(new Coordinate(x, y));
                eg.relocate(x*32, y*32);
                getChildren().add(eg);
            }
        }
    }

    public void tick() {
        render();
        systemRunning = true;
        container.tick();
        if (container.noBullets()) {
            reset();
            enableRunButton.run();
        }
    }

    public void reset() {
        timer.cancel();
        container.reset();
        systemRunning = false;
        enableRunButton.run();
        render();
    }

    private Node emptyGraphic(Coordinate coordinate) {
        Rectangle rectangle = new Rectangle(32.0, 32.0, Color.TRANSPARENT);
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

    public void clearAll() {
        container.clearAll();
        App.clearOutput();
        reset();
    }
}
