package ui.widget;

import common.FileChooserDialog;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Cursor;
import java.io.File;

/**
 *
 */
public class TopMenu extends JMenuBar {


    /**
     *
     * */
    private final CanvasWidget widget;

    /**
     *
     */
    public TopMenu(CanvasWidget widget) {
        this.widget = widget;

        this.setCursor(Cursor.getDefaultCursor());

        this.add(createFileMenu(widget));
        this.add(createEditMenu());
        this.add(createExportMenu(widget));
        this.add(createHelpMenu());
    }

    /**
     *
     */
    private JMenu createEditMenu() {
        var subMenu = new JMenu("Edit");

        subMenu.setCursor(Cursor.getDefaultCursor());

        JMenuItem undoItem = new JMenuItem("Undo");
        JMenuItem redoItem = new JMenuItem("Redo");

        JMenuItem copyItem = new JMenuItem("Copy");

        copyItem.addActionListener(e -> {
            this.widget.getCanvas().setCopyOnSelectedShape(true);
        });


        JMenuItem pasteItem = new JMenuItem("Paste");

        pasteItem.addActionListener(e -> {
            this.widget.getCanvas().copySelectedShape();
        });


        subMenu.add(undoItem);
        subMenu.add(redoItem);
        subMenu.add(copyItem);
        subMenu.add(pasteItem);
        return subMenu;
    }

    /**
     *
     * */
    private JMenu createExportMenu(CanvasWidget widget) {
        var subMenu = new JMenu("Export");

        subMenu.setCursor(Cursor.getDefaultCursor());

        var exportToPNG = new JMenuItem("Export to PNG");

        exportToPNG.addActionListener(e -> {
            File file = FileChooserDialog.showSaveFileChooser();

            widget.getCanvas().export(file, "png");

        });

        var exportToJPG = new JMenuItem("Export to JPG");

        exportToJPG.addActionListener(e -> {
            File file = FileChooserDialog.showSaveFileChooser();

            widget.getCanvas().export(file, "jpg");
        });

        subMenu.add(exportToPNG);
        subMenu.add(exportToJPG);

        return subMenu;
    }

    /**
     *
     */
    private JMenu createHelpMenu() {
        JMenu subMenu = new JMenu("Help");
        subMenu.setCursor(Cursor.getDefaultCursor());

        JMenuItem cutItem = new JMenuItem("About");
        subMenu.add(cutItem);

        return subMenu;
    }

    /**
     *
     */
    private JMenu createFileMenu(CanvasWidget widget) {
        JMenu subMenu = new JMenu("File");
        subMenu.setCursor(Cursor.getDefaultCursor());

        JMenuItem newItem = new JMenuItem("New");

        newItem.addActionListener(e -> {
            widget.getCanvas().clear();
        });


        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");

        subMenu.add(newItem);
        subMenu.add(openItem);
        subMenu.add(saveItem);

        return subMenu;
    }
}
