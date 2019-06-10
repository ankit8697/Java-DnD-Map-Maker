import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class View extends Application {
    Model model;
    Scene scene;
    MapView mapView;
    Controller controller;
    Stage stage;

    public View() {
        this.model = new Model(20,20,21, this);
        controller = new Controller(this);
//      Creates the map
        mapView = new MapView(model, controller);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("D&D Combat Map");

        scene = new Scene(mapView.getWindow(), 1050, 900);
        primaryStage.setScene(scene);
        controller.setScene(scene);
        this.stage = primaryStage;
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

    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }


}

