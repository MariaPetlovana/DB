package me.az1.dblab.Client.Gui;

import me.az1.dblab.Client.ClientController;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    protected ControlPanel controlPanel;
    protected DatabasePanel databasePanel;
    protected ClientController controller;

    public MainPanel(ClientController controller) {
        super(new BorderLayout());

        controlPanel = new ControlPanel(controller);
        databasePanel = new DatabasePanel(controller);
        this.controller = controller;

        add(controlPanel, BorderLayout.PAGE_START);
        add(databasePanel, BorderLayout.CENTER);

        setMinimumSize(new Dimension(500, 500));

        controller.Refresh();
    }
}
