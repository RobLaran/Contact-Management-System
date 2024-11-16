package org.cms.jdbc;

import org.cms.contactmanagementsystem.Contact;
import org.cms.contactmanagementsystem.User;

import java.sql.*;

public class DatabaseConnectivity {
    private final static String url = "jdbc:mysql://localhost:3306/contactsdb";
    private final static String root = "root";
    private final static String password = "darting1223";

    private static Statement statement;
    private static String query;


//  Connection for database
    public static Statement setupConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            Connection connection = DriverManager.getConnection(url, root, password);

            statement = connection.createStatement();
        } catch(SQLException sqlExc) {
            System.out.println(sqlExc.getLocalizedMessage());
        }

        return null;
    }


//    Fetching Data from Database with query
    public static ResultSet fetchUser(String name, String password, String email) throws SQLException {
        query = "select * from user where username = '" + name + "' and password = '"+password+"' and " +
                "email = '"+email+"';";

        return statement.executeQuery(query);
    }

    public static ResultSet fetchUserByID(int id) throws SQLException {
        query = "select * from user where user_id = "+id+";";

        return statement.executeQuery(query);
    }

    public static ResultSet fetchContact(int userID) throws SQLException {
        query = "select * from contact where user_id = " + userID + ";";

        return statement.executeQuery(query);
    }

    public static ResultSet fetchContactWithName(String name, int userID) throws SQLException {
        query = "select * from contact where user_id = " + userID + " and contact_name = '"+name+"';";

        return statement.executeQuery(query);
    }

    public static ResultSet fetchContactWithNumber(String number, int userID) throws SQLException {
        query = "select * from contact where user_id = " + userID + " and contact_number = "+number+";";

        return statement.executeQuery(query);
    }


//  Retrieving specific user data from database
    public static boolean findUsername(String name) {
        try {
            query = "select username from user where username = '" + name + "';";
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean findEmail(String email) {
        try {
            query = "select email from user where email = '" + email + "';";
            ResultSet resultSet = statement.executeQuery(query);

           return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean findPhoneNumber(String phoneNumber) {
        try {
            query = "select phone_number from user where phone_number = " + phoneNumber + ";";
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getUsername(String name) {
        try {
            query = "select username from user where username = '" + name + "';";
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getPassword(String name, String password) {
        try {
            query = "select password from user where username = '"+name+"' and password = '"+password+"';";
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getEmail(String name, String password, String email) {
        try {
            query = "select password from user where username = '"+name+"' and password = '"+password+"' " +
                    "and email = '"+email+"';";
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    Inserting new data into database
    public static void insertUserData(String name, String password, Long phoneNumber, String email) throws SQLException {
        query = "insert into user (username, password, phone_number, email) " +
                "values ('"+name+"', '"+password+"', "+phoneNumber+", '"+email+"');";

        statement.executeUpdate(query);

        query = "";
    }

    public static void insertContactData(Contact contact) throws SQLException {
        query = "insert into contact (contact_name, contact_number, email, address, relationship, user_id) " +
                "values ('"+contact.getName()+"', "+contact.getPhoneNumber()+", '"+contact.getEmail()+"', '"+contact.getAddress()+"', '"+contact.getRelationship()+"', "+contact.getUserID()+");";

        statement.executeUpdate(query);
    }

    public static void updateUser(User user) throws SQLException {
        query = "update user " +
                "set username = '"+user.getName()+"', phone_number = "+user.getPhoneNumber()+
                ", email = '"+user.getEmail()+"', address = '"+user.getAddress()+ "'" +
                ", note = '"+user.getNote()+"' " +
                "where user_id = "+user.getID()+";";

        statement.executeUpdate(query);
    }

    public static void updateContact(Contact contact) throws SQLException {
        query = "update contact " +
                "set contact_name = '"+contact.getName()+"', contact_number = "+contact.getPhoneNumber()+", email = '"+contact.getEmail()+"'," +
                "address = '"+contact.getAddress()+"', relationship = '"+contact.getRelationship()+"', note = '"+contact.getNote()+"' " +
                "where contact_id = "+contact.getID()+" and user_id = "+contact.getUserID()+";";

        statement.executeUpdate(query);
    }

    public static void deleteContact(Contact contact) throws SQLException {
        query = "delete from contact where contact_id = " + contact.getID() + " and user_id = "+ contact.getUserID() +";";

        statement.executeUpdate(query);
    }



//    insert into user (username, phone_number, password, email)
//              values('bambi', 094719364, 'rsbambi', 'bambs@gmail.com');

}
