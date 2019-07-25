package main;

import database.DatabaseConnection;
import database.ProjectDAO;

import java.net.ServerSocket;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class App {

    public static void main (String[] args) {
        try {
            new ServerSocket(9581);

            normalizeDatabase();

            Tray tray = new Tray();
            tray.show();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Desculpe, mas só uma aplicação pode ser executada por vez.");
            System.exit(0);
        }
    }

    public static void normalizeDatabase() {
        DatabaseConnection dc = new DatabaseConnection();
        dc.getConnection();

        if (dc.hasBeenRecreated()) {
            new ProjectDAO().migrate();
        }
    }

}
