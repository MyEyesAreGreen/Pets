package simplemodel;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class PetEditDialog {
    public Pet pet;
    public Stage dialog;
    private ComboBox<String> typeEdit;
    private TextField nameEdit;
    private Spinner<Integer> yearEdit;
    private Spinner<Integer> monthEdit;
    private TextField ownerEdit;
    private ImageView image;
    public Font font;
    public GridPane root;
    private ButtonType result = ButtonType.CANCEL;

    public PetEditDialog(Pet pet) {
        this.pet = pet;
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit pet");

        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                root.setBackground(new Background(new BackgroundFill(Color.color(Math.random(), Math.random(), Math.random()), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        font = Font.font("Tahoma", FontWeight.NORMAL, 18);

        createComboBox();
        createNameText();
        createYearSpinner();
        createMonthSpinner();
        createOwnerText();
        createImage(pet);
        createButtons();

        Scene scene = new Scene(root, 600, 700);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void createComboBox() {
        Label typePet = new Label("Type:");
        typePet.setFont(font);
        root.add(typePet, 0, 0);
        typeEdit = new ComboBox<>();
        typeEdit.setStyle("-fx-font-size: 18 pt");
        typeEdit.getItems().addAll(
                "Cat",
                "Dog",
                "Hamster",
                "Mouse",
                "Rabbit",
                "Fish",
                "Horse",
                "Squirrel"
        );
        typeEdit.setValue(pet.getType());
        root.add(typeEdit, 1, 0);
    }

    private void createNameText() {
        Label namePet = new Label("Name:");
        namePet.setFont(font);
        root.add(namePet, 0, 1);
        nameEdit = new TextField();
        nameEdit.setFont(font);
        nameEdit.setText(pet.getName());
        root.add(nameEdit, 1, 1);
    }

    private void createYearSpinner() {
        Label AgePet = new Label("Year:");
        AgePet.setFont(font);
        root.add(AgePet, 0, 2);
        yearEdit = new Spinner<>(0, 50, pet.getYear(), 1);
        yearEdit.setEditable(false);
        yearEdit.setStyle("-fx-font-size: 18 pt");
        root.add(yearEdit, 1, 2);
    }
    private void createMonthSpinner() {
        Label AgePet = new Label("Month:");
        AgePet.setFont(font);
        root.add(AgePet, 0, 3);
        monthEdit = new Spinner<>(0, 11, pet.getMonth(), 1);
        monthEdit.setEditable(false);
        monthEdit.setStyle("-fx-font-size: 18 pt");
        root.add(monthEdit, 1, 3);
    }

    private void createOwnerText() {
        Label ownerPet = new Label("Owner name:");
        ownerPet.setFont(font);
        root.add(ownerPet, 0, 4);
        ownerEdit = new TextField();
        ownerEdit.setFont(font);
        ownerEdit.setText(pet.getOwner());
        root.add(ownerEdit, 1, 4);
    }

    private void fileDialog(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open your pictures");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Pictures", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(primaryStage);

        Label imgPet = new Label("Image:");
        imgPet.setFont(font);
        root.add(imgPet, 0, 5);
        image = new ImageView(new Image(file.toURI().toString()));
        image.setFitHeight(300);
        image.setFitWidth(300);
        root.add(image,1,5);
    }

    private void createImage(Pet pet){
        if (pet.getImageView() != null) {
            Label imgPet = new Label("Image:");
            imgPet.setFont(font);
            root.add(imgPet, 0, 5);
            image = pet.getImageView();
            root.add(image, 1, 5);
        }
    }

    private void createButtons() {
        Button btnOk = new Button("Ok");
        btnOk.setFont(font);
        btnOk.setOnAction((ActionEvent e) -> {
            if (isInputValid()) handleOk();
            else message();
        });
        root.add(btnOk, 0, 6);

        Button btnAddImg = new Button("Add Image");
        btnAddImg.setFont(font);
        btnAddImg.setOnAction((ActionEvent e) -> fileDialog(dialog));
        root.add(btnAddImg, 1, 6);

        Button btnCancel = new Button("Cancel");
        btnCancel.setFont(font);
        btnCancel.setOnAction((ActionEvent e) -> handleCancel());
        root.add(btnCancel, 2, 6);
    }

    private void message(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Data entry error");
        alert.setHeaderText("Name entry error");
        alert.setContentText("\"The pet's or owner's name consists of letters, numbers, spaces, +, -!!!\\n \"");
        alert.showAndWait();
    }

    private boolean isInputValid() {
        return (nameEdit.getText().matches("[a-zA-Z0-9&\\-+ ]+") ||
                ownerEdit.getText().matches("[a-zA-Z0-9&\\-+ ]+"));
    }

    private void handleOk() {
        pet.setType(typeEdit.getValue());
        pet.setName(nameEdit.getText());
        pet.setYear(yearEdit.getValue());
        pet.setMonth(monthEdit.getValue());
        pet.setOwner(ownerEdit.getText());
        pet.setImageView(image);
        result = ButtonType.OK;
        dialog.close();
    }

    private void handleCancel() {
        result = ButtonType.CANCEL;
        dialog.close();
    }

    public ButtonType getResult() { return result; }
}
