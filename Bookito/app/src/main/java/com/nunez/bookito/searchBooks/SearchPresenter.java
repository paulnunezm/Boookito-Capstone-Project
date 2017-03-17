package com.nunez.bookito.searchBooks;

import com.nunez.bookito.models.Book;

import java.util.ArrayList;

/**
 * Created by paulnunez on 3/17/17.
 */

public class SearchPresenter implements SearchBooksContract.Presenter {

  private SearchBooksContract.View view;
  private SearchBooksContract.Repository repository;

  public SearchPresenter(SearchBooksContract.View view, SearchBooksContract.Repository repository) {
    this.view = view;
    this.repository = repository;
  }


  @Override
  public void searchBooks(String bookTitle) {
    if (!bookTitle.isEmpty()) {
      view.showLoading();
      try {
        repository.searchBooks(bookTitle);
      } catch (RuntimeException e) {
        view.hideLoading();
        view.displayError();
      }
    }
  }

  @Override
  public void loadBooks(ArrayList<Book> books) {
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
