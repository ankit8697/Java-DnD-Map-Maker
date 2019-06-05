import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;


public class Tile extends Button {

    private Cube cube;
    private StackPane display;
    private MapModel map;

    private Tile(Cube cube, Node display, MapModel map) {
        super("", display);
        this.display = (StackPane) display;
        this.cube = cube;
        this.map = map;
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
        display.getChildren().removeAll();
        this.updateTerrains(terrains);
        this.updateCreatures(creatures);
    }

    private void updateTerrains(List<Terrain> terrains) {
        StackPane displayPane = new StackPane();
        int numTerrains = terrains.size();
        double scaleFactor = Math.sqrt(numTerrains-1)/Math.sqrt(numTerrains);
        for (int i = 0; i < numTerrains; i++) {
            double scale = Math.pow(scaleFactor, i);
            Rectangle shape = new Rectangle();
            shape.widthProperty().bind(this.widthProperty().multiply(scale));
            shape.heightProperty().bind(this.heightProperty().multiply(scale));
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
            String displayString = displayName + " : " + deltaHeight;
            displayBox.getChildren().add(new Text(displayString));
        }
        displayBox.setAlignment(Pos.CENTER);
        display.getChildren().add(displayBox);
    }


}
