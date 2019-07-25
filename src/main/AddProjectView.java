package main;

import javax.swing.*;

public class AddProjectView {
    private OnChoosedEvent onChoosedEvent;
    private JFileChooser chooser;

    public AddProjectView(OnChoosedEvent onChoosedEvent) {
        this.onChoosedEvent = onChoosedEvent;
    }

    public void show() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
        chooser.setDialogTitle("Selecione o diretório do projeto");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setApproveButtonToolTipText("Adicionar");
        chooser.setApproveButtonText("Adicionar");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            if (onChoosedEvent != null) {
                onChoosedEvent.onChoosed(chooser.getSelectedFile().toString(), chooser.getSelectedFile().getName());
            }
        }
    }

    public void setOnChoosedEvent(OnChoosedEvent onChoosedEvent) {
        this.onChoosedEvent = onChoosedEvent;
    }

    public interface OnChoosedEvent {
        void onChoosed(String path, String name);
    }
}
