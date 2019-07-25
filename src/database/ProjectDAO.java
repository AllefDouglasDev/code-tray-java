package database;

import models.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    private Connection conn;

    public ProjectDAO() {
        this.conn = new DatabaseConnection().getConnection();
    }

    public void migrate() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS projects ("
                    + " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " name VARCHAR(60) NOT NULL,"
                    + " path VARCHAR(512) NOT NULL,"
                    + " deleted INTEGER DEFAULT 0,"
                    + "	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")");
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void store(Project project) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO projects (name, path) VALUES (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getPath());
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Project> findAll() {
        PreparedStatement stmt = null;
        ResultSet rs;

        ArrayList<Project> products = new ArrayList<>();

        try {
            String query = "SELECT * FROM projects WHERE deleted = 0";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Project product = new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("path"),
                        rs.getInt("deleted") == 1,
                        rs.getTimestamp("created_at")
                );

                products.add(product);
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return products;
    }

    public void delete(int id) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE projects SET deleted = 1 WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
