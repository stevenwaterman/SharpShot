package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.Direction;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

public class Diamond extends GridPane {
    public Diamond(@NotNull Direction rotation, Color color, String s) {
        super();
        Polygon polygon = new Polygon(0.0, 16.0,16.0, 0.0, 32.0, 16.0, 16.0, 32.0);
        polygon.setRotate(rotation.getDegrees());
        polygon.setFill(color);
        add(polygon, 0, 0);
        if(s != null){
            Label label = new Label(s);
            add(label, 0, 0);
            setHalignment(label, HPos.CENTER);
        }
    }
}
