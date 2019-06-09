import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;


public class Tile extends Button {

    private Cube cube;
    private StackPane display;
    private MapModel map;
    private boolean isStrongHighlighted;
    private boolean isWeakHighlighted;
    private static final double STRONG_HIGHLIGHT_STRENGTH = 0.5;
    private static final double WEAK_HIGHLIGHT_STRENGTH = 0.25;


    private Tile(Cube cube, Node display, MapModel map) {
        super("", display);
        this.display = (StackPane) display;
        this.cube = cube;
        this.map = map;
        isStrongHighlighted = false;
        this.setMinWidth(30);
        this.setMinHeight(30);
        this.setMaxHeight(Double.MAX_VALUE);
        this.setMaxWidth(Double.MAX_VALUE);
        this.setStyle("-fx-border-color : rgba(0,0,0,0.5); -fx-border-width : 1;");
        int[] coordinates = cube.getCoordinates();
        List<Cube> column = map.getColumn(coordinates[0], coordinates[1]);
        for (Cube cubeInColumn : column) {
            cubeInColumn.setTile(this);
        }
        this.update();
    }

    public Tile(Cube cube, MapModel map) {
        this(cube, new StackPane(), map);
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        int[] coordinates = cube.getCoordinates();
        List<Cube> column = map.getColumn(coordinates[0], coordinates[1]);
        for (Cube cubeInColumn : column) {
            cubeInColumn.setTile(null);
        }
        this.cube = cube;
        coordinates = cube.getCoordinates();
        column = map.getColumn(coordinates[0], coordinates[1]);
        for (Cube cubeInColumn : column) {
            cubeInColumn.setTile(this);
        }
    }

    public void update() {
        List<Terrain> terrains = cube.getListOfTerrain();
        int[] coordinates = cube.getCoordinates();
        List<Creature> creatures = map.getCreaturesInColumn(coordinates[0], coordinates[1]);
        display.getChildren().clear();
        this.updateTerrains(terrains);
        this.updateCreatures(creatures);
        if (isStrongHighlighted) {
            this.isStrongHighlighted = false;
            this.highlight(true);
        }
        if (isWeakHighlighted) {
            this.isWeakHighlighted = false;
            this.highlight(false);
        }
    }

    private void updateTerrains(List<Terrain> terrains) {
        StackPane displayPane = new StackPane();
        int numTerrains = terrains.size();
        double scaleFactor = Math.sqrt(numTerrains-1)/Math.sqrt(numTerrains);
        for (int i = 0; i < numTerrains; i++) {
            double scale = Math.pow(scaleFactor, i) * 1;
            Rectangle shape = new Rectangle();
            shape.widthProperty().bind(this.widthProperty().subtract(2).multiply(scale));
            shape.heightProperty().bind(this.heightProperty().subtract(2).multiply(scale));
            shape.setFill(terrains.get(i).getColor());
            displayPane.getChildren().add(shape);
        }
        display.getChildren().add(displayPane);
    }

    private void updateCreatures(List<Creature> creatures) {
        VBox displayBox = new VBox();
        for (Creature creature : creatures) {
            String displayName = creature.getDisplayName();
            int creatureLocation = creature.getCurrentLocation().getCoordinates()[2];
            int cubeLocation = cube.getCoordinates()[2];
            int deltaZ = (creatureLocation - cubeLocation);
            int deltaHeight = deltaZ * cube.getSideLength();
            String displayString = displayName + ": " + deltaHeight;
            Text displayableData = new Text(displayString);
            displayableData.setStyle("-fx-stroke: white; -fx-stroke-width: 1");
            displayableData.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
            displayBox.getChildren().add(displayableData);
        }
        displayBox.setAlignment(Pos.CENTER);
        display.getChildren().add(displayBox);
    }

    private void highlight(boolean strong) {
        if (!(isWeakHighlighted || isStrongHighlighted)) {
            double opacity;
            if (strong) {
                opacity = STRONG_HIGHLIGHT_STRENGTH;
            } else {
                opacity = WEAK_HIGHLIGHT_STRENGTH;
            }
            Rectangle highlightBox = new Rectangle();
            highlightBox.setFill(Color.valueOf("HOTPINK"));
            highlightBox.setOpacity(opacity);
            highlightBox.widthProperty().bind(this.widthProperty().subtract(2));
            highlightBox.heightProperty().bind(this.heightProperty().subtract(2));
            display.getChildren().add(highlightBox);
        } else if (isWeakHighlighted && strong) {
            display.getChildren().get(display.getChildren().size() - 1).setOpacity(STRONG_HIGHLIGHT_STRENGTH);
        }
        if (strong) {
            isStrongHighlighted = true;
        } else {
            isWeakHighlighted = true;
        }
    }

    private void unhighlight() {
        if (isStrongHighlighted || isWeakHighlighted) {
            display.getChildren().remove(display.getChildren().size() - 1);
        }
        isStrongHighlighted = false;
        isWeakHighlighted = false;
    }

    private void removeStrongHighlight() {
        if (isWeakHighlighted) {
            display.getChildren().get(display.getChildren().size() - 1).setOpacity(WEAK_HIGHLIGHT_STRENGTH);
        } else {
            this.unhighlight();
        }
        isStrongHighlighted = false;
    }

    private void removeWeakHighlight() {
        if (!isStrongHighlighted) {
            this.unhighlight();
        }
        isWeakHighlighted = false;
    }

    public boolean isStrongHighlighted() {
        return isStrongHighlighted;
    }

    public void setHighlighted(boolean strong, boolean highlighted) {
        if (highlighted) {
            this.highlight(strong);
        }
        else {
            if (strong) {
                this.removeStrongHighlight();
            } else {
                this.removeWeakHighlight();
            }
        }
    }

    public void toggleStrongHighlight() {
        if (isStrongHighlighted) {
            this.unhighlight();
        }
        else {
            this.setHighlighted(true, true);
        }
    }

    public boolean isWeakHighlighted() {
        return isWeakHighlighted;
    }
}
