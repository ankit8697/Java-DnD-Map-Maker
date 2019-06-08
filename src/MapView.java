import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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

    public MapView(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        window = new BorderPane();

        Menu fileMenu = new Menu("File");
        Menu addMenu = new Menu("Add");
        Menu helpMenu = new Menu("Help");

        MenuItem addMenuPlayer = new MenuItem("Player");
        MenuItem addMenuMonster = new MenuItem("Monster");
        MenuItem addMenuTerrain = new MenuItem("Terrain");

        addMenu.getItems().addAll(addMenuPlayer, addMenuMonster, addMenuTerrain);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, addMenu, helpMenu);

        options = new VBox(10);
        options.setPrefWidth(100);
        options.setAlignment(Pos.BASELINE_CENTER);
        options.setPadding(new Insets(0,5,0,5));

        Label terrain = new Label("Terrain");
        String terrainOptions[] = {"Forest", "Ocean"};
        ChoiceBox terrainsDropdown = new ChoiceBox(FXCollections.observableArrayList(terrainOptions));
        terrainsDropdown.setId("terrainsDropdown");
        Label selectShapeLabel = new Label("Shape Selection");
        String shapeOptions[] = {"Tile", "Circle", "Cylinder", "Sphere"};
        ChoiceBox shapeDropdown = new ChoiceBox(FXCollections.observableArrayList(shapeOptions));
        shapeDropdown.setId("shapeDropdown");
        shapeDropdown.getSelectionModel().selectFirst();
        Label displayHeight = new Label("Shift Height");
        displayHeight.setWrapText(true);
        TextField displayHeightField = new TextField();
        displayHeightField.setId("displayHeightField");
        Label setHeight = new Label("Set Height");
        TextField setHeightField = new TextField();
        Label clickLabel = new Label("Click Actions");
        String[] clicks = { "Move", "Highlight", "Select",  "Delete"};
        ChoiceBox clickOptionsDropdown = new ChoiceBox(FXCollections.observableArrayList(clicks));
        clickOptionsDropdown.setId("clickOptionsDropdown");
        options.getChildren().addAll(
                clickLabel,
                clickOptionsDropdown,
                terrain,
                terrainsDropdown,
                selectShapeLabel,
                shapeDropdown,
                displayHeight,
                displayHeightField,
                setHeight,
                setHeightField
        );

        key = new VBox();
        Label keyLabel = new Label("Key");
        key.setPadding(new Insets(0,5,0,5));
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
}
