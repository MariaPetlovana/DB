package me.az1.dblab.Client.Gui;

import javax.swing.*;
import java.awt.*;

public class StringRequestDialog extends JPanel {
    String[] result;
    public StringRequestDialog(String[] resultOutput) {
        super(new BorderLayout());

        result = resultOutput;

        JTextField textField = new JTextField("Enter here");
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        add(textField, BorderLayout.CENTER);
        add(okButton, BorderLayout.PAGE_END);
        add(cancelButton, BorderLayout.PAGE_END);

        setMinimumSize(new Dimension(500, 500));
    }
}
