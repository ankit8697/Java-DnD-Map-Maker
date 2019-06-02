import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Math;

public class Cube {
    private int[] coordinates; // bottom-left-back corner of the cube
    private int sideLength;
    private List<Creature> listOfCreatures; // List of all the creatures currently inside of the cube
    private List<Terrain> listOfTerrain; // any current terrain effects within the cube
    public static final int DEFAULT_SIDE_LENGTH = 5;

    public Cube(int x, int y, int z) {
        int[] coordinatesHolder = {x, y, z};
        coordinates = coordinatesHolder;
        sideLength = DEFAULT_SIDE_LENGTH;
        listOfCreatures = new ArrayList<Creature>();
        listOfTerrain = new ArrayList<Terrain>();
    }

    public List<Creature> getListOfCreatures() {
        return listOfCreatures;
    }

    public void setListOfCreatures(List<Creature> listOfCreatures) {
        this.listOfCreatures = listOfCreatures;
    }

    public List<Creature> addToListOfCreatures(Creature creature) {
        this.listOfCreatures.add(creature);
        return this.listOfCreatures;
    }

    public List<Creature> removeFromListOfCreatures(Creature creature) {
        this.listOfCreatures.remove(creature);
        return this.listOfCreatures;
    }

    public List<Terrain> getListOfTerrain() {
        return listOfTerrain;
    }

    public void setListOfTerrain(List<Terrain> listOfTerrain) {
        this.listOfTerrain = listOfTerrain;
    }

    public List<Terrain> addToListOfTerrain(Terrain terrain) {
        this.listOfTerrain.add(terrain);
        return this.listOfTerrain;
    }

    public List<Terrain> removeFromListOfTerrain(Terrain terrain) {
        this.listOfTerrain.remove(terrain);
        return this.listOfTerrain;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public double getEuclideanDistanceToLocation(int x, int y, int z) {
        double distance = Math.sqrt((x - this.coordinates[0]) ^ 2 +
                (y - this.coordinates[1]) ^ 2 +
                (z - this.coordinates[2]) ^ 2);
        return distance;
    }

    public int getDNDDistanceToLocation(int x, int y, int z) { // D&D does diagonals weirdly - don't ask
        ArrayList<Integer> distanceList = new ArrayList<Integer>();
        int[] distances = {x - this.coordinates[0], y - this.coordinates[1], z - this.coordinates[2]};
        for (int distance : distances) {
            distanceList.add(distance);
        }
        Collections.sort(distanceList);
        int maxDistance = distanceList.get(2);
        int secondLargestDistance = distanceList.get(1);
        int diagonalsPrice = (int) (this.sideLength * Math.floor(secondLargestDistance / (2 * this.sideLength)));
        int distance = maxDistance + diagonalsPrice;
        return distance;
    }
}