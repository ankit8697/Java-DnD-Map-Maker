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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class HelpView {
    Scene scene;
    Stage stage;
    Controller controller;

    /*
    Constructor method for HelpView that will let it use the controller
     */
    public HelpView(Controller controller) {
        this.controller = controller;
    }

    /*
    createWindow opens the HelpMenu when it is called. Method returns nothing
     */
    public void createWindow() {
        stage = new Stage();
        stage.setTitle("Help");
        VBox help = new VBox();
        help.setAlignment(Pos.BASELINE_CENTER);
        help.setLayoutY(-60);
        Text addMessage = new Text("How to Use the Map:\n" +
                "    You can add creatures or terrains to the map by clicking the add button and selecting which type you'd like to add.\n" +
                "    After filling out the form that pops up with your desired information (note, for display purposes it is best if your\n" +
                "    creature display name is 1 letter max) and then choosing a height (for creatures only),\n" +
                "    your new creature/terrain will be added into the appropriate dropdown on the side. All terrains will additionally\n" +
                "    appear in the key with their name. From there you can use the apply/move/delete buttons to interact with the board.\n" +
                "    You can also highlight and select tiles in various ways from the sidebar. Finally, you can also shift or set the height\n" +
                "    of the map from the sidebar and everything on the map will adjust to reflect the change.");

        help.getChildren().addAll(
                addMessage
        );
        scene = new Scene(help, 900, 325);
        stage.setScene(scene);
        stage.show();
    }
}
