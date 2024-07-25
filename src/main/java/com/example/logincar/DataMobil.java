package com.example.logincar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataMobil {


        @FXML
        private Button keluarMobil;

        @FXML
        private RadioButton auto;

        @FXML
        private Button hapusMobil;

        @FXML
        private TextField inputkode;

        @FXML
        private TextField kapasitas;

        @FXML
        private RadioButton manual;

        @FXML
        private TextField mobil;

        @FXML
        private Button simpanmobil;

        @FXML
        private ToggleGroup transmisi;




        Connection con;
        PreparedStatement pst;



        public void hapusDataMobil(ActionEvent event){
            String databaseName = "mobil";
            String username = "root";
            String password = "fahmifadilah25";
            String url = "jdbc:mysql://127.0.0.1:3306/"+databaseName;
            String kode = inputkode.getText();
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url, username, password);
                pst = con.prepareStatement("DELETE FROM dataMobil WHERE kode_mobil = ?");
                pst.setString(1,kode);

                int status = pst.executeUpdate();

                if(status == 1){
                    JOptionPane.showMessageDialog(null,"Record added");

                }else{
                    JOptionPane.showMessageDialog(null,"Record failed");
                }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void simpanDataMobil(ActionEvent event){
            String databaseName = "mobil";
            String username = "root";
            String password = "fahmifadilah25";
            String url = "jdbc:mysql://127.0.0.1:3306/"+databaseName;

            String namaMobil = mobil.getText();
            String kode = inputkode.getText();
            Integer kapasitasMobil = Integer.valueOf(kapasitas.getText());
            transmisi = new ToggleGroup();
            auto.setToggleGroup(transmisi);
            manual.setToggleGroup(transmisi);

            RadioButton rb = (RadioButton) transmisi.getSelectedToggle();
            String selectValue = rb.getText();
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url, username, password);
                pst = con.prepareStatement("INSERT INTO dataMobil(kode_mobil, nama_mobil, transmisi, kapasitas)VALUES(?,?,?,?)");
                pst.setString(1,kode);
                pst.setString(2,namaMobil);
                pst.setString(3, selectValue);
                pst.setInt(4,kapasitasMobil);
                int status = pst.executeUpdate();

                if(status == 1){
                    JOptionPane.showMessageDialog(null,"Record added");

                }else{
                    JOptionPane.showMessageDialog(null,"Record failed");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        public void keluar(ActionEvent event){
            Stage stage = (Stage) keluarMobil.getScene().getWindow();
            stage.close();
        }



}
