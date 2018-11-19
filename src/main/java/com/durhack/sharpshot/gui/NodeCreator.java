package com.durhack.sharpshot.gui;

import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.*;
import com.durhack.sharpshot.nodes.io.InNode;
import com.durhack.sharpshot.nodes.io.ListNode;
import com.durhack.sharpshot.nodes.io.NodeAscii;
import com.durhack.sharpshot.nodes.io.NodeOut;
import com.durhack.sharpshot.nodes.math.*;
import com.durhack.sharpshot.nodes.math.DivNode;
import com.durhack.sharpshot.nodes.math.MultNode;
import com.durhack.sharpshot.nodes.routing.*;
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

        node = new InNode(0);
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Provides input", node.toGraphic(), () -> {
            int suggested = minInIndex.get();
            BigInteger response = getNumberInput("Enter Input Index", "0 for shoot 0 at start\nArguments are 1-indexed", suggested);
            if (response == null) {
                return null;
            } else {
                return new InNode(response.intValue());
            }
        }));

        node = new NodeOut();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Provides output", node.toGraphic(), NodeOut::new));

        node = new NodeAscii();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Outputs an Ascii String corresponding to input", node.toGraphic(), NodeAscii::new));

        node = new AddNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Adds two numbers", node.toGraphic(), AddNode::new));

        node = new SubNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Subtracts the second input from the first input", node.toGraphic(), SubNode::new));

        node = new MultNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Multiplies two numbers", node.toGraphic(), MultNode::new));

        node = new DivNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Divides the first number by the second number", node.toGraphic(), DivNode::new));

        node = new BranchNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Redirects all bullets", node.toGraphic(), BranchNode::new));

        node = new IfPositiveNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Redirects all positive bullets", node.toGraphic(), IfPositiveNode::new));

        node = new IfZeroNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Redirects all bullets with value zero", node.toGraphic(), IfZeroNode::new));

        node = new SplitterNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Splits bullets input three", node.toGraphic(), SplitterNode::new));

        node = new ConstantNode(BigInteger.ZERO);
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Whenever a bullet passes through, releases another bullet with user-determined value", node.toGraphic(), () -> {
            BigInteger value = getNumberInput("Enter Constant Value");
            if (value == null) {
                return null;
            } else {
                return new ConstantNode(value);
            }
        }));

        node = new RandomNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Provides a random output between 0 and input bullet - 1", node.toGraphic(), RandomNode::new));

        node = new RotateNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Rotates incoming bullets clockwise", node.toGraphic(), RotateNode::new));

        node = new ACRotateNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Rotates incoming bullets anticlockwise", node.toGraphic(), ACRotateNode::new));

        node = new VoidNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Destroys all incoming bullets", node.toGraphic(), VoidNode::new));

        node = new HaltNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Terminates the program", node.toGraphic(), HaltNode::new));

        node = new ListNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Every time a bullet comes in, outputs the next value in the list of inputs", node.toGraphic(), ListNode::new));

        node = new StackNode();
        nodeTypes.add(new NodeTypeDescriptor(node.toString(), "Inputs to back of node pop from stack, inputs to other sides add to stack", node.toGraphic(), StackNode::new));
    }

    @Nullable
    private BigInteger getNumberInput(@NotNull String header) {
        return getNumberInput(header, "", 0);
    }

    @Nullable
    private BigInteger getNumberInput(@NotNull String header, @NotNull String content, @NotNull Integer start) {
        TextInputDialog dialog = new TextInputDialog(start.toString());
        dialog.setTitle("New Node");
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        Optional<String> result = dialog.showAndWait();
        return result.map(BigInteger::new).orElse(null);
    }

    @Nullable
    public INode createNode() {
        NodeTypeDescriptor descriptor = getSelectionModel().getSelectedItem();
        if (descriptor == null) {
            return null;
        } else {
            return descriptor.getSupplier().get();
        }
    }
}
