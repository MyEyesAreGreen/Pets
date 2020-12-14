package simplemodel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.stream.Collectors;


public class Main extends Application {
    private final ObservableList<Pet> data = FXCollections.observableArrayList();
    private final TableView<Pet> dataTableView  = new TableView<>();
    private String errorMessage = "";

    @Override
    public void start(Stage primaryStage) {
        if(!errorMessage.isEmpty()) showMessage(errorMessage);
        primaryStage.setTitle("List of pets");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5));
        root.setStyle("-fx-font-size: 18 pt");

        readDataFromFile(new File("src/main/resources/input.txt"));
        createTable();
        dataTableView.setItems(data);

        root.setCenter(dataTableView);
        root.setTop(new MenuBar(createFileMenu(), createEditMenu(), createShowMenu()));

        Scene scene = new Scene(root,1000,900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private  void showMessage(String message) {
        Alert messageAlert = new Alert(Alert.AlertType.WARNING,message, ButtonType.OK);
        messageAlert.showAndWait();
    }

    private void readDataFromFile(File dataFile) {
        try {
            data.clear();
            errorMessage="";
            BufferedReader in = new BufferedReader(new FileReader(dataFile));
            String str;
            while ((str = in.readLine()) != null) {
                try {
                    if(str.isEmpty()) break;
                    String[] dataArray = str.split(" +");
                    if ((dataArray.length < 5) || (dataArray.length > 6))
                        throw new Exception("wrong data");
                    String type = dataArray[0];
                    String name = dataArray[1];
                    int year = Integer.parseInt(dataArray[2]);
                    int month = Integer.parseInt(dataArray[3]);
                    String owner = dataArray[4];
                    Pet pet;
                    try{
                        ImageView img = new ImageView(dataArray[5]);
                        pet = new Pet(type, name, year, month, owner,img);
                    }
                    catch (Exception e){
                        pet = new Pet(type, name, year, month, owner);
                    }
                    data.add(pet);
                } catch (Exception e){
                    errorMessage+=e.getMessage()+"\n";
                    in.close();
                }
            }
            in.close();
        } catch (IOException e){ errorMessage+=e.getMessage()+"\n"; }
    }

    private void saveDataToFile(File dataFile) {
        try {
            FileWriter out = new FileWriter(dataFile);
            for (Pet pet : data) {
                out.write(pet.getType() + " " + pet.getName() + " "
                        + pet.getYear().toString() + " " + pet.getMonth().toString() + " "
                        + pet.getOwner() + " ");
                if (pet.getImageView() != null) out.write(pet.getImageView().getImage().getUrl() + "\n");
                else out.write("\n");
            }
            out.close();
        } catch (IOException e){ showMessage(e.getMessage()); }
    }

    private void handleFileOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Data File");
        File file = fileChooser.showOpenDialog(null);
        if (file == null) { return; }
        readDataFromFile(file);
        if(!errorMessage.isEmpty()) showMessage(errorMessage);
    }

    private void handleFileSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data File");
        File file = fileChooser.showSaveDialog(null);
        if (file == null) { return; }
        saveDataToFile(file);
    }

    private void handleButtonEdit() {
        Pet pet = dataTableView.getSelectionModel().getSelectedItem();
        if (pet != null) {
            PetEditDialog petEditDialog = new PetEditDialog(pet);
            if (petEditDialog.getResult()== ButtonType.OK) {
                data.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
            }
        } else { showMessage("No selected item!"); }
    }

    private void handleButtonAdd() {
        Pet pet = new Pet("", "", 0, 0, "");
        PetEditDialog petEditDialog = new PetEditDialog(pet);
        if (petEditDialog.getResult() == ButtonType.OK) {
            data.add(pet);
            data.sort((o1,o2)->o1.getName().compareToIgnoreCase(o2.getName()));
        }
    }

    private void handleButtonDelete() {
        int selectedIndex = dataTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) { dataTableView.getItems().remove(selectedIndex); }
        else { showMessage("No deleted item!"); }
    }

    private void handleButtonShow() {
        Pet pet = dataTableView.getSelectionModel().getSelectedItem();
        if (pet != null) { showPet(pet); }
        else { showMessage("No selected item!"); }
    }

    private void showPet(Pet pet) {
        Stage stage = new Stage();
        stage.setTitle("Show selected pet");
        BorderPane pane = new BorderPane();
        ViewPet viewPet = new ViewPet(pet);
        pane.setCenter(viewPet.getPane());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
        data.sort((o1,o2)->o1.getName().compareToIgnoreCase(o2.getName()));
    }

    @SuppressWarnings("unchecked")
    private void createTable() {
        TableColumn<Pet, Number> indexColumn = new TableColumn<>("#");
        indexColumn.setMinWidth(50);
        indexColumn.setSortable(false);
        indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(dataTableView.getItems().indexOf(column.getValue()) + 1));

        TableColumn<Pet, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setMinWidth(150);

        TableColumn<Pet, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(100);

        TableColumn<Pet, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearCol.setMinWidth(100);

        TableColumn<Pet, Integer> monthCol = new TableColumn<>("Month");
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthCol.setMinWidth(100);

        TableColumn<Pet, String> ownerCol = new TableColumn<>("Owner");
        ownerCol.setCellValueFactory(new PropertyValueFactory<>("owner"));
        ownerCol.setMinWidth(100);

        TableColumn<Pet, ImageView> firstColumn = new TableColumn<>("Images");
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        firstColumn.setMinWidth(300);

        TableColumn<Pet, Integer> dataCol = new TableColumn<>("Age");
        dataCol.getColumns().addAll(yearCol, monthCol);

        dataTableView.getColumns().setAll(indexColumn, nameCol, typeCol, dataCol, ownerCol, firstColumn);
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

    private Menu createFileMenu() {
        Menu menuFile = new Menu("File");
        MenuItem open = new MenuItem("Open");
        open.setOnAction((ActionEvent t) -> handleFileOpen());
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        MenuItem save = new MenuItem("Save");
        save.setOnAction((ActionEvent t) -> handleFileSave());
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> Platform.exit());
        exit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));

        menuFile.getItems().addAll(open, save, new SeparatorMenuItem(), exit);
        return menuFile;
    }

    private Menu createEditMenu() {
        Menu menuEdit = new Menu("Edit");
        MenuItem add = new MenuItem("Add pet");
        add.setOnAction((ActionEvent t) -> handleButtonAdd());
        MenuItem edit = new MenuItem("Edit pet");
        edit.setOnAction((ActionEvent t) -> handleButtonEdit());
        MenuItem del = new MenuItem("Delete pet");
        del.setOnAction((ActionEvent t) -> handleButtonDelete());

        menuEdit.getItems().addAll(add, edit, del);
        return menuEdit;
    }

    public Menu createShowMenu(){
        Menu menuShow = new Menu("Show");
        MenuItem about = new MenuItem("About pet");
        about.setOnAction((ActionEvent event) -> handleButtonShow());
        MenuItem filter = new MenuItem("Filter type pet");
        filter.setOnAction((ActionEvent e) -> {
            Pet pet = dataTableView.getSelectionModel().getSelectedItem();
            if (pet != null) {
                ObservableList<Pet> dataShow = FXCollections.observableArrayList();
                dataShow.setAll(data.stream().filter(myPet -> myPet.isTheSameType(pet)).collect(Collectors.toList()));
                dataTableView.setItems(dataShow);
            } else {
                showMessage("No selected item!");
            }
        });
        MenuItem showAll = new MenuItem("Show all pet");
        showAll.setOnAction((ActionEvent event) -> dataTableView.setItems(data));
        MenuItem inform = new MenuItem("Information");
        inform.setOnAction((ActionEvent t) -> informDialog());

        menuShow.getItems().addAll(about, filter, showAll, new SeparatorMenuItem(), inform);
        return menuShow;
    }

    public  static  void main (String[] args) {
        launch(args);
    }
}
