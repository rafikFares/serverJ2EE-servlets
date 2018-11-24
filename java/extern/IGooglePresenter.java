package extern;

import extern.books_api.res.Book;
import extern.books_api.res.google.GoogleBooksResult;
import io.reactivex.Single;

import java.util.List;

public interface IGooglePresenter {

    Single<GoogleBooksResult> getPreferedBooksAsObservable(String theme, String max);

    List<Book> getPreferedBooks(String theme, String max);

}
