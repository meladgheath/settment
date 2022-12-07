package org.softwareengine.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {

    private int seq ;
    private int  id ;
    private String covnenatID ;
    private String name ;

    private String account ;
    private double debit ;
    private double credit ;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCovnenatID() {
        return covnenatID;
    }

    public void setCovnenatID(String covnenatID) {
        this.covnenatID = covnenatID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void saveif() throws SQLException {
        String sql = "INSERT into transactions (covnenatID,name,debit,credit,account)  " +
                " SELECT id ,name ,debit , credit ,account   FROM Covnenat where id = '"+this.covnenatID+"'";

        System.out.println(sql);
        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        statement.executeUpdate(sql);
        DatabaseService.CloseConnection();

    }

    public void save() throws SQLException {
        String sql ;



        sql = "INSERT INTO transactions (covnenatID, name ,debit, credit,account) VALUES (?,?,?,?,?)";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

        ps.setString(1, this.covnenatID);
        ps.setString(2, this.name);
        ps.setDouble(3, this.debit);
        ps.setDouble(4, this.credit);
        ps.setString(5,this.account);
        ps.executeUpdate();

        DatabaseService.CloseConnection();
    }


    public ObservableList<Transaction> getInfo() throws SQLException {
        ObservableList<Transaction> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM transactions WHERE covnenatID = '"+this.covnenatID+"'" ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0;
        while (resultSet.next()) {
            Transaction one = new Transaction();

            one.setSeq(++i);
            one.setId(resultSet.getInt("id"));
            one.setCovnenatID(resultSet.getString("covnenatID"));
            one.setName(resultSet.getString("name"));
            one.setAccount(resultSet.getString("account"));
            one.setCredit(resultSet.getDouble("credit"));
            one.setDebit(resultSet.getDouble("debit"));

            list.add(one);
        }
        resultSet.close();
        statement.close();
        DatabaseService.CloseConnection();
        return list;
    }
    public  boolean Ishas () throws SQLException {
        String sql = "SELECT count(*) as c FROM transactions WHERE covnenatID = '"+this.covnenatID+"'" ;

        DatabaseService.openConnection();
        Statement stat = DatabaseService.connection.createStatement();
        ResultSet resultSet = stat.executeQuery(sql);
        resultSet.next();
        boolean ishas ;
        if (resultSet.getInt("c")==0)
            ishas=true ;
        else
            ishas=false;
        DatabaseService.CloseConnection();

        return ishas ;
    }
}