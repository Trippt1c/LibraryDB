import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tripptic on 11/26/2023.
 * Project: LibraryDB
 * File: StartDB
 * Good Luck
 */

public class StartDB
{
    public static void main(String[] args) throws SQLException, IOException
    {
        QueryHandler DB = new QueryHandler();
        ArrayList<TBook> books = parseBooks("books.csv");
        System.out.println(books.get(0));
        System.out.println(books.get(1));
        initDB(DB);
        addAuthors(DB,books);
    }

    public static ArrayList<TBook> parseBooks(String fileName) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        ArrayList<TBook> books = new ArrayList<>();
        String line = in.readLine();
        while((line = in.readLine()) != null)
        {
            String[] data = line.split("\t");
            String isbn = data[1];
            String title = data[2];
            String[] authors = data[3].split(",");
            books.add(new TBook(isbn,title,authors));
        }
    	return books;
    }

    public static void addAuthors(QueryHandler DB, ArrayList<TBook> books) throws SQLException
    {
        ArrayList<String> authors = new ArrayList<>();
        for (TBook book : books)
        {
            for (String author : book.authors)
            {
                if(!authors.contains(author))
                {
                    DB.update("INSERT INTO AUTHORS (Name) VALUES ('"+author+"')");
                    authors.add(author);
                }
            }
        }
    }

    public static void initDB(QueryHandler DB) throws SQLException
    {
    	DB.update("CREATE TABLE IF NOT EXISTS BOOK (Isbn TEXT PRIMARY KEY, Title TEXT)");
        DB.update("CREATE TABLE IF NOT EXISTS BOOK_AUTHORS (Author_id INTEGER PRIMARY KEY, Isbn TEXT)");
    	DB.update("CREATE TABLE IF NOT EXISTS AUTHORS (Author_id INTEGER PRIMARY KEY, Name TEXT)");
        DB.update("CREATE TABLE IF NOT EXISTS BORROWER (Card_id INTEGER PRIMARY KEY, Ssn TEXT, Bname TEXT, Address TEXT, Phone TEXT)");
    	DB.update("CREATE TABLE IF NOT EXISTS BOOK_LOANS (Loan_id INTEGER PRIMARY KEY, Isbn TEXT, Card_id INTEGER, Date_out TEXT, Due_date TEXT, Date_in TEXT)");
    	DB.update("CREATE TABLE IF NOT EXISTS FINES (Loan_id INTEGER PRIMARY KEY, fine_amt REAL, Paid INTEGER)");
    }

    public void wipeDB(QueryHandler DB) throws SQLException
    {
    	DB.update("DROP TABLE IF EXISTS BOOK");
    	DB.update("DROP TABLE IF EXISTS BOOK_AUTHORS");
    	DB.update("DROP TABLE IF EXISTS AUTHORS");
    	DB.update("DROP TABLE IF EXISTS BORROWER");
    	DB.update("DROP TABLE IF EXISTS BOOK_LOANS");
    	DB.update("DROP TABLE IF EXISTS FINES");
        initDB(DB);
    }
}
class TBook
{
    String isbn;
    String title;
    String[] authors;

    public TBook(String isbn, String title, String[] authors)
    {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
    }

    @Override
    public String toString()
    {
        return "Book{" +
               "isbn='" + isbn + '\'' +
               ", title='" + title + '\'' +
               ", authors=" + Arrays.toString(authors) +
               '}';
    }
}


