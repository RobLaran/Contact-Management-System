package org.cms.contactmanagementsystem;

public class Contact {
    private int ID;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String relationship;
    private String note;
    private int userID;

    public Contact(String name, String email, String phoneNumber, String address, String relationship) {
        this.ID = 0;
        this.userID = 0;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.relationship = relationship;
        this.note = null;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setDescription(String note) {
        this.note = note;
    }

    public int getID() {
        return this.ID != 0 ? this.ID : 0;
    }

    public String getName() {
        return this.name != null ? this.name : "";
    }

    public String getEmail() {
        return this.email != null ? this.email : "";
    }

    public String getPhoneNumber() {
        return this.phoneNumber != null ? this.phoneNumber : "";
    }

    public String getAddress() {
        return this.address != null ? this.address : "";
    }

    public String getRelationship() {
        return this.relationship != null ? this.relationship : "";
    }

    public String getNote() {
        return this.note != null ? this.note : "";
    }

    public int getUserID() {
        return this.userID != 0 ? this.userID : 0;
    }

}
