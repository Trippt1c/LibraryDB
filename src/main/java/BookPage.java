//This class will be used to display a panel with all the information for a given book
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
	
	public void display(JPanel displayBox) {
		
		//JLabels used to display book info
		JLabel title = new JLabel(bookTitle);
		title.setBounds(20, 30, 100, 5000);
		displayBox.add(title);
		
		JLabel author = new JLabel("Author: "+bookAuthor);
		author.setBounds(20, 70, 100, 40);
		displayBox.add(author);
		
		JLabel isbn = new JLabel("ISBN: "+bookisbn);
		isbn.setBounds(20, 120, 100, 40);
		displayBox.add(isbn);
		
		String statusString = "";
		if (!isCheckedOut) {
			statusString = "Available for checkout";
		}
		else {
			statusString = "Checked out";
		}
		JLabel status = new JLabel(statusString);
		status.setBounds(20,140,100,40);
		displayBox.add(status);
		
		//rent button will create a new window notifying user of success/failure
		JButton rent = new JButton("Borrow this book");
		rent.setBounds(10, 65, 100, 40);
		displayBox.add(rent);		
		rent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame popupWindow = new JFrame();
				String messageText = "";
				if (booksInCart < 3) {
					messageText = (bookTitle+" added to cart. Checkout with your library card");
					booksInCart++;
					//TODO add method to update the checkout cart in main
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
				popupWindow.setSize(500, 200);
				popupWindow.setLayout(null);
				popupWindow.setVisible(true);
			}
		});
		
		//This button could link to the info from BOOK_AUTHORS table
		JButton authorPage = new JButton("More from this author");
		authorPage.setBounds(10, 105, 200, 20);
		displayBox.add(authorPage);
		
	}
}