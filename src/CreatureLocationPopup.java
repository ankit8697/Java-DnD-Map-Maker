import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class CreatureLocationPopup extends Popup {
    Label labelInstructions;
    TextField heightField;
    Label heightFieldLabel;
    Stage owner;

    /**
     * @param owner The stage in which the popup shows up
     * @param creatureToAdd The creature that must be added to the tile that will be selected
     */
    public CreatureLocationPopup(Stage owner, Creature creatureToAdd) {
        VBox display = new VBox();
        labelInstructions = new Label("Click the tile " + creatureToAdd + " should display in");
        heightFieldLabel = new Label("Height: ");
        heightField = new TextField("0");
        heightField.setPromptText("Height");
        HBox heightDisplay = new HBox();
        heightDisplay.getChildren().addAll(heightFieldLabel, heightField);
        heightDisplay.setAlignment(Pos.BASELINE_CENTER);
        this.owner = owner;
        display.getChildren().addAll(labelInstructions, heightDisplay);
        this.getContent().add(display);
        display.setPadding(new Insets(5, 5, 5, 5));
        display.setStyle("-fx-background-color: yellow;" +
                         "-fx-border-color: black;" +
                         "-fx-border-width: 2px");
        this.show(owner, owner.getX()+owner.getWidth()/2 - 100, owner.getY() + 20);
    }

    /**
     * @return TextField that contains the height of the creature
     */
    public TextField getHeightField() {
        return heightField;
    }
}
