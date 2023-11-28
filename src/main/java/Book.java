public class Book {
	private String title;
	private String author;
	private String isbn;
	private boolean isCheckedOut;
	public Book(String bookTitle, String bookAuthor, String bookisbn, boolean bookStatus) {
		title = bookTitle;
		author = bookAuthor;
		isbn = bookisbn;
		isCheckedOut = bookStatus;
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
	public boolean getStatus() {
		return isCheckedOut;
	}
	public void checkout() {
		isCheckedOut = true;
	}
	public void returnBook() {
		isCheckedOut = false;
	}
}
