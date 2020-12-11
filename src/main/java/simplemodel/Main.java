package simplemodel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class Main extends Application {

    private VBox vBox;
    private ImageView pic;
    private final Pet pet = new Pet("Cat","Mary",3, 4, "Nick");

    private void createSceneElements(){
        ViewPet viewPet = new ViewPet(pet);

        pic = new ImageView();
        pic.setFitHeight(200);
        pic.setPreserveRatio(true);

        vBox = new VBox();
        vBox.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
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

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFileMenu(), createEditMenu());

        SplitMenuButton menuColor = createEditColorMenu();
        createCopyImgMenu();

        root.setTop(menuBar);
        root.setCenter(vBox);
        root.setBottom(menuColor);

        Scene scene = new Scene(root,500,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public SplitMenuButton createEditColorMenu(){
        MenuItem red = new MenuItem("Plum");
        red.setOnAction((ActionEvent event) -> vBox.setBackground(new Background(new BackgroundFill(Color.PLUM, CornerRadii.EMPTY, Insets.EMPTY))));
        MenuItem blue = new MenuItem("Tan");
        blue.setOnAction((ActionEvent event) -> vBox.setBackground(new Background(new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY))));
        MenuItem green = new MenuItem("Khaki");
        green.setOnAction((ActionEvent event) -> vBox.setBackground(new Background(new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY))));
        SplitMenuButton textColorMenu = new SplitMenuButton(red, blue, green);
        textColorMenu.setText("Select color");
        textColorMenu.setOnAction((ActionEvent event) -> vBox.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY))));
        return textColorMenu;
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
        alert.getDialogPane().setMinSize(500, 200);
        alert.showAndWait();
    }

    public  static  void main (String[] args) {
        launch(args);
    }
}
