import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;

public class Controller {
    View view;
    Model model;
    Scene scene;

    public Controller(View view) {
        this.view = view;
        this.model = view.getModel();
    }

    public void onTileClick(Tile tile) {
        tile.toggleHighlight();
    }
}
