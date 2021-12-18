package sample.DataModel;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsList {
    private ObservableList<Contact> contacts;

    public ContactsList(){
        contacts = FXCollections.observableArrayList();
    }

    public ObservableList<Contact> getContactsList(){
        return contacts;
    }

    public void addContact(Contact item){
        contacts.add(item);
    }

    public void deleteContact(Contact item){
        contacts.remove(item);
    }

    public void getContactsFromDb(){
        Connection conn = DatabaseConnection.db.getConnection();
        String getContactsQuery = "SELECT * from contacts";
        try{
            ResultSet getContactsResultSet = conn.createStatement().executeQuery(getContactsQuery);
            while(getContactsResultSet.next()){
                contacts.add(new Contact(getContactsResultSet.getString("_id_contact"),
                        getContactsResultSet.getString("firstname"),
                        getContactsResultSet.getString("lastname"),
                        getContactsResultSet.getString("phonenumber"),
                        getContactsResultSet.getString("notes")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertContactInDB(Contact item){
        Connection conn = DatabaseConnection.db.getConnection();
        String insertQuery = "Insert into contacts(firstname, lastname, phonenumber, notes) values('"
                + item.getFirstName() + "','" + item.getLastName() +"','" + item.getPhoneNumber() + "','" +
                item.getNotes()+"')";
        try{
            conn.createStatement().execute(insertQuery);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteContactFromDb(Contact item){
        Connection conn = DatabaseConnection.db.getConnection();
        String deleteQuery = "Delete from contacts where firstname = '"+ item.getFirstName() +"' and " +
                "lastname = '" + item.getLastName() + "' and phonenumber = '" + item.getPhoneNumber()+"' and" +
                " notes = '" + item.getNotes()+"'";
        try{
            conn.createStatement().execute(deleteQuery);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateItemInDb(Contact updatedContact, Contact oldContact){
        Connection conn = DatabaseConnection.db.getConnection();
        String updateQuery = "update contacts set firstname = '" + updatedContact.getFirstName() +
                "' ,lastname = '" + updatedContact.getLastName() +"' ,phonenumber = '" + updatedContact.getPhoneNumber() +
                "' ,notes = '" + updatedContact.getNotes() + "' where firstname = '"+ oldContact.getFirstName() +
                "' and lastname = '" + oldContact.getLastName() + "' and phonenumber = '" + oldContact.getPhoneNumber() + "' and " +
                "notes = '" + oldContact.getNotes()+"'";
        try{
            conn.createStatement().execute(updateQuery);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}