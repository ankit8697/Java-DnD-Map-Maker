import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CreatureCreatorView {
    Scene scene;
    CheckBox isPlayerCheckBox;
    boolean isPlayer;

    public CreatureCreatorView(boolean isPlayer) {
        isPlayerCheckBox = new CheckBox();
        this.isPlayer = isPlayer;
    }

    public void createWindow() {
        Stage stage = new Stage();
        stage.setTitle("Create Creature");
        VBox creature = new VBox();
        creature.setAlignment(Pos.BASELINE_CENTER);
        creature.setSpacing(15);
        creature.setPadding(new Insets(10,10,10,10));

        Label isPlayerLabel = new Label("Are you a player?");
        isPlayerCheckBox.setSelected(isPlayer);
        HBox isPlayerChoice = new HBox(5);
        isPlayerChoice.getChildren().addAll(isPlayerLabel, isPlayerCheckBox);
        TextField hp = new TextField();
        hp.setPromptText("HP");
        TextField initiativeBonus = new TextField();
        initiativeBonus.setPromptText("Initiative Bonus");
        TextField walkSpeed = new TextField();
        walkSpeed.setPromptText("Walking Speed");
        TextField swimSpeed = new TextField();
        swimSpeed.setPromptText("Swimming Speed");
        TextField flySpeed = new TextField();
        flySpeed.setPromptText("Flying Speed");
        TextField burrowSpeed = new TextField();
        burrowSpeed.setPromptText("Burrow Speed");
        TextField climbSpeed = new TextField();
        climbSpeed.setPromptText("Climb Speed");
        TextField creatureType = new TextField();
        creatureType.setPromptText("Creature Type");
        TextField name = new TextField();
        name.setPromptText("Player Name");
        TextField displayName = new TextField();
        displayName.setPromptText("Display Name");
        Button submit = new Button("Submit");
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
}
