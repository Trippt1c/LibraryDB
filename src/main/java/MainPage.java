import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
//From the main page, the user can search for books or open the checkout and rentals pages, or create a new borrower entry in the database.
public class MainPage {
	private static JFrame window = new JFrame();
	private static JTextField searchEntry = new JTextField();
	private static ArrayList<Book> searchResults = new ArrayList<Book>();
	private static ArrayList<Book> checkoutCart = new ArrayList<Book>();
	private static int currentPageNo = 0;
	private static int totalPageNo = 0;
	private static JLabel pageNo = new JLabel("Page 1 of 1");
	private static JPanel[] displayPanels = new JPanel[10];
	private static JLabel queryDisplay = new JLabel("");
	private static boolean checkoutClicked;
	public static void main(String args[]) {
		JButton newUser = new JButton("Create Account");
		JButton rentals = new JButton("View my rentals");
		JButton checkout = new JButton("Checkout");
		JButton search = new JButton("Search");
		JButton next = new JButton("Next Page");
		JButton previous = new JButton("Previous Page");
		
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
				getSearchResults(query);
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
		window.setLayout(null);
		window.setVisible(true);
	}
	
	//Gets the search results from a query and stores them in the arraylist searchResults
	public static void getSearchResults(String query) {
		searchResults.clear();
	
		//test data 
		searchResults.add(new Book("Book1","Author1","isbn1", false));
		searchResults.add(new Book("Book2","Author2","isbn2", false));
		searchResults.add(new Book("Book3","Author3","isbn3", false));
		searchResults.add(new Book("Book4","Author4","isbn4", false));
		searchResults.add(new Book("Book5","Author5","isbn5", false));
		searchResults.add(new Book("Book6","Author6","isbn6", false));
		searchResults.add(new Book("Book7","Author7","isbn7", false));
		searchResults.add(new Book("Book8","Author8","isbn8", false));
		searchResults.add(new Book("Book9","Author9","isbn9", false));
		searchResults.add(new Book("Book10","Author10","isbn10", false));
		searchResults.add(new Book("Book11","Author11","isbn11", false));
		searchResults.add(new Book("Book12","Author12","isbn12", false));
		searchResults.add(new Book("Book13","Author13","isbn13", false));
		searchResults.add(new Book("Book14 very long title with many words","Author14 very long author name with many words","isbn14", true));
		searchResults.add(new Book("Book15","Author15","isbn15", false));
		searchResults.add(new Book("Book16","Author16","isbn16", false));
		searchResults.add(new Book("Book17","Author17","isbn17", false));
		searchResults.add(new Book("Book18","Author18","isbn18", false));
		searchResults.add(new Book("Book19","Author19","isbn19", false));
		searchResults.add(new Book("Book20","Author20","isbn20", false));
		searchResults.add(new Book("Book21","Author21","isbn21", false));
		searchResults.add(new Book("Book22","Author22","isbn22", false));
		searchResults.add(new Book("Book23","Author23","isbn23", false));
		searchResults.add(new Book("Book24","Author24","isbn24", false));
		searchResults.add(new Book("Book25","Author25","isbn25", false));
		searchResults.add(new Book("Book26","Author26","isbn26", false));
		searchResults.add(new Book("Book27","Author27","isbn27", false));
		searchResults.add(new Book("Book28","Author28","isbn28", false));
		searchResults.add(new Book("Book29","Author29","isbn29", false));
		searchResults.add(new Book("Book30","Author30","isbn30", false));
		searchResults.add(new Book("Book31","Author31","isbn31", false));
		searchResults.add(new Book("Book32","Author32","isbn32", false));
		searchResults.add(new Book("Book33","Author33","isbn33", false));
		searchResults.add(new Book("Book34","Author34","isbn34", false));
		searchResults.add(new Book("Book35","Author35","isbn35", false));
		
		//TODO: create method to search the database for any titles/authors that match the query and add them to search results
		//then remove test data
		
		totalPageNo = searchResults.size()/10;
	}
	
	//Function creates a new window to get the user's library card number
	private static JTextField cardNoEntry = new JTextField();
	private static JFrame popupLogin = new JFrame();
	public static void displayLoginWindow() {
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
				//TODO: replace if statement with method to check if the given card number returns a match in the database
				if (cardNoEntry.getText().equals("12345")) {
					JFrame popupWindow = new JFrame();
					JLabel message = new JLabel("That card number does not match any accounts in the library.");
					message.setBounds(20, 20, 350, 50);
					popupWindow.add(message);
					popupWindow.setSize(500, 200);
					popupWindow.setLayout(null);
					popupWindow.setVisible(true);
				}
				else if (checkoutClicked) {
					CheckOut check = new CheckOut(cardNoEntry.getText(), checkoutCart);
				} else {
					Rentals rent = new Rentals(cardNoEntry.getText());
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
	
	
	private static JFrame newBorrower = new JFrame();
	private static JTextField nameField = new JTextField();
	private static JTextField ssnField = new JTextField();
	private static JTextField addressField = new JTextField();
	private static JTextField phoneField = new JTextField();
	public static void createBorrower() {
		
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
				String libraryCard = "12345678";//generate new id here
				String responseMessage = "";
				newBorrower.setVisible(false);
				
				
				//TODO create new entry in database using given data and newly generated library card if the ssn is unique
				//and replace this if statement with code that checks if the ssn is unique
				if (borrowerssn .equals("123456")) {
					responseMessage = "Error: That SSN already exists within the database.";
				}
				else {
					responseMessage = ("Account created. Your library card number is: "+libraryCard);
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
				if(searchResults.get(index).getStatus()) {
					messageText = ("This book is unavailable");
				}
				else if (checkoutCart.size() >= 3) {
					messageText = ("You cannot rent more than 3 books");
				}
				else {
					messageText = (searchResults.get(index).getTitle()+" added to cart. Checkout with your library card");
					searchResults.get(index).checkout();
					checkoutCart.add(searchResults.get(index));
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
