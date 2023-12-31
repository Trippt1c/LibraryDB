import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

// TODO: add functionality of validating a checkout (unavailable, already max checked out, etc.)
public class CheckOut {
	private static JFrame window = new JFrame();
	private static ArrayList<Book> checkoutCart;
    public CheckOut(String libraryCard, ArrayList<Book> cartToEmpty, Calendar currentDate) {
    	window = new JFrame();
    	checkoutCart = new ArrayList<Book>();
    	final String id = libraryCard;
        window.setTitle("Checkout");
        BookPage test = null;
        BookPage test2 = null;
        BookPage test3 = null;
        
        if (!cartToEmpty.isEmpty()) {
        	Book temp = cartToEmpty.remove(0);
        	test = new BookPage(temp);
        	checkoutCart.add(temp);
        }
        if (!cartToEmpty.isEmpty()) {
        	Book temp = cartToEmpty.remove(0);
        	test2 = new BookPage(temp);
        	checkoutCart.add(temp);
        }
        if (!cartToEmpty.isEmpty()) {
        	Book temp = cartToEmpty.remove(0);
        	test3 = new BookPage(temp);
        	checkoutCart.add(temp);
        }
        //JButton newUser = new JButton("Create Account");
        JButton mainPage = new JButton("Return to Main Page"); // use this button to return to main page
        JButton rentals = new JButton("View my rentals");
        //JButton checkout = new JButton("Checkout");
        JButton confirm = new JButton("Confirm Checkout");
        //JButton search = new JButton("Search");
        //JTextField searchEntry = new JTextField();

        JLabel idLabel = new JLabel("ID "+ id);
        idLabel.setBounds(10, 10, 100, 20);
        window.add(idLabel);

        String name = "";
        int numRented = 0;
        
		try {
			//get user name
			QueryHandler handler = new QueryHandler();
			ResultSet user = handler.query("SELECT * FROM BORROWER WHERE Card_id LIKE '"+id+"'");
			while (user.next()) {
				name = user.getString("Bname");
			}
			
			//get number of books rented
			ResultSet rentedBooks = handler.query("SELECT * FROM BOOK_LOANS WHERE Card_id LIKE '"+id+"' AND Date_in LIKE 'STILL OUT'");
			while (rentedBooks.next()) {
				numRented++;
			}
			handler.close();
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		
        JLabel userName = new JLabel("Name: "+name);
        userName.setBounds(10, 40, 500, 20);
        window.add(userName);
        
        JLabel numRentalsLabel = new JLabel("Books rented by this account: "+numRented);
        numRentalsLabel.setBounds(10, 70, 200, 20);
        window.add(numRentalsLabel);
        
        mainPage.setBounds(120,10, 150, 20); // was newUser
        window.add(mainPage);

        rentals.setBounds(300,10, 150, 20);
        window.add(rentals);
        rentals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Rentals rent = new Rentals(id, currentDate);
            }
        });


        JLabel tooManyBooks = new JLabel("You can only rent 3 books at a time.");
        JLabel tooManyBooks2 = new JLabel("Please return your rented books before checking out more.");
        tooManyBooks.setBounds(475, 10, 350, 20);
        tooManyBooks2.setBounds(475, 20, 350, 20); 
        window.add(tooManyBooks);
    	window.add(tooManyBooks2);

        confirm.setBounds(475, 10, 150, 20); // was checkout
        window.add(confirm);

        //Replace checkout button with error message if the user has more than 3 books
        if (numRented+checkoutCart.size() > 3) {
        	confirm.setVisible(false);
        }
        else {
        	tooManyBooks.setVisible(false);
        	tooManyBooks2.setVisible(false);
        }
        
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayDueDateWindow(currentDate);
				try {
					QueryHandler handler = new QueryHandler();
					SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
					String checkoutDate = dateFormat.format(currentDate.getTime());
					Calendar newDate = (Calendar) currentDate.clone();
			        newDate.add(Calendar.DAY_OF_MONTH, 14);
			        String dueDate = dateFormat.format(newDate.getTime());
			        int loanNoIterator = 0;
			        
			        ArrayList<Integer> forbiddenids = new ArrayList<Integer>();
			        ResultSet getids = handler.query("SELECT * FROM BOOK_LOANS");
					while (getids.next()) {
						forbiddenids.add(Integer.valueOf(getids.getString("Loan_id")));
					}

					while (!checkoutCart.isEmpty()) {
						while (forbiddenids.contains(loanNoIterator) && !forbiddenids.isEmpty()) {
							loanNoIterator++;
						}
						int loanid = loanNoIterator;
						Book checkedOut = checkoutCart.remove(0);
						handler.update("INSERT INTO BOOK_LOANS (Loan_id, Isbn, Card_id, Date_out, Due_date, Date_in) Values ('"+loanid+"', '"+checkedOut.getisbn()+"', '"+id+"', '"+checkoutDate+"', '"+dueDate+"', '"+"STILL OUT')");
						forbiddenids.add(loanid);
					}
					handler.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        
        mainPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
            }
        });
        //searchEntry.setBounds(10, 50, 300, 20);
        //window.add(searchEntry);

        //search.setBounds(350, 50, 100, 20);
        //window.add(search);

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

        if (test != null) {
        	test.display(panel1);
        }
        if (test2 != null) {
        	test2.display(panel2);
        }
        if (test3 != null) {
        	test3.display(panel3);
        }
        window.setSize(1000, 1000);
        window.repaint();
        window.setLayout(null);
        window.setVisible(true);
    }

    //Function creates a new window to get the user's library card number
/*    public static void displayLoginWindow() {
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

    private static JFrame popupDueDate;
    public static void displayDueDateWindow(Calendar today) {
    	popupDueDate = new JFrame();
        JLabel message = new JLabel(getDueDateMessage(today));
        JButton close = new JButton("Close");
        message.setBounds(20, 10, 750, 30);
        close.setBounds(20, 50, 100, 30);
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                popupDueDate.dispose();
            }
        });

        popupDueDate.add(message);
        popupDueDate.add(close);
        popupDueDate.setSize(400, 150);
        popupDueDate.setLayout(null);
        popupDueDate.setVisible(true);
        window.setVisible(false);
    }

    private static String getDueDateMessage(Calendar today) {
        Calendar calendar = Calendar.getInstance(); // current date + 14 days
        calendar.add(Calendar.DAY_OF_MONTH, 14);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        Calendar newDate = (Calendar) today.clone();
        newDate.add(Calendar.DAY_OF_MONTH, 14);
        String dueDate = dateFormat.format(newDate.getTime());

        return "Book(s) checked out. Please return by " + dueDate;
    }
}