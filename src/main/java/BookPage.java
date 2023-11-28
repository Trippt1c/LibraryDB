//This class will be used to display a panel with all the information for a given book
import javax.swing.*;
import java.awt.event.*;
public class BookPage {
	//get required data
	private String bookTitle = "";
	private String bookAuthor = "";
	private String bookisbn = "";
	private int booksInCart = 0;
	private boolean isCheckedOut = false;
			
	public BookPage(Book book) {
		//initialize data
		bookTitle = book.getTitle();
		bookAuthor = book.getAuthor();
		bookisbn = book.getisbn();
		booksInCart = 0;
		isCheckedOut = book.getStatus();
	}
	
	public void display(JPanel displayBox) {
		
		//JLabels used to display book info
		JLabel title = new JLabel(bookTitle);
		displayBox.add(title);
		
		JLabel author = new JLabel("Author: "+bookAuthor);
		displayBox.add(author);
		
		JLabel isbn = new JLabel("ISBN: "+bookisbn);
		displayBox.add(isbn);
		
		String statusString = "";
		if (!isCheckedOut) {
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
		//JButton authorPage = new JButton("More from this author");
		//authorPage.setBounds(10, 105, 200, 20);
		//displayBox.add(authorPage);
		
	}
}