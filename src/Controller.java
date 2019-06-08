import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;

public class Controller {
    View view;
    Model model;
    Scene scene;

    public Controller() {
        this.view = new View();
        this.model = this.view.getModel();
    }


}
