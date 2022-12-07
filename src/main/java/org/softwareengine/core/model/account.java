package org.softwareengine.core.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.utils.service.DatabaseService;

import java.io.*;
import java.sql.*;


public class account {


    private int seq ;
    private int id ;
    private String number  ;
    private String name    ;


    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void save() throws SQLException, FileNotFoundException {
        String sql = "INSERT  INTO  account ( number , name  ) values (? , ?)" ;
        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

        ps.setString(1,this.number);
        ps.setString(2,this.name    );

        ps.executeUpdate();

        DatabaseService.CloseConnection();
    }


    public ObservableList<account> getInfoWHERENumber() throws SQLException {
        ObservableList<account> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM account WHERE number = '"+this.number+"'";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            account one = new account() ;

            one.setName(resultSet.getString("name"));
            one.setNumber(resultSet.getString("number"));

            list.add(one);
        }
        return list ;
    }
    public account getInfoWHERENumberAllon() throws SQLException {

        String sql = "SELECT * FROM account WHERE number = '"+this.number+"'";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);


        resultSet.next() ;
            account one = new account() ;

            one.setName(resultSet.getString("name"));
            one.setNumber(resultSet.getString("number"));



        return one ;
    }
    public ObservableList<account> getWHERElike() throws SQLException {
        ObservableList<account> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM account WHERE name like '%"+this.name+"%'";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            account one = new account() ;

//            one.setId(resultSet.getInt("id"));
            one.setName(resultSet.getString("name"));
            one.setNumber(resultSet.getString("number"));

            list.add(one);
        }
        return list ;
    }

    public void  delete() throws SQLException {
        ObservableList<account> list = FXCollections.observableArrayList();
        String sql = "DELETE FROM account WHERE number = '"+this.number+"'";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();

        System.out.println(sql);
        statement.executeUpdate(sql);
    }


    public ObservableList<account> getInfo() throws SQLException {
        ObservableList<account> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM account ";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            account one = new account() ;

            one.setSeq(++i);
            one.setNumber(resultSet.getString("number"));
            one.setName(resultSet.getString("name"));

            list.add(one);
        }
        return list ;
    }
}