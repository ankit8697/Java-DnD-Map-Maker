import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class View extends Application {
    Model model;

    public View() {
        this.model = new Model(20,20,21);
    }

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
        int yMax = 20;
        int xMax = 20;
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                Tile tile = new Tile(model.getMapModel().getCube(x,y,0), model.getMapModel());
//                tile.setStyle("-fx-background-color: yellow");
                grid.setHgrow(tile, Priority.ALWAYS);
                grid.setVgrow(tile, Priority.ALWAYS);
                tile.setAlignment(Pos.CENTER);
                grid.add(tile,x,y);
                if(x == 0) {
                    RowConstraints row = new RowConstraints();
                    row.setPercentHeight(100/yMax);
                    grid.getRowConstraints().add(row);
                }
            }
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/xMax);
            grid.getColumnConstraints().add(column);
        }

        ArrayList<Integer> movement = new ArrayList<>();
        ArrayList<Boolean> passable = new ArrayList<>();
        Color color = Color.valueOf("BLUE");
        Color anotherColor = Color.valueOf("RED");
        Terrain testTerrain = new Terrain(movement, passable, false, false, false, "Blue", color);
        Terrain anotherTestTerrain = new Terrain(movement, passable, false, false, false, "Red", anotherColor);
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
        Creature testCreature = new Creature(false, 0, 0, 0, 0, 0, 0, 0, "Humanoid", attackDistances, "TestName", "T1");
        Creature anotherTestCreature = new Creature(false, 0, 0, 0, 0, 0, 0, 0, "Humanoid", attackDistances, "TestName", "T2");
        Creature yetAnotherTestCreature = new Creature(false, 0, 0, 0, 0, 0, 0, 0, "Humanoid", attackDistances, "TestName", "T3");
        model.getMapModel().addCreature(testCreature, model.getMapModel().getCube(3,4,1));
        model.getMapModel().addCreature(anotherTestCreature, model.getMapModel().getCube(3,4,13));
        model.getMapModel().addCreature(yetAnotherTestCreature, model.getMapModel().getCube(3,4,20));
        model.getMapModel().addTerrain(testTerrain, testCubes);
        model.getMapModel().addTerrain(anotherTestTerrain, testCubes);


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

