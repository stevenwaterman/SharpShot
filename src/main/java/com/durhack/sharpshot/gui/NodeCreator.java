package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Supplier;

public class NodeCreator extends ListView<NodeTypeDescriptor> {

    private BigInteger getNumberInput(String header) {
        TextInputDialog dialog = new TextInputDialog("0");
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

    public NodeCreator() {
        super(FXCollections.observableArrayList());
        ObservableList<NodeTypeDescriptor> nodeTypes = getItems();

        INode node;

        node = new NodeIn(0);
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Provides input", node.toGraphic(), () -> new NodeIn(getNumberInput("Enter Input Index").intValue())));

        node = new NodeOut();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Provides output", node.toGraphic(), NodeOut::new));

        node = new NodeAdd();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Adds two numbers", node.toGraphic(), NodeAdd::new));

        node = new NodeSub();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Subtracts the second input from the first input", node.toGraphic(), NodeSub::new));

        node = new NodeMult();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Multiplies two numbers", node.toGraphic(), NodeMult::new));

        node = new NodeDiv();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Divides the first number by the second number", node.toGraphic(), NodeDiv::new));

        node = new NodeBranch();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Redirects all bullets", node.toGraphic(), NodeBranch::new));

        node = new NodeIfPositive();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Redirects all positive bullets", node.toGraphic(), NodeIfPositive::new));

        node = new NodeIfZero();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Redirects all bullets with value zero", node.toGraphic(), NodeIfZero::new));

        node = new NodeSplitter();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Splits bullets into three", node.toGraphic(), NodeSplitter::new));

        node = new NodeConstant(BigInteger.ONE);
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Whenever a bullet passes through, releases another bullet with user-determined value", node.toGraphic(), () -> new NodeConstant(getNumberInput("Enter Constant Value"))));

        node = new NodeRandom();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Provides a random output between 0 and input bullet - 1", node.toGraphic(), NodeRandom::new));

        node = new NodeRotateClockwise();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Rotates incoming bullets clockwise", node.toGraphic(), NodeRotateClockwise::new));

        node = new NodeRotateAnticlockwise();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Rotates incoming bullets anticlockwise", node.toGraphic(), NodeRotateAnticlockwise::new));

        node = new NodeVoid();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Destroys all incoming bullets", node.toGraphic(), NodeVoid::new));
    }

    public INode getNode() {
        return getSelectionModel().getSelectedItem().getSupplier().get();
    }
}
