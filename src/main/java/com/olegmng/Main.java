package com.olegmng;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:database.db");
        prepareTables(connection);
        insertTables(connection);
//        readTables(connection);

//        executeUpdate(connection);

        prepareState(connection);



        connection.close();
    }

    public static void prepareTables(Connection connection) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table if not exists users(
                                        id bigint,
                                        name varchar(255)
                    )
                    """);
        }
    }

    public static void insertTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {

            statement.execute("""
                         
                    insert into users(id, name) values(1, 'Oleg'),
                    (2, 'Greg'),
                    (3, 'Peter'),
                    (4, 'Olga') 
                    """);

//            statement.execute("insert into users(id, name) values(1, 'Oleg')");
//            statement.execute("insert into users(id, name) values(2, 'Greg')");
//            statement.execute("insert into users(id, name) values(3, 'Peter')");
//            statement.execute("insert into users(id, name) values(4, 'Olga')");
        }
    }

    public static void readTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    select * from users
                    """);
            int count = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("[" + count + "]" + ", id = " + id + ", name = " + name);
                count++;
            }
        }
    }

    public static void executeUpdate(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {

            int updateRows = statement.executeUpdate("""
                    update users set name = 'unknown' where id > 2
                    """);

        }
    }

    public static void prepareState(Connection connection) throws SQLException {
        try ( PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from users where name = ?")) {


            preparedStatement.setString(1, "Oleg");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("[" + '#' + "]" + ", id = " + id + ", name = " + name);

            }

        }
    }
}