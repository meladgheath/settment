package org.softwareengine.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class banks {


        private int id ;

        private String name ;

        private String referenceNumber ;


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


    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void save() throws SQLException {
            String sql = "INSERT INTO banks (name,ref) VALUES (?,?)" ;

            DatabaseService.openConnection();
            PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

//            ps.setInt   (1,this.code    );
//            ps.setString(2,this.name    );
//            ps.setInt   (3,this.packages);
            ps.setString(1,this.name);
            ps.setString(2,this.referenceNumber);

            ps.executeUpdate();

            DatabaseService.CloseConnection();
        }

        public ObservableList<org.softwareengine.core.model.banks> getInfo() throws SQLException {
            ObservableList<banks> list = FXCollections.observableArrayList();
            String sql = "SELECT * FROM banks ORDER BY id";

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int i = 0 ;
            while (resultSet.next()) {
                banks one = new banks() ;


                one.setId(resultSet.getInt("id"));
                one.setName(resultSet.getString("name"));
                one.setReferenceNumber(resultSet.getString("ref"));

                list.add(one);
            }
            return list ;
        }

    public ObservableList<org.softwareengine.core.model.banks> getInfoIDs() throws SQLException {
        ObservableList<banks> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM banks ORDER BY id";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            banks one = new banks() ;

            one.setId(resultSet.getInt("id"));
            one.setName(resultSet.getString("name"));
            one.setReferenceNumber(resultSet.getString("ref"));

            list.add(one);
        }
        return list ;
    }

    public ObservableList<org.softwareengine.core.model.banks> getInfoWHEREid() throws SQLException {
        ObservableList<banks> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM banks WHERE id ="+this.id;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            banks one = new banks() ;

            one.setId(resultSet.getInt("id"));
            one.setName(resultSet.getString("name"));
            one.setReferenceNumber(resultSet.getString("ref"));

            list.add(one);
        }
        return list ;
    }

    public ObservableList<banks> getInfoWHEREref() throws SQLException {
        ObservableList<banks> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM banks WHERE ref ='"+this.referenceNumber+"'";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            banks one = new banks() ;

            one.setId(resultSet.getInt("id"));
            one.setName(resultSet.getString("name"));
            one.setReferenceNumber(resultSet.getString("ref"));

            list.add(one);
        }
        return list ;
    }
    public ObservableList<org.softwareengine.core.model.banks> getWHERElike() throws SQLException {
        ObservableList<banks> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM banks WHERE name like '%"+this.name+"%'";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int i = 0 ;
        while (resultSet.next()) {
            banks one = new banks() ;

            one.setId(resultSet.getInt("id"));
            one.setName(resultSet.getString("name"));
            one.setReferenceNumber(resultSet.getString("ref"));

            list.add(one);
        }
        return list ;
    }

    public void  delete() throws SQLException {
            ObservableList<banks> list = FXCollections.observableArrayList();
            String sql = "DELETE FROM banks WHERE id = "+this.id;

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();


            statement.executeUpdate(sql);
        }



        public ObservableList<banks> getInfoID() throws SQLException {
            ObservableList<banks> list = FXCollections.observableArrayList();
            String sql = "SELECT id FROM banks ";

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            while (resultSet.next()) {
                banks one = new banks() ;

                one.setId(resultSet.getInt("id"));

                list.add(one);
            }
            return list ;
        }


        public banks getInfoByID() throws SQLException {

            String sql;

            sql = "SELECT * FROM banks where id =  "+this.id;


            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            banks one = null ;
            while (resultSet.next()) {
                one = new banks() ;

                one.setId(resultSet.getInt("id"));

                one.setName(resultSet.getString("name"));



            }
            return one ;
        }
    }


