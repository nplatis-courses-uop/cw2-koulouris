package gr.uop;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        ArrayList<String> startingList = new ArrayList<>();
        for(int i = 0; i <= 30; i++){
            left.getItems().add("Item "+i);
            startingList.add(left.getItems().get(i));
        }
        left.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ListView<String> temp = new ListView<>();
        temp.getItems().addAll(left.getItems());

        ListView<String> right = new ListView<>();
        right.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
        filter.setPrefColumnCount(20);

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



        /*functionality*/
        toRight.setOnAction((e)->{    
            ObservableList<String> toMove = left.getSelectionModel().getSelectedItems();
            right.getItems().addAll(toMove);
            left.getItems().removeAll(toMove);
            temp.getItems().clear();
            temp.getItems().addAll(left.getItems());
        });
        toLeft.setOnAction((e)->{
            ObservableList<String> toMove = right.getSelectionModel().getSelectedItems();
            if(left.getItems().isEmpty()){
                left.getItems().add(0, toMove.get(0));
            }
            for(String item: toMove){
                if(left.getItems().contains(item)){
                    continue;
                }
                int startingIndex = startingList.indexOf(item);
                for(String leftItem: left.getItems()){
                    if(startingIndex < startingList.indexOf(leftItem)){
                        left.getItems().add(left.getItems().indexOf(leftItem), item);
                        break;
                    }
                }
                if(!left.getItems().contains(item)){//add it to the end
                    left.getItems().add(left.getItems().size(), item);
                }
            }

            temp.getItems().clear();
            temp.getItems().addAll(left.getItems());
            right.getItems().removeAll(toMove);
        });
        Up.setOnAction((e)->{
            ObservableList<String> toMove = right.getSelectionModel().getSelectedItems();
            ArrayList<String> toSelect = new ArrayList<>();
            for(String item: toMove){
                int index = right.getItems().indexOf(item);
                if(index > 0){
                    String tmp = right.getItems().get(index-1);
                    right.getItems().set(index-1, item);
                    right.getItems().set(index, tmp);
                    toSelect.add(item);
                }
            }
            right.getSelectionModel().clearSelection();
            for(String item:toSelect){
                right.getSelectionModel().select(item);
            }
        });
        Down.setOnAction((e)->{
            ObservableList<String> toMove = right.getSelectionModel().getSelectedItems();
            ArrayList<String> toSelect = new ArrayList<>();
            for(String item: toMove){
                int index = right.getItems().indexOf(item);
                if(index < right.getItems().size()-1){
                    String tmp = right.getItems().get(index+1);
                    right.getItems().set(index+1, item);
                    right.getItems().set(index, tmp);
                    toSelect.add(item);
                }
            }
            right.getSelectionModel().clearSelection();
            for(String item:toSelect){
                right.getSelectionModel().select(item);
            }
        });
       
        filter.textProperty().addListener((observable, oldValue, newValue)->{
            ListView<String> filteredList = new ListView<>();
            for(String item: temp.getItems()){
                if(item.contains(newValue)){
                    filteredList.getItems().add(item);
                }
            }

            left.getItems().clear();
            if(newValue.isEmpty() || newValue.isBlank()){
                left.getItems().addAll(temp.getItems());
            }
            else{
                left.getItems().addAll(filteredList.getItems());
            }
        });

  
        left.getItems().addListener((ListChangeListener<String>) change-> {
            while(change.next()){
                toRight.setDisable(left.getItems().isEmpty());
            }
        });
       
        right.getItems().addListener((ListChangeListener<String>) change-> {
            while(change.next()){//look for next changes
                toLeft.setDisable(right.getItems().isEmpty());
                Up.setDisable(right.getItems().isEmpty() || (right.getItems().size() == 1));
                Down.setDisable(right.getItems().isEmpty() || (right.getItems().size() == 1));
            }
        });
        right.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c->{
            while(c.next()){
                Up.setDisable(right.getSelectionModel().getSelectedIndices().contains(0));
            }
        });
        right.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c->{
            while(c.next()){
                Down.setDisable(right.getSelectionModel().getSelectedIndices().contains(right.getItems().size()-1));
            }
        });

        //initially, buttons for the right list are disabled
        Up.setDisable(true);
        Down.setDisable(true);
        toLeft.setDisable(true);
        /***************/

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