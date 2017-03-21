package com.nunez.bookito.repositories;

import com.nunez.bookito.entities.GoodreadsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by paulnunez on 3/18/17.
 */

public interface GoodreadsService {

  @GET("/search")
  Call<GoodreadsResponse> searchBooks(
      @Query("q") String searchString, // supports book title, author, ISBN & fields phrase searching
      @Query("page") int page,
      @Query("key") String goodreadsApiKey
  );
}
