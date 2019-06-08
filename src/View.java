import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class View extends Application {
    Model model;
    Scene scene;
    MapView mapView;
    Controller controller;

    public View() {
        this.model = new Model(20,20,21, this);
        controller = new Controller(this);
        mapView = new MapView(model, controller);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("D&D Combat Map");

        ArrayList<Integer> movement = new ArrayList<>();
        ArrayList<Boolean> passable = new ArrayList<>();
        Color color = Color.valueOf("BLUE");
        Color anotherColor = Color.valueOf("RED");
        Terrain testTerrain = new Terrain(movement, passable, false, false,
                false, "Blue", color);
        Terrain anotherTestTerrain = new Terrain(movement, passable, false, false,
                false, "Red", anotherColor);
        int[] attackDistances = {0, 0, 0};
        List<Cube> testCubes = new ArrayList<>();
        testCubes.add(model.getMapModel().getCube(3,3,0));
        Cube cubeWithIssues = model.getMapModel().getCube(3, 4, 0);
        testCubes.add(cubeWithIssues);
        testCubes.add(model.getMapModel().getCube(7,7,0));
        testCubes.add(model.getMapModel().getCube(3,5,0));
        testCubes.add(model.getMapModel().getCube(4,3,0));
        testCubes.add(model.getMapModel().getCube(8,19,0));
        testCubes.add(model.getMapModel().getCube(3,1,0));
        testCubes.add(model.getMapModel().getCube(5,4,0));
        testCubes.add(model.getMapModel().getCube(2,1,0));
        testCubes.add(model.getMapModel().getCube(5,1,0));
        Creature testCreature = new Creature(false, 0, 0, 0, 0,
                0, 0, 0, "Humanoid", attackDistances,
                "TestName", "T1");
        Creature anotherTestCreature = new Creature(false, 0, 0, 0, 0,
                0, 0, 0, "Humanoid", attackDistances,
                "TestName", "T2");
        Creature yetAnotherTestCreature = new Creature(false, 0, 0, 0, 0,
                0, 0, 0, "Humanoid", attackDistances,
                "TestName", "T3");
        model.getMapModel().addCreature(testCreature, model.getMapModel().getCube(3,4,1));
//        model.getMapModel().addCreature(anotherTestCreature, model.getMapModel().getCube(3,4,13));
//        model.getMapModel().addCreature(yetAnotherTestCreature, model.getMapModel().getCube(3,4,20));
        model.getMapModel().addTerrain(testTerrain, testCubes);
        model.getMapModel().addTerrain(anotherTestTerrain, testCubes);

        ArrayList<Terrain> terrainsList = model.getMapModel().getTerrains();
        mapView.updateKey(terrainsList);
        testCubes.get(0).getTile().setHighlighted(true);
        model.getMapModel().getCube(9,4,10).getTile().setHighlighted(true);

        scene = new Scene(mapView.getWindow(), 1000, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public Model getModel() {
        return model;
    }

    public MapView getMapView() {
        return mapView;
    }

    public Scene getScene() {
        return scene;
    }

    public ChoiceBox getTerrainsDropdown() {
        return (ChoiceBox) scene.lookup("#terrainsDropdown");
    }

    public ChoiceBox gethighlightsDropdown() {
        return (ChoiceBox) scene.lookup("#highlightsDropdown");
    }

    public ChoiceBox getclicksOptionsDropdown() {
        return (ChoiceBox) scene.lookup("#clicksOptionsDropdown");
    }

    public ChoiceBox getDisplayHeightField() {
        return (ChoiceBox) scene.lookup("#displayHeightField");
    }

    public static void main(String[] args) {
        launch(args);
    }


}

