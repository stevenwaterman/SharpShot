package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Movement> movements = new ArrayList<>();

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
            movements.add(new Movement(coord, newCoord));
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
                bullets.put(coordinate, newBullet);

                Coordinate newCoordinate = coordinate.plus(newBullet.getDirection());
                movements.add(new Movement(coordinate, newCoordinate));
            }
        }

        //Remove swapping bullets
        List<Movement> read = new ArrayList<>();
        Set<Coordinate> toDelete = new HashSet<>();
        for (Movement movement : movements) {
            Coordinate from = movement.getFrom();
            Coordinate to = movement.getTo();
            boolean foundSwaps = read.stream().anyMatch(other -> from.equals(other.getTo()) && to.equals(other.getFrom()));
            if(foundSwaps){
                toDelete.add(from);
                toDelete.add(to);
            }
            read.add(movement);
        }
        movements.removeIf(movement -> toDelete.contains(movement.getFrom()) || toDelete.contains(movement.getTo()));

        //Remove bullets that end in the same place
        read.clear();
        toDelete.clear();
        for (Movement movement : movements) {
            Coordinate from = movement.getFrom();
            Coordinate to = movement.getTo();
            boolean foundSameFinal = read.stream().anyMatch(other -> to.equals(other.getTo()));
            if(foundSameFinal){
                toDelete.add(to);
            }
            read.add(movement);
        }
        movements.removeIf(movement -> toDelete.contains(movement.getTo()));

        //Move bullets
        Map<Coordinate, Bullet> newBullets = new HashMap<>();
        for (Movement movement : movements) {
            newBullets.put(movement.getTo(), bullets.get(movement.getFrom()));
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

    private static <T> Set<T> collisions(List<T> collection) {
        Set<T> found = new HashSet<>();
        Set<T> banned = new HashSet<>();

        for (T elem : collection) {
            if (found.contains(elem)) {
                found.remove(elem);
                banned.add(elem);
            } else if (!banned.contains(elem)) {
                found.add(elem);
            }
        }

        return banned;
    }
}