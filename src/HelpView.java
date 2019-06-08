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
    //@Override
    public HelpView(View view) {
        BorderPane window = new BorderPane();
        Text helpTitle = new Text("Help");
        helpTitle.setTextAlignment(TextAlignment.CENTER);
        helpTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        Text addMessage = new Text("If you would like to add a new player or monster to the map, " +
                "please click Add from the main screen and select the type of object you would like to add");
        Text leftBarMessage = new Text("From the left bar, you can interact with objects on the map from the Click Actions drop down," +
                "select a type of terrain or a shape to highlight, or change the current height of the map");
        Text tileDescription = new Text("All squares on the map will display any terrain affecting them.");
        Text rightBarMessage = new Text("On the right side of the map you can find all current terrains on the map" +
                "along with their names and colors");

    }
}
