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
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Help");
        BorderPane window = new BorderPane();

        Text helpTitle = new Text("Help");
        helpTitle.setTextAlignment(TextAlignment.CENTER);
        helpTitle.setFont(Font.font("Helvetica", FontWeight.BOLD));

    }
}
