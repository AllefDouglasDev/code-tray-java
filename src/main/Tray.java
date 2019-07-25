package main;

import database.ProjectDAO;
import models.Project;
import utils.Terminal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Tray {
    private final TrayIcon trayIcon;
    private SystemTray tray;
    private ArrayList<Project> projects;
    private ProjectDAO projectDAO;

    public Tray() {
        projectDAO = new ProjectDAO();
        loadProjects();

        tray = SystemTray.getSystemTray();
        trayIcon = new TrayIcon(getIcon("images/bulb.gif"), "Code Tray", renderPopupMenu());
        trayIcon.setImageAutoSize(true);
    }

    public void show() {
        if (! SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "Recurso ainda nao esta disponivel pra o seu sistema");
            return;
        }

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Erro, TrayIcon não sera adicionado.");
            e.printStackTrace();
        }
    }

    private ArrayList<Project> loadProjects() {
        projects = projectDAO.findAll();
        return projects;
    }

    private Image getIcon(String path) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PopupMenu renderPopupMenu() {
        PopupMenu popupMenu = new PopupMenu("Menu de Opções");

        MenuItem addProjectItem = new MenuItem("Adicionar projeto");
        addProjectItem.addActionListener(onAddNewProject());
        MenuItem exitItem = new MenuItem("Sair");
        exitItem.addActionListener(onExit());

        popupMenu.add(addProjectItem);
        if (projects.size() > 0) {
            popupMenu.addSeparator();
            renderProjects(popupMenu);
        }
        popupMenu.addSeparator();
        popupMenu.add(exitItem);

        return popupMenu;
    }

    private ActionListener onExit () {
        return e -> System.exit(0);
    }

    private ActionListener onAddNewProject () {
        return e -> {
            AddProjectView addProjectView = new AddProjectView((path, name) -> {
                Project project = new Project(45, path, name);
                projectDAO.store(project);
                projects.add(project);
                updateTray();
            });
            addProjectView.show();
        };
    }

    private void renderProjects (PopupMenu popupMenu) {
        for (Project project: projects) {
            PopupMenu projectPopup = new PopupMenu(project.getName());

            MenuItem itemOpenFolder = new MenuItem("Abrir pasta");
            itemOpenFolder.addActionListener(onOpenFolder(project));
            MenuItem itemOpenInVSCode = new MenuItem("Abrir com VS Code");
            itemOpenInVSCode.addActionListener(onOpenWithVSCode(project));
            MenuItem itemRemove = new MenuItem("Remover");
            itemRemove.addActionListener(onRemoveItem(project));

            projectPopup.add(itemOpenFolder);
            projectPopup.add(itemOpenInVSCode);
            projectPopup.add(itemRemove);

            popupMenu.add(projectPopup);
        }
    }
    
    private ActionListener onOpenFolder (Project project) {
        return e -> {
            try {
                Terminal.openFolder(project.getPath());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    private ActionListener onOpenWithVSCode (Project project) {
        return e -> {
            try {
                Terminal.openVSCode(project.getPath());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    private ActionListener onRemoveItem (Project project) {
        return e -> {
            projectDAO.delete(project.getId());
            projects.remove(project);
            updateTray();
        };
    }

    private void updateTray() {
        tray.getTrayIcons()[0].setPopupMenu(renderPopupMenu());
    }
}
