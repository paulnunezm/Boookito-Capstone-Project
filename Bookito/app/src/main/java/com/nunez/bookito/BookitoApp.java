package com.nunez.bookito;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
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

    FirebaseRepo.getInstance().setContext(this);
    MobileAds.initialize(getApplicationContext(), "ca-app-pub-5949676227120501~8601500878");
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
