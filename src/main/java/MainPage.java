import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
//From the main page, the user can search for books or open the checkout and rentals pages, or create a new borrower entry in the database.
public class MainPage {
	private static JFrame window;
	private static JTextField searchEntry;
	private static ArrayList<Book> searchResults;
	private static ArrayList<Book> checkoutCart;
	private static int currentPageNo;
	private static int totalPageNo;
	private static JLabel pageNo;
	private static JPanel[] displayPanels;
	private static JLabel queryDisplay;
	private static boolean checkoutClicked;
	private static Calendar currentDate;
	public static void main(String args[]) {
		window = new JFrame();
		searchEntry = new JTextField();
		searchResults = new ArrayList<Book>();
		checkoutCart = new ArrayList<Book>();
		currentPageNo = 0;
		totalPageNo = 0;
		displayPanels = new JPanel[10];
		queryDisplay = new JLabel("");
		pageNo = new JLabel("Page 1 of 1");
		JButton newUser = new JButton("Create Account");
		JButton rentals = new JButton("View my rentals");
		JButton checkout = new JButton("Checkout");
		JButton search = new JButton("Search");
		JButton next = new JButton("Next Page");
		JButton previous = new JButton("Previous Page");
		JLabel warning = new JLabel("Note: This process will take several minutes");

		JButton incrementDate = new JButton("+");
		JButton decrementDate = new JButton("-");
		currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		JLabel displayDate = new JLabel(dateFormat.format(currentDate.getTime()));
		JLabel changeDate = new JLabel("Set current day:");
		
		displayDate.setBounds(830, 70, 150, 20);
		window.add(displayDate);
		changeDate.setBounds(600, 70, 150, 20);
		window.add(changeDate);
		incrementDate.setBounds(700, 70, 50, 20);
		window.add(incrementDate);
		decrementDate.setBounds(750, 70, 50, 20);
		window.add(decrementDate);
		
		incrementDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDate.add(Calendar.DATE, 1);
				displayDate.setText(dateFormat.format(currentDate.getTime()));
			}
		});
		
		decrementDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDate.add(Calendar.DATE, -1);
				displayDate.setText(dateFormat.format(currentDate.getTime()));
			}
		});
		
		JButton restartDatabase = new JButton("Restart Database");
		restartDatabase.setBounds(600, 10, 150, 20);
		window.add(restartDatabase);

		warning.setBounds(600, 30, 300, 20);
		window.add(warning);
		restartDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartDB restart = new StartDB();
				try {
					restart.start();//Takes roughly 15 minutes
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		window.setTitle("Book Search");
		
		queryDisplay.setBounds(10,70, 500, 20);
		window.add(queryDisplay);
		
		newUser.setBounds(10,10, 150, 20);
		window.add(newUser);
		newUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createBorrower();
			}
		});
		
		rentals.setBounds(200,10, 150, 20);
		window.add(rentals);
		rentals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkoutClicked = false;
				displayLoginWindow();
			}
		});
		
		checkout.setBounds(400, 10, 150, 20);
		window.add(checkout);
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkoutClicked = true;
				displayLoginWindow();
			}
		});
		
		searchEntry.setBounds(10, 50, 300, 20);
		window.add(searchEntry);
		search.setBounds(350, 50, 100, 20);
		window.add(search);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = searchEntry.getText();
				currentPageNo = 0;
				try {
					getSearchResults(query);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				displaySearchResults();
				int pageNoPlus1 = currentPageNo + 1;
				int totalPlus1 = totalPageNo + 1;
				pageNo.setText("Page "+pageNoPlus1+" of "+totalPlus1);
				queryDisplay.setText("Showing results for: "+query);
			}
		});
		
		previous.setBounds(10, 650, 150, 20);
		window.add(previous);
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPageNo > 0) {
					currentPageNo -= 1;
					displaySearchResults();
					int pageNoPlus1 = currentPageNo + 1;
					int totalPlus1 = totalPageNo + 1;
					pageNo.setText("Page "+pageNoPlus1+" of "+totalPlus1);
				}
			}
		});
		
		next.setBounds(750, 650, 150, 20);
		window.add(next);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPageNo < totalPageNo) {
					currentPageNo += 1;
					displaySearchResults();
					int pageNoPlus1 = currentPageNo + 1;
					int totalPlus1 = totalPageNo + 1;
					pageNo.setText("Page "+pageNoPlus1+" of "+totalPlus1);
				}
			}
		});
		
		pageNo.setBounds(400, 650, 150, 20);
		window.add(pageNo);
		
		//Establish bounds for panels that display search results
		for (int i = 0; i < 10 ; i++) {
			if (i%2==0) {
				displayPanels[i] = new JPanel();
				displayPanels[i].setBounds(10, 50+50*((i+1)%10), 450, 100);
			}
			else {
				displayPanels[i] = new JPanel();
				displayPanels[i].setBounds(510, 50+50*(i%10), 450, 100);
			}
			window.add(displayPanels[i]);
			displayPanels[i].setLayout(new BoxLayout(displayPanels[i], BoxLayout.Y_AXIS));
			displayPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		
		window.setSize(1000, 1000);
		window.repaint();
		window.setLayout(null);
		window.setVisible(true);
	}
	
	//Gets the search results from a query and stores them in the arraylist searchResults
	public static void getSearchResults(String query) throws SQLException {
		searchResults.clear();

		QueryHandler handler = new QueryHandler();
		
		//Search by title
		ResultSet matchingTitles = handler.query("SELECT * FROM BOOK WHERE Title LIKE '%"+query+"%'");
		while (matchingTitles.next()) {
			String title = matchingTitles.getString("Title");
			String isbn = matchingTitles.getString("Isbn");
			String authorString = "";
			boolean checkedOut = false;
			ResultSet authorids = handler.query("SELECT * FROM BOOK_AUTHORS WHERE Isbn LIKE '%"+isbn+"%'");
			while (authorids.next()) {
				ResultSet authorNames = handler.query("SELECT * FROM AUTHORS WHERE Author_id LIKE '%"+authorids.getString("Author_id")+"%'");

				ResultSet authorNames = handler.query("SELECT * FROM AUTHORS WHERE Author_id LIKE '"+authorids.getString("Author_id")+"'");

				while (authorNames.next()) {
					authorString = authorString+" "+authorids.getString("");
				}
			}
			ResultSet bookLoaned = handler.query("SELECT * FROM BOOK_LOANS WHERE Isbn LIKE '%"+isbn+"%' AND Date_in IS NULL");
			checkedOut = bookLoaned.next();
			searchResults.add(new Book(title, authorString, isbn, checkedOut));
		}
		
		//Search by isbn
		ResultSet matchingIsbns = handler.query("SELECT * FROM BOOK WHERE Isbn LIKE '%"+query+"%'");
		while (matchingIsbns.next()) {
			String title = matchingIsbns.getString("Title");
			String isbn = matchingIsbns.getString("Isbn");
			String authorString = "";
			boolean checkedOut = false;
			ResultSet authorids = handler.query("SELECT * FROM BOOK_AUTHORS WHERE Isbn LIKE '%"+isbn+"%'");
			while (authorids.next()) {

				ResultSet authorNames = handler.query("SELECT * FROM AUTHORS WHERE Author_id LIKE '%"+authorids.getString("Author_id")+"%'");

				ResultSet authorNames = handler.query("SELECT * FROM AUTHORS WHERE Author_id LIKE '"+authorids.getString("Author_id")+"'");

				while (authorNames.next()) {
					authorString = authorString+" "+authorNames.getString("Name");
				}
			}
			ResultSet bookLoaned = handler.query("SELECT * FROM BOOK_LOANS WHERE Isbn LIKE '%"+isbn+"%' AND Date_in IS NULL");
			checkedOut = bookLoaned.next();
			searchResults.add(new Book(title, authorString, isbn, checkedOut));
		}
			
		//Search by author
		ResultSet matchingAuthors = handler.query("SELECT * FROM BOOK WHERE Isbn LIKE '%"+query+"%'");
		while (matchingAuthors.next()) {
			ResultSet matchingAuthorid = handler.query("SELECT * FROM BOOK_AUTHORS WHERE Author_id LIKE '%"+matchingAuthors.getString("Author_id")+"%'");
			ResultSet booksByThisAuthor = handler.query("SELECT * FROM BOOK WHERE Isbn LIKE '%"+matchingAuthorid.getString("Isbn")+"%'");
			while (booksByThisAuthor.next()) {
				String title = matchingIsbns.getString("Title");
				String isbn = matchingIsbns.getString("Isbn");
				String authorString = "";
				boolean checkedOut = false;
				ResultSet authorids = handler.query("SELECT * FROM BOOK_AUTHORS WHERE Isbn LIKE '%"+isbn+"%'");
				while (authorids.next()) {
					ResultSet authorNames = handler.query("SELECT * FROM AUTHORS WHERE Author_id LIKE '%"+authorids.getString("Author_id")+"%'");
					while (authorNames.next()) {
						authorString = authorString+" "+authorNames.getString("Name");
					}
				}
				ResultSet bookLoaned = handler.query("SELECT * FROM BOOK_LOANS WHERE Isbn LIKE '%"+isbn+"%' AND Date_in IS NULL");
				checkedOut = bookLoaned.next();
				searchResults.add(new Book(title, authorString, isbn, checkedOut));
			}
		}
		
		handler.close();
		
		totalPageNo = searchResults.size()/10;
	}
	
	//Function creates a new window to get the user's library card number
	private static JTextField cardNoEntry;
	private static JFrame popupLogin;
	public static void displayLoginWindow() {
		cardNoEntry = new JTextField();
		popupLogin = new JFrame();

		JLabel message = new JLabel("Enter your library card number.");
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
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = cardNoEntry.getText();
				boolean foundMatch = false;
				try {
					QueryHandler handler = new QueryHandler();
					ResultSet getid = handler.query("SELECT * FROM BORROWER WHERE Card_id LIKE '%"+id+"%'");
					foundMatch = getid.next();
					//foundMatch = true;
					handler.close();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (!foundMatch) {
					JFrame popupWindow = new JFrame();
					JLabel message = new JLabel("That card number does not match any accounts in the library.");
					message.setBounds(20, 20, 350, 50);
					popupWindow.add(message);
					popupWindow.setSize(500, 200);
					popupWindow.setLayout(null);
					popupWindow.setVisible(true);
				}
				else if (checkoutClicked) {
					CheckOut check = new CheckOut(cardNoEntry.getText(), checkoutCart, currentDate);
				} else {
					Rentals rent = new Rentals(cardNoEntry.getText(), currentDate);
				}
				popupLogin.setVisible(false);
			}
		});
	}
	
	//Function displays the requested search results in the 10 panels in the middle of the page
	public static void displaySearchResults() {
		for (int i = 0; i < 10; i++) {
			displayPanels[i].removeAll();
		}
		for (int i = 0; i < 10; i++) {
			if (currentPageNo*10+i < searchResults.size()) {
				displayBook(displayPanels[i], currentPageNo*10+i); 
				displayPanels[i].setVisible(true);
				displayPanels[i].repaint();
			}
			else {
				displayPanels[i].setVisible(false);
				displayPanels[i].repaint();
			}
		}
	}
	
	
	private static JFrame newBorrower;
	private static JTextField nameField;
	private static JTextField ssnField;
	private static JTextField addressField;
	private static JTextField phoneField;
	public static void createBorrower() {
		newBorrower = new JFrame();
		nameField = new JTextField();
		ssnField = new JTextField();
		addressField = new JTextField();
		phoneField = new JTextField();

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 20, 100, 20);
		newBorrower.add(nameLabel);
		nameField.setBounds(10, 40, 300, 20);
		newBorrower.add(nameField);
		JLabel ssnLabel = new JLabel("SSN:");
		ssnLabel.setBounds(10, 60, 300, 20);
		newBorrower.add(ssnLabel);
		ssnField.setBounds(10, 80, 300, 20);
		newBorrower.add(ssnField);
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setBounds(10, 100, 300, 20);
		newBorrower.add(addressLabel);
		addressField.setBounds(10, 120, 300, 20);
		newBorrower.add(addressField);
		JLabel phoneLabel = new JLabel("Phone number:");
		phoneLabel.setBounds(10, 140, 300, 20);
		newBorrower.add(phoneLabel);
		phoneField.setBounds(10, 160, 300, 20);
		newBorrower.add(phoneField);
		JButton submit = new JButton("Submit");
		submit.setBounds(10, 190, 300, 20);
		newBorrower.add(submit);
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String borrowerName = nameField.getText();
				String borrowerssn = ssnField.getText();
				String borrowerAddress = addressField.getText();
				String borrowerPhone = phoneField.getText();
				String libraryCard = "";
				String responseMessage = "";
				newBorrower.setVisible(false);
				boolean isUniquessn = true; 
				int cardNoIterator = 1;
				
				//check if ssn is unique and generate new id. needs proper testing once database initialization is complete
				try {
					QueryHandler handler = new QueryHandler();
					ResultSet matchingssn = handler.query("SELECT * FROM BORROWER WHERE Ssn LIKE '"+borrowerssn+"'");
					ResultSet getids = handler.query("SELECT * FROM BORROWER");
					isUniquessn = !matchingssn.next();
					
					ArrayList<Integer> forbiddenids = new ArrayList<Integer>();
					while (getids.next()) {
						forbiddenids.add(Integer.valueOf(getids.getString("Card_id")));
					}
					while (forbiddenids.contains(cardNoIterator) && !forbiddenids.isEmpty()) {
						cardNoIterator++;
					}
					libraryCard = (""+cardNoIterator);
					handler.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				//insert into database
				if (isUniquessn) {
					responseMessage = ("Account created. Your library card number is: "+libraryCard);
					try {
						QueryHandler handler = new QueryHandler();
						handler.update("INSERT INTO BORROWER (Card_id, ssn, Bname, Address, Phone) Values ('"+libraryCard+"', '"+borrowerssn+"', '"+borrowerName+"', '"+borrowerAddress+"', '"+borrowerPhone+"')");
						handler.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else {
					responseMessage = "Error: That SSN already exists within the database.";
				}
				
				newBorrower.setVisible(false);
				JFrame responseWindow = new JFrame();
				JLabel responseLabel = new JLabel(responseMessage);
				responseLabel.setBounds(20, 20, 350, 50);
				responseWindow.add(responseLabel);
				responseWindow.setSize(500, 200);
				responseWindow.setLayout(null);
				responseWindow.setVisible(true);
			}
		});
		
		newBorrower.setSize(500, 500);
		newBorrower.repaint();
		newBorrower.setLayout(null);
		newBorrower.setVisible(true);
	}
	
	public static void displayBook(JPanel displayBox, int i) {
		final int index = i;
		//JLabels used to display book info
		JLabel title = new JLabel(searchResults.get(index).getTitle());
		displayBox.add(title);
		
		JLabel author = new JLabel("Author: "+searchResults.get(index).getAuthor());
		displayBox.add(author);
		
		JLabel isbn = new JLabel("ISBN: "+searchResults.get(index).getisbn());
		displayBox.add(isbn);
		
		String statusString = "";
		if (!searchResults.get(index).getStatus()) {
			statusString = "Available for checkout";
		}
		else {
			statusString = "Checked out";
		}
		JLabel status = new JLabel(statusString);
		displayBox.add(status);
		
		//rent button will create a new window notifying user of success/failure
		JButton rent = new JButton("Borrow this book");
		displayBox.add(rent);		
		rent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame popupWindow = new JFrame();
				String messageText = "";
				boolean isInCart = false;
				for (int i = 0 ; i < checkoutCart.size(); i++) {
					if (searchResults.get(index).getisbn().equals(checkoutCart.get(i).getisbn())) {
						isInCart = true;
					}
				}
				if (isInCart) {
					messageText = ("This book is already in your cart.");
				}
				else if(searchResults.get(index).getStatus()) {
					messageText = ("This book is unavailable.");
				}
				else if (checkoutCart.size() >= 3) {
					messageText = ("You cannot rent more than 3 books.");
				}
				else {
					messageText = (searchResults.get(index).getTitle()+" added to cart.");
					searchResults.get(index).checkout();
					checkoutCart.add(searchResults.get(index));
					JLabel message2 = new JLabel("Checkout with your library card id.");
					message2.setBounds(20, 40, 350, 50);
					popupWindow.add(message2);
				}
				JLabel message = new JLabel(messageText);
				message.setBounds(20, 20, 350, 50);
				popupWindow.add(message);
				popupWindow.setSize(500, 200);
				popupWindow.setLayout(null);
				popupWindow.setVisible(true);
			}
		});
	};
}
