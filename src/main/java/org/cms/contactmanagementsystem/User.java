package org.cms.contactmanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userID;
    private String name;
    private Long phoneNumber;
    private String email;
    private String password;

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
    }

    public List<Contact> getContacts() {
        return this.userContacts;
    }
}
