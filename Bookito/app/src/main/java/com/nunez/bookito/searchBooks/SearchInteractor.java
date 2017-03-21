package com.nunez.bookito.searchBooks;

import android.app.Application;
import android.util.Log;

import com.nunez.bookito.BookitoApp;
import com.nunez.bookito.BuildConfig;
import com.nunez.bookito.entities.BookWrapper;
import com.nunez.bookito.entities.GoodreadsResponse;
import com.nunez.bookito.repositories.GoodreadsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by paulnunez on 3/17/17.
 */

public class SearchInteractor implements SearchBooksContract.Interactor {
  private SearchPresenter presenter;
  private BookitoApp      app;

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
              ArrayList<BookWrapper> books = response.body()
                  .getSearch()
                  .getResults()
                  .getBookWrappers();

              sendBooksToPresenter(books);

            } else {
              int statusCode = response.code();
              new RuntimeException(String.valueOf(response.code()));
            }
          }

          @Override
          public void onFailure(Call<GoodreadsResponse> call, Throwable t) {
            new RuntimeException(t.getMessage());
          }
        });
  }

  @Override
  public void setPresenter(SearchPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void sendBooksToPresenter(ArrayList<BookWrapper> books) {
    presenter.loadBooks(books);
  }
}
