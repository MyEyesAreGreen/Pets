package simplemodel;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PetEditDialog {
    private Pet pet;
    private Stage dialog;
    private ComboBox<String> typeEdit;
    private TextField nameEdit;
    private Spinner<Double> ageEdit;
    private TextField ownerEdit;
    private Font font;
    private GridPane root;
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
        font = Font.font("Tahoma", FontWeight.NORMAL, 20);
        createComboBox();
        createNameText();
        createSpinner();
        createOwnerText();
        createButtons();
        Scene scene = new Scene(root, 600, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    public ButtonType getResult() { return result; }

    private void createComboBox() {
        Label typePet = new Label("Type:");
        typePet.setFont(font);
        root.add(typePet, 0, 0);
        typeEdit = new ComboBox<>();
        typeEdit.setStyle("-fx-font-size: 24 pt");
        typeEdit.getItems().addAll(
                "Cat",
                "Dog",
                "Hamster",
                "Mouse",
                "Rabbit",
                "Fishes"
        );
        typeEdit.setValue(pet.getType());
        root.add(typeEdit, 1, 0);
    }

    private void createNameText() {
        Label namePet = new Label("Name:");
        namePet.setFont(font);
        root.add(namePet, 0, 1);
        nameEdit = new TextField();
        nameEdit.setFont(Font.font(24));
        nameEdit.setText(pet.getName());
        root.add(nameEdit, 1, 1);
    }

    private void createSpinner() {
        Label AgePet = new Label("Age:");
        AgePet.setFont(font);
        root.add(AgePet, 0, 2);
        ageEdit = new Spinner<>(1, 100, pet.getAge(), 0.1);
        ageEdit.setStyle("-fx-font-size: 24 pt");
        ageEdit.setEditable(true);
        root.add(ageEdit, 1, 2);
    }

    private void createOwnerText() {
        Label ownerPet = new Label("Owner name:");
        ownerPet.setFont(font);
        root.add(ownerPet, 0, 3);
        ownerEdit = new TextField();
        ownerEdit.setFont(Font.font(24));
        ownerEdit.setText(pet.getOwner());
        root.add(ownerEdit, 1, 3);
    }

    private void createButtons() {
        Button btnOk = new Button("Ok");
        btnOk.setFont(Font.font(24));
        root.add(btnOk, 0, 4);
        btnOk.setOnAction((ActionEvent e) -> {
            if (isInputValid()) handleOk();
            else message();
        });
        Button btnCancel = new Button("Cancel");
        btnCancel.setFont(Font.font(24));
        root.add(btnCancel, 1, 4);
        btnCancel.setOnAction((ActionEvent e) -> {
            handleCancel();
        });
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
        pet.setType(typeEdit.getValue().toString());
        pet.setName(nameEdit.getText());
        pet.setAge(ageEdit.getValue());
        pet.setOwner(ownerEdit.getText());
        result = ButtonType.OK;
        dialog.close();
    }

    private void handleCancel() {
        result = ButtonType.CANCEL;
        dialog.close();
    }
}
