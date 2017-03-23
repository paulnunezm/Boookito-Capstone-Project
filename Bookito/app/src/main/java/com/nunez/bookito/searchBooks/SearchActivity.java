package com.nunez.bookito.searchBooks;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.nunez.bookito.R;
import com.nunez.bookito.customViews.BookTextWatcher;
import com.nunez.bookito.entities.BookWrapper;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchBooksContract.View {
  private static final String TAG = "SearchActivity";

  private BookTextWatcher  mTextWatcher;
  private SearchPresenter  presenter;
  private SearchInteractor interactor;
  private ContentLoadingProgressBar progressBar;
  private TextView testText;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    EditText editText = (EditText) findViewById(R.id.editText);
    progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
    testText = (TextView) findViewById(R.id.testText);

    mTextWatcher = new BookTextWatcher(this);
    interactor = new SearchInteractor(getApplication());
    presenter = new SearchPresenter(this, interactor);

    editText.addTextChangedListener(mTextWatcher);

  }

  @Override
  public void showBooks(ArrayList<BookWrapper> booksArray) {
    Log.d(TAG, "showBooks: ");
    for (BookWrapper bookWrapper: booksArray) {
        testText.append("\n"+ bookWrapper.getBook().getTitle());
      Log.i(TAG, "showBooks: "+bookWrapper.getBook().getTitle());
    }
  }

  @Override
  public void showNoBooksFound() {
    Log.e(TAG, "showNoBooksFound: ");
  }

  @Override
  public void displayError() {
    Log.e(TAG, "displayError: ");
  }

  @Override
  public void showLoading() {
    progressBar.show();
  }

  @Override
  public void hideLoading() {
    progressBar.hide();
  }


  /**
   * This is called everytime the text on the edit text is changed.
   * This is called by the {@link BookTextWatcher}.
   *
   * @param text {string} text changed in the edit text.
   */
  @Override
  public void onSearchTextChange(String text) {
    testText.setText("");
    presenter.searchBooks(text);
  }
}
