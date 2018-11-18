package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Supplier;

class NodeCreator extends ListView<NodeTypeDescriptor> {

    public NodeCreator(Supplier<Integer> minInIndex) {
        super(FXCollections.observableArrayList());
        ObservableList<NodeTypeDescriptor> nodeTypes = getItems();

        INode node;

        node = new NodeIn(0);
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Provides input", node.toGraphic(), () -> {
            int suggested = minInIndex.get();
            BigInteger response = getNumberInput("Enter Input Index", suggested);
            if (response == null) {
                return null;
            } else {
                return new NodeIn(response.intValue());
            }
        }));

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
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Whenever a bullet passes through, releases another bullet with user-determined value", node.toGraphic(), () -> {
            BigInteger value = getNumberInput("Enter Constant Value");
            if (value == null) {
                return null;
            } else {
                return new NodeConstant(value);
            }
        }));

        node = new NodeRandom();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Provides a random output between 0 and input bullet - 1", node.toGraphic(), NodeRandom::new));

        node = new NodeRotateClockwise();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Rotates incoming bullets clockwise", node.toGraphic(), NodeRotateClockwise::new));

        node = new NodeRotateAnticlockwise();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Rotates incoming bullets anticlockwise", node.toGraphic(), NodeRotateAnticlockwise::new));

        node = new NodeVoid();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Destroys all incoming bullets", node.toGraphic(), NodeVoid::new));

        node = new NodeHalt();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Terminates the program", node.toGraphic(), NodeHalt::new));
    }

    @Nullable
    private BigInteger getNumberInput(@NotNull String header) {
        return getNumberInput(header, 0);
    }

    @Nullable
    private BigInteger getNumberInput(@NotNull String header, @NotNull Integer start) {
        TextInputDialog dialog = new TextInputDialog(start.toString());
        dialog.setTitle("New Node");
        dialog.setHeaderText(header);
        dialog.setContentText("");

        Optional<String> result = dialog.showAndWait();
        return result.map(BigInteger::new).orElse(null);
    }

    @Nullable
    public INode createNode() {
        return getSelectionModel().getSelectedItem().getSupplier().get();
    }
}
