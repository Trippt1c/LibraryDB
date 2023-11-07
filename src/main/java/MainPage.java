import javax.swing.*;
//MainPage does nothing for now, just creates a BookPage
public class MainPage {
	public static void main(String args[]) {
		JFrame window = new JFrame();
		BookPage test = new BookPage();
		test.display(window);
		window.setSize(500, 500);
		window.setLayout(null);
		window.setVisible(true);
	}
}
