import javafx.scene.paint.Color;
import java.util.*;

public class Terrain {
    private ArrayList<Integer> moveCostArray;
    private ArrayList<Boolean> isPassableArray;
    private boolean eventOnStartTurn;
    private boolean eventOnEnter;
    private boolean eventOnEndTurn;
    private String name;
    private Color color;
    private ArrayList<Cube> currentLocations;

    /**
     * @param moveCostArray The array of move costs of the terrain
     * @param isPassableArray The array of booleans about whether the terrain is passable for different movement types
     * @param eventOnStartTurn Boolean for whether the terrain triggers an event at the start of the turn
     * @param eventOnEnter Boolean for whether the terrain triggers an event when entering it
     * @param eventOnEndTurn Boolean for whether the terrain triggers an event at the end of the turn
     * @param name Name of the terrain
     * @param color Color of the terrain
     */
    public Terrain(ArrayList<Integer> moveCostArray,
                   ArrayList<Boolean> isPassableArray,
                   boolean eventOnStartTurn,
                   boolean eventOnEnter,
                   boolean eventOnEndTurn,
                   String name,
                   Color color) {
        this.moveCostArray = moveCostArray;
        this.isPassableArray = isPassableArray;
        this.eventOnStartTurn = eventOnStartTurn;
        this.eventOnEnter = eventOnEnter;
        this.eventOnEndTurn = eventOnEndTurn;
        this.name = name;
        this.currentLocations = new ArrayList<>();
        this.color = color;
    }

    public ArrayList<Boolean> getIsPassableArray() {
        return isPassableArray;
    }

    public void setIsPassableArray(ArrayList<Boolean> isPassableArray) {
        this.isPassableArray = isPassableArray;
    }

    public ArrayList<Integer> getMoveCostArray() {
        return moveCostArray;
    }

    public void setMoveCostArray(ArrayList<Integer> moveCostArray) {
        this.moveCostArray = moveCostArray;
    }

    public boolean isEventOnStartTurn() {
        return eventOnStartTurn;
    }

    public void setEventOnStartTurn(boolean eventOnStartTurn) {
        this.eventOnStartTurn = eventOnStartTurn;
    }

    public boolean isEventOnEnter() {
        return eventOnEnter;
    }

    public void setEventOnEnter(boolean eventOnEnter) {
        this.eventOnEnter = eventOnEnter;
    }

    public boolean isEventOnEndTurn() {
        return eventOnEndTurn;
    }

    public void setEventOnEndTurn(boolean eventOnEndTurn) {
        this.eventOnEndTurn = eventOnEndTurn;
    }

    public ArrayList<Cube> getCurrentLocations() {
        return currentLocations;
    }

    /**
     * @param currentLocations Tells the cubes that are in the terrain that they contain the terrain
     */
    public void setCurrentLocations(ArrayList<Cube> currentLocations) {
        for (Cube cube : currentLocations) {
            cube.removeFromListOfTerrain(this);
        }
        this.currentLocations = currentLocations;
        for (Cube cube : currentLocations) {
            cube.addToListOfTerrain(this);
        }
    }

    /**
     * @param cube The cube to which this terrain must be added
     */
    public void addToCurrentLocations(Cube cube) {
        cube.addToListOfTerrain(this);
        this.currentLocations.add(cube);
    }

    /**
     * @param cube The cube from which this terrain must be removed
     */
    public void removeFromCurrentLocations(Cube cube) {
        cube.removeFromListOfTerrain(this);
        this.currentLocations.remove(cube);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Deleting this terrain from all cubes that contain it
     */
    public void delete() {
        for (Cube cube : currentLocations) {
            cube.removeFromListOfTerrain(this);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}