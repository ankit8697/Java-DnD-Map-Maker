import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreatureCreatorView {
    Scene scene;
    Stage stage;

    Controller controller;

    public CreatureCreatorView(Controller controller) {
        this.controller = controller;
    }

    public void createWindow() {
        stage = new Stage();
        stage.setTitle("Create Creature");
        VBox creature = new VBox();
        creature.setAlignment(Pos.BASELINE_CENTER);
        creature.setSpacing(15);
        creature.setPadding(new Insets(10,10,10,10));
        CheckBox isPlayerCheckBox = new CheckBox();
        isPlayerCheckBox.setId("isPlayerCheckBox");
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
        TextField attackDistances = new TextField();
        attackDistances.setPromptText("Largest Attack Distance");
        attackDistances.setId("attackDistances");
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
                attackDistances,
                name,
                displayName,
                submit
        );

        scene = new Scene(creature, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void clickSubmit() {
        CheckBox isPlayerCheckBox = (CheckBox) scene.lookup("#isPlayerCheckBox");
        boolean isPlayer = isPlayerCheckBox.isSelected();

        TextField hpTextField = (TextField) scene.lookup("#hp");
        int hp = Integer.parseInt(hpTextField.getText());

        TextField initiativeBonusTextField = (TextField) scene.lookup("#initiativeBonus");
        int initiativeBonus = Integer.parseInt(initiativeBonusTextField.getText());

        TextField walkSpeedTextField = (TextField) scene.lookup("#walkSpeed");
        int walkSpeed = Integer.parseInt(walkSpeedTextField.getText());

        TextField swimSpeedTextField = (TextField) scene.lookup("#swimSpeed");
        int swimSpeed = Integer.parseInt(swimSpeedTextField.getText());

        TextField flySpeedTextField = (TextField) scene.lookup("#flySpeed");
        int flySpeed = Integer.parseInt(flySpeedTextField.getText());

        TextField burrowSpeedTextField = (TextField) scene.lookup("#burrowSpeed");
        int burrowSpeed = Integer.parseInt(burrowSpeedTextField.getText());

        TextField climbSpeedTextField = (TextField) scene.lookup("#climbSpeed");
        int climbSpeed = Integer.parseInt(climbSpeedTextField.getText());

        TextField creatureTypeTextField = (TextField) scene.lookup("#creatureType");
        String creatureType = creatureTypeTextField.getText();

        TextField attackDistancesTextField = (TextField) scene.lookup("#attackDistances");
        int[] attackDistances = new int[3];
        attackDistances[0] = Integer.parseInt(attackDistancesTextField.getText());

        TextField nameTextField = (TextField) scene.lookup("#name");
        String name = nameTextField.getText();

        TextField displayNameTextField = (TextField) scene.lookup("#displayName");
        String displayName = displayNameTextField.getText();

        controller.addCreature(isPlayer, hp, initiativeBonus, walkSpeed,
                swimSpeed, flySpeed, burrowSpeed, climbSpeed, creatureType,
                attackDistances, name, displayName);

        stage.close();
    }
}
