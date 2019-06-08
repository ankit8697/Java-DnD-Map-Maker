import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    View view;
    Model model;
    Scene scene;
    List<Cube> selectedCubes;

    public Controller(View view) {
        this.view = view;
        this.model = view.getModel();
        selectedCubes = new ArrayList<>();
    }

    public void onTileClick(Tile tile) {
        tile.toggleStrongHighlight();
    }

    public List<Cube> getSelectedCubes() {
        return selectedCubes;
    }

    public void setSelectedCubes(List<Cube> selectedCubes) {
        this.selectedCubes = selectedCubes;
    }
}
