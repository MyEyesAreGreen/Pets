package simplemodel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class ViewPet {
    private final Pet pet;
    private GridPane grid;
    private Text typePet;
    private Text namePet;
    private Text yearPet;
    private Text monthPet;
    private Text ownerPet;

    private void createPane(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Font font = Font.font("Tahoma", FontWeight.NORMAL, 24);

        Label typePetTitle = new Label("Type: ");
        typePetTitle.setFont(font);
        grid.add(typePetTitle, 0, 0);
        typePet = new Text();
        typePet.setFont(font);
        typePet.setOnMouseEntered((MouseEvent me) -> typePet.setFill(Color.RED));
        typePet.setOnMouseExited((MouseEvent me) -> typePet.setFill(Color.BLACK));
        grid.add(typePet, 1, 0, 3, 1);

        Label namePetTitle = new Label("Name: ");
        namePetTitle.setFont(font);
        grid.add(namePetTitle, 0, 1);
        namePet = new Text();
        namePet.setFont(font);
        namePet.setOnMouseEntered((MouseEvent me) -> namePet.setFill(Color.RED));
        namePet.setOnMouseExited((MouseEvent me) -> namePet.setFill(Color.BLACK));
        grid.add(namePet, 1, 1, 3, 1);

        Label agePetTitle = new Label("Age: ");
        agePetTitle.setFont(font);
        grid.add(agePetTitle, 0, 2);
        yearPet = new Text();
        yearPet.setFont(font);
        yearPet.setOnMouseEntered((MouseEvent me) -> yearPet.setFill(Color.RED));
        yearPet.setOnMouseExited((MouseEvent me) -> yearPet.setFill(Color.BLACK));
        grid.add(yearPet, 1, 2);
        Label andPetTitle = new Label(" year(-s) and month(-s)");
        andPetTitle.setFont(font);
        grid.add(andPetTitle, 2, 2);
        monthPet = new Text();
        monthPet.setFont(font);
        monthPet.setOnMouseEntered((MouseEvent me) -> monthPet.setFill(Color.RED));
        monthPet.setOnMouseExited((MouseEvent me) -> monthPet.setFill(Color.BLACK));
        grid.add(monthPet, 3, 2);

        Label ownerPetTitle = new Label("Owner: ");
        ownerPetTitle.setFont(font);
        grid.add(ownerPetTitle, 0, 3);
        ownerPet = new Text();
        ownerPet.setFont(font);
        ownerPet.setOnMouseEntered((MouseEvent me) -> ownerPet.setFill(Color.RED));
        ownerPet.setOnMouseExited((MouseEvent me) -> ownerPet.setFill(Color.BLACK));
        grid.add(ownerPet, 1, 3, 3, 1);

        if (pet.getImageView() != null){
            ImageView imgPet = pet.getImageView();
            grid.add(imgPet,0,4,4,1);
        }
    }

    private void addListenersPet(){
        typePet.textProperty().bind(pet.typeStringProperty());
        namePet.textProperty().bind(pet.nameStringProperty());
        yearPet.textProperty().bind(pet.yearIntegerProperty().asString());
        monthPet.textProperty().bind(pet.monthIntegerProperty().asString());
        ownerPet.textProperty().bind(pet.ownerStringProperty());
    }

    public GridPane getPane() {
        return grid;
    }

    public ViewPet(Pet pet) {
        this.pet = pet;
        createPane();
        addListenersPet();
    }
}
