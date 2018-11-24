package extern;

import extern.books_api.GoogleApiClient;
import extern.books_api.res.Book;
import extern.books_api.res.google.GoogleBooksResult;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.googleapis.com/books/v1/volumes?q=subject:fiction&maxResults=2
 */

public class GoogleBooks implements IGooglePresenter {

    Object lock = new Object();
    private boolean result = false;
    private List<Book> books;

    public GoogleBooks() {
    }

    @Override
    public Single<GoogleBooksResult> getPreferedBooksAsObservable(String theme, String max) {
        synchronized (lock) {
            return GoogleApiClient.getApi().getBooks("subject:" + theme, max);
        }
    }

    @Override
    public List<Book> getPreferedBooks(String theme, String max) {
        synchronized (lock) {
            result = false;
            GoogleApiClient.getApi().getBooks("subject:" + theme, max)
                    .map(data -> {
                        List<Book> resultBooks = new ArrayList<Book>();
                        try {
                            if (data != null && data.getTotalItems() > 0) {
                                result = true;
                                for (int i = 0; i < data.getTotalItems()
                                        && i < Integer.valueOf(max); i++) {
                                    resultBooks.add(new Book(data.getItems().get(i)));
                                }
                                return resultBooks;
                            } else {
                                return resultBooks;
                            }
                        } catch (Exception e) {
                            return resultBooks;
                        }
                    })
                    .subscribe(tmpBookList -> {
                                if (result)
                                    setBooks(tmpBookList);
                            },
                            Throwable::printStackTrace);


            if (result)
                return getBooks();
            else
                return null;
        }
    }


    private List<Book> getBooks() {
        return books;
    }

    private void setBooks(List<Book> books) {
        this.books = books;
    }
}
