import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
//From the main page, the user can search for books or open the checkout and rentals pages, or create a new borrower entry in the database.
public class MainPage {
	static JFrame window = new JFrame();
	static JTextField searchEntry = new JTextField();
	static ArrayList<Book> searchResults = new ArrayList<Book>();
	static int currentPageNo = 0;
	static int totalPageNo = 0;
	static JLabel pageNo = new JLabel("Page 1 of 1");
	static JPanel[] displayPanels = new JPanel[10];
	static JLabel queryDisplay = new JLabel("");
	public static void main(String args[]) {
		JButton newUser = new JButton("Create Account");
		JButton rentals = new JButton("View my rentals");
		JButton checkout = new JButton("Checkout");
		JButton search = new JButton("Search");
		JButton next = new JButton("Next Page");
		JButton previous = new JButton("Previous Page");
		
		queryDisplay.setBounds(10,70, 500, 20);
		window.add(queryDisplay);
		
		newUser.setBounds(10,10, 150, 20);
		window.add(newUser);
		
		rentals.setBounds(200,10, 150, 20);
		window.add(rentals);
		rentals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLoginWindow();
				//TODO create a new class to handle the user's rental page
			}
		});
		
		checkout.setBounds(400, 10, 150, 20);
		window.add(checkout);
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLoginWindow();
				//TODO create a new class to handle the user's checkout page
			}
		});
		
		searchEntry.setBounds(10, 50, 300, 20);
		window.add(searchEntry);
		search.setBounds(350, 50, 100, 20);
		window.add(search);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = searchEntry.getText();
				if (query != "") {
					currentPageNo = 0;
					getSearchResults(query);
					displaySearchResults();
					int pageNoPlus1 = currentPageNo + 1;
					int totalPlus1 = totalPageNo + 1;
					pageNo.setText("Page "+pageNoPlus1+" of "+totalPlus1);
					queryDisplay.setText("Showing results for: "+query);
				}
			}
		});
		
		
		
		checkout.setBounds(400, 10, 150, 20);
		window.add(checkout);
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLoginWindow();
				//TODO create a new class to handle the user's checkout page
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
	public static void displayLoginWindow() {
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
	}
	
	//Function displays the requested search results in the 10 panels in the middle of the page
	public static void displaySearchResults() {
		for (int i = 0; i < 10; i++) {
			displayPanels[i].removeAll();
		}
		for (int i = 0; i < 10; i++) {
			if ((currentPageNo)*10+i < searchResults.size()) {
				BookPage book = new BookPage(searchResults.get((currentPageNo)*10+i)); 
				book.display(displayPanels[i]);
				displayPanels[i].setVisible(true);
				displayPanels[i].repaint();
			}
			else {
				displayPanels[i].setVisible(false);
				displayPanels[i].repaint();
			}
		}
	}
}
