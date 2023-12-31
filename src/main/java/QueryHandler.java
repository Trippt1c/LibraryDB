import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Tripptic on 11/30/2023.
 * Project: LibraryDB
 * File: Query
 * Good Luck
 */

public class QueryHandler
{
    Connection connection = null;

    public QueryHandler() throws SQLException
    {
        connection = DriverManager.getConnection("jdbc:sqlite:Library.db");
    }

    public ResultSet query(String query)
    {
        try
        {
            return connection.createStatement().executeQuery(query);
            //return true;
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            return null;
            //return false;
        }
    }

    public boolean update(String query)
    {
        try
        {
            connection.createStatement().executeUpdate(query);
            return true;
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public void close()
    {
        try
        {
            if(connection != null)
                connection.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection()
    {
        return connection;
    }
}
