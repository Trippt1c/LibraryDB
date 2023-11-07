//This class will be used to display a window/Panel when the search returns a book
import javax.swing.*;
import java.awt.event.*;
public class BookPage {
	//get required data
	String bookTitle = "";
	String bookAuthor = "";
	String bookisbn = "";
	int booksInCart = 0;
	boolean isCheckedOut = false;
			
	public BookPage() {
		//get required data
		//TODO replace test data with method to obtain real data from database
		bookTitle = "Test Book";
		bookAuthor = "AuthorName";
		bookisbn = "0123456789";
		booksInCart = 0;
		isCheckedOut = false;
	}
	
	public void display(JFrame window) {
		
		//JLabels used to display book info
		JLabel title = new JLabel(bookTitle);
		title.setBounds(10, 30, 200, 50);
		window.add(title);
		
		JLabel author = new JLabel("Author: "+bookAuthor);
		author.setBounds(10, 70, 200, 50);
		window.add(author);
		
		JLabel isbn = new JLabel("ISBN: "+bookisbn);
		isbn.setBounds(10, 120, 200, 50);
		window.add(isbn);
		
		String statusString = "";
		if (!isCheckedOut) {
			statusString = "Available for checkout";
		}
		else {
			statusString = "Checked out";
		}
		JLabel status = new JLabel(statusString);
		status.setBounds(10,140,200,50);
		window.add(status);
		
		//rent button will create a new window notifying user of success/failure
		JButton rent = new JButton("Borrow this book");
		rent.setBounds(10, 65, 200, 20);
		window.add(rent);		
		rent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame popupWindow = new JFrame();
				String messageText = "";
				if (booksInCart < 3) {
					messageText = (bookTitle+" added to cart. Checkout with your library card");
					booksInCart++;
				}
				else if (booksInCart >= 3) {
					messageText = ("You cannot rent more than 3 books");
				}
				else if(isCheckedOut) {
					messageText = ("This book is unavailable");
				}
				JLabel message = new JLabel(messageText);
				message.setBounds(20, 20, 350, 50);
				popupWindow.add(message);
				popupWindow.setSize(400, 200);
				popupWindow.setLayout(null);
				popupWindow.setVisible(true);
			}
		});
		
		//Back button will be used to return to main page
		//Alternatively the BookPage class could be made into JPanels to put on the search page
		//in which case we dont need this button
		JButton back = new JButton("<- Return to search");
		back.setBounds(10, 10, 200, 20);
		window.add(back);
		
		//This button could link to the info from BOOK_AUTHORS table
		JButton authorPage = new JButton("More from this author");
		authorPage.setBounds(10, 105, 200, 20);
		window.add(authorPage);
		
	}
}