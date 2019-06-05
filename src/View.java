import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class View extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("D&D Combat Map");
        BorderPane window = new BorderPane();

        Menu fileMenu = new Menu("File");
        Menu addMenu = new Menu("Add");
        Menu helpMenu = new Menu("Help");

        MenuItem addMenuPlayer = new MenuItem("Player");
        MenuItem addMenuMonster = new MenuItem("Monster");
        MenuItem addMenuTerrain = new MenuItem("Terrain");

        addMenu.getItems().addAll(addMenuPlayer, addMenuMonster, addMenuTerrain);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, addMenu, helpMenu);

        VBox options = new VBox(10);
        options.setPrefWidth(100);
        options.setAlignment(Pos.BASELINE_CENTER);

        Label terrain = new Label("Terrain");
        String terrainOptions[] = {"Forest", "Ocean"};
        ChoiceBox terrains = new ChoiceBox(FXCollections.observableArrayList(terrainOptions));
        Label highlight = new Label("Highlight");
        String highlightOptions[] = {"Circle", "Square", "Cylinder", "Sphere"};
        ChoiceBox highlights = new ChoiceBox(FXCollections.observableArrayList(highlightOptions));
        Label displayHeight = new Label("Shift Display Height");
        TextField displayHeightField = new TextField();
        options.getChildren().addAll(
                terrain,
                terrains,
                highlight,
                highlights,
                displayHeight,
                displayHeightField
        );

        VBox key = new VBox();
        Label keyLabel = new Label("Key");
        key.getChildren().addAll(keyLabel);

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                Button btn = new Button();
//                btn.setStyle("-fx-background-color: yellow");
                grid.setHgrow(btn, Priority.ALWAYS);
                grid.setVgrow(btn, Priority.ALWAYS);
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setMaxHeight(Double.MAX_VALUE);
                grid.add(btn,x,y);

            }
        }

        window.setTop(menuBar);
        window.setLeft(options);
        window.setCenter(grid);
        window.setRight(key);
        primaryStage.setScene(new Scene(window, 1000, 900));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

