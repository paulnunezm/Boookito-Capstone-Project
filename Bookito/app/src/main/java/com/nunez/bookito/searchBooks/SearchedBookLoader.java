package com.nunez.bookito.searchBooks;

import android.content.Context;
import android.util.Log;

import com.nunez.bookito.BuildConfig;
import com.nunez.bookito.entities.BookWrapper;
import com.nunez.bookito.entities.GoodreadsResponse;
import com.nunez.bookito.repositories.GoodreadsService;

import org.simpleframework.xml.core.ValueRequiredException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by paulnunez on 3/30/17.
 */

public class SearchedBookLoader extends android.support.v4.content.AsyncTaskLoader<List<BookWrapper>> {
  private static final String TAG = "SearchedBookLoader";

  List<BookWrapper> books;
  Retrofit          retrofit;
  private String searchQuery;
  int requestPage;

  SearchedBookLoader(Context context) {
    super(context);

  }

  SearchedBookLoader(Context context, String searchQuery) {
    super(context);
    this.searchQuery = searchQuery;
    requestPage = 1;
    retrofit = new Retrofit.Builder()
        .baseUrl(GoodreadsService.BASE_URL)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build();
  }

  /**
   * This function is called in a background thread and should generate a new set of
   * data to be published by the loader.
   * <p>
   * This is where the heavy work is done.
   */
  @Override
  public List<BookWrapper> loadInBackground() {
    // retrieve the books.
    books = new ArrayList<>();

    if(searchQuery != null && !searchQuery.isEmpty()){
      Log.d(TAG, "started with: "+searchQuery);

      GoodreadsService goodreadsService = retrofit.create(GoodreadsService.class);
      Call<GoodreadsResponse> call = goodreadsService.searchBooks(searchQuery, 1,
          BuildConfig.GoodreadsApiKey);

      try {
        GoodreadsResponse response = call.execute().body();
        books = response.getSearch().getResults().getBookWrappers();

      } catch (Exception e) {
        if(e instanceof IOException || e instanceof ValueRequiredException) {
          Log.e(TAG, "loadInBackground: ", e );
          books = Collections.emptyList();
          return books;

        }else{
          Log.e(TAG, "loadInBackground: another exeption ", e);

          e.printStackTrace();
          books = Collections.emptyList();
          return books;
        }
      }
      Log.d(TAG, "loadInBackground: Finished ");
      return books;

    }else{
      Log.i(TAG, "loadInBackground: searchQuery empty or null");
      return books;

    }
//    return books;
  }

  @Override
  protected void onStartLoading() {
    cancelLoad();
    forceLoad();
  }

  @Override
  protected void onReset() {
    super.onReset();

    // Ensure the loader is stopped
    onStopLoading();
  }

  @Override
  public void deliverResult(List<BookWrapper> data) {
    super.deliverResult(data);
    Log.i(TAG, "deliverResult: ");
  }
}
