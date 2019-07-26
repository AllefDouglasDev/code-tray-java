package main;

import database.DatabaseConnection;
import database.ProjectDAO;

import java.net.ServerSocket;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class App {

    @SuppressWarnings("resource")
	public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            new ServerSocket(9581);

            normalizeDatabase();
            Tray tray = new Tray();
            tray.show();
        } catch (Exception e) {
            e.printStackTrace();
        	String errorMessage = "Erro: " + e.getMessage() + " \n - Verifique se o aplicativo ja esta aberto";
            JOptionPane.showMessageDialog(null, errorMessage, "Erro", JOptionPane.ERROR_MESSAGE);
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
