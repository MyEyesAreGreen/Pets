package simplemodel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Model-View-Controller");
        Pet pet = new Pet("Cat","Mary",3.2, "Nick");
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        ViewPet viewPet = new ViewPet(pet);
        root.getChildren().add(viewPet.getPane());
        Button btn = new Button("Edit");
        btn.setFont(Font.font(20));
        btn.setOnAction((event) -> {
            PetEditDialog petEditDialog = new PetEditDialog(pet);
        });
        root.getChildren().add(btn);

        Scene scene = new Scene(root,500,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public  static  void main (String[] args) {
        launch(args);
    }
}
