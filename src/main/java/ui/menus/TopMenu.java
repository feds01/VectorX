package ui.menus;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TopMenu implements BaseMenu {
    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoItem = new JMenuItem("Undo");
        editMenu.add(undoItem);
        JMenuItem redoItem = new JMenuItem("Redo");
        editMenu.add(redoItem);

        JMenuItem cutItem = new JMenuItem("Cut");
        editMenu.add(cutItem);
        JMenuItem copyItem = new JMenuItem("Copy");
        editMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        editMenu.add(pasteItem);
        return editMenu;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem cutItem = new JMenuItem("About");
        helpMenu.add(cutItem);

        return helpMenu;
    }

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

    @Override
    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createHelpMenu());

        return menuBar;
    }
}
