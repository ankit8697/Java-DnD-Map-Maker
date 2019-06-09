import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class MapView {
    Model model;
    MenuBar menuBar;
    VBox options;
    VBox key;
    GridPane grid;
    BorderPane window;
    Controller controller;
    ArrayList<Creature> creatures;
    ArrayList<Terrain> terrains;
    ChoiceBox terrainsDropdown;
    ChoiceBox creaturesDropdown;
    ObservableList<Creature> observableCreatureList;
    ObservableList<Terrain> observableTerrainList;

    public MapView(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        window = new BorderPane();
        Menu addMenu = new Menu("Add");
        Menu helpMenu = new Menu("Help");

        MenuItem addMenuCreature = new MenuItem("Creature");
        addMenuCreature.setOnAction(e -> {
            CreatureCreatorView creatureView = new CreatureCreatorView(controller);
            /*
              I made a new function to create the window as if we
              set it up like MapView then it creates an instance of itself
              immediately, which is not what we want.
             */
            creatureView.createWindow();
        });
        MenuItem addMenuTerrain = new MenuItem("Terrain");
        addMenuTerrain.setOnAction(e -> {
            TerrainCreatorView terrainView = new TerrainCreatorView(controller);
            terrainView.createWindow();
        });

        addMenu.getItems().addAll(addMenuCreature, addMenuTerrain);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(addMenu, helpMenu);

        options = new VBox(10);
        options.setPrefWidth(100);
        options.setAlignment(Pos.BASELINE_CENTER);
        options.setPadding(new Insets(0,10,0,5));

        Label optionsHeader = new Label("Options");
        optionsHeader.setStyle("-fx-underline: true");
        optionsHeader.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 20));
        optionsHeader.setAlignment(Pos.BASELINE_CENTER);
        Label terrain = new Label("Terrain");
        terrains = model.getMapModel().getTerrains();
        observableTerrainList = FXCollections.observableArrayList(terrains);
        terrainsDropdown = new ChoiceBox();
        terrainsDropdown.getItems().setAll(observableTerrainList);
        terrainsDropdown.setConverter(new StringConverter<Terrain>() {
            @Override
            public String toString(Terrain terrain) {
                if (terrain != null) {
                    return terrain.getName();
                }
                return "";
            }

            @Override
            public Terrain fromString(String string) {
                return null;
            }
        });
        terrainsDropdown.setId("terrainsDropdown");
        Label creature = new Label("Creature");
        creatures = model.getMapModel().getCreatures();
        observableCreatureList = FXCollections.observableArrayList(creatures);
        creaturesDropdown = new ChoiceBox();
        creaturesDropdown.getItems().setAll(observableCreatureList);
        creaturesDropdown.setConverter(new StringConverter<Creature>() {
            @Override
            public String toString(Creature creature) {
                if (creature != null) {
                    return creature.getName();
                }
                return "";
            }

            @Override
            public Creature fromString(String string) {
                return null;
            }
        });
        creaturesDropdown.setId("creaturesDropdown");
        Button applyToSelected = new Button("Apply");

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPadding(new Insets(10,0,5,0));
        separator1.setMaxWidth(80);

        applyToSelected.setId("applyToSelected");
        Label selectShapeLabel = new Label("Shape Selection");
        String shapeOptions[] = {"Tile", "Circle", "Cylinder", "Sphere"};
        ChoiceBox shapeDropdown = new ChoiceBox(FXCollections.observableArrayList(shapeOptions));
        shapeDropdown.setId("shapeDropdown");
        shapeDropdown.getSelectionModel().selectFirst();
        TextField radius = new TextField();
        radius.setPromptText("Radius (ft)");
        radius.setId("radius");

        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPadding(new Insets(10,0,5,0));
        separator2.setMaxWidth(80);

        Label displayHeight = new Label("Shift Height");
        displayHeight.setWrapText(true);
        TextField displayHeightField = new TextField();
        displayHeightField.setId("displayHeightField");
        Label setHeight = new Label("Set Height");
        TextField setHeightField = new TextField();
        Label clickLabel = new Label("Click Actions");
        String[] clicks = { "Move", "Highlight", "Select", "Unselect", "Delete"};
        ChoiceBox clickOptionsDropdown = new ChoiceBox(FXCollections.observableArrayList(clicks));
        clickOptionsDropdown.setId("clickOptionsDropdown");
        clickOptionsDropdown.getSelectionModel().select(1);
        Text selectedCubes = new Text("Selected: ");
        Text numberOfSelectedCubes = new Text();
        HBox selectedCubeCounter = new HBox();
        selectedCubeCounter.getChildren().addAll(selectedCubes, numberOfSelectedCubes);
        Button clearAll = new Button("Clear All");
        clearAll.setId("clearAll");
        options.getChildren().addAll(
                optionsHeader,
                clickLabel,
                clickOptionsDropdown,
                terrain,
                terrainsDropdown,
                creature,
                creaturesDropdown,
                applyToSelected,
                separator1,
                selectShapeLabel,
                shapeDropdown,
                radius,
                clearAll,
                separator2,
                displayHeight,
                displayHeightField,
                setHeight,
                setHeightField,
                selectedCubeCounter
        );

        key = new VBox();
        Label keyLabel = new Label("Key");
        key.setPadding(new Insets(0,10,0,10));
        key.setSpacing(5);
        List<Terrain> terrainsList = new ArrayList<>();
        updateKey(terrainsList);

        grid = new GridPane();
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

                tile.setOnAction(e -> controller.onTileClick(tile));
            }
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/xMax);
            grid.getColumnConstraints().add(column);
        }

        window.setTop(menuBar);
        window.setLeft(options);
        window.setCenter(grid);
        window.setRight(key);
    }

    public void updateKey(List<Terrain> terrainsList) {
        Label keyLabel = new Label("Key");
        keyLabel.setStyle("-fx-underline: true");
        keyLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 20));
        keyLabel.setAlignment(Pos.BASELINE_CENTER);
        key.getChildren().clear();
        key.getChildren().add(keyLabel);
        for (Terrain terrain : terrainsList) {
            HBox terrainBox = new HBox();
            Rectangle coloredSquare = new Rectangle(15,15);
            String displayString = " : " + terrain.getName();
            Text terrainName = new Text(displayString);
            coloredSquare.setFill(terrain.getColor());
            terrainBox.getChildren().addAll(coloredSquare, terrainName);
            key.getChildren().add(terrainBox);
        }
    }

    public Model getModel() {
        return model;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public VBox getOptions() {
        return options;
    }

    public VBox getKey() {
        return key;
    }

    public GridPane getGrid() {
        return grid;
    }

    public BorderPane getWindow() {
        return window;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void setOptions(VBox options) {
        this.options = options;
    }

    public void setKey(VBox key) {
        this.key = key;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public void setWindow(BorderPane window) {
        this.window = window;
    }

    public void updateCreatures() {
        this.observableCreatureList = FXCollections.observableArrayList(this.creatures);
        creaturesDropdown.setItems(this.observableCreatureList);
    }

    public void updateTerrain() {
        this.observableTerrainList = FXCollections.observableArrayList(this.terrains);
        terrainsDropdown.setItems(this.observableTerrainList);
    }

}
