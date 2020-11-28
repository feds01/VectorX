package ui.widget;

import common.FileChooserDialog;
import file.Loader;
import file.SaveManager;
import file.Saver;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Cursor;
import java.io.File;

/**
 * TopMenu widget class that is used to create and display the action
 * menu.
 *
 * @author 200008575
 * */
public class TopMenu extends JMenuBar {


    /**
     *
     * */
    private final CanvasWidget widget;
    private final JFrame frame;

    /**
     *
     */
    public TopMenu(CanvasWidget widget, JFrame frame) {
        this.widget = widget;
        this.frame = frame;

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
            File file = FileChooserDialog.showSaveFileChooser("png");

            widget.getCanvas().export(file, "png");

        });

        var exportToJPG = new JMenuItem("Export to JPG");

        exportToJPG.addActionListener(e -> {
            File file = FileChooserDialog.showSaveFileChooser("jpg");

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

            // reset the title to null
            frame.setTitle("Vector X - Editing New File");
        });


        JMenuItem openItem = new JMenuItem("Open");

        openItem.addActionListener(e -> {
            SaveManager.getInstance().openFile(widget, frame);
        });

        JMenuItem saveItem = new JMenuItem("Save");

        saveItem.addActionListener(e -> {
            SaveManager.getInstance().saveFile(widget, frame, false);
        });

        JMenuItem saveAsItem = new JMenuItem("Save As");

        saveAsItem.addActionListener(e -> {
            SaveManager.getInstance().saveFile(widget, frame, true);
        });

        subMenu.add(newItem);
        subMenu.add(openItem);
        subMenu.add(saveItem);
        subMenu.add(saveAsItem);

        return subMenu;
    }
}
