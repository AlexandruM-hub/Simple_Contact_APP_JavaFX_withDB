package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import sample.DataModel.Contact;
import sample.DataModel.ContactsList;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TableColumn<Contact, String> firstNameTableColumn, lastNameTableColumn, phoneTableColumn, notesTableColumn;

    @FXML
    private TableView<Contact> contactTableView;

    @FXML
    private BorderPane mainPanel;

    private ContactsList data;

    @FXML
    private ContextMenu contextMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellFactories();
        data = new ContactsList();
        data.getContactsFromDb();
        contactTableView.setItems(data.getContactsList());
        contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        contextMenu.getItems().add(deleteMenuItem);
        contactTableView.setContextMenu(contextMenu);
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Contact item = contactTableView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });
    }

    private void deleteItem(Contact item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setContentText("Are your sure that you want to delete " + item.getFirstName() + " contact?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            data.deleteContactFromDb(item);
            data.deleteContact(item);
        }
    }

    private void setCellFactories() {
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        notesTableColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    public void addNewContactOnAction(String title, boolean flag){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle(title);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddNewContact.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        ContactController contactController = fxmlLoader.getController();


        Contact item = contactTableView.getSelectionModel().getSelectedItem();
        if(!flag){
            if(item == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Choose Contact to update");
                alert.showAndWait();
                return;
            }else{
                contactController.toUpdateField(item);
            }
        }
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if(flag){
                Contact contact = contactController.getNewContact();
                data.addContact(contact);
                data.insertContactInDB(contact);
            } else{
                Contact oldContact = new Contact(item.getFirstName(), item.getLastName(), item.getPhoneNumber(),
                        item.getNotes());
                contactController.updatedThisContact(item);
                data.updateItemInDb(item, oldContact);
            }
        }
    }

    public void changeContactOnAction() {
        String title = "Change Contact";
        boolean flag = false;
        addNewContactOnAction(title, flag);
    }

    public void AddNewContact(){
        String title = "Add new Contact";
        boolean flag = true;
        addNewContactOnAction(title, flag);
    }
}
