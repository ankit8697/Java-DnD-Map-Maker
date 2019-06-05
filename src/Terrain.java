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

    public Terrain(ArrayList<Integer> moveCostArray,
                   ArrayList<Boolean> isPassableArray,
                   boolean eventOnStartTurn,
                   boolean eventOnEnter,
                   boolean eventOnEndTurn,
                   String name,
                   ArrayList<Cube> currentLocations,
                   Color color) {
        this.moveCostArray = moveCostArray;
        this.isPassableArray = isPassableArray;
        this.eventOnStartTurn = eventOnStartTurn;
        this.eventOnEnter = eventOnEnter;
        this.eventOnEndTurn = eventOnEndTurn;
        this.name = name;
        this.currentLocations = currentLocations;
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

    public void setCurrentLocations(ArrayList<Cube> currentLocations) {
        for (Cube cube : currentLocations) {
            cube.removeFromListOfTerrain(this);
        }
        this.currentLocations = currentLocations;
        for (Cube cube : currentLocations) {
            cube.addToListOfTerrain(this);
        }
    }

    public void addToCurrentLocations(Cube cube) {
        cube.addToListOfTerrain(this);
        this.currentLocations.add(cube);
    }

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