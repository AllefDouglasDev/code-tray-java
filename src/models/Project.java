package models;

import java.sql.Timestamp;

public class Project {
    private int id;
    private String path;
    private String name;
    private boolean deleted;
    private Timestamp createdAt;

    public Project (int id, String path, String name) {
        this.id = id;
        this.path = path;
        this.name = name;
    }

    public Project (int id, String name, String path, boolean deleted, Timestamp createdAt) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getPath () {
        return path;
    }

    public void setPath (String path) {
        this.path = path;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public boolean isDeleted () {
        return deleted;
    }

    public void setDeleted (boolean deleted) {
        this.deleted = deleted;
    }

    public Timestamp getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt (Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
