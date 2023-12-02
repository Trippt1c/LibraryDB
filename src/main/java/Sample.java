import java.sql.ResultSet;
import java.sql.SQLException;

public class Sample
{
    public static void main(String[] args) throws SQLException
    {
        QueryHandler handler = new QueryHandler();
        ResultSet matchingTitles = handler.query("SELECT * FROM BOOK WHERE Title LIKE '%Classical%'");
        while (matchingTitles.next())
        {
            //.out.println("matching titles");
            //printResultSet(matchingTitles);

            String title = matchingTitles.getString("Title");
            String isbn = matchingTitles.getString("Isbn");
            System.out.println("isbn: " + isbn);
            ResultSet authorids = handler.query("SELECT * FROM BOOK_AUTHORS WHERE Isbn LIKE '%" + isbn + "%'");
            //System.out.println("author ids");
            //printResultSet(authorids);
            while (authorids.next())
            {
                String authorid = authorids.getString("Author_id");
                String authorName = authorids.getString("Name");
                System.out.println("author id: " + authorid);
                System.out.println("author name: " + authorName);
                ResultSet authorNames = handler.query("SELECT * FROM AUTHORS WHERE Author_id LIKE '%" + authorid + "%'");
                System.out.println("author names");
                printResultSet(authorNames);
            }

        }
    }
    public static void printResultSet(ResultSet rs) throws SQLException
    {
        int col = rs.getMetaData().getColumnCount();
        while (rs.next())
        {
            for (int i = 1; i <= col; i++)
            {
                System.out.println(rs.getString(i) + " ");
            }
        }
    }
}