package extern.books_api.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import extern.books_api.res.google.Item;

import java.util.List;

public class Book {

    @SerializedName("booTitle")
    @Expose
    private String bookTitle;
    @SerializedName("authors")
    @Expose
    private List<String> bookAuthors;
    @SerializedName("publisher")
    @Expose
    private String bookPublisher;
    @SerializedName("publishedDate")
    @Expose
    private String bookPublishedDate;
    @SerializedName("description")
    @Expose
    private String bookDescription;
    @SerializedName("bookImageLink")
    @Expose
    private String bookImageLink;
    @SerializedName("bookStoreLink")
    @Expose
    private String bookStoreLink;
    @SerializedName("bookPreviewLink")
    @Expose
    private String bookPreviewLink;

    public Book() {
    }

    public Book(Item item) {
        this.bookTitle = item.getVolumeInfo().getTitle();
        this.bookAuthors = item.getVolumeInfo().getAuthors();
        this.bookPublisher = item.getVolumeInfo().getPublisher();
        this.bookPublishedDate = item.getVolumeInfo().getPublishedDate();
        this.bookDescription = item.getVolumeInfo().getDescription();
        this.bookImageLink = item.getVolumeInfo().getImageLinks().getThumbnail();
        this.bookStoreLink = item.getAccessInfo().getWebReaderLink();
        this.bookPreviewLink = item.getVolumeInfo().getPreviewLink();
    }

    public Book(String bookTitle, List<String> bookAuthors, String bookPublisher,
                String bookPublishedDate, String bookDescription,
                String bookImageLink, String bookStoreLink, String bookPreviewLink) {
        this.bookTitle = bookTitle;
        this.bookAuthors = bookAuthors;
        this.bookPublisher = bookPublisher;
        this.bookPublishedDate = bookPublishedDate;
        this.bookDescription = bookDescription;
        this.bookImageLink = bookImageLink;
        this.bookStoreLink = bookStoreLink;
        this.bookPreviewLink = bookPreviewLink;
    }

    public String getBookImageLink() {
        return bookImageLink;
    }

    public void setBookImageLink(String bookImageLink) {
        this.bookImageLink = bookImageLink;
    }

    public String getBookStoreLink() {
        return bookStoreLink;
    }

    public void setBookStoreLink(String bookStoreLink) {
        this.bookStoreLink = bookStoreLink;
    }

    public String getBookPreviewLink() {
        return bookPreviewLink;
    }

    public void setBookPreviewLink(String bookPreviewLink) {
        this.bookPreviewLink = bookPreviewLink;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public List<String> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<String> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookPublishedDate() {
        return bookPublishedDate;
    }

    public void setBookPublishedDate(String bookPublishedDate) {
        this.bookPublishedDate = bookPublishedDate;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookTitle='" + bookTitle + '\'' +
                ", bookAuthors=" + bookAuthors +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", bookPublishedDate='" + bookPublishedDate + '\'' +
                ", bookDescription='" + bookDescription + '\'' +
                ", bookImageLink='" + bookImageLink + '\'' +
                ", bookStoreLink='" + bookStoreLink + '\'' +
                ", bookPreviewLink='" + bookPreviewLink + '\'' +
                '}';
    }
}
