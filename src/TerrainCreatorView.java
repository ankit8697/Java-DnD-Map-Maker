import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class TerrainCreatorView {
    Scene scene;
    public void createWindow() {
        Stage stage = new Stage();
        stage.setTitle("Create Creature");
        VBox creature = new VBox();
        creature.setAlignment(Pos.BASELINE_CENTER);
        creature.setSpacing(15);
        creature.setPadding(new Insets(10,10,10,10));



        scene = new Scene(creature, 400, 600);
        stage.setScene(scene);
        stage.show();
    }
}
