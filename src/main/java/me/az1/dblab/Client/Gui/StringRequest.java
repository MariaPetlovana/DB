package me.az1.dblab.Client.Gui;

import javax.swing.*;

public class StringRequest {
    public static String RequestString() {
        String s = (String)JOptionPane.showInputDialog(
                null,
                "Complete the sentence:\n"
                        + "\"Green eggs and...\"",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "ham");

        System.out.println("Returning " + s);
        return s;

//        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        final String[] result = new String[1];
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    JFrame frame = new JFrame("String request window");
//                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                    frame.add(new StringRequestDialog(result), BorderLayout.CENTER);
//                    frame.pack();
////                    frame.setModalityType(ModalityType.APPLICATION_MODAL);
//                    frame.setVisible(true);
//                }
//            });
//        System.out.println("Returning " + result[0]);
//        return result[0];
    }
}
