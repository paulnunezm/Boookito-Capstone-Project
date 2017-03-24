package com.nunez.bookito.searchBooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.nunez.bookito.R;
import com.nunez.bookito.customViews.BookTextWatcher;
import com.nunez.bookito.entities.BookWrapper;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchBooksContract.View {
  private static final String TAG = "SearchActivity";

  private BookTextWatcher   mTextWatcher;
  private SearchPresenter   presenter;
  private SearchInteractor  interactor;
  private ProgressBar       progress;
  private RecyclerView      recyclerView;
  private GridLayoutManager gridLayoutManager;
  private SearchAdapter     adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    EditText editText = (EditText) findViewById(R.id.editText);
    progress = (ProgressBar) findViewById(R.id.progress);
    recyclerView = (RecyclerView) findViewById(R.id.recycler);

    int gridColumns =  getResources().getInteger(R.integer.search_activity_columns);
    gridLayoutManager = new GridLayoutManager(this,
        gridColumns,
        LinearLayoutManager.VERTICAL,
        false);
    mTextWatcher = new BookTextWatcher(this);
    interactor = new SearchInteractor(getApplication());
    presenter = new SearchPresenter(this, interactor);

    editText.addTextChangedListener(mTextWatcher);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setHasFixedSize(true);
  }

  @Override
  public void showBooks(ArrayList<BookWrapper> booksArray) {
    Log.d(TAG, "showBooks() called with: booksArray = [" + booksArray + "]");
    adapter = new SearchAdapter(booksArray);
    recyclerView.setAdapter(adapter);

    adapter.notifyDataSetChanged();

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
    recyclerView.setVisibility(View.GONE);
    progress.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    recyclerView.setVisibility(View.VISIBLE);
    progress.setVisibility(View.GONE);
  }


  /**
   * This is called everytime the text on the edit text is changed.
   * This is called by the {@link BookTextWatcher}.
   *
   * @param text {string} text changed in the edit text.
   */
  @Override
  public void onSearchTextChange(String text) {
    presenter.searchBooks(text);
  }
}
