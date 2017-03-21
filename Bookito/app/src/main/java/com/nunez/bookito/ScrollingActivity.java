package com.nunez.bookito;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nunez.bookito.entities.BookWrapper;
import com.nunez.bookito.searchBooks.SearchBooksContract;
import com.nunez.bookito.searchBooks.SearchInteractor;
import com.nunez.bookito.searchBooks.SearchPresenter;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity implements SearchBooksContract.View{
  private static final String TAG = "ScrollingActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scrolling);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    SearchInteractor searchInteractor = new SearchInteractor(getApplication());
    final SearchPresenter presenter = new SearchPresenter(this,searchInteractor);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Searching books", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();

        presenter.searchBooks("dracula");
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_scrolling, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void showBooks(ArrayList<BookWrapper> booksArray) {
    Log.d(TAG, "showBooks() called with: booksArray = [" + booksArray + "]");
  }

  @Override
  public void showNoBooksFound() {
    Log.d(TAG, "showNoBooksFound() called");
  }

  @Override
  public void displayError() {
    Log.d(TAG, "displayError() called");
  }

  @Override
  public void showLoading() {
    Log.d(TAG, "showLoading() called");
  }

  @Override
  public void hideLoading() {
    Log.d(TAG, "hideLoading() called");
  }
}
