package simplemodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    private DoubleProperty age;
    public DoubleProperty ageDoubleProperty() {
        if (age == null) age = new SimpleDoubleProperty();
        return age;
    }
    public final void setAge(Double value) {
        ageDoubleProperty().set(value);
    }
    public final Double getAge() {
        return ageDoubleProperty().get();
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


    public Pet(String type, String name, double age, String owner){
        setName(name);
        setType(type);
        setAge(age);
        setOwner(owner);
    }

    public void increaseAge() {
        //if ((age*100)%100==11) age=(age%10)+1;
        //else age=age+0.1;
        //age++;
    }
}
