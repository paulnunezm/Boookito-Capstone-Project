package com.nunez.bookito.searchBooks;

import com.nunez.bookito.entities.BookWrapper;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/17/17.
 */

public class SearchPresenter implements SearchBooksContract.Presenter {
  private static final String TAG = "SearchPresenter";

  private SearchBooksContract.View       view;
  private SearchBooksContract.Interactor interactor;

  public SearchPresenter(SearchBooksContract.View view, SearchBooksContract.Interactor interactor) {
    this.view = view;
    this.interactor = interactor;
    this.interactor.setPresenter(this);
  }

  @Override
  public void searchBooks(String bookTitle) {
    if (!bookTitle.isEmpty()) {
      view.showLoading();
      try {
        interactor.searchBooks(bookTitle);
      } catch (RuntimeException e) {
        view.hideLoading();
        view.displayError();
      } catch (Exception e) {
        e.printStackTrace();
        view.hideLoading();
        view.displayError();
      }
    }
  }

  @Override
  public void loadBooks(ArrayList<BookWrapper> books) {
    view.hideLoading();

    if(books == null){
      view.displayError();
    }else if (!books.isEmpty()) {
      view.showBooks(books);
    }else{
      view.showNoBooksFound();
    }

  }
}
