import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Controller {
    View view;
    Model model;
    Scene scene;
    List<Cube> selectedCubes;
    Queue<Creature> creaturesToAdd;
    CreatureLocationPopup currentPopup;

    /**
     * @param view The view that displays all the windows of the application.
     */
    public Controller(View view) {
        this.view = view;
        this.model = view.getModel();
        selectedCubes = new ArrayList<>();
        scene = view.getScene();
        selectedCubes = new ArrayList<>();
        creaturesToAdd = new ArrayDeque<>();
    }

    /**
     * @param tile The tile that is being clicked by the user
     */
    public void onTileClick(Tile tile) {
        if (creaturesToAdd.isEmpty()) {
            ChoiceBox clickOptions = (ChoiceBox) scene.lookup("#clickOptionsDropdown");
            String selectedOption = (String) clickOptions.getValue();
            ArrayList<Cube> cubes;
            Text numberOfSelectedCubes;
            switch (selectedOption) {
                case "Highlight":
                    if (selectedCubes.size() != 0) {
                        for (Cube cubeToUnhighlight: selectedCubes) {
                            cubeToUnhighlight.getTile().setHighlighted(true, false);
                            cubeToUnhighlight.getTile().setHighlighted(false, false);
                        }
                        selectedCubes.clear();
                    }
                    cubes = getCubesInShape(tile);
                    for (Cube cube : cubes) {
                        if (cube == cube.getTile().getCube()) {
                            cube.getTile().toggleStrongHighlight();
                        }
                    }
                    break;
                case "Select":
                    cubes = getCubesInShape(tile);
                    for (Cube cube : cubes) {
                        if (!(selectedCubes.contains(cube))) {
                            selectedCubes.add(cube);
                        }
                    }
                    for (Tile tileToUnhighlight: view.getMapView().getTiles()) {
                        tileToUnhighlight.setHighlighted(true, false);
                        tileToUnhighlight.setHighlighted(false, false);
                    }
                    for (Cube cube : selectedCubes) {
                        if (cube == cube.getTile().getCube()) {
                            cube.getTile().setHighlighted(true, true);
                        }
                        else {
                            cube.getTile().setHighlighted(false, true);
                        }
                    }
                    break;
                case "Unselect":
                    cubes = getCubesInShape(tile);
                    for (Cube cube : cubes) {
                        selectedCubes.remove(cube);
                    }
                    for (Tile tileToUnhighlight: view.getMapView().getTiles()) {
                        tileToUnhighlight.setHighlighted(true, false);
                        tileToUnhighlight.setHighlighted(false, false);
                    }
                    for (Cube cube : selectedCubes) {
                        if (cube == cube.getTile().getCube()) {
                            cube.getTile().setHighlighted(true, true);
                        }
                        else {
                            cube.getTile().setHighlighted(false, true);
                        }
                    }
                    break;
            }
            numberOfSelectedCubes = (Text) this.view.getScene().lookup("#numberOfSelectedCubes");
            numberOfSelectedCubes.setText("" + selectedCubes.size());
        } else {
            giveCreatureALocation(creaturesToAdd.poll(), tile);
        }
    }

    /**
     * @return Getter for cubes selected by the user
     */
    public List<Cube> getSelectedCubes() {
        return selectedCubes;
    }

    /**
     * @param selectedCubes Set the cubes selected by the user to the list of selected cubes
     */
    public void setSelectedCubes(List<Cube> selectedCubes) {
        this.selectedCubes = selectedCubes;
    }

    /**
     * @param tile The tile being clicked on by the user when highlighting a shape
     * @return An ArrayList of Cubes that are included in the highlighted shape
     */
    public ArrayList<Cube> getCubesInShape(Tile tile) {
        ChoiceBox shape = (ChoiceBox) scene.lookup("#shapeDropdown");
        String value = (String) shape.getValue();
        ArrayList<Cube> cubes;
        int[] coordinates = tile.getCube().getCoordinates();
        TextField radiusTextField = (TextField) scene.lookup("#radius");
        int radius;
        switch (value) {
            case "Tile":
                cubes = new ArrayList<>();
                cubes.add(tile.getCube());
                return cubes;
            case "Circle":
                try {
                    radius = Integer.parseInt(radiusTextField.getText());
                } catch (Exception e) {
                    radius = -5;
                }
                cubes = model.getMapModel().getCircle(radius, coordinates);
                return cubes;
            case "Cylinder":
                try {
                    radius = Integer.parseInt(radiusTextField.getText());
                } catch (Exception e) {
                    radius = -5;
                }
                cubes = model.getMapModel().getCylinder(radius, coordinates);
                return cubes;
            case "Sphere":
                try {
                    radius = Integer.parseInt(radiusTextField.getText());
                } catch (Exception e) {
                    radius = -5;
                }
                cubes = model.getMapModel().getSphere(radius, coordinates);
                return cubes;
            default:
                cubes = new ArrayList<>();
                return cubes;
        }
    }

    /**
     * @param isPlayer Check if creature is a player or not
     * @param hp HP of the creature
     * @param initiativeBonus The initiative bonus of the creature
     * @param walkSpeed The walk speed of the creature
     * @param swimSpeed The swim speed of the creature
     * @param flySpeed The fly speed of the creature
     * @param burrowSpeed The burrow speed of the creature
     * @param climbSpeed The climb speed of the creature
     * @param creatureType The creature type of the creature
     * @param attackDistances The attack distances that the creature has
     * @param name The name of the creature
     * @param displayName The name that will be displayed on the map (kept short to make it fit)
     * @param numberOfCreatures How many of this creature is on the map
     */
    public void addCreature(boolean isPlayer,
                            int hp,
                            int initiativeBonus,
                            int walkSpeed,
                            int swimSpeed,
                            int flySpeed,
                            int burrowSpeed,
                            int climbSpeed,
                            String creatureType,
                            int[] attackDistances,
                            String name,
                            String displayName,
                            int numberOfCreatures) {
        for (int i=1; i <= numberOfCreatures; i++) {
            Creature creature = null;
            String tempName = name;
            String tempDisplayName = displayName;
            if (numberOfCreatures > 1) {
                tempName = name + " " + i;
                tempDisplayName = displayName + i;
            }
            creature = new Creature(isPlayer, hp, initiativeBonus, walkSpeed,
                    swimSpeed, flySpeed, burrowSpeed,
                    climbSpeed, creatureType, attackDistances,
                    tempName, tempDisplayName);
            model.getMapModel().addCreatureToList(creature);
            creaturesToAdd.add(creature);
        }
        view.mapView.updateCreatures();
        this.popupCreatureLocationGiver(creaturesToAdd.peek());
    }

    /**
     * @param moveCostArray Check if there is a move cost to the terrain in the order of movements
     * @param isPassableArray Check if the terrain is passable in the order of movements
     * @param eventOnStartTurn Check if there are any events on the start of the turn when on the terrain
     * @param eventOnEnter Check if there are any events on entering the terrain
     * @param eventOnEndTurn Check if there are any events on the end of the turn when on the terrain
     * @param name Name of the terrain
     * @param color Color of the terrain
     */
    public void addTerrain(ArrayList<Integer> moveCostArray,
                           ArrayList<Boolean> isPassableArray,
                           boolean eventOnStartTurn,
                           boolean eventOnEnter,
                           boolean eventOnEndTurn,
                           String name,
                           Color color) {
        Terrain terrain = new Terrain(moveCostArray, isPassableArray,
                                    eventOnStartTurn, eventOnEnter,
                                    eventOnEndTurn, name, color);

        model.getMapModel().addTerrain(terrain);
        view.mapView.updateTerrain();

    }


    /**
     * @param creatureToAdd The creature that must be added to the map
     */
    private void popupCreatureLocationGiver(Creature creatureToAdd) {
        currentPopup = new CreatureLocationPopup(view.getStage(), creatureToAdd);

    }

    /**
     * @param creature The creature to be added to the map
     * @param tile The tile that the creature must be added to
     */
    private void giveCreatureALocation(Creature creature, Tile tile) {
        Cube baseCube = tile.getCube();
        TextField heightField = currentPopup.getHeightField();
        int height;
        try {
            height = Integer.parseInt(heightField.getText());
        } catch (NumberFormatException e) {
            height = 0;
        }
        int xPos = baseCube.getCoordinates()[0];
        int yPos = baseCube.getCoordinates()[1];
        int zPos = baseCube.getCoordinates()[2] + height/baseCube.getSideLength();
        if(zPos < 0) {
            zPos = 0;
        }
        if (zPos > model.getMapModel().getDimensions()[2]-1) {
            zPos = model.getMapModel().getDimensions()[2]-1;
        }
        Cube targetCube = model.getMapModel().getCube(xPos, yPos, zPos);
        model.getMapModel().moveCreature(creature, targetCube);
        currentPopup.hide();
        currentPopup = null;
        if(!(creaturesToAdd.isEmpty())) {
            popupCreatureLocationGiver(creaturesToAdd.peek());
        }
    }

    /**
     * @return Returns the scene being used by the controller
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @param scene Changes the scene being used by the controller
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Moves the selected creature to another space
     */
    public void moveSelectedCreature() {
        ChoiceBox creaturesDropdown = (ChoiceBox) view.getScene().lookup("#creaturesDropdown");
        Creature selectedCreature = (Creature) creaturesDropdown.getSelectionModel().getSelectedItem();
        creaturesToAdd.add(selectedCreature);
        popupCreatureLocationGiver(creaturesToAdd.peek());
    }

    /**
     * Deletes all creatures that have been selected
     */
    public void deleteSelectedCreature() {
        ChoiceBox creaturesDropdown = (ChoiceBox) view.getScene().lookup("#creaturesDropdown");
        Creature selectedCreature = (Creature) creaturesDropdown.getSelectionModel().getSelectedItem();
        model.getMapModel().deleteCreature(selectedCreature);
        view.mapView.updateCreatures();
    }

    /**
     * Applies the selected terrain to the cubes that have been selected
     */
    public void applySelectedTerrainToCubes() {
        ChoiceBox terrainsDropdown = (ChoiceBox) view.getScene().lookup("#terrainsDropdown");
        Terrain selectedTerrain = (Terrain) terrainsDropdown.getSelectionModel().getSelectedItem();
        model.getMapModel().addTerrainToCubes(selectedTerrain, selectedCubes);
    }

    /**
     * Removes the selected terrain from the cubes that have been selected
     */
    public void removeSelectedTerrain() {
        ChoiceBox terrainsDropdown = (ChoiceBox) view.getScene().lookup("#terrainsDropdown");
        Terrain selectedTerrain = (Terrain) terrainsDropdown.getSelectionModel().getSelectedItem();
        model.getMapModel().removeTerrain(selectedTerrain);
    }

    /**
     * Clears all selections and highlights
     */
    public void clearAllSelections() {
        selectedCubes.clear();
        for (Tile tile: view.getMapView().getTiles()) {
            tile.setHighlighted(true, false);
            tile.setHighlighted(false, false);
        }
        Text numberOfSelectedCubes = (Text) this.view.getScene().lookup("#numberOfSelectedCubes");
        numberOfSelectedCubes.setText("" + selectedCubes.size());
    }

    /**
     * Shifts the height of the map according to inputted value
     */
    public void shiftHeight() {
        TextField shiftHeightField = (TextField) scene.lookup("#shiftHeightField");
        int shiftHeight;
        try {
            shiftHeight = Integer.parseInt(shiftHeightField.getText());
        } catch (NumberFormatException e) {
            shiftHeight = 0;
        }

        List<Tile> tiles = view.getMapView().getTiles();
        shiftHeight = shiftHeight / tiles.get(0).getCube().getSideLength();
        for (Tile tile : tiles) {
            int[] coordinates = tile.getCube().getCoordinates();
            int zPos = coordinates[2] + shiftHeight;
            if(zPos < 0) {
                zPos = 0;
            }
            if (zPos > model.getMapModel().getDimensions()[2]-1) {
                zPos = model.getMapModel().getDimensions()[2]-1;
            }
            Cube cube = model.getMapModel().getCube(coordinates[0], coordinates[1], zPos);
            tile.setCube(cube);
            tile.update();
        }
        for (Tile tileToUnhighlight: view.getMapView().getTiles()) {
            tileToUnhighlight.setHighlighted(true, false);
            tileToUnhighlight.setHighlighted(false, false);
        }
        for (Cube cube : selectedCubes) {
            if (cube == cube.getTile().getCube()) {
                cube.getTile().setHighlighted(true, true);
            }
            else {
                cube.getTile().setHighlighted(false, true);
            }
        }
    }

    /**
     * Sets the height of the map to the inputted value
     */
    public void setHeight() {
        TextField setHeightField = (TextField) scene.lookup("#setHeightField");
        int setHeight;
        try {
            setHeight = Integer.parseInt(setHeightField.getText());
        } catch (NumberFormatException e) {
            return;
        }

        List<Tile> tiles = view.getMapView().getTiles();
        setHeight = setHeight / tiles.get(0).getCube().getSideLength();
        for (Tile tile : tiles) {
            int[] coordinates = tile.getCube().getCoordinates();
            int zPos = setHeight;
            if(zPos < 0) {
                zPos = 0;
            }
            if (zPos > model.getMapModel().getDimensions()[2]-1) {
                zPos = model.getMapModel().getDimensions()[2]-1;
            }
            Cube cube = model.getMapModel().getCube(coordinates[0], coordinates[1], zPos);
            tile.setCube(cube);
            tile.update();
        }
        for (Tile tileToUnhighlight: view.getMapView().getTiles()) {
            tileToUnhighlight.setHighlighted(true, false);
            tileToUnhighlight.setHighlighted(false, false);
        }
        for (Cube cube : selectedCubes) {
            if (cube == cube.getTile().getCube()) {
                cube.getTile().setHighlighted(true, true);
            }
            else {
                cube.getTile().setHighlighted(false, true);
            }
        }
    }
}
