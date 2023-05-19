package com.gudang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class deletePanel {

    public JPanel getDeletePanel(){
        return mainDeletePanel;
    }

    private JPanel mainDeletePanel;
    private JLabel LabelDelete;
    private JTextField textFieldId;
    private JButton DeleteButton;
    private JButton cancleButton;
    public deletePanel() {
        DeleteButton.addActionListener(new ActionListener() {
         @Override
            public void actionPerformed(ActionEvent e) {
             String userId;
             userId = textFieldId.getText();
             if (!Objects.equals(userId, "")) {
                 try {
                     PreparedStatement preparedStatement = Connector.connectDB().prepareStatement("DELETE FROM stokbarang WHERE id=?;");
                     preparedStatement.setString(1, userId);
                     preparedStatement.executeUpdate();
                     JOptionPane.showMessageDialog(null, "Data Successfully Deleted");
                     JComponent component = (JComponent) e.getSource();
                     Window window = SwingUtilities.getWindowAncestor(component);
                     window.dispose();
                 } catch (SQLException err) {
                     err.printStackTrace();
                 }
             } else {
                 JOptionPane.showMessageDialog(null, "Id target shouldn't empty");
             }

         }
        });
        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
