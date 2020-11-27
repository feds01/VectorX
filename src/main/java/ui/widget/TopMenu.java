package ui.widget;

import common.FileChooserDialog;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.io.File;
import java.io.IOException;

/**
 *
 * */
public class TopMenu {

    /**
     *
     * */
    private JMenu createEditMenu() {
        var editMenu = new JMenu("Edit");

        JMenuItem undoItem = new JMenuItem("Undo");
        editMenu.add(undoItem);
        JMenuItem redoItem = new JMenuItem("Redo");
        editMenu.add(redoItem);

        JMenuItem copyItem = new JMenuItem("Copy");
        editMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        editMenu.add(pasteItem);
        return editMenu;
    }

    private JMenu createExportMenu(CanvasWidget widget) {
        var editMenu = new JMenu("Export");

        var exportToPNG = new JMenuItem("Export to PNG");

        exportToPNG.addActionListener(e -> {
            File file = FileChooserDialog.showSaveFileChooser();

            widget.getCanvas().export(file,"png");

        });

        var exportToJPG = new JMenuItem("Export to JPG");

        exportToJPG.addActionListener(e -> {
            File file = FileChooserDialog.showSaveFileChooser();

            widget.getCanvas().export(file,"jpg");
        });

        editMenu.add(exportToPNG);
        editMenu.add(exportToJPG);

        return editMenu;
    }

    /**
     *
     * */
    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem cutItem = new JMenuItem("About");
        helpMenu.add(cutItem);

        return helpMenu;
    }

    /**
     *
     * */
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        fileMenu.add(newItem);
        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);
        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);
        return fileMenu;
    }

    /**
     *
     * */
    public JMenuBar createMenu(CanvasWidget widget) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createExportMenu(widget));
        menuBar.add(createHelpMenu());

        return menuBar;
    }
}
