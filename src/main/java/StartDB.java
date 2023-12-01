import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
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
        ArrayList<TBorrower> borrowers = parseBorrowers("borrowers.csv");

        wipeDB(DB);
        addAuthors(DB,books);
        addBooks(DB,books);
        addBookAuthors(DB,books);
        addBorrowers(DB,borrowers);
        DB.close();
    }

    public static ArrayList<TBook> parseBooks(String fileName) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        ArrayList<TBook> books = new ArrayList<TBook>();
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

        ArrayList<String> authors = new ArrayList<String>();
        String insertQuery = "INSERT INTO AUTHORS (Name) VALUES (?)";
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(insertQuery);
        for (TBook book : books)
        {
            for (String author : book.authors)
            {
                if(!authors.contains(author))
                {
                    preparedStatement.setString(1,author);
                    preparedStatement.executeUpdate();
                    authors.add(author);
                    System.out.println("Added author: " + author);
                }
            }
        }
    }

    public static void addBooks(QueryHandler DB, ArrayList<TBook> books) throws SQLException
    {
        String insertQuery = "INSERT INTO BOOK (Isbn, Title) VALUES (?,?)";
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(insertQuery);
        for (TBook book : books)
        {
            preparedStatement.setString(1,book.isbn);
            preparedStatement.setString(2,book.title);
            preparedStatement.executeUpdate();
            System.out.println("Added book: " + book);
        }
    }

    public static void addBookAuthors(QueryHandler DB, ArrayList<TBook> books) throws SQLException
    {
        String insertQuery = "INSERT INTO BOOK_AUTHORS (Isbn, Author_id) VALUES (?,?)";
        ArrayList<String> pairs = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(insertQuery);
        for (TBook book : books)
        {
            for (String author : book.authors)
            {
                if(!pairs.contains(book.isbn +" "+ author))
                {
                    preparedStatement.setString(1, book.isbn);
                    preparedStatement.setString(2, author);
                    preparedStatement.executeUpdate();
                    pairs.add(book.isbn +" "+ author);
                    System.out.println("Added book author: " + book.isbn + " " + author);
                }
            }
        }
    }

    public static void addBorrowers(QueryHandler DB, ArrayList<TBorrower> borrowers) throws SQLException
    {
        String insertQuery = "INSERT INTO BORROWER (Ssn, Bname, Address, Phone) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(insertQuery);
        for (TBorrower borrower : borrowers)
        {
            preparedStatement.setString(1,borrower.ssn);
            preparedStatement.setString(2,borrower.bname);
            preparedStatement.setString(3,borrower.address);
            preparedStatement.setString(4,borrower.phone);
            preparedStatement.executeUpdate();
            System.out.println("Added borrower: " + borrower);
        }
    }

    public static ArrayList<TBorrower> parseBorrowers(String fileName) throws IOException
    {
    	BufferedReader in = new BufferedReader(new FileReader(fileName));
    	ArrayList<TBorrower> borrowers = new ArrayList<>();
    	String line = in.readLine();
    	while((line = in.readLine()) != null)
    	{
    		String[] data = line.split(",");
    		String ssn = data[1];
    		String bname = data[2] + " " + data[3];
    		String address = data[5];
    		String phone = data[8];
    		borrowers.add(new TBorrower(ssn,bname,address,phone));
    	}
    	return borrowers;
    }

    public static void initDB(QueryHandler DB)
    {
    	DB.update("CREATE TABLE IF NOT EXISTS BOOK (Isbn TEXT PRIMARY KEY, Title TEXT)");
        DB.update("CREATE TABLE IF NOT EXISTS BOOK_AUTHORS (Author_id INTEGER, Isbn TEXT, PRIMARY KEY(Author_id, Isbn))");
    	DB.update("CREATE TABLE IF NOT EXISTS AUTHORS (Author_id INTEGER PRIMARY KEY, Name TEXT)");
        DB.update("CREATE TABLE IF NOT EXISTS BORROWER (Card_id INTEGER PRIMARY KEY, Ssn TEXT, Bname TEXT, Address TEXT, Phone TEXT)");
    	DB.update("CREATE TABLE IF NOT EXISTS BOOK_LOANS (Loan_id INTEGER PRIMARY KEY, Isbn TEXT, Card_id INTEGER, Date_out TEXT, Due_date TEXT, Date_in TEXT)");
    	DB.update("CREATE TABLE IF NOT EXISTS FINES (Loan_id INTEGER PRIMARY KEY, fine_amt REAL, Paid INTEGER)");
    }

    public static void wipeDB(QueryHandler DB)
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
class TBorrower
{
    String ssn;
    String bname;
    String address;
    String phone;

    public TBorrower(String ssn, String bname, String address, String phone)
    {
        this.ssn = ssn;
        this.bname = bname;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return "Borrower{" +
               ", ssn='" + ssn + '\'' +
               ", bname='" + bname + '\'' +
               ", address='" + address + '\'' +
               ", phone='" + phone + '\'' +
               '}';
    }
}


