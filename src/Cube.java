import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Math;

public class Cube {
    private int[] coordinates; // bottom-left-back corner of the cube
    private int sideLength;
    private List<Creature> listOfCreatures; // List of all the creatures currently inside of the cube
    private List<Terrain> listOfTerrain; // any current terrain effects within the cube
    private static final int DEFAULT_SIDE_LENGTH = 5;
    private Tile tile = null;

    public Cube(int x, int y, int z) {
        int[] coordinatesHolder = {x, y, z};
        coordinates = coordinatesHolder;
        sideLength = DEFAULT_SIDE_LENGTH;
        listOfCreatures = new ArrayList<>();
        listOfTerrain = new ArrayList<>();
    }

    public List<Creature> getListOfCreatures() {
        return listOfCreatures;
    }

    public void setListOfCreatures(List<Creature> listOfCreatures) {
        this.listOfCreatures = listOfCreatures;
        tile.update();
    }

    public List<Creature> addToListOfCreatures(Creature creature) {
        this.listOfCreatures.add(creature);
        tile.update();
        return this.listOfCreatures;
    }

    public List<Creature> removeFromListOfCreatures(Creature creature) {
        this.listOfCreatures.remove(creature);
        tile.update();
        return this.listOfCreatures;
    }

    public List<Terrain> getListOfTerrain() {
        return listOfTerrain;
    }

    public void setListOfTerrain(List<Terrain> listOfTerrain) {
        tile.update();
        this.listOfTerrain = listOfTerrain;
    }

    public List<Terrain> addToListOfTerrain(Terrain terrain) {
        this.listOfTerrain.add(terrain);
        tile.update();
        return this.listOfTerrain;
    }

    public List<Terrain> removeFromListOfTerrain(Terrain terrain) {
        this.listOfTerrain.remove(terrain);
        return this.listOfTerrain;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public int getSideLength() {
        return sideLength;
    }

    public double getEuclideanDistanceToLocation(int x, int y, int z) {
        double distance = Math.sqrt(Math.pow((x - this.coordinates[0]), 2) +
                Math.pow((y - this.coordinates[1]), 2) +
                Math.pow((z - this.coordinates[2]), 2));
        double feetDistance = distance * this.sideLength;
        return feetDistance;
    }

    public double getDNDDistanceToLocation(int x, int y, int z) { // D&D does diagonals weirdly - don't ask
        ArrayList<Integer> distanceList = new ArrayList<>();
        int[] distances = {x - this.coordinates[0], y - this.coordinates[1], z - this.coordinates[2]};
        for(int i = 0; i < distances.length; i++) {
            distances[i] = Math.abs(distances[i]);
        }
        for (int distance : distances) {
            distanceList.add(distance);
        }
        Collections.sort(distanceList);
        int maxDistance = distanceList.get(2) * sideLength;
        int secondLargestDistance = distanceList.get(1) * sideLength;
        int diagonalsPrice = (int) (this.sideLength * Math.floor(secondLargestDistance / (2 * this.sideLength)));
        int distance = maxDistance + diagonalsPrice;
        return (double) distance;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}