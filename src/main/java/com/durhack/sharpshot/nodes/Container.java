package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;

//TODO how do we deal with the container returning multiple outputs over multiple ticks?

public class Container implements INode {
    private int width;
    private int height;

    private Direction rotation = Direction.UP;

    @NotNull
    private Map<Coordinate, INode> nodes = new HashMap<>();

    @NotNull
    private Map<Coordinate, Bullet> bullets = new HashMap<>();

    public Container(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public boolean noBullets() {
        return bullets.isEmpty();
    }

    @Override
    public @NotNull Direction getRotation() {
        return rotation;
    }

    @Override
    public void rotateClockwise() {
        rotation = Direction.clockwiseOf(rotation);
    }

    @NotNull
    @Override
    public Map<Direction, BigInteger> run(@NotNull Bullet bullet) {
        return new HashMap<>();
    }

    @NotNull
    public Map<Coordinate, INode> getNodes() {
        return nodes;
    }

    @NotNull
    public Map<Coordinate, Bullet> getBullets() {
        return bullets;
    }

    public void start(List<BigInteger> input) {
        // In nodes add input
        Map<Coordinate, Bullet> newBullets = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            for (Coordinate coordinate : getNodes().keySet()) {
                INode x = getNodes().get(coordinate);
                if (x instanceof NodeIn && ((NodeIn) x).getIndex() == i) {
                    Map<Direction, BigInteger> bulletParams = ((NodeIn) x).into(input.get(i));

                    for (Map.Entry<Direction, BigInteger> newBulletEntry : bulletParams.entrySet()) {
                        Bullet newBullet = new Bullet(x.getRotation(), newBulletEntry.getValue());
                        Coordinate newCoordinate = coordinate.plus(newBullet.getDirection());
                        newBullets.put(newCoordinate, newBullet);
                    }
                }
            }
        }

        bullets.putAll(newBullets);
    }

    /**
     * Bullets on top of node -> store
     * Other bullets -> tick
     * stored bullets -> process and output
     * all bullets -> check
     */
    public void tick() {
        List<Pair<Movement, Bullet>> movements = new ArrayList<>();

        //Bullets on nodes
        Map<Coordinate, Bullet> capturedBullets = new HashMap<>(bullets);
        capturedBullets.keySet().retainAll(nodes.keySet());

        //Bullets not on nodes
        Map<Coordinate, Bullet> freeBullets = new HashMap<>(bullets);
        freeBullets.keySet().removeAll(capturedBullets.keySet());

        //Generate movements for free bullets
        for (Map.Entry<Coordinate, Bullet> entry : freeBullets.entrySet()) {
            Bullet bullet = entry.getValue();
            Coordinate coord = entry.getKey();
            Coordinate newCoord = entry.getKey().plus(bullet.getDirection()).wrap(width, height);
            movements.add(new Pair<>(new Movement(coord, newCoord), bullet));
        }

        //Update captured bullets
        for (Map.Entry<Coordinate, Bullet> entry : capturedBullets.entrySet()) {
            Coordinate coordinate = entry.getKey();
            INode node = nodes.get(coordinate);

            //TODO this brings great shame onto my family
            Direction bulletDirection = entry.getValue().getDirection();
            Direction dir = Direction.UP;
            while (dir != node.getRotation()) {
                dir = dir.clockwise();
                bulletDirection = bulletDirection.antiClockwise();
            }
            Bullet bullet = new Bullet(bulletDirection, entry.getValue().getValue());

            Map<Direction, BigInteger> bulletParams = node.run(bullet);

            for (Map.Entry<Direction, BigInteger> newBulletEntry : bulletParams.entrySet()) {
                //TODO this too
                bulletDirection = newBulletEntry.getKey();
                dir = Direction.UP;
                while (dir != node.getRotation()) {
                    dir = dir.clockwise();
                    bulletDirection = bulletDirection.clockwise();
                }

                Bullet newBullet = new Bullet(bulletDirection, newBulletEntry.getValue());
                Coordinate newCoordinate = coordinate.plus(newBullet.getDirection()).wrap(width, height);
                Movement movement = new Movement(coordinate, newCoordinate);
                movements.add(new Pair<>(movement, newBullet));
            }
        }

        //Remove swapping bullets
        List<Movement> read = new ArrayList<>();
        Set<Coordinate> toDelete = new HashSet<>();
        for (Pair<Movement, Bullet> pair : movements) {
            Movement movement = pair.getKey();
            Coordinate from = movement.getFrom();
            Coordinate to = movement.getTo();
            boolean foundSwaps = read.stream().anyMatch(other -> from.equals(other.getTo()) && to.equals(other.getFrom()));
            if(foundSwaps){
                toDelete.add(from);
                toDelete.add(to);
            }
            read.add(movement);
        }
        movements.removeIf(pair -> toDelete.contains(pair.getKey().getFrom()) || toDelete.contains(pair.getKey().getTo()));

        //Remove bullets that end in the same place
        read.clear();
        toDelete.clear();
        for (Pair<Movement, Bullet> pair: movements) {
            Movement movement = pair.getKey();
            Coordinate to = movement.getTo();
            boolean foundSameFinal = read.stream().anyMatch(other -> to.equals(other.getTo()));
            if(foundSameFinal){
                toDelete.add(to);
            }
            read.add(movement);
        }
        movements.removeIf(pair -> toDelete.contains(pair.getKey().getTo()));

        //Move bullets
        Map<Coordinate, Bullet> newBullets = new HashMap<>();
        for (Pair<Movement, Bullet> pair: movements) {
            Movement movement = pair.getKey();
            Bullet bullet = pair.getValue();
            newBullets.put(movement.getTo(), bullet);
        }
        bullets.clear();
        bullets.putAll(newBullets);
    }

    public int getWidth() {
        return width;
    }

    @Override
    public @NotNull Node toGraphic() {
        return new Rectangle(32.0, 32.0, Color.GREEN);
    }


    public void reset() {
        bullets.clear();
        for (INode n : getNodes().values())
            n.reset();
    }
}