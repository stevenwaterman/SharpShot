package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.INode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.util.function.Supplier;

class NodeTypeDescriptor extends HBox {
    private final Supplier<INode> supplier;

    public NodeTypeDescriptor(String name, String tooltip, Node graphic, Supplier<INode> supplier) {
        this(new Label(name), tooltip, graphic, supplier);
    }

    private NodeTypeDescriptor(Label label, String tooltipText, Node graphic, Supplier<INode> supplier){
        super(16.0, graphic, label);
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(this, tooltip);
        this.supplier = supplier;
    }

    public Supplier<INode> getSupplier() {
        return supplier;
    }
}
