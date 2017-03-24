package com.nunez.bookito;

import io.appflate.restmock.RESTMockServer;

/**
 * Created by paulnunez on 3/18/17.
 */

public class TestBookitoApp extends BookitoApp {

  @Override
  public  String getGoodreadsBaseUrl(){
    return RESTMockServer.getUrl();
  }
}
