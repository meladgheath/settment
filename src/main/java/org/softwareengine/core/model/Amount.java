package org.softwareengine.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Amount {
    private int seq ;
    private int id;
    private int employment;
    private int storeID;
    private int num;
    private int empID ;
    private String employmentName;
    private String store;
    private String stateName;
    private String mark ;

    private int status ;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getSeq() {
        return seq;
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public int getEmployment() {
        return employment;
    }

    public void setEmployment(int employment) {
        this.employment = employment;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getEmploymentName() {
        return employmentName;
    }

    public void setEmploymentName(String employmentName) {
        this.employmentName = employmentName;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void save() throws SQLException {
        String sql = "insert into degree ( employment , num  ) values (?,?)" ;

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);


        ps.setInt(1,this.employment);
//        ps.setInt(2,this.de  );

        ps.setInt(2,this.num);

        ps.executeUpdate();

        DatabaseService.CloseConnection();
    }

    public int getCount() throws SQLException {
        String sql = "SELECT count(id)  as c FROM degree " ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next() ;
        int counter = resultSet.getInt("c");
        DatabaseService.connection.close();

        return counter;

    }

    public int getCount(int first , int second) throws SQLException {
        String sql = "SELECT count(id ) as c from degree WHERE " +
                "num >="+first+" and num <= "+second ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next() ;
        int counter = resultSet.getInt("c");
        DatabaseService.connection.close();

        return counter;

    }

    public int getCount(int first , int second,int s) throws SQLException {

        String sql = "SELECT count(*) as c  FROM degree INNER JOIN employment on degree.employment = employment.id " +
                "WHERE status = "+s+" and (num >="+first+" AND num <= "+second+")" ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next() ;
        int counter = resultSet.getInt("c");
        DatabaseService.connection.close();

        System.out.println("the count of ex is "+counter);
        return counter;

    }
    public int getCount(int s) throws SQLException {
        String sql = "SELECT count(*) as c  FROM degree INNER JOIN employment on degree.employment = employment.id " +
                "WHERE status = "+s ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next() ;
        int counter = resultSet.getInt("c");
        DatabaseService.connection.close();

        System.out.println("get count with status "+counter);
        System.out.println("the status is "+s);
        return counter;

    }



    public Amount getInfoNumber() throws SQLException {
        String sql = "SELECT sum(num) as number FROM amount WHERE itemid = "+this.employment +" and storeid = "+this.storeID+" " ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next() ;

        Amount one = new Amount() ;
        one.setNum(resultSet.getInt("number"));

        DatabaseService.connection.close();

        return one;
    }

    public ObservableList<Amount> getInfoItemInSpecStore() throws SQLException {
        ObservableList<Amount> list = FXCollections.observableArrayList();
//            String sql = "SELECT  (SELECT name FROM item where id = itemid) as item , (SELECT name FROM store where id = storeid) as store , num FROM amount";
        String sql = "SELECT itemid ,(SELECT name FROM item WHERE id = itemid ) as items , \n" +
                "sum(num) as number  FROM amount WHERE storeid = "+this.storeID+"  GROUP by itemid " ;


        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println(" the access is "+this.storeID);
        int i = 0 ;
        while (resultSet.next()) {
            Amount one = new Amount() ;

            one.setId(++i);

            one.setEmployment(resultSet.getInt("itemid"));
            one.setEmploymentName(resultSet.getString("items"));
            one.setNum(resultSet.getInt("number"));

            list.add(one);
        }
        resultSet.close();
        statement.close();
        DatabaseService.CloseConnection();
        return list ;
    }

    //TODO This method has problem
    public ObservableList<Amount> getInfoTransactions() throws SQLException {
        ObservableList<Amount> list = FXCollections.observableArrayList();
//            String sql = "SELECT  (SELECT name FROM item where id = itemid) as item , (SELECT name FROM store where id = storeid) as store , num FROM amount";
        String sql = "SELECT (SELECT name FROM item WHERE id = itemid) as item ,\n" +
                "(CASE state\n" +
                "WHEN 'S' THEN (SELECT name FROM store WHERE id = storeid  ) \n" +
                "WHEN 'D' THEN (SELECT name FROM driver WHERE id = storeid )\n" +
                "END) as store , num ,\n" +
                "     (CASE state \n" +
                "   WHEN 'S' THEN 'Store'\n" +
                "   WHEN 'D' THEN 'Driver'\n" +
                "   END) as state FROM amount " ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            Amount one = new Amount() ;

            one.setId(++i);
            one.setStore(resultSet.getString("store"));
            one.setEmploymentName(resultSet.getString("item"));
            one.setNum(resultSet.getInt("num"));
//                one.setStateName(resultSet.getString("state"));

            list.add(one);
        }
        resultSet.close();
        statement.close();
        DatabaseService.CloseConnection();
        return list ;
    }

    public void delete() throws SQLException {
        String sql = "delete from degree where id ="+this.id ;


        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();

        statement.executeUpdate(sql);

        DatabaseService.CloseConnection();
    }
    public ObservableList<Amount> getInfo() throws SQLException {
        ObservableList<Amount> list = FXCollections.observableArrayList();

//        String sql = "SELECT id , " +
//                "(select name from employment where id = degree.employment) as employment ," +
//                "(SELECT code FROM employment WHERE id = degree.employment ) as empId" +
//                ", num from degree" ;
        String sql = "SELECT id ,(select name from employment where id = degree.employment) as employment ,\n" +
                "(SELECT code FROM employment WHERE id = degree.employment ) as empId,\n" +
                "(CASE \n" +
                "WHEN num >=225 THEN 'ممتاز'\n" +
                "WHEN num <=224 AND num >=187 THEN 'جيد جدا'\n" +
                "WHEN num <=186 AND num >=150 THEN 'جيد'\n" +
                "WHEN num <=149 AND num >=111 THEN 'مقبول'\n" +
                "WHEN num <= 110 THEN 'ضعيف'\n" +
                " END) as mark\n" +
                ", num from degree" ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            Amount one = new Amount() ;

            one.setSeq(++i);
            one.setId(resultSet.getInt("id"));

            one.setEmpID(resultSet.getInt("empId"));
            one.setMark(resultSet.getString("mark")); ;
            one.setEmploymentName(resultSet.getString("employment"));
            one.setNum(resultSet.getInt("num"));

            list.add(one);
        }
        resultSet.close();
        statement.close();
        DatabaseService.CloseConnection();
        return list ;
    }

    public ObservableList<Amount> getspec() throws SQLException {
        ObservableList<Amount> list = FXCollections.observableArrayList();

//        String sql = "SELECT id , " +
//                "(select name from employment where id = degree.employment) as employment ," +
//                "(SELECT code FROM employment WHERE id = degree.employment ) as empId" +
//                ", num from degree" ;
        String sql = "SELECT id , (SELECT name FROM employment WHERE id = degree.employment) as employment \n" +
                ", num ,(SELECT code FROM employment WHERE id = degree.employment ) as empId ," +
                " (CASE \n" +
                "                WHEN num >=225 THEN 'ممتاز'\n" +
                "                WHEN num <=224 AND num >=187 THEN 'جيد جدا'\n" +
                "                WHEN num <=186 AND num >=150 THEN 'جيد'\n" +
                "                WHEN num <=149 AND num >=111 THEN 'مقبول'\n" +
                "                WHEN num <= 110 THEN 'ضعيف'\n" +
                "                END) as mark\n" +
                "\n" +
                " , (SELECT id FROM status where status.id = (SELECT status from employment WHERE id = degree.employment)) as st  from degree  where st = "+this.status ;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            Amount one = new Amount() ;

            one.setSeq(++i);
            one.setId(resultSet.getInt("id"));

            one.setEmpID(resultSet.getInt("empId"));
            one.setMark(resultSet.getString("mark")); ;
            one.setEmploymentName(resultSet.getString("employment"));
            one.setNum(resultSet.getInt("num"));

            list.add(one);
        }
        resultSet.close();
        statement.close();
        DatabaseService.CloseConnection();
        return list ;
    }


    public void update () throws SQLException {

        String sql = "" ;


        DatabaseService.openConnection();

        sql = "DELETE FROM amount where num = 0" ;

        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
//            ps.setString(1,this.name);
//            ps.setInt   (2,this.id  );

        ps.executeUpdate();


    }



    public ObservableList<org.softwareengine.core.model.Store> getInfoID() throws SQLException {
        ObservableList<org.softwareengine.core.model.Store> list = FXCollections.observableArrayList();
        String sql = "SELECT  id  FROM store order by id  ";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            org.softwareengine.core.model.Store one = new org.softwareengine.core.model.Store() ;

            one.setId(resultSet.getInt("id"));


            list.add(one);
        }
        resultSet.close();
        statement.close();
        DatabaseService.CloseConnection();
        return list ;
    }

    public Amount getNameByID() throws SQLException {
        ObservableList<Store> list = FXCollections.observableArrayList();
        String sql = "SELECT  name  FROM store where id = "+this.id;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next() ;

//            setName(resultSet.getString("name"));

        resultSet.close();
        statement.close();
        DatabaseService.CloseConnection();

        return this ;
    }
}