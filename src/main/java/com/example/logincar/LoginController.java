package com.example.logincar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;



public class LoginController {

    @FXML
    public Button simpanDashboard;

    @FXML
    private Button cancelbutton;

    @FXML
    private Label massageButton;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button keluarDashboard;

    @FXML
    private Button dataPeminjam;

    @FXML
    private Button dataMobil;

    @FXML
    private TextField alamatPeminjam;

    @FXML
    private TextField emailPeminjam;

    @FXML
    private TextField kode;

    @FXML
    private TextField namaPeminjam;
    @FXML
    private ToggleGroup pembayaran;
    @FXML
    private TextField telp;

    @FXML
    private RadioButton bank;

    @FXML
    private RadioButton cash;

    @FXML
    private RadioButton dana;
    @FXML
    private RadioButton ovo;
    @FXML
    private RadioButton auto;

    @FXML
    private TextField inputkode;

    @FXML
    private TextField kapasitas;

    @FXML
    private RadioButton manual;

    @FXML
    private TextField mobil;

    @FXML
    private Button simpanDashboard2;

    @FXML
    private ToggleGroup transmisi;


    Connection con;
    PreparedStatement pst;

    // form data peminjam
    @FXML
    public void simpanDashboard(ActionEvent event){
        String databaseName = "loginCar";
        String username = "root";
        String password = "fajar01";
        String url = "jdbc:mysql://127.0.0.1:3306/"+databaseName;

        String nama = namaPeminjam.getText();
        String telfon = telp.getText();
        String alamat = alamatPeminjam.getText();
        String email = emailPeminjam.getText();
        String kodeMobil = kode.getText();
        pembayaran = new ToggleGroup();
        bank.setToggleGroup(pembayaran);
        dana.setToggleGroup(pembayaran);
        ovo.setToggleGroup(pembayaran);
        cash.setToggleGroup(pembayaran);

        RadioButton rd = (RadioButton) pembayaran.getSelectedToggle();
        String selectValue = rd.getText();


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            pst = con.prepareStatement("INSERT INTO dataPeminjam(nama_peminjam, no_telp, alamat, email, kode_mobil, metode_bayar)VALUES(?,?,?,?,?,?)");
            pst.setString(1,nama);
            pst.setString(2,telfon);
            pst.setString(3,alamat);
            pst.setString(4,email);
            pst.setString(5,kodeMobil);
            pst.setString(6, selectValue);
            int status = pst.executeUpdate();

            if(status == 1){
                JOptionPane.showMessageDialog(null,"Record added");
                namaPeminjam.setText("");
                telp.setText("");
                alamatPeminjam.setText("");
                emailPeminjam.setText("");
                kode.setText("");
                namaPeminjam.requestFocus();
            }else{
                JOptionPane.showMessageDialog(null,"Record failed");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // form data mobil
    @FXML
    public void simpanDashboard2(ActionEvent event){
        String databaseName = "loginCar";
        String username = "root";
        String password = "fahmifadilah25";
        String url = "jdbc:mysql://localhost:3306/"+databaseName;

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




    // Pindah ke dashboard mobil
    public void btnDataMobil(ActionEvent event){
        try {
            Stage stage1 = (Stage) dataMobil.getScene().getWindow();
            stage1.close();
            Stage otherWindow = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard2.fxml"));
            Parent root = loader.load();

            otherWindow.initStyle(StageStyle.UNDECORATED);
            otherWindow.setScene(new Scene(root));


            otherWindow.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Pindah ke Data Peminjam
    public void btnDataPeminjam(ActionEvent event){
        try {
            Stage stage1 = (Stage) dataPeminjam.getScene().getWindow();
            stage1.close();
            Stage otherWindow = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dasboard.fxml"));
            Parent root = loader.load();

            otherWindow.initStyle(StageStyle.UNDECORATED);
            otherWindow.setScene(new Scene(root));


            otherWindow.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // menutup dashboard
    public void exitDashboard(ActionEvent event){
        Stage stage = (Stage) keluarDashboard.getScene().getWindow();
        stage.close();
    }

    //
    public void massageButtonEvent(ActionEvent event){

        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()){
            validateLogin();
        }else {
            massageButton.setText("please enter username and password");
        }

    }

    // Melakukan Cancel Aplikasi
    public void cancelButtonActions(ActionEvent e){
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }

    // Melakukan Validasi Login
    public void validateLogin(){
        LoginDbConnection loginDbConnection = new LoginDbConnection();
        Connection connection = loginDbConnection.getConnection();

        String verifyLogin = "SELECT count(1) FROM loginform WHERE username = '"+usernameTextField.getText()+"' AND password = '"+passwordTextField.getText()+"'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(verifyLogin);

            while (result.next()){
                if (result.getInt(1) == 1){
                    massageButton.setText("Welcome");
                    switchToDasboard();
                } else {
                    massageButton.setText("invalid login, please try again!");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Berpindah ke Dashboard
    public void switchToDasboard()  {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("dasboard.fxml"));
            Parent root = loader.load();


            Stage otherWindow = new Stage();
            otherWindow.initStyle(StageStyle.UNDECORATED);
            otherWindow.setScene(new Scene(root));


            otherWindow.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}