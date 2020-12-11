package simplemodel;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ViewPet {
    private Pet pet;
    private GridPane grid;
    private Text typePet;
    private Text namePet;
    private Text agePet;
    private Text ownerPet;

    private void createPane(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Font font = Font.font("Tahoma", FontWeight.NORMAL, 24);

        typePet = new Text();
        typePet.setFont(font);
        GridPane.setHalignment(typePet, HPos.CENTER);
        grid.add(typePet, 0, 0, 2, 1);

        namePet = new Text();
        namePet.setFont(font);
        grid.add(namePet, 0, 1, 2, 1);

        Label agePetTitle = new Label("Age");
        agePetTitle.setFont(font);
        grid.add(agePetTitle, 0, 2);

        agePet = new Text();
        agePet.setFont(font);
        grid.add(agePet, 1, 2);

        ownerPet = new Text();
        ownerPet.setFont(font);
        grid.add(ownerPet, 0, 3, 2, 1);
    }

    private void addListenersPet(){
        typePet.textProperty().bind(pet.typeStringProperty());
        namePet.textProperty().bind(pet.nameStringProperty());
        agePet.textProperty().bind(pet.ageDoubleProperty().asString());
        ownerPet.textProperty().bind(pet.ownerStringProperty());
    }

    public GridPane getPane() {
        return grid;
    }

    public void setPet (Pet pet) {
        this.pet = pet;
        addListenersPet();
    }

     /*public void setInform(){
        namePet.setText(myPet.getName());
        typePet.setText(myPet.getType());
        agePet.setText(Double.toString(myPet.getAge()));
        ownerPet.setText(myPet.getOwner());
    }*/

    public ViewPet(Pet pet) {
        createPane();
        setPet(pet);
    }
}
