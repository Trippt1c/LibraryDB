public class Book {
	private String title = "";
	private String author = "";
	private String isbn = "";
	private boolean isCheckedOut = false;
	public Book(String bookTitle, String bookAuthor, String bookisbn, boolean checkedOut) {
		title = bookTitle;
		author = bookAuthor;
		isbn = bookisbn;
		isCheckedOut = checkedOut;
	}
	public void checkoutBook() {
		isCheckedOut = true;
	}
	public void returnBook() {
		isCheckedOut = false;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getisbn() {
		return isbn;
	}
	public boolean getStatus(){
		return isCheckedOut;
	}
}
