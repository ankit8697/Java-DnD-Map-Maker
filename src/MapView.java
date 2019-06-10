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
    private Model model;
    private MenuBar menuBar;
    private VBox options;
    private VBox key;
    private GridPane grid;
    private List<Tile> tiles;
    private BorderPane window;
    private Controller controller;
    private ArrayList<Creature> creatures;
    private ArrayList<Terrain> terrains;
    private ChoiceBox terrainsDropdown;
    private ChoiceBox creaturesDropdown;
    private ObservableList<Creature> observableCreatureList;
    private ObservableList<Terrain> observableTerrainList;

    public MapView(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        window = new BorderPane();
        tiles = new ArrayList<>();
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
        options.setPrefWidth(150);
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
        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPadding(new Insets(10,0,5,0));
        separator1.setMaxWidth(80);

        Button creatureMoveButton = new Button("Move");
        creatureMoveButton.setOnAction(event -> controller.moveSelectedCreature());
        Button creatureDeleteButton = new Button("Delete");
        creatureDeleteButton.setOnAction(event -> controller.deleteSelectedCreature());
        HBox creatureButtons = new HBox(10);
        creatureButtons.setAlignment(Pos.BASELINE_CENTER);
        creatureButtons.getChildren().addAll(creatureMoveButton, creatureDeleteButton);

        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        separator3.setPadding(new Insets(10,0,5,0));
        separator3.setMaxWidth(80);
        Button applyToSelected = new Button("Apply");
        applyToSelected.setOnAction(event -> controller.applySelectedTerrainToCubes());
        Button terrainDeleteButton = new Button("Delete");
        terrainDeleteButton.setOnAction(event -> controller.removeSelectedTerrain());
        HBox terrainButtons = new HBox(10);
        terrainButtons.setAlignment(Pos.BASELINE_CENTER);
        terrainButtons.getChildren().addAll(applyToSelected, terrainDeleteButton);
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

        Label shiftHeight = new Label("Shift Height");
        shiftHeight.setWrapText(true);
        TextField shiftHeightField = new TextField();
        shiftHeightField.setId("shiftHeightField");
        Label setHeight = new Label("Set Height");
        TextField setHeightField = new TextField();
        setHeightField.setId("setHeightField");
        Button applyShiftHeight = new Button("Apply");
        applyShiftHeight.setOnAction(e -> controller.shiftHeight());
        Button applySetHeight = new Button("Apply");
        applySetHeight.setOnAction(e -> controller.setHeight());


        Label clickLabel = new Label("Click Actions");
        String[] clicks = {"Select", "Unselect", "Highlight"};
        ChoiceBox clickOptionsDropdown = new ChoiceBox(FXCollections.observableArrayList(clicks));
        clickOptionsDropdown.setId("clickOptionsDropdown");
        clickOptionsDropdown.getSelectionModel().select(0);

        Text selectedCubes = new Text("Selected Cubes: ");
        Text numberOfSelectedCubes = new Text("0");
        numberOfSelectedCubes.setId("numberOfSelectedCubes");
        HBox selectedCubeCounter = new HBox();
        selectedCubeCounter.setAlignment(Pos.BASELINE_CENTER);
        selectedCubeCounter.getChildren().addAll(selectedCubes, numberOfSelectedCubes);

        Button clearAll = new Button("Clear Highlights");
        clearAll.setOnAction(event -> controller.clearAllSelections());
        clearAll.setId("clearAll");
        options.getChildren().addAll(
                optionsHeader,
                clickLabel,
                clickOptionsDropdown,
                selectShapeLabel,
                shapeDropdown,
                radius,
                clearAll,
                selectedCubeCounter,
                separator3,
                terrain,
                terrainsDropdown,
                terrainButtons,
                separator1,
                creature,
                creaturesDropdown,
                creatureButtons,
                separator2,
                shiftHeight,
                shiftHeightField,
                applyShiftHeight,
                setHeight,
                setHeightField,
                applySetHeight
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
                this.tiles.add(tile);
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

    public List<Tile> getTiles() {
        return tiles;
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
