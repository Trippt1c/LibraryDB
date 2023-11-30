import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// TODO: add functionality of validating a checkout (unavailable, already max checked out, etc.)
public class CheckOut {
	private static JFrame window = new JFrame();
    public CheckOut(String libraryCard, ArrayList<Book> checkoutCart) {
    	final String id = libraryCard;
        window.setTitle("Checkout");
        BookPage test = null;
        BookPage test2 = null;
        BookPage test3 = null;
        if (!checkoutCart.isEmpty()) {
        	test = new BookPage(checkoutCart.remove(0));
        }
        if (!checkoutCart.isEmpty()) {
        	test2 = new BookPage(checkoutCart.remove(0));
        }
        if (!checkoutCart.isEmpty()) {
        	test3 = new BookPage(checkoutCart.remove(0));
        }
        //JButton newUser = new JButton("Create Account");
        JButton mainPage = new JButton("Return to Main Page"); // use this button to return to main page
        JButton rentals = new JButton("View my rentals");
        //JButton checkout = new JButton("Checkout");
        JButton confirm = new JButton("Confirm Checkout");
        //JButton search = new JButton("Search");
        //JTextField searchEntry = new JTextField();

        JLabel idLabel = new JLabel("ID "+ id); // TODO: make adjustable
        idLabel.setBounds(10, 10, 100, 20);
        window.add(idLabel);

        mainPage.setBounds(120,10, 150, 20); // was newUser
        window.add(mainPage);

        rentals.setBounds(300,10, 150, 20);
        window.add(rentals);
        rentals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Rentals rent = new Rentals(id);
            }
        });

        confirm.setBounds(475, 10, 150, 20); // was checkout
        window.add(confirm);
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayDueDateWindow();
                //TODO: update database with new rentals
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

    private static JFrame popupDueDate = new JFrame();
    public static void displayDueDateWindow() {
        JLabel message = new JLabel(getDueDateMessage());
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
    }

    private static String getDueDateMessage() {
        Calendar calendar = Calendar.getInstance(); // current date + 14 days
        calendar.add(Calendar.DAY_OF_MONTH, 14);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String dueDate = dateFormat.format(calendar.getTime());

        return "Book(s) checked out. Please return by " + dueDate;
    }
}