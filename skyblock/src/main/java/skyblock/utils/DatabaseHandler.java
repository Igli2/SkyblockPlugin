package skyblock.utils;

import java.sql.*;
import java.util.UUID;

public class DatabaseHandler {
    private static final String dbPath = "jdbc:sqlite:player_data.db";
    private Connection databaseConnection;

    public DatabaseHandler() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.databaseConnection = DriverManager.getConnection(DatabaseHandler.dbPath);
            if (this.databaseConnection != null) {
                DatabaseMetaData metaData = this.databaseConnection.getMetaData();
                System.out.println("Running " + metaData.getDriverName() + " on version " + metaData.getDriverVersion());
                this.initTables();
            } else {
                System.out.println("Could not create database connection.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTables() {
        try {
            Statement statement = this.databaseConnection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS playerdata (uuid TEXT PRIMARY KEY, money INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            this.databaseConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getDatabaseConnection() {
        return this.databaseConnection;
    }
}
