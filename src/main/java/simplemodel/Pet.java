package simplemodel;

import javafx.beans.property.*;

public class Pet {
    private StringProperty type;
    public StringProperty typeStringProperty() {
        if (type == null) type = new SimpleStringProperty();
        return type;
    }
    public final void setType(String value){
        typeStringProperty().set(value);
    }
    public final String getType(){
        return typeStringProperty().get();
    }

    private StringProperty name;
    public StringProperty nameStringProperty() {
        if (name == null) name = new SimpleStringProperty();
        return name;
    }
    public final void setName(String value){
        nameStringProperty().set(value);
    }
    public final String getName(){
        return nameStringProperty().get();
    }

    private IntegerProperty year;
    public IntegerProperty yearIntegerProperty() {
        if (year == null) year = new SimpleIntegerProperty();
        return year;
    }
    public final void setYear(Integer value) {
        yearIntegerProperty().set(value);
    }
    public final Integer getYear() {
        return yearIntegerProperty().get();
    }

    private IntegerProperty month;
    public IntegerProperty monthIntegerProperty() {
        if (month == null) month = new SimpleIntegerProperty();
        return month;
    }
    public final void setMonth(Integer value) {
        monthIntegerProperty().set(value);
    }
    public final Integer getMonth() {
        return monthIntegerProperty().get();
    }

    private StringProperty owner;
    public StringProperty ownerStringProperty() {
        if (owner == null) owner = new SimpleStringProperty();
        return owner;
    }
    public final void setOwner(String value){
        ownerStringProperty().set(value);
    }
    public final String getOwner(){
        return ownerStringProperty().get();
    }

    public Pet(String type, String name, int year, int month, String owner){
        setName(name);
        setType(type);
        setYear(year);
        setMonth(month);
        setOwner(owner);
    }
}
