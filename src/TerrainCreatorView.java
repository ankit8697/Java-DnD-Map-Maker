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
        stage.setTitle("Create Terrain");
        VBox terrain = new VBox();
        terrain.setAlignment(Pos.BASELINE_CENTER);
        terrain.setSpacing(15);
        terrain.setPadding(new Insets(10,10,10,10));

        TextField terrainName = new TextField();
        terrainName.setId("terrainName");
        terrainName.setPromptText("Terrain Name");

        CheckBox hasEventOnEnterCheckBox = new CheckBox();
        hasEventOnEnterCheckBox.setId("hasEventOnEnterCheckBox");
        Label hasEventOnEnterLabel = new Label("Has event on entering space?");
        CheckBox hasEventOnStartTurnCheckBox = new CheckBox();
        hasEventOnStartTurnCheckBox.setId("hasEventOnEnterCheckBox");
        Label hasEventOnStartTurnLabel = new Label("Has event on starting a turn in the space?");
        CheckBox hasEventOnEndTurnCheckBox = new CheckBox();
        hasEventOnEndTurnCheckBox.setId("hasEventOnEndTurnCheckBox");
        Label hasEventOnEndTurnLabel = new Label("Has event on ending a turn in the space?");

        Label moveTypeHeader = new Label("This terrain is passable for: ");
        CheckBox isPassableForWalkSpeed = new CheckBox();
        isPassableForWalkSpeed.setId("isPassableForWalkSpeed");
        Label isPassableForWalkSpeedLabel = new Label("Walk Speeds");
        CheckBox isPassableForSwimSpeed = new CheckBox();
        isPassableForSwimSpeed.setId("isPassableForSwimSpeed");
        Label isPassableForSwimSpeedLabel = new Label("Swim Speeds");
        CheckBox isPassableForClimbSpeed = new CheckBox();
        isPassableForClimbSpeed.setId("isPassableForClimbSpeed");
        Label isPassableForClimbSpeedLabel = new Label("Climb Speeds");
        CheckBox isPassableForBurrowSpeed = new CheckBox();
        isPassableForBurrowSpeed.setId("isPassableForBurrowSpeed");
        Label isPassableForBurrowSpeedLabel = new Label("Burrow Speeds");
        CheckBox isPassableForFlySpeed = new CheckBox();
        isPassableForFlySpeed.setId("isPassableForFlySpeed");
        Label isPassableForFlySpeedLabel = new Label("Fly Speeds");



        Button submit = new Button("Submit");
        submit.setOnAction(e -> clickSubmit());
        submit.setId("terrainSubmit");

        terrain.getChildren().addAll(
                terrainName,
                hasEventOnEnterLabel,
                hasEventOnEnterCheckBox,
                hasEventOnStartTurnLabel,
                hasEventOnStartTurnCheckBox,
                hasEventOnEndTurnLabel,
                hasEventOnEndTurnCheckBox,
                moveTypeHeader,
                isPassableForWalkSpeedLabel,
                isPassableForWalkSpeed,
                isPassableForSwimSpeedLabel,
                isPassableForSwimSpeed,
                isPassableForClimbSpeedLabel,
                isPassableForClimbSpeed,
                isPassableForBurrowSpeedLabel,
                isPassableForBurrowSpeed,
                isPassableForFlySpeedLabel,
                isPassableForFlySpeed,
                submit
        );



        scene = new Scene(terrain, 400, 600);
        stage.setScene(scene);
        stage.show();
    }
    public void clickSubmit() {

    }
}
