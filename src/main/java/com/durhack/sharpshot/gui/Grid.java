package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.Container;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

public class Grid extends Application {
    private GridPane pane = new GridPane();
    private Container container;

    public Grid() {
        container = new Container(new INode[10][5]);
        container.getNodes()[5][2] = new Container(container.getNodes());

        container.getBullets().add(new Bullet(Direction.DOWN, 1 , 2, BigInteger.ONE));
    }

    private void render(){
        pane.getChildren().clear();

        @NotNull INode[][] nodes = container.getNodes();
        for (int x = 0; x < nodes.length; x++) {
            INode[] row = nodes[x];
            for (int y = 0; y < row.length; y++) {
                INode node = row[y];
                pane.add(toGraphic(node), x, y);
            }
        }

        for (Bullet bullet : container.getBullets()) {
            pane.add(toGraphic(bullet), bullet.getX(), bullet.getY());
        }
    }

    private void tick(){
        container.tick();
        render();
    }

    @NotNull
    private Node toGraphic(@Nullable INode node){
        if(node == null){
            return new Rectangle(32.0, 32.0, Color.GRAY);
        }
        else{
            return new Rectangle(32.0, 32.0, Color.RED);
        }
    }

    @NotNull
    private Node toGraphic(@NotNull Bullet bullet){
        StackPane stackPane = new StackPane();

        Rectangle background = new Rectangle(16.0, 16.0, Color.WHEAT);
        stackPane.getChildren().add(background);

        BigInteger value = bullet.getValue();
        if(value != null){
            Label label = new Label(value.toString());
            stackPane.getChildren().add(label);
        }

        return stackPane;
    }

    public static void main(String[] args){
        launch(Grid.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        render();
        Scene rootScene = new Scene(pane);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }
}
