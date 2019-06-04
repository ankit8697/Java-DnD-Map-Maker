import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle("D&D Combat Map");
        BorderPane window = new BorderPane();
        Menu fileMenu = new Menu("File");
        Menu addMenu = new Menu("Add");
        Menu helpMenu = new Menu("Help");

        MenuItem addMenuPlayer = new MenuItem("Player");
        MenuItem addMenuMonster = new MenuItem("Monster");
        MenuItem addMenuTerrain = new MenuItem("Terrain");

        addMenu.getItems().addAll(addMenuPlayer, addMenuMonster, addMenuTerrain);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, addMenu, helpMenu);

        VBox options = new VBox();
        Label terrain = new Label("Terrain");
        Label highlight = new Label("Highlight");
        options.getChildren().addAll(terrain, highlight);


        VBox key = new VBox();
        Label keyLabel = new Label("Key");
        key.getChildren().addAll(keyLabel);

        GridPane grid = new GridPane();

        grid.setGridLinesVisible(true);
        for (int x = 0; x<20; x++) {
            for (int y = 0; y < 20; y++) {
                Button btn = new Button();
                grid.setHgrow(btn, Priority.ALWAYS);
                grid.setVgrow(btn, Priority.ALWAYS);
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setMaxHeight(Double.MAX_VALUE);
                grid.add(btn,x,y);
            }
        }

        window.setTop(menuBar);
        window.setLeft(options);
        window.setCenter(grid);
        window.setRight(key);
        primaryStage.setScene(new Scene(window, 1000, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
