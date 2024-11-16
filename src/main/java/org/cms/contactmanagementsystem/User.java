package org.cms.contactmanagementsystem;

import org.cms.jdbc.DatabaseConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int userID;
    private String name;
    private Long phoneNumber;
    private String email;
    private String password;
    private String address;
    private int numberOfContacts;
    private String note;

    private List<Contact> userContacts;

    public User(int userID, String name, Long phoneNumber, String email, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;

        userContacts = new ArrayList<>();

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setID(int userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumberOfContacts(int numberOfContacts) {
        this.numberOfContacts = numberOfContacts;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getID() {
        return this.userID;
    }

    public String getPassword() {
        return this.password;
    }

    public void putContact(Contact contact) {
        userContacts.add(contact);
        this.numberOfContacts++;
    }

    public List<Contact> getContacts() {
        try {
            if(userContacts.isEmpty()) {
                ResultSet resultSet = DatabaseConnectivity.fetchContact(getID());

                while(resultSet.next()) {
                    String name = resultSet.getString("contact_name");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("contact_number");
                    String address = resultSet.getString("address");
                    String relationship = resultSet.getString("relationship");
                    String note = resultSet.getString("note");
                    int ID = resultSet.getInt("user_id");

                    Contact contact = new Contact(name, email, phone, address, relationship);
                    contact.setDescription(note);
                    contact.setUserID(ID);

                    userContacts.add(contact);
                }
            } else {
                userContacts.clear();
                ResultSet resultSet = DatabaseConnectivity.fetchContact(getID());

                while(resultSet.next()) {
                    String name = resultSet.getString("contact_name");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("contact_number");
                    String address = resultSet.getString("address");
                    String relationship = resultSet.getString("relationship");
                    String note = resultSet.getString("note");
                    int ID = resultSet.getInt("user_id");

                    Contact contact = new Contact(name, email, phone, address, relationship);
                    contact.setDescription(note);
                    contact.setUserID(ID);

                    userContacts.add(contact);
                }
            }

            return this.userContacts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAddress() {
        return this.address;
    }

    public int getNumberOfContacts() {
        return this.numberOfContacts;
    }

    public String getNote() {
        return this.note;
    }

}
