package com.durhack.sharpshot.util;

import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import com.durhack.sharpshot.nodes.*;
import com.google.gson.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Serialiser {

    static class NodeData {
        public String type;
        public int x, y;
        public Direction rotation;
        public String extra;

        public NodeData(String type, int x, int y, Direction rotation, String extra) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.extra = extra;
        }
    }

    static class Data {
        public int width, height;
        public List<NodeData> nodes = new ArrayList<>();
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

    public static Container loadJSON(String json) {
        Gson gson = new GsonBuilder().create();
        Data data = gson.fromJson(json, Data.class);

        Container container = new Container(data.width, data.height);

        for(NodeData nd : data.nodes) {
            String type = nd.type;
            INode newNode;
            switch(type) {
                case "NodeIn":  newNode = new NodeIn(Integer.parseInt(nd.extra)); break;
                case "NodeOut": newNode = new NodeOut(); break;

                case "NodeAdd": newNode = new NodeAdd(); break;
                case "NodeSub": newNode = new NodeSub(); break;
                case "NodeMult": newNode = new NodeMult(); break;
                case "NodeDiv": newNode = new NodeDiv(); break;

                case "NodeBranch":   newNode = new NodeBranch(); break;
                case "NodeSplitter": newNode = new NodeSplitter(); break;

                case "NodeConstant": newNode = new NodeConstant(new BigInteger(nd.extra)); break;

                case "NodeVoid": newNode = new NodeVoid(); break;

                case "NodeIfPositive": newNode = new NodeIfPositive(); break;
                case "NodeIf0": newNode = new NodeIf0(); break;

                case "NodeRotateClockwise": newNode = new NodeRotateClockwise(); break;
                case "NodeRotateAnticlockwise": newNode = new NodeRotateAnticlockwise(); break;

                case "NodeRandom": newNode = new NodeRandom(); break;

                default:
                    throw new RuntimeException("This shouldn't happen, cannot read serialised node in Serialiser");
            }

            // wow.. lazy
            while(newNode.getRotation() != nd.rotation) newNode.rotateClockwise();

            container.getNodes().put(new Coordinate(nd.x, nd.y), newNode);
        }

        return container;
    }

    /*
    public static void main(String[] args) {
        Container container = new Container(50, 30);
        Map<Coordinate, INode> nodes = container.getNodes();
        nodes.put(new Coordinate(1, 2), new NodeAdd());
        nodes.put(new Coordinate(2, 2), new NodeBranch());
        nodes.put(new Coordinate(3, 2), new NodeConstant(new BigInteger("11")));
        NodeDiv div = new NodeDiv();
        div.rotateClockwise();
        nodes.put(new Coordinate(4, 2), div);
        nodes.put(new Coordinate(5, 2), new NodeIf0());
        nodes.put(new Coordinate(6, 2), new NodeIfPositive());
        nodes.put(new Coordinate(7, 2), new NodeIn(2));
        nodes.put(new Coordinate(8, 2), new NodeMult());
        nodes.put(new Coordinate(9, 2), new NodeOut());
        nodes.put(new Coordinate(10, 2), new NodeRotateAnticlockwise());
        nodes.put(new Coordinate(11, 2), new NodeRotateClockwise());
        nodes.put(new Coordinate(12, 2), new NodeSplitter());
        nodes.put(new Coordinate(13, 2), new NodeSub());
        nodes.put(new Coordinate(14, 2), new NodeVoid());

        System.out.println(loadJSON(getJSON(container)));
    }*/
}
