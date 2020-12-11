package simplemodel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class Main extends Application {

    private VBox vBox;
    private ImageView pic;
    private final Pet pet = new Pet("Squirrel","Mary",3, 4, "Nick",//);
            new Image("file:src/main/resources/Squirrel.jpg"));

    private void createSceneElements(){
        ViewPet viewPet = new ViewPet(pet);

        Image img = new Image("file:src/main/resources/Squirrel.jpg");
        pic = new ImageView(img);
        pic.setFitHeight(200);
        pic.setPreserveRatio(true);

        vBox = new VBox();
        vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(viewPet.getPane());
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Model-View-Controller");
        BorderPane root = new BorderPane();
        root.setStyle("-fx-font-size: 18 pt");

        createSceneElements();
        createCopyImgMenu();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFileMenu(), createEditMenu(), createColorMenu());

        root.setTop(menuBar);
        root.setCenter(vBox);
        root.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                vBox.setBackground(new Background(new BackgroundFill(Color.color(Math.random(), Math.random(), Math.random()), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        Scene scene = new Scene(root,600,700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void createCopyImgMenu(){
        ContextMenu cmCopyImg = new ContextMenu();
        MenuItem cmItem1 = new MenuItem("Copy Image");
        cmItem1.setOnAction((ActionEvent e) ->{
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putImage(pic.getImage());
            clipboard.setContent(content);
        });
        cmCopyImg.getItems().add(cmItem1);
        pic.setOnContextMenuRequested((ContextMenuEvent e) -> cmCopyImg.show(pic, e.getScreenX(), e.getScreenY()));
    }

    private Menu createFileMenu() {
        Menu menuFile = new Menu("File");
        MenuItem shuffle = new MenuItem("Return Text");
        shuffle.setOnAction((ActionEvent t) -> vBox.setVisible(true));
        MenuItem clear = new MenuItem("Clear");
        clear.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        clear.setOnAction((ActionEvent t) -> vBox.setVisible(false));
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> Platform.exit());

        menuFile.getItems().addAll(shuffle, clear, new SeparatorMenuItem(), exit);
        return menuFile;
    }

    private Menu createEditMenu() {
        Menu menuEdit = new Menu("Edit");
        MenuItem editData = new MenuItem("Edit Data");
        editData.setOnAction((ActionEvent t) -> new PetEditDialog(pet));
        MenuItem inform = new MenuItem("Information");
        inform.setOnAction((ActionEvent t) -> informDialog());
        menuEdit.getItems().addAll(editData,inform);
        return menuEdit;
    }

    private void informDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information about Author");
        alert.setHeaderText(null);
        alert.setContentText("The program was developed by MyEyesAreGreen in 2020 as part of DemidOnline " +
                "JavaFX professional development program.\n" + "It shows the main points of working with " +
                "menus, forms, fields, and properties. It may be useful for someone.!");
        alert.getDialogPane().setMinSize(300, 100);
        alert.showAndWait();
    }

    public Menu createColorMenu(){
        Menu menuColor = new Menu("Color");
        MenuItem pink = new MenuItem("Pink");
        pink.setOnAction((ActionEvent event) -> vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY))));
        MenuItem cyan = new MenuItem("Cyan");
        cyan.setOnAction((ActionEvent event) -> vBox.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY))));
        MenuItem green = new MenuItem("Green");
        green.setOnAction((ActionEvent event) -> vBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY))));
        MenuItem grey = new MenuItem("Grey");
        grey.setOnAction((ActionEvent event) -> vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY))));
        menuColor.getItems().addAll(pink, cyan, green, grey);
        return menuColor;
    }

    public  static  void main (String[] args) {
        launch(args);
    }
}
