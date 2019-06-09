import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    public Controller(View view) {
        this.view = view;
        this.model = view.getModel();
        scene = view.getScene();
        selectedCubes = new ArrayList<>();
        creaturesToAdd = new ArrayDeque<>();
    }

    public void onTileClick(Tile tile) {
        if (creaturesToAdd.isEmpty()) {
            ChoiceBox clickOptions = (ChoiceBox) scene.lookup("#clickOptionsDropdown");
            String selectedOption = (String) clickOptions.getValue();
            switch (selectedOption) {
                case "Move":
                    break;
                case "Highlight":
                    ArrayList<Cube> cubes = getCubesInShape(tile);
                    for (Cube cube : cubes) {
                        if (cube == cube.getTile().getCube()) {
                            cube.getTile().toggleStrongHighlight();
                        }
                    }
                    break;
                case "Select":
                    break;
                case "Unselect":
                    break;
                case "Delete":
                    break;
            }
        } else {
            giveCreatureALocation(creaturesToAdd.poll(), tile);
        }
    }

    public List<Cube> getSelectedCubes() {
        return selectedCubes;
    }

    public void setSelectedCubes(List<Cube> selectedCubes) {
        this.selectedCubes = selectedCubes;
    }

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
        this.popupCreatureLocationGiver();
    }

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


    private void popupCreatureLocationGiver() {
        currentPopup = new CreatureLocationPopup(view.getStage());

    }

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
        Cube targetCube = model.getMapModel().getCube(xPos, yPos, zPos);
        model.getMapModel().moveCreature(creature, targetCube);
        currentPopup.hide();
        currentPopup = null;
        if(!(creaturesToAdd.isEmpty())) {
            popupCreatureLocationGiver();
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
