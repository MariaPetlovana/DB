package me.az1.dblab.Client;

import me.az1.dblab.Common.DatabaseController;
import me.az1.dblab.Common.Scheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientController {
    protected ArrayList<DatabaseUpdateHandler> updateListeners;
    protected DatabaseController controller;

    protected long lastUsedTable;

    public ClientController(DatabaseController controller) {
        updateListeners = new ArrayList<DatabaseUpdateHandler>();
        this.controller = controller;
        this.lastUsedTable = -1;
    }

    public long LastUsedTable() {
        return lastUsedTable;
    }

    public DatabaseController GetDatabaseController() {
        return controller;
    }

    public void AddUpdateListener(DatabaseUpdateHandler listener) {
        updateListeners.add(listener);
    }

    public void NewDatabase() {
        System.out.println("new click");
        try {
            controller.NewDatabase();
        } catch (RemoteException e) {
            HandleRemoteException(e);
        }
        RefreshDatabase();
    }

    private void HandleRemoteException(RemoteException e) {
        System.out.printf("Got RemoteException :(");
        e.printStackTrace();
    }

    public void LoadDatabase() {
        System.out.println("load click");
        String path = GetPathToSave();
        if (path == null) {
            return;
        }

        try {
            controller.LoadDatabase(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        RefreshDatabase();
    }

    public void SaveDatabase() {
        System.out.println("save click");
        String path = GetPathToSave();
        if (path == null) {
            return;
        }

        try {
            controller.SaveDatabase(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CloseDatabase() {
        try {
            controller.CloseDatabase();
        } catch (RemoteException e) {
            HandleRemoteException(e);
        }
        RefreshDatabase();
    }

    public void CreateTable() {
        String name = GetTableName();
        if (name == null) {
            return;
        }

        Scheme scheme = GetTableScheme();
        if (scheme == null) {
            return;
        }

        try {
            lastUsedTable = controller.DatabaseAddEmptyTable(scheme, name);
        } catch (RemoteException e) {
            HandleRemoteException(e);
        }
        RefreshDatabase();
    }

    public void AddEmptyRow(long tableVersion) {
        try {
            lastUsedTable = tableVersion;
            controller.DatabaseTableAddEmptyRow(tableVersion);
        } catch (RemoteException e) {
            HandleRemoteException(e);
        }
        RefreshDatabase();
    }

    public void RemoveTableRows(long tableVersion, long[] rowVersions) {
        for (long rowVersion : rowVersions) {
            try {
                lastUsedTable = tableVersion;
                controller.DatabaseTableRemoveRow(tableVersion, rowVersion);
            } catch (RemoteException e) {
                HandleRemoteException(e);
            }
        }

        RefreshDatabase();
    }

    public void RemoveTable(long version) {
        try {
            lastUsedTable = version;
            controller.DatabaseRemoveTable(version);
        } catch (RemoteException e) {
            HandleRemoteException(e);
        }
        RefreshDatabase();
    }

    public void Refresh() {
        OnUpdate();
    }

    private void RefreshDatabase() {
        OnUpdate();
    }

    private void OnUpdate() {
        for (DatabaseUpdateHandler handler : updateListeners) {
            handler.HandleDatabaseUpdate();
        }
    }

    private static String GetPathToSave() {
        return (String) JOptionPane.showInputDialog(
                null,
                "Enter path:",
                "Path request dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "sample.db"
//                "C:/Users/artem/Dropbox/temp/win.db"
//                "C:/Users/artem/Dropbox/temp/winx.db"
//                "/Users/artemhb/Dropbox/temp/winx.db"
//                "/Users/artemhb/Dropbox/temp/1.db"
        );
    }

    private static String GetTableName() {
        return (String) JOptionPane.showInputDialog(
                null,
                "Enter table name:",
                "Table name request dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Table 1"
        );
    }

    private static Scheme GetTableScheme() {
        String line = (String) JOptionPane.showInputDialog(
                null,
                "Enter scheme in a following way. " +
                "Concatenate types, separating with comma to a single string.\n" +
                "Supported types are Int, Float, Char, Enum.",
                "Scheme request dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Int,Enum"
        );

        if (line == null) {
            return null;
        }

        return Scheme.ParseFromString(line);
    }

    public void SetTableFieldValue(long tableVersion, long rowVersion, int column, String value) {
        try {
            lastUsedTable = tableVersion;
            controller.DatabaseTableSetFieldStr(tableVersion, rowVersion, column, value);
        } catch (RemoteException e) {
            HandleRemoteException(e);
        }
        System.out.println("Set " + rowVersion + "; " + column + " to " + value);
        RefreshDatabase();
    }

    public static void ShowWarning(String text) {
        System.out.println("WARNING: " + text);
    }

//    public void SortTableByKey(long tableVersion) {
//        String key = (String) JOptionPane.showInputDialog(
//                null,
//                "Enter column index (0-based):",
//                "Column request dialog",
//                JOptionPane.PLAIN_MESSAGE,
//                null,
//                null,
//                "0"
//        );
//
//        try {
//            int column = Integer.parseInt(key);
//            try {
//                lastUsedTable = tableVersion;
//                controller.DatabaseTableSortByKey(tableVersion, column);
//            } catch (RemoteException e) {
//                HandleRemoteException(e);
//            }
//        }
//        catch (NumberFormatException e) {
//            ShowWarning("Can't parse column number");
//        }
//
//        RefreshDatabase();
//    }

    public void TableFind(long tableVersion, int nCols) {
        String defaultPattern = "";
        for (int col = 0; col < nCols; ++col) {
            if (col > 0) {
                defaultPattern += "|";
            }

            defaultPattern += ".*";
        }

        String pattern = (String) JOptionPane.showInputDialog(
                null,
                "Enter pattern:",
                "Pattern request dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                defaultPattern
        );

        try {
            lastUsedTable = controller.DatabaseTableFind(tableVersion, pattern);
        } catch (RemoteException e) {
            HandleRemoteException(e);
        }

        RefreshDatabase();
    }

    public void RemoveTableDuplicates(long tableVersion) {
            try {
                lastUsedTable = tableVersion;
                controller.DatabaseTableRemoveDuplicates(tableVersion);
            } catch (RemoteException e) {
                HandleRemoteException(e);
            }

        RefreshDatabase();
    }

    private static void ShowDialog(Component frame, final Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        component.addHierarchyListener(new HierarchyListener() {
            public void hierarchyChanged(HierarchyEvent e) {
                Window window = SwingUtilities.getWindowAncestor(component);
                if (window instanceof Dialog) {
                    Dialog dialog = (Dialog) window;
                    if (!dialog.isResizable()) {
                        dialog.setResizable(true);
                    }
                }
            }
        });

        JOptionPane.showMessageDialog(frame, scrollPane);
    }

    public static void DisplayHTML(String value) {
        JEditorPane output = new JEditorPane();
        output.setEditable(false);
        output.setContentType("text/html");
        output.setText(value);

        JPanel myPanel = new JPanel();
        myPanel.add(output);

        ShowDialog(null, myPanel);
    }

    private static int ShowConfirmDialog(Component frame, final Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        component.addHierarchyListener(new HierarchyListener() {
            public void hierarchyChanged(HierarchyEvent e) {
                Window window = SwingUtilities.getWindowAncestor(component);
                if (window instanceof Dialog) {
                    Dialog dialog = (Dialog) window;
                    if (!dialog.isResizable()) {
                        dialog.setResizable(true);
                    }
                }
            }
        });

        return JOptionPane.showConfirmDialog(frame, scrollPane, "Please", JOptionPane.OK_CANCEL_OPTION);
    }

    public static String GetText() {
        JTextArea input = new JTextArea();
        input.setText("Enter HTML here:");

        JPanel myPanel = new JPanel();
        myPanel.add(input);

        int result = ShowConfirmDialog(null, myPanel);
        if (result == JOptionPane.OK_OPTION) {
            return input.getText();
        }

        return null;
    }
}
