import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainPage {
	public static void main(String args[]) {
		JFrame window = new JFrame();
		BookPage test = new BookPage();
		BookPage test2 = new BookPage();
		BookPage test3 = new BookPage();
		JButton newUser = new JButton("Create Account");
		JButton rentals = new JButton("View my rentals");
		JButton checkout = new JButton("Checkout");
		JButton search = new JButton("Search");
		JTextField searchEntry = new JTextField();
		
		newUser.setBounds(10,10, 150, 20);
		window.add(newUser);
		
		rentals.setBounds(200,10, 150, 20);
		window.add(rentals);
		rentals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLoginWindow();
			}
		});
		
		checkout.setBounds(400, 10, 150, 20);
		window.add(checkout);
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLoginWindow();
			}
		});
		
		searchEntry.setBounds(10, 50, 300, 20);
		window.add(searchEntry);
		
		search.setBounds(350, 50, 100, 20);
		window.add(search);
		
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
		
		test.display(panel1);
		test2.display(panel2);
		test3.display(panel3);
		window.setSize(1000, 1000);
		window.setLayout(null);
		window.setVisible(true);
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
}
