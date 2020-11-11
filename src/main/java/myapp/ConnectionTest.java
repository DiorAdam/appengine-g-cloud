package myapp;

import java.sql.*;

public class ConnectionTest {
    String url;
    public ConnectionTest(){
        //url = "jdbc:google:mysql://104.199.20.88:3306/GlucoseTest";
        url = "jdbc:google:mysql://capable-shard-294808:europe-west1:test/GlucoseTest";
    }
    public Connection connect(){
        Connection conn = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.jdbc.GoogleDriver");
            System.out.println("hehe");
            conn = DriverManager.getConnection(url ,"root", "gfh349.klm");
        }
        catch(Exception e){
            System.err.println(e);
        }
        return conn;
    }


    public void createTable(){
        String sql = "Create Table GlucPerMinute (minute INTEGER , glucose DOUBLE, PRIMARY KEY(minute))";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()){
            stmt.executeUpdate(sql);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    public void add(int minute, double glucose){
        String sql = "INSERT INTO GlucPerMinute(minute, glucose) VALUES(?,?)";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            System.out.println("Inserting Glucose Concentration Into GlucPerMinute");

            stmt.setInt(1, minute);
            stmt.setDouble(2, glucose);
            stmt.executeUpdate();
            System.out.println("Data successfully Inserted");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String select(int minute){
        String sql = "Select glucose from GlucPerMinute where minute = ?";
        String ans = "";

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, minute);
            System.out.println("selecting");
            ResultSet rs = stmt.executeQuery();
            System.out.println("selecting");
            while (rs.next()) {
                ans += rs.getDouble("glucose");
                System.out.println("Successfully selected");
            }
        }
        catch(Exception e){
            ans += e.getMessage();
            System.err.println(e.getMessage());
        }
        return ans;
    }
}
