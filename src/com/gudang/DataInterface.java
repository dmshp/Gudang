package com.gudang;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataInterface {

    public DataInterface() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statNama, statJumlah;
                statNama   = textFieldNama.getText();
                statJumlah = textFieldJumlah.getText();

                try {
                    preparedStatement = Connector.connectDB().prepareStatement("INSERT INTO stokbarang (namabarang, jumlah) values (?,?);");
                    preparedStatement.setString(1, statNama);
                    if (statJumlah.matches("\\d+")) {
                    preparedStatement.setString(2, statJumlah);
                    preparedStatement.executeUpdate();
                    showData();
                    JOptionPane.showMessageDialog(null, "Barang berhasil ditambahkan.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Mohon memasukkan angka pada kolom 'Jumlah'.");
                    }

                } catch (SQLException err){
                    Logger.getLogger(DataInterface.class.getName()).log(Level.SEVERE, null, err);
                }
                textFieldNama.setText("");
                textFieldJumlah.setText("");
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::createUpdateGUI);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::createDeleteGUI);
            }
        });
    }

    private void showData() {
        try{
            Object[] collumTitle = {"Id", "Nama Barang", "Jumlah"};
            tableModel = new DefaultTableModel(null, collumTitle);
            jTable.setModel(tableModel);

            Connection connection = Connector.connectDB();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            resultSet = statement.executeQuery("SELECT * FROM stokbarang");
            while (resultSet.next()) {
                Object[] data = {
                        resultSet.getString("id"),
                        resultSet.getString("namabarang"),
                        resultSet.getString("jumlah")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }

    public JPanel getMainPanel() {
        showData();
        return mainPanel;
    }

    private static void createUpdateGUI() {
        updatePanel UpdateUI = new updatePanel();
        JPanel updateRoot = UpdateUI.getMainUpdatePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(updateRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private DefaultTableModel tableModel;

    private ResultSet resultSet;

    private PreparedStatement preparedStatement;

    private static void createDeleteGUI(){
        deletePanel deleteUI = new deletePanel();
        JPanel deleteRoot = deleteUI.getDeletePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(deleteRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private JPanel mainPanel;
    private JLabel jJudulPanel;
    private JTextField textFieldNama;
    private JTextField textFieldJumlah;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel jFirstPanel;
    private JPanel jSecPanel;
    private JPanel jThirdPanel;
    private JLabel jLabelNamabarang;
    private JLabel jLabelJumlah;
    private JTable jTable;
    private JScrollPane jScroll;


}
