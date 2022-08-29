package org.softwareengine.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Covenant {

    private int seq ;
    private int id ;
    private String account ;
    private String name ;
    private String recipient ;
    private int brnID ;
    private String brnName ;
    private String des ;
    private double debit ;
    private double credit ;


    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBrnName() {
        return brnName;
    }

    public void setBrnName(String brnName) {
        this.brnName = brnName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getBrnID() {
        return brnID;
    }

    public void setBrnID(int brnID) {
        this.brnID = brnID;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public void save() throws SQLException {
        String sql = "INSERT INTO Covnenat (id,name,recipient,banks,desc,debit,credit,account) VALUES (?,?,?,?,?,?,?,?)" ;

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

        ps.setInt(1,this.id);
        ps.setString(2,this.name);
        ps.setString(3,this.recipient);
        ps.setInt(4,this.brnID);
        ps.setString(5,this.des);
        ps.setDouble(6,this.debit);
        ps.setDouble(7,this.credit);
        ps.setString(8,this.account);

        ps.executeUpdate();

        DatabaseService.CloseConnection();
    }

    public ObservableList<Covenant> getInfo() throws SQLException {
        ObservableList<Covenant> list = FXCollections.observableArrayList();
//            String sql = "SELECT * FROM item ORDER BY id";
        String sql = "SELECT id , name , recipient,(SELECT name from banks WHERE id = banks) as bank,desc " +
                ", debit, credit , account FROM Covnenat " ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            Covenant one = new Covenant() ;

            one.setSeq(++i);
            one.setId(resultSet.getInt("id"));
            one.setName(resultSet.getString("name"));
            one.setRecipient(resultSet.getString("recipient"));
            one.setBrnName(resultSet.getString("bank"));
            one.setDes(resultSet.getString("desc"));
            one.setDebit(resultSet.getDouble("debit"));
            one.setCredit(resultSet.getDouble("credit"));
            one.setAccount(resultSet.getString("account"));
            list.add(one);
        }
        return list ;
    }

    public Covenant getInfoWHEREid() throws SQLException {
        ObservableList<Covenant> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Covnenat WHERE id = "+this.id;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        resultSet.next() ;

        Covenant one = new Covenant();

        one.setId(resultSet.getInt("id"));
        one.setName(resultSet.getString("name"));

        DatabaseService.CloseConnection();

        return one ;
    }

}
