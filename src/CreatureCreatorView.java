import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CreatureCreatorView {
    Scene scene;

    Controller controller;

    public CreatureCreatorView(Controller controller) {
        this.controller = controller;
    }

    public void createWindow() {
        Stage stage = new Stage();
        stage.setTitle("Create Creature");
        VBox creature = new VBox();
        creature.setAlignment(Pos.BASELINE_CENTER);
        creature.setSpacing(15);
        creature.setPadding(new Insets(10,10,10,10));
        CheckBox isPlayerCheckBox = new CheckBox();
        Label isPlayerLabel = new Label("Are you a player?");

        HBox isPlayerChoice = new HBox(5);
        isPlayerChoice.getChildren().addAll(isPlayerLabel, isPlayerCheckBox);
        TextField hp = new TextField();
        hp.setId("hp");
        hp.setPromptText("HP");
        TextField initiativeBonus = new TextField();
        initiativeBonus.setId("initiativeBonus");
        initiativeBonus.setPromptText("Initiative Bonus");
        TextField walkSpeed = new TextField();
        walkSpeed.setId("walkSpeed");
        walkSpeed.setPromptText("Walking Speed");
        TextField swimSpeed = new TextField();
        swimSpeed.setId("swimSpeed");
        swimSpeed.setPromptText("Swimming Speed");
        TextField flySpeed = new TextField();
        flySpeed.setId("flySpeed");
        flySpeed.setPromptText("Flying Speed");
        TextField burrowSpeed = new TextField();
        burrowSpeed.setId("burrowSpeed");
        burrowSpeed.setPromptText("Burrow Speed");
        TextField climbSpeed = new TextField();
        climbSpeed.setId("climbSpeed");
        climbSpeed.setPromptText("Climb Speed");
        TextField creatureType = new TextField();
        creatureType.setId("creatureType");
        creatureType.setPromptText("Creature Type");
        TextField name = new TextField();
        name.setId("name");
        name.setPromptText("Player Name");
        TextField displayName = new TextField();
        displayName.setId("displayName");
        displayName.setPromptText("Display Name");
        Button submit = new Button("Submit");
        submit.setOnAction(e -> clickSubmit());
        submit.setId("creatureSubmit");

        creature.getChildren().addAll(
                isPlayerChoice,
                hp,
                initiativeBonus,
                walkSpeed,
                swimSpeed,
                flySpeed,
                burrowSpeed,
                climbSpeed,
                creatureType,
                name,
                displayName,
                submit
        );

        scene = new Scene(creature, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void clickSubmit() {

    }
}
