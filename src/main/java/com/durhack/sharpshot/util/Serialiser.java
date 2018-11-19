package com.durhack.sharpshot.util;

import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.gui.Grid;
import com.durhack.sharpshot.nodes.*;
import com.durhack.sharpshot.nodes.io.InNode;
import com.durhack.sharpshot.nodes.io.ListNode;
import com.durhack.sharpshot.nodes.io.NodeAscii;
import com.durhack.sharpshot.nodes.io.NodeOut;
import com.durhack.sharpshot.nodes.math.*;
import com.durhack.sharpshot.nodes.math.AddNode;
import com.durhack.sharpshot.nodes.math.MultNode;
import com.durhack.sharpshot.nodes.routing.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Serialiser {

    static class NodeData {
        final String type;
        final int x;
        final int y;
        final Direction rotation;
        String extra;

        NodeData(String type, int x, int y, Direction rotation, String extra) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.extra = extra;
        }
    }

    static class Data {
        int width;
        int height;
        final List<NodeData> nodes = new ArrayList<>();
    }


    public static String getJSON(Container cont) {
        Data data = new Data();

        data.width = cont.getWidth();
        data.height = cont.getHeight();

        for(Map.Entry<Coordinate, INode> entry: cont.getNodes().entrySet()) {
            INode node = entry.getValue();
            Coordinate coord = entry.getKey();

            NodeData nd = new NodeData(node.getClass().getSimpleName(), coord.getX(), coord.getY(), node.getRotation(), "");

            if(node instanceof ConstantNode)
                nd.extra = ((ConstantNode) node).getValue().toString();
            if(node instanceof InNode)
                nd.extra = String.valueOf(((InNode) node).getIndex());

            data.nodes.add(nd);
        }


        Gson gson = new GsonBuilder().create();
        gson.toJson(data);
        return gson.toJson(data);
    }

    public static Container loadJSON(Grid grid, String json) {
        Gson gson = new GsonBuilder().create();
        Data data = gson.fromJson(json, Data.class);

        Container container = new Container(grid, data.width, data.height);

        for(NodeData nd : data.nodes) {
            String type = nd.type;
            INode newNode;
            switch(type) {
                case "InNode":  newNode = new InNode(Integer.parseInt(nd.extra)); break;
                case "NodeOut": newNode = new NodeOut(); break;
                case "NodeAscii": newNode = new NodeAscii(); break;

                case "AddNode": newNode = new AddNode(); break;
                case "SubNode": newNode = new SubNode(); break;
                case "MultNode": newNode = new MultNode(); break;
                case "DivNode": newNode = new DivNode(); break;

                case "BranchNode":   newNode = new BranchNode(); break;
                case "SplitterNode": newNode = new SplitterNode(); break;

                case "ConstantNode": newNode = new ConstantNode(new BigInteger(nd.extra)); break;

                case "VoidNode": newNode = new VoidNode(); break;

                case "IfPositiveNode": newNode = new IfPositiveNode(); break;
                case "IfZeroNode": newNode = new IfZeroNode(); break;

                case "RotateNode": newNode = new RotateNode(); break;
                case "ACRotateNode": newNode = new ACRotateNode(); break;

                case "RandomNode": newNode = new RandomNode(); break;

                case "HaltNode": newNode = new HaltNode(); break;
                case "ListNode": newNode = new ListNode(); break;
                case "StackNode": newNode = new StackNode(); break;

                default:
                    System.out.println(type);
                    throw new RuntimeException("This shouldn't happen, cannot read serialised node in Serialiser");
            }

            // wow.. lazy
            while(newNode.getRotation() != nd.rotation) newNode.rotateClockwise();

            container.getNodes().put(new Coordinate(nd.x, nd.y), newNode);
        }

        return container;
    }
}
