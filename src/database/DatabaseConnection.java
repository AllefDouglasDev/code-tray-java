package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_NAME = "tray.db";
    private boolean hasConnection;
    private boolean hasBeenRecreated;

    public Connection getConnection() {
        Connection conn = null;

        try {
            verifyDatabaseExists();

            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database/" + DB_NAME;

            conn = DriverManager.getConnection(url);

            hasConnection = true;
        } catch (ClassNotFoundException e) {
            hasConnection = false;
            System.out.println("Classe de banco de dados não encontrada");
            e.printStackTrace();
        } catch (SQLException sqle) {
            hasConnection = false;
            System.out.println("Erro ao se conectar ao banco mercado.db");
            sqle.printStackTrace();
        } catch (Exception e) {
            hasConnection = false;
            System.out.println("Erro ao criar arquivo de banco de dados em database/mercado.db");
            e.printStackTrace();
        }

        return conn;
    }

    private void verifyDatabaseExists() throws Exception {
        verifyDBDirectoryExists();
        verifyDBFileExists();
    }

    private void verifyDBDirectoryExists() {
        File directory = new File("database");

        if (!directory.exists())
            createDatabaseDirectory(directory);
    }

    private void verifyDBFileExists() throws Exception {
        File db = new File("database\\" + DB_NAME);

        if (!db.exists()) {
            createDatabaseFile(db);
            hasBeenRecreated = true;
        }
    }

    private void createDatabaseFile(File db) throws Exception {
        db.createNewFile();
    }

    private void createDatabaseDirectory(File directory) {
        directory.mkdir();
    }

    public boolean hasBeenRecreated() {
        return hasBeenRecreated;
    }

    public boolean hasConnection() {
        return hasConnection;
    }
}
