package com.nunez.bookito.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by paulnunez on 3/20/17.
 */

@Root(name = "search", strict = false)
public class Search{

  @Element(name = "results-start")
  int resultsStart;

  @Element(name = "results-end")
  int resultsEnd;

  @Element(name = "total-results")
  int resultsTotal;

  @Element(name = "results")
  private Results results;

  public int getResultsStart() {
    return resultsStart;
  }

  public void setResultsStart(int resultsStart) {
    this.resultsStart = resultsStart;
  }

  public int getResultsEnd() {
    return resultsEnd;
  }

  public void setResultsEnd(int resultsEnd) {
    this.resultsEnd = resultsEnd;
  }

  public int getResultsTotal() {
    return resultsTotal;
  }

  public void setResultsTotal(int resultsTotal) {
    this.resultsTotal = resultsTotal;
  }

  public Results getResults() {
    return results;
  }

  public void setResults(Results results) {
    this.results = results;
  }
}