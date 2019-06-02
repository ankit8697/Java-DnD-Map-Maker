import java.util.*;
public class Terrain {
    ArrayList<Integer> moveCostArray;
    ArrayList<Boolean> isPassableArray;
    boolean eventOnStartTurn;
    boolean eventOnEnter;
    boolean eventOnEndTurn;
    String name;
    ArrayList<Cube> currentLocations;

    public Terrain(ArrayList<Integer> moveCostArray,
                   ArrayList<Boolean> isPassableArray,
                   boolean eventOnStartTurn,
                   boolean eventOnEnter,
                   boolean eventOnEndTurn,
                   String name,
                   ArrayList<Cube> currentLocations) {
        this.moveCostArray = moveCostArray;
        this.isPassableArray = isPassableArray;
        this.eventOnStartTurn = eventOnStartTurn;
        this.eventOnEnter = eventOnEnter;
        this.eventOnEndTurn = eventOnEndTurn;
        this.name = name;
        this.currentLocations = currentLocations;
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
        this.currentLocations.remove(cube);
    }

    public void delete() {
        for (Cube cube : currentLocations) {
            cube.removeFromListOfTerrain(this);
        }
    }

}