package com.durhack.sharpshot.util;

import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.gui.Grid;
import com.durhack.sharpshot.nodes.*;
import com.google.gson.*;

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

            if(node instanceof NodeConstant)
                nd.extra = ((NodeConstant) node).getValue().toString();
            if(node instanceof NodeIn)
                nd.extra = String.valueOf(((NodeIn) node).getIndex());

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
                case "NodeIn":  newNode = new NodeIn(Integer.parseInt(nd.extra)); break;
                case "NodeOut": newNode = new NodeOut(); break;
                case "NodeAscii": newNode = new NodeAscii(); break;

                case "NodeAdd": newNode = new NodeAdd(); break;
                case "NodeSub": newNode = new NodeSub(); break;
                case "NodeMult": newNode = new NodeMult(); break;
                case "NodeDiv": newNode = new NodeDiv(); break;

                case "NodeBranch":   newNode = new NodeBranch(); break;
                case "NodeSplitter": newNode = new NodeSplitter(); break;

                case "NodeConstant": newNode = new NodeConstant(new BigInteger(nd.extra)); break;

                case "NodeVoid": newNode = new NodeVoid(); break;

                case "NodeIfPositive": newNode = new NodeIfPositive(); break;
                case "NodeIfZero": newNode = new NodeIfZero(); break;

                case "NodeRotateClockwise": newNode = new NodeRotateClockwise(); break;
                case "NodeRotateAnticlockwise": newNode = new NodeRotateAnticlockwise(); break;

                case "NodeRandom": newNode = new NodeRandom(); break;

                case "NodeHalt": newNode = new NodeHalt(); break;

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
