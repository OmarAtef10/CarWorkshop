import java.sql.*;
import java.util.Scanner;

public class DB {
    public static void connect(String u,String p){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/conTEST?serverTimezone=UTC",
                    u,
                    p
            );
            System.out.println("CONNECTED!");

            Statement s = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet res = s.executeQuery("SELECT ID, firstname, lastname, email FROM Customers;");
            //To show data about table
            ResultSetMetaData data = res.getMetaData();

            System.out.println(data.getColumnName(1));

            res.last();
            Scanner scanner = new Scanner(System.in);
            scanner.nextInt();
            System.out.println("Number of rows: "+res.getRow()+"\nNumber of columns: "+data.getColumnCount());


        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
