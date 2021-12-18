package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.DataModel.Contact;

public class ContactController {
    @FXML
    private TextField firstName, lastName, phoneNumber, notes;

    public Contact getNewContact(){
        String first = firstName.getText();
        String last = lastName.getText();
        String phone = phoneNumber.getText();
        String note = notes.getText();

        return new Contact(first, last, phone, note);

    }

    public void toUpdateField(Contact item){
        firstName.setText(item.getFirstName());
        lastName.setText(item.getLastName());
        phoneNumber.setText(item.getPhoneNumber());
        notes.setText(item.getNotes());
    }

    public Contact updatedThisContact(Contact item){
        item.setFirstName(firstName.getText());
        item.setLastName(lastName.getText());
        item.setNotes(notes.getText());
        item.setPhoneNumber(phoneNumber.getText());
        return item;
    }

}
