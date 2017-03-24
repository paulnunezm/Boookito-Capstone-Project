package com.nunez.bookito.customViews;

import android.text.Editable;
import android.text.TextWatcher;

import com.nunez.bookito.searchBooks.SearchBooksContract;

/**
 * Created by paulnunez on 3/21/17.
 */

public class BookTextWatcher implements TextWatcher {

  private SearchBooksContract.View view;

  public BookTextWatcher(SearchBooksContract.View view) {
    this.view = view;

  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
   }

  @Override
  public void afterTextChanged(Editable s) {
    view.onSearchTextChange(s.toString());
  }
}
