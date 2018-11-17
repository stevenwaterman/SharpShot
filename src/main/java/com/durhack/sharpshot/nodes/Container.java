package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.Bullet;
import com.durhack.sharpshot.Coordinate;
import com.durhack.sharpshot.Direction;
import com.durhack.sharpshot.INode;
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

    /**
     * Bullets on top of node -> store
     * Other bullets -> tick
     * stored bullets -> process and output
     * all bullets -> check
     */
    public void tick() {
        //Bullets on nodes
        Map<Coordinate, Bullet> capturedBullets = new HashMap<>(bullets);
        capturedBullets.keySet().retainAll(nodes.keySet());

        //Bullets not on nodes
        Map<Coordinate, Bullet> freeBullets = new HashMap<>(bullets);
        freeBullets.keySet().removeAll(capturedBullets.keySet());

        //Update free bullets
        Map<Coordinate, Bullet> newBullets = new HashMap<>();
        Set<Coordinate> collisions = new HashSet<>();

        for (Map.Entry<Coordinate, Bullet> entry : freeBullets.entrySet()) {
            Coordinate coordinate = entry.getKey();
            Bullet bullet = entry.getValue();

            if(newBullets.containsKey(coordinate)){
                collisions.add(coordinate);
            }
            else{
                newBullets.put(coordinate, bullet);
            }
        }

        //Update captured bullets
        for (Map.Entry<Coordinate, Bullet> entry : capturedBullets.entrySet()) {
            Coordinate coordinate = entry.getKey();
            INode node = nodes.get(coordinate);

            //TODO this brings great shame onto my family
            Direction bulletDirection = entry.getValue().getDirection();
            Direction dir = Direction.UP;
            while(dir != node.getRotation()){
                dir = dir.clockwise();
                bulletDirection = bulletDirection.antiClockwise();
            }
            Bullet bullet = new Bullet(bulletDirection, entry.getValue().getValue());

            Map<Direction, BigInteger> bulletParams = node.run(bullet);

            for (Map.Entry<Direction, BigInteger> newBulletEntry : bulletParams.entrySet()) {
                //TODO this too
                bulletDirection = newBulletEntry.getKey();
                dir = Direction.UP;
                while(dir != node.getRotation()){
                    dir = dir.clockwise();
                    bulletDirection = bulletDirection.clockwise();
                }
                Bullet newBullet = new Bullet(bulletDirection, newBulletEntry.getValue());

                Coordinate newCoordinate = coordinate.plus(newBullet.getDirection());

                if(newBullets.containsKey(newCoordinate)){
                    collisions.add(newCoordinate);
                }
                else{
                    newBullets.put(newCoordinate, newBullet);
                }
            }
        }

        //Remove collisions and move bullets
        newBullets.keySet().removeAll(collisions);
        bullets.clear();
        bullets.putAll(newBullets);
    }

    public int getWidth() {
        return width;
    }
}
