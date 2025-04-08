package rfp;

import java.sql.*;

public class DbConnect {

    private static final String url = "jdbc:mysql://localhost:3306/rfp";
    private static final String username = "root";
    private static final String password = "velocityRFP";



    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

//        Connection connection =
        return DriverManager.getConnection(url, username, password);

        /*
         String query1 = String.format("INSERT into user(email, password, role) values('%s', '%s', '%s')", "first", "last", "admin");
         int effected = statement.executeUpdate(query1);
         if (effected > 0) System.out.println("Data inserted");
         else System.out.println("data not added");
         String query = "Select * from checker";
         ResultSet result = statement.executeQuery(query);
         while(result.next()) {
             int id = result.getInt("id");
             String name = result.getString("name");
             System.out.println(id + " " + name);
         }
        */

    }
}

