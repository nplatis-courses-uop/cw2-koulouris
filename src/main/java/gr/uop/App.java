package gr.uop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        ListView<String> left = new ListView<>();
        for(int i = 0; i <= 30; i++){
            left.getItems().add("Item "+i);
        }
        left.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ListView<String> right = new ListView<>();
        right.setMaxHeight(left.getMaxHeight());
        Button toRight = new Button();
        Button toLeft = new Button();
        ImageView rightImageView = new ImageView(new Image(getClass().getResourceAsStream("images/right.png")));
        toRight.setGraphic(rightImageView);
        ImageView leftImageView = new ImageView(new Image(getClass().getResourceAsStream("images/left.png")));
        toLeft.setGraphic(leftImageView);
        Button Up = new Button();
        Button Down = new Button();
        ImageView upImageView = new ImageView(new Image(getClass().getResourceAsStream("images/up.png")));
        Up.setGraphic(upImageView);
        ImageView downImageView = new ImageView(new Image(getClass().getResourceAsStream("images/down.png")));
        Down.setGraphic(downImageView);
        TextField filter = new TextField();

        VBox LeftBox = new VBox();
        LeftBox.getChildren().add(filter);
        LeftBox.getChildren().add(left);
        LeftBox.setSpacing(10);
        VBox.setVgrow(filter, Priority.NEVER);
        VBox.setVgrow(left, Priority.ALWAYS);

        VBox middleButtons = new VBox();
        middleButtons.getChildren().add(toRight);
        middleButtons.getChildren().add(toLeft);
        middleButtons.setAlignment(Pos.BASELINE_CENTER);
        VBox.setVgrow(toRight, Priority.NEVER);
        VBox.setVgrow(toLeft, Priority.NEVER);

        VBox rightListButtons = new VBox();
        rightListButtons.getChildren().add(Up);
        rightListButtons.getChildren().add(Down);
        rightListButtons.setAlignment(Pos.BASELINE_CENTER);
        VBox.setVgrow(Up, Priority.NEVER);
        VBox.setVgrow(Down, Priority.NEVER);

        HBox mainBox = new HBox();
        mainBox.getChildren().add(LeftBox);
        mainBox.getChildren().add(middleButtons);
        mainBox.getChildren().add(right);
        mainBox.getChildren().add(rightListButtons);
        mainBox.setSpacing(10);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(10, 10, 50, 10));
        middleButtons.setAlignment(Pos.CENTER);
        rightListButtons.setAlignment(Pos.CENTER);        

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(mainBox);

        var scene = new Scene(mainPane, 640, 480);
        stage.setTitle("Lists");
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}