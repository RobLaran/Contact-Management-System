package org.cms.jdbc;

import org.cms.contactmanagementsystem.Contact;

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
    public static ResultSet fetchUsers(String name) throws SQLException {
        query = "select * from user where username = '" + name + "';";

        return statement.executeQuery(query);
    }

    public static ResultSet fetchContacts(int userID) throws SQLException {
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
    public static String getUsername(String name) {
        try {
            query = "select username from user where username = '" + name + "';";
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static String getPassword(String password) {
        try {
            query = "select password from user where password = '" + password + "';";
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static String getUserEmail(String email) {
        try {
            query = "select email from user where email = '" + email + "';";
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static Long getUserPhoneNumber(Long phoneNumber) {
        try {
            query = "select phone_number from user where phone_number = " + phoneNumber + ";";
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0L;
    }


//    Inserting new data into database
    public static void insertUserData(String name, String password, Long phoneNumber, String email) throws SQLException {
        query = "insert into user (username, password, phone_number, email) " +
                "values ('"+name+"', '"+password+"', "+phoneNumber+", '"+email+"');";

        statement.executeUpdate(query);
    }

    public static void insertContactData(Contact contact) throws SQLException {
        query = "insert into contact (contact_name, contact_number, email, address, relationship, user_id) " +
                "values ('"+contact.getName()+"', "+contact.getPhoneNumber()+", '"+contact.getEmail()+"', '"+contact.getAddress()+"', '"+contact.getRelationship()+"', "+contact.getUserID()+");";

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
