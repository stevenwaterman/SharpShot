package com.durhack.sharpshot.nodes;

import com.durhack.sharpshot.*;
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

    /**
     * Bullets on top of node -> store
     * Other bullets -> tick
     * stored bullets -> process and output
     * all bullets -> check
     */
    public void tick(List<BigInteger> pendingInput) {
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

        // In nodes add input
        /*
        if(pendingInput.size() > 0) {
            for(Coordinate coordinate : getNodes().keySet()) {
                INode x = getNodes().get(coordinate);
                if(x instanceof NodeIn) {com.durhack.sharpshot.nodes
                    Map<Direction, BigInteger> bulletParams = ((NodeIn) x).into(pendingInput.get(0));

                    for (Map.Entry<Direction, BigInteger> newBulletEntry : bulletParams.entrySet()) {
                        Bullet newBullet = new Bullet(x.getRotation(), newBulletEntry.getValue());
                        Coordinate newCoordinate = coordinate.plus(newBullet.getDirection());
                        movements.add(new Movement(coordinate, newCoordinate));
                    }
                }
            }
            pendingInput.remove(0);
        }
        */

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
                while(dir != node.getRotation()) {
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
        List<SwapCollisionTest> swapCollisions = movements.stream().map(Movement::swapCollisionTest).collect(Collectors.toList());
        Set<SwapCollisionTest> toDeleteSwap = collisions(swapCollisions);
        for (SwapCollisionTest test: toDeleteSwap) {
            bullets.remove(test.getFrom());
            bullets.remove(test.getTo());
        }
        movements.removeAll(toDeleteSwap.stream().map(SwapCollisionTest::toMovement).collect(Collectors.toList()));

        //Remove bullets that will end in the same placecom.durhack.sharpshot.nodes
        List<FinalCollisionTest> finalCollisions = movements.stream().map(Movement::finalCollisionTest).collect(Collectors.toList());
        Set<FinalCollisionTest> toDeleteFinal = collisions(finalCollisions);
        for (FinalCollisionTest movement : toDeleteFinal) {
            bullets.remove(movement.getTo());
        }
        movements.removeAll(toDeleteFinal.stream().map(FinalCollisionTest::toMovement).collect(Collectors.toList()));

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
        for(INode n : getNodes().values())
            n.reset();
    }

    private static <T> Set<T> collisions(List<T> collection){
        Set<T> found = new HashSet<>();
        Set<T> banned = new HashSet<>();

        for (T elem : collection) {
            if(!banned.contains(elem)){
                if(found.contains(elem)){
                    found.remove(elem);
                    banned.add(elem);
                }
                else{
                    found.add(elem);
                }
            }
        }

        return banned;
    }

    public static class FinalCollisionTest {
        private Coordinate from;
        private Coordinate to;

        FinalCollisionTest(Coordinate from, Coordinate to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FinalCollisionTest collision = (FinalCollisionTest) o;
            return to.equals(collision.to);
        }

        @Override
        public int hashCode() {
            return to.hashCode();
        }

        public Coordinate getFrom() {
            return from;
        }

        Coordinate getTo() {
            return to;
        }

        Movement toMovement() {
            return new Movement(from, to);
        }
    }

    public static class Movement {
        private Coordinate from;
        private Coordinate to;

        Movement(Coordinate from, Coordinate to) {
            this.from = from;
            this.to = to;
        }

        SwapCollisionTest swapCollisionTest(){
            return new SwapCollisionTest(from, to);
        }

        FinalCollisionTest finalCollisionTest(){
            return new FinalCollisionTest(from, to);
        }

        Coordinate getFrom() {
            return from;
        }

        Coordinate getTo() {
            return to;
        }
    }

    public static class SwapCollisionTest {
        private Coordinate from;
        private Coordinate to;

        SwapCollisionTest(Coordinate from, Coordinate to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SwapCollisionTest collision = (SwapCollisionTest) o;
            return(from.equals(collision.to) && to.equals(collision.from));
        }

        @Override
        public int hashCode() {
            return from.hashCode() + to.hashCode();
        }

        Coordinate getFrom() {
            return from;
        }

        Coordinate getTo() {
            return to;
        }

        Movement toMovement() {
            return new Movement(from, to);
        }
    }
}