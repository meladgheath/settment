package org.softwareengine.core.model;



//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

    public class employment {

        private int id ;
        private int code ;
        private int status;
        private String statusName;
        private String name ;
        private double value ;
        private int packages ;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPackages() {
            return packages;
        }

        public void setPackages(int packages) {
            this.packages = packages;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        /*   public int getType() {
                    return status;
                }

                public void setType(int type) {
                    this.status = type;
                }

                public String getTypeName() {
                    return statusName;
                }

                public void setTypeName(String typeName) {
                    this.statusName = typeName;
                }
        */
        public void save() throws SQLException {
            String sql = "INSERT INTO employment (name , code, status) VALUES (?,?,?)" ;

            DatabaseService.openConnection();
            PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

//            ps.setInt   (1,this.code    );
//            ps.setString(2,this.name    );
//            ps.setInt   (3,this.packages);
            ps.setString(1,this.name);
            ps.setInt(2,this.code);
            ps.setInt(3,this.status);

            ps.executeUpdate();

            DatabaseService.CloseConnection();
        }

        public void delete() throws SQLException {
            String sql = "DELETE FROM employment WHERE id = "+ this.id;

            DatabaseService.openConnection();
            Statement stat = DatabaseService.connection.createStatement();

            stat.executeUpdate(sql);

            DatabaseService.CloseConnection();

        }

        public ObservableList<employment> getInfo() throws SQLException {
            ObservableList<employment> list = FXCollections.observableArrayList();
//            String sql = "SELECT * FROM item ORDER BY id";
            String sql = "select  name , code , (SELECT name from status where id = employment.status) as status\n" +
                    " from employment  " ;

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int i = 0 ;
            while (resultSet.next()) {
                employment one = new employment() ;

                one.setId(++i);
                one.setCode(resultSet.getInt("code"));
                one.setName(resultSet.getString("name"));
                one.setStatusName(resultSet.getString("status"));
//                one.setPackages(resultSet.getInt("package"));
//                one.setValue(resultSet.getDouble("value"));
                list.add(one);
            }
            return list ;
        }


        public ObservableList<employment> getInfoIDs() throws SQLException {
            ObservableList<employment> list = FXCollections.observableArrayList();
//            String sql = "SELECT * FROM item ORDER BY id";
            String sql = "select  * from employment  " ;

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int i = 0 ;
            while (resultSet.next()) {
                employment one = new employment() ;


                one.setId(resultSet.getInt("id"));
                one.setCode(resultSet.getInt("code"));
                one.setName(resultSet.getString("name"));
                one.setStatusName(resultSet.getString("status"));

                list.add(one);
            }
            return list ;
        }

        public employment getInfoWHEREid() throws SQLException {
            ObservableList<employment> list = FXCollections.observableArrayList();
            String sql = "SELECT * FROM employment WHERE code = "+this.code;

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int i = 0 ;
            resultSet.next() ;
            employment one = new employment() ;

            one.setId(resultSet.getInt("id"));
            one.setName(resultSet.getString("name"));

            DatabaseService.CloseConnection();

            return one ;
        }

        public void update (String cases) throws SQLException {

            String sql = "" ;



            DatabaseService.openConnection();


            PreparedStatement ps ;
            switch (cases) {
                case "name" :
                    sql = "UPDATE item SET name = ? where id = ?" ;
                    ps = DatabaseService.connection.prepareStatement(sql);
                    ps.setString(1,this.name);
                    ps.setInt   (2,this.id  );
                    ps.executeUpdate();
                    break;

                case "code" :
                    sql = "UPDATE item SET code = ? where id = ?" ;
                    ps = DatabaseService.connection.prepareStatement(sql);
                    ps.setInt(1,this.code);
                    ps.setInt(2,this.id  );
                    ps.executeUpdate();
                    break;
                case "package" :
                    sql = "UPDATE item SET package = ? where id = ?" ;
                    ps = DatabaseService.connection.prepareStatement(sql);
                    ps.setInt(1,this.packages);
                    ps.setInt(2,this.id  );
                    ps.executeUpdate();
                    break;
                case "value":
                    sql = "UPDATE item SET value = ? where id = ?" ;
                    ps = DatabaseService.connection.prepareStatement(sql);
                    ps.setDouble(1,this.value);
                    ps.setInt(2 ,this.id  );
                    ps.executeUpdate();
                    break;

            }

            System.out.println(sql);

            DatabaseService.CloseConnection();

        }

        public ObservableList<employment> getInfoID() throws SQLException {

                ObservableList<employment> list = FXCollections.observableArrayList();
//            String sql = "SELECT * FROM item ORDER BY id";
                String sql = "select id , name , code , (SELECT name from status where id = item.type) as type\n" +
                        " from employment  " ;

                DatabaseService.openConnection();
                Statement statement = DatabaseService.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                int i = 0 ;
                while (resultSet.next()) {
                    employment one = new employment() ;

                    one.setId(resultSet.getInt("id"));
                    one.setCode(resultSet.getInt("code"));
                    one.setName(resultSet.getString("name"));
                    one.setStatusName(resultSet.getString("status"));
//                one.setPackages(resultSet.getInt("package"));
//                one.setValue(resultSet.getDouble("value"));
                    list.add(one);
                }
                return list ;
            }


        public ObservableList<employment> getInfoByCode() throws SQLException {
            ObservableList<employment> list = FXCollections.observableArrayList();


            String sql;
            System.out.println("code . . . . . "+this.code);
            if (this.code == 0)
                sql = "SELECT id , code ,  name , value  FROM item ORDER BY id";
            else

                sql  = "SELECT id , code , name , value FROM item WHERE code::text like '%"+this.code+"%' ORDER BY id";

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            while (resultSet.next()) {
                employment one = new employment() ;

                one.setId(resultSet.getInt("id"));
                one.setCode(resultSet.getInt("code"));
                one.setName(resultSet.getString("name"));
                one.setValue(resultSet.getDouble("value"));

                list.add(one);
            }
            return list ;
        }


        public employment getInfoWHERECode() throws SQLException {



            String sql = "SELECT id , code ,  name , value  FROM employment WHERE code = "+this.code+" ORDER BY id";
            System.out.println(sql);

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);



            employment one = new employment() ;

            resultSet.next() ;

            one.setId(resultSet.getInt("id"));
            one.setCode(resultSet.getInt("code"));
            one.setName(resultSet.getString("name"));
            one.setValue(resultSet.getDouble("value"));

            return one ;
        }


        public employment getInfoByID() throws SQLException {



            String sql;

            sql = "SELECT * FROM item where id =  "+this.id;


            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            employment one = null ;
            while (resultSet.next()) {
                one = new employment() ;

                one.setId(resultSet.getInt("id"));
                one.setCode(resultSet.getInt("code"));
                one.setName(resultSet.getString("name"));
                one.setPackages(resultSet.getInt("package"));


            }
            return one ;
        }


        public void renewalItem() throws SQLException {

            String sql = "UPDATE item SET value = "+this.value+" WHERE id = "+this.id ;

            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();

            statement.executeUpdate(sql);


            DatabaseService.CloseConnection();

        }
    }