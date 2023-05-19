package com.gudang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class updatePanel {

    public updatePanel() {
        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent component = (JComponent) e.getSource();
                Window window = SwingUtilities.getWindowAncestor(component);
                window.dispose();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Id, NamaBarang, JumlahBarang;
                Id = KolomId.getText();
                NamaBarang = UpdateNamaBarang.getText();
                JumlahBarang = UpdateJumlahBarang.getText();
                if (!Objects.equals(Id, "") && !Objects.equals(NamaBarang, "") && !Objects.equals(JumlahBarang, "")){
                    try {
                        preparedStatement = Connector.connectDB().prepareStatement("UPDATE stokbarang SET namabarang=?, jumlah=? WHERE id=?;");
                        preparedStatement.setString(1, NamaBarang);
                        preparedStatement.setString(2, JumlahBarang);
                        preparedStatement.setString(3, Id);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Update Data Success");
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (SQLException exception){
                        exception.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Input shouldn't empty");
                }
            }
        });
    }

    public JPanel getMainUpdatePanel() {
        return mainUpdatePanel;
    }

    private PreparedStatement preparedStatement;
    private JPanel mainUpdatePanel;
    private JLabel jTitleUpdatePanel;
    private JLabel jLabelid;
    private JTextField KolomId;
    private JTextField UpdateNamaBarang;
    private JTextField UpdateJumlahBarang;
    private JLabel jLabelNamabarang;
    private JLabel jLabelJumlahBarang;
    private JButton updateButton;
    private JButton cancleButton;
}
