package com.nunez.bookito.searchBooks;

import org.junit.Test;

import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RequestsVerifier;

import static io.appflate.restmock.utils.RequestMatchers.pathStartsWith;

/**
 * Created by paulnunez on 3/20/17.
 */
public class SearchInteractorTest {

//  @Rule
//  ActivityTestRule<ScrollingActivity> activityTestRule = new ActivityTestRule<ScrollingActivity>(ScrollingActivity.class, true, false);

  @Test
  public void shouldPass_SearchRequestAndXmlParsing(){
    RESTMockServer.reset();

//    activityTestRule.launchActivity();

    RESTMockServer.whenGET(pathStartsWith("search"))
        .thenReturnFile(200,"search/enders.xml");

    RequestsVerifier.verifyGET(pathStartsWith("search")).invoked();
  }

}