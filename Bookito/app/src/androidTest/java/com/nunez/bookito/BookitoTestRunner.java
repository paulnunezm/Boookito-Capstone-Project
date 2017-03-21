package com.nunez.bookito;

import android.app.Application;
import android.content.Context;

import io.appflate.restmock.android.RESTMockTestRunner;

/**
 * Created by paulnunez on 3/20/17.
 */

public class BookitoTestRunner extends RESTMockTestRunner {

  // This let us uses our test Bookito App methods instead of the Bookito app class
  @Override
  public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return super.newApplication(cl, TestBookitoApp.class.getName(), context);
  }
}
