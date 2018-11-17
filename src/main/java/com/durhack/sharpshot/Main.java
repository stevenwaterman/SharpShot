package com.durhack.sharpshot;

import com.durhack.sharpshot.gui.Grid;
import javafx.application.Application;

public class Main {
    public static void main(String args[]) {
        Grid game = new Grid();
        Application.launch(Grid.class, args);
    }
}
