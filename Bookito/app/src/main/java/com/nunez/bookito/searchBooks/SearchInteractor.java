package com.nunez.bookito.searchBooks;

import android.app.Application;

import com.nunez.bookito.BookitoApp;
import com.nunez.bookito.BuildConfig;
import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.entities.BookWrapper;
import com.nunez.bookito.entities.GoodreadsResponse;
import com.nunez.bookito.mvp.BaseContract;
import com.nunez.bookito.repositories.FirebaseRepo;
import com.nunez.bookito.repositories.GoodreadsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulnunez on 3/17/17.
 */

public class SearchInteractor implements SearchBooksContract.Interactor {
  private SearchPresenter        presenter;
  private BookitoApp             app;
  private ArrayList<BookWrapper> books;

  public SearchInteractor(Application app) {
    this.app = (BookitoApp) app;
  }

  @Override
  public void searchBooks(String bookTitle) {

    GoodreadsService goodreadsService =
        app.getRetrofitClient(app.getGoodreadsBaseUrl())
            .create(GoodreadsService.class);

    goodreadsService.searchBooks(bookTitle, 1, BuildConfig.GoodreadsApiKey)
        .enqueue(new Callback<GoodreadsResponse>() {
          @Override
          public void onResponse(Call<GoodreadsResponse> call, Response<GoodreadsResponse> response) {
            if (response.isSuccessful()) {
              books = response.body()
                  .getSearch()
                  .getResults()
                  .getBookWrappers();
              sendBooksToPresenter(books);

            } else {
              int statusCode = response.code();
              new RuntimeException(String.valueOf(response.code()));
              sendBooksToPresenter(null);
            }
          }

          @Override
          public void onFailure(Call<GoodreadsResponse> call, Throwable t) {
            new RuntimeException(t.getMessage()).printStackTrace();
            sendBooksToPresenter(books);
          }
        });
  }

  @Override
  public void setPresenter(BaseContract.BasePresenter presenter) {
    this.presenter = (SearchPresenter) presenter;
  }

  @Override
  public void sendBooksToPresenter(ArrayList<BookWrapper> books) {
    presenter.loadBooks(books);
  }

  @Override
  public void saveBookTo(Book book, String nodeName) {
    FirebaseRepo.getInstance().saveBook(book, nodeName);
    presenter.displayMessage(app.getString(R.string.search_activity_book_saved, nodeName));
  }
}
