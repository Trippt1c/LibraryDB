import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Rentals {
	private static JFrame window = new JFrame();
	private static ArrayList<String> loanids = new ArrayList<String>();
    public Rentals(String id) {
        window.setTitle("Rentals");
        //JButton newUser = new JButton("Create Account");
        JButton mainPage = new JButton("Return to Main Page"); // use this button to return to main page
        //JButton rentals = new JButton("View my rentals");
        JButton update = new JButton("Return Books");
        JButton pay = new JButton("Pay Fines");
        //JButton confirm = new JButton("Confirm Checkout");
        //JButton search = new JButton("Search");
        //JTextField searchEntry = new JTextField();
        ArrayList<Loan> loans = new ArrayList<Loan>();
        

        JLabel idLabel = new JLabel("ID "+ id); // TODO: make adjustable
        String name = "";
        
		try {
			QueryHandler handler = new QueryHandler();
			ResultSet user = handler.query("SELECT * FROM BORROWER WHERE Card_id LIKE '"+id+"'");
			while (user.next()) {
				name = user.getString("Bname");
			}
			ResultSet getLoans = handler.query("SELECT * FROM BOOK_LOANS WHERE Card_id LIKE '"+id+"'");
			while (getLoans.next()) {
				String loanId = getLoans.getString("Loan_id");
				String isbn = getLoans.getString("Isbn");
				String dateDue = getLoans.getString("Due_date");
				String dateIn = getLoans.getString("Date_in");
				boolean hasFine = false;
				ResultSet getFine = handler.query("SELECT * FROM FINES WHERE loan_id LIKE '"+loanId+"'");
				hasFine = getFine.next();
				loans.add(new Loan(loanId, isbn, dateDue, dateIn, hasFine));
				loanids.add(loanId);
			}
			handler.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        JLabel userName = new JLabel("Name: "+name);
        userName.setBounds(10, 40, 100, 20);
        window.add(userName);
        
        idLabel.setBounds(10, 10, 100, 20);
        window.add(idLabel);

        mainPage.setBounds(120,10, 150, 20); // was newUser
        window.add(mainPage);

        update.setBounds(300,10, 150, 20);
        window.add(update);
        
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					QueryHandler handler = new QueryHandler();
					for (int i = 0 ; i< loanids.size(); i++) {
	                	ResultSet getReturned = handler.query("SELECT * FROM LOANS WHERE loan_id LIKE '"+loanids.get(i)+"'");
	                	String returnDate = getReturned.getString("Date_in");
	                	if (returnDate.equals("STILL OUT")) {
	                		handler.update("");//modify entry to have today as date_in
	                	}
	                }
				} 
            	catch (SQLException e1) {

					e1.printStackTrace();
				}
            }
        });
        

        pay.setBounds(475, 10, 150, 20); // was checkout
        window.add(pay);
        pay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					QueryHandler handler = new QueryHandler();
					for (int i = 0 ; i< loanids.size(); i++) {
	                	//modify entry to mark fine as paid
	                }
				} 
            	catch (SQLException e1) {

					e1.printStackTrace();
				}
            }
        });
        
        mainPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);            }
        });

        //searchEntry.setBounds(10, 50, 300, 20);
        //window.add(searchEntry);

        //search.setBounds(350, 50, 100, 20);
        //window.add(search);

        // Calculate due date and date in
        Calendar currentDate = Calendar.getInstance();
        Calendar dueDate = (Calendar) currentDate.clone();
        dueDate.add(Calendar.DAY_OF_MONTH, 14); // static to 14 days from today
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        JLabel dueDateLabel1 = new JLabel("Due Date: " + dateFormat.format(dueDate.getTime()));
        dueDateLabel1.setBounds(500, 125, 150, 20);
        window.add(dueDateLabel1);

        // Assume the book is checked in a week after the due date
        Calendar dateIn = (Calendar) dueDate.clone();
        dateIn.add(Calendar.DAY_OF_MONTH, 7);
        JLabel dateInLabel = new JLabel("Date In: " + dateFormat.format(dateIn.getTime()));
        dateInLabel.setBounds(500, 150, 150, 20);
        window.add(dateInLabel);

        // Calculate fines
        long daysDifference = (dateIn.getTimeInMillis() - dueDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
        double fineAmount = daysDifference * 0.25;
        JLabel fineLabel = new JLabel("Fine: $" + String.format("%.2f", fineAmount));
        fineLabel.setBounds(500, 175, 150, 20);
        window.add(fineLabel);

        // Set a due date prior to today's date
        Calendar pastDueDate = Calendar.getInstance();
        pastDueDate.add(Calendar.DAY_OF_MONTH, -7); // Set to 7 days ago
        JLabel dueDateLabel2 = new JLabel("Due Date: " + dateFormat.format(pastDueDate.getTime()));
        dueDateLabel2.setBounds(500, 375, 150, 20);
        window.add(dueDateLabel2);

        // Display "Date In: STILL OUT" underneath dueDateLabel2
        JLabel dateInStillOutLabel = new JLabel("Date In: STILL OUT");
        dateInStillOutLabel.setBounds(500, 400, 150, 20);
        window.add(dateInStillOutLabel);

        // Display "Fine: x" where x is the dollar amount of the fine calculated by [(the difference between the due date and today) * $0.25]
        long daysDifferenceStillOut = (currentDate.getTimeInMillis() - pastDueDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
        double fineAmountStillOut = daysDifferenceStillOut * 0.25;
        JLabel fineStillOutLabel = new JLabel("Fine: $" + String.format("%.2f", fineAmountStillOut));
        fineStillOutLabel.setBounds(500, 425, 150, 20);
        window.add(fineStillOutLabel);

        // Display the sum of fines
        double sumOfFines = fineAmount + fineAmountStillOut;
        JLabel finesSumLabel = new JLabel("Fines ($0.25/day): $" + String.format("%.2f", sumOfFines));
        finesSumLabel.setBounds(500, 75, 250, 20);
        window.add(finesSumLabel);

        //Once the search functionality is added, these panels will be used to display the entries that are returned from a search
        JPanel panel1 = new JPanel();
        panel1.setBounds(10, 100, 300, 200);
        window.add(panel1);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));        

       	JPanel panel2 = new JPanel();
       	panel2.setBounds(10, 350, 300, 200);
       	window.add(panel2);
       	panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
       	panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        	
        JPanel panel3 = new JPanel();
        panel3.setBounds(10, 600, 300, 200);
        window.add(panel3);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        panel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        int panelNum = 0;
        while (!loans.isEmpty()) {
        	Loan temp = loans.remove(0);
        	try {
        		QueryHandler handler = new QueryHandler();
        		ResultSet matchingIsbns = handler.query("SELECT * FROM BOOK WHERE Isbn LIKE '"+temp.getIsbn()+"'");
        		while (matchingIsbns.next()) {
        			String title = matchingIsbns.getString("Title");
        			String isbn = matchingIsbns.getString("Isbn");
        			String authorString = "";
        			boolean checkedOut = false;
        			ResultSet authorids = handler.query("SELECT * FROM BOOK_AUTHORS WHERE Isbn LIKE '"+isbn+"'");
        			while (authorids.next()) {
        				ResultSet authorNames = handler.query("SELECT * FROM AUTHORS WHERE Author_id LIKE '"+authorids.getString("Author_id")+"'");
        				while (authorNames.next()) {
        					authorString = authorString + authorNames.getString("Name");
        				}
        			}
        			ResultSet bookLoaned = handler.query("SELECT * FROM BOOK_LOANS WHERE Isbn LIKE '"+isbn+"'");
        			checkedOut = bookLoaned.next();
        			BookPage panel = new BookPage(new Book(title, authorString, isbn, checkedOut));
        			switch (panelNum) {
        				case 0:
        					panel.display(panel1);
        				case 1:
        					panel.display(panel2);
        				case 2:
        					panel.display(panel3);
        			}
        		}
        	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
        	}
        	panelNum++;
        }

        window.setSize(1000, 1000);
        window.setLayout(null);
        window.setVisible(true);
    }

    //Function creates a new window to get the user's library card number
    /*public static void displayLoginWindow() {
        JFrame popupLogin = new JFrame();
        JLabel message = new JLabel("Enter your library card number.");
        JTextField cardNoEntry = new JTextField(30);
        JButton login = new JButton("Login");
        message.setBounds(20, 10, 350, 30);
        cardNoEntry.setBounds(20, 40, 350, 30);
        login.setBounds(20, 70, 350, 30);
        popupLogin.add(message);
        popupLogin.add(cardNoEntry);
        popupLogin.add(login);
        popupLogin.setSize(500, 200);
        popupLogin.setLayout(null);
        popupLogin.setVisible(true);
    }*/
}

class Loan {
	private String id;
	private String isbn;
	private String dueDate;
	private String returnDate;
	private boolean finePaid;
	public Loan(String getId, String getIsbn, String getDue, String getReturn, boolean getFine) {
		id = getId;
		isbn = getIsbn;
		dueDate = getDue;
		returnDate = getReturn;
		finePaid = getFine;
	}
	
	public String getId() {
		return id;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public String getReturnDate() {
		return returnDate;
	}
	
	public boolean getFine() {
		return finePaid;
	}
}