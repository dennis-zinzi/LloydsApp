package uk.ac.ncl.csc2022.team10.databasemanager;

/* author : Sanzhar Zholdiyarov
 * email: zholdiyarov@gmail.com
 * date modified: 16/02/14
 * Purpose: This class is Database Manager, which helps to retrieve information from a mysql databases
 */
import java.sql.DriverManager;
import java.sql.ResultSet;
import android.database.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DatabaseManager {

    /* Declaration of variables */
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    /** Method to connect to a database **/
    public void startConnection(String connectionAddress) throws SQLException,
            ClassNotFoundException, java.sql.SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connect = (Connection) DriverManager.getConnection(connectionAddress);
        statement = (Statement) connect.createStatement();
    }

    /** Method to stop connection **/
    public void stopConnection() throws SQLException, java.sql.SQLException {
        connect.close();
    }

    /** Method to get name **/
    public String getName(int id) throws SQLException, java.sql.SQLException {
        resultSet = statement.executeQuery("SELECT name FROM users WHERE id="
                + id);
        while (resultSet.next()) {
            String user = resultSet.getString("name");
            return user;
        }
        return null;
    }

    /** Method to get balance **/
    public int getBalance(int id) throws SQLException, java.sql.SQLException {
        resultSet = statement
                .executeQuery("SELECT balance FROM users WHERE id=" + id);
        while (resultSet.next()) {
            int balance = resultSet.getInt("balance");
            return balance;
        }
        return 0;
    }

    /** Method to get login information **/
    public int checkLogin(int account, String password) throws SQLException,
            java.sql.SQLException {
        resultSet = statement
                .executeQuery("SELECT password FROM login WHERE acc=" + account);
        while (resultSet.next()) {
            String pwd = resultSet.getString("password");
            if (password.equalsIgnoreCase(pwd)) {
                return 1; // If passwords is correct
            } else {
                return 0; // If something wrong
            }
        }
        return 0;
    }


}