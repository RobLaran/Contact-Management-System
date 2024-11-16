package org.cms.contactmanagementsystem;

import org.cms.jdbc.DatabaseConnectivity;
import org.cms.utilities.Validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ContactManager  {
    private User user;
    private List<Contact> contactList = new ArrayList<>();

    public ContactManager() {
        this.user = null;
    }

    public Contact createContact(String name, String phoneNumber, String email, String address, String relationship) {
        Contact contact = new Contact(name, email, phoneNumber, address, relationship);
        contact.setUserID(user.getID());

        return contact;
    }

    public void addContact(Contact contact) {
        contactList.add(contact);
        user.putContact(contact);

        try {
            DatabaseConnectivity.insertContactData(contact);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteContact(Contact contact) {
        try {
            DatabaseConnectivity.deleteContact(contact);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editUser() {
        try {
            DatabaseConnectivity.updateUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editContact(Contact contact) {
        try {
            DatabaseConnectivity.updateContact(contact);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public Contact getContact(String name) {
        try {
            ResultSet resultSet = DatabaseConnectivity.fetchContactWithName(name, user.getID());
            Contact contact = null;

            while(resultSet.next()) {
                String contactName = resultSet.getString("contact_name");
                String contactNumber = resultSet.getString("contact_number");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String relationship = resultSet.getString("relationship");
                int ID = resultSet.getInt("contact_id");

                contact = new Contact(contactName, email, contactNumber, address, relationship);
                contact.setID(ID);
                contact.setUserID(user.getID());
            }

            return contact;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> loadContacts() throws SQLException {
//        ResultSet allContactsFromUser = DatabaseConnectivity.fetchContact(user.getID());
//
//        while(allContactsFromUser.next()) {
//            int userID = allContactsFromUser.getInt("user_id");
//            String contactName = allContactsFromUser.getString("contact_name");
//            String contactNumber = allContactsFromUser.getString("contact_number");
//            String email = allContactsFromUser.getString("email");
//            String address = allContactsFromUser.getString("address");
//            String relationship = allContactsFromUser.getString("relationship");
//
//            Contact newContact = new Contact(contactName, email, contactNumber, address, relationship);
//            newContact.setID(userID);
//            user.putContact(newContact);
//        }

        if(contactList.isEmpty()) {
            contactList.addAll(user.getContacts());
        } else {
            contactList.clear();
            contactList.addAll(user.getContacts());
        }

        List<String> loadedList = new ArrayList<>();

        for (Contact cont : contactList) {
            loadedList.add(cont.getName());
        }

        return loadedList;
    }

    public String searchContact(String searchedContact) {
        try {
            ResultSet resultSet;

            if(Validator.checkNumber(searchedContact)) {
                resultSet = DatabaseConnectivity.fetchContactWithNumber(searchedContact, user.getID());
            } else {
                resultSet = DatabaseConnectivity.fetchContactWithName(searchedContact, user.getID());
            }

            if(resultSet.next()) {
                return resultSet.getString("contact_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "not found";
    }
}
