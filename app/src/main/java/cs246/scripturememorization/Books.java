package cs246.scripturememorization;

public class Books {

    private String bookTitle;
    private String bookId;

    public Books(String bookTitle, String bookId) {
        this.bookTitle = bookTitle;
        this.bookId = bookId;
    }

    public String getBookTitle() { return this.bookTitle; }
    public String getBookId() {
        return this.bookId;
    }
}
