package com.nunez.bookito;

import android.app.Application;

import com.nunez.bookito.repositories.FirebaseRepo;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by paulnunez on 3/17/17.
 */

public class BookitoApp extends Application {

  private Retrofit retrofit = null;

  @Override
  public void onCreate() {
    super.onCreate();

//    new FirebaseRepo().initialize();
    FirebaseRepo.getInstance().setContext(this);
  }

  public Retrofit getRetrofitClient(String baseUrl) {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(baseUrl)
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build();
    }
    return retrofit;
  }

  public String getGoodreadsBaseUrl() {
    return "https://www.goodreads.com";
  }
}
