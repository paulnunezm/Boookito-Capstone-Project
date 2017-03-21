package com.nunez.bookito.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by paulnunez on 3/20/17.
 */

@Root(name = "work", strict = false)
public class BookWrapper {

  @Element(name = "original_publication_year")
  private int origialPublicationYear;

  @Element(name = "average_rating")
  private String averageRating;

  @Element(name = "best_book")
  private Book book;

  public int getOrigialPublicationYear() {
    return origialPublicationYear;
  }

  public void setOrigialPublicationYear(int origialPublicationYear) {
    this.origialPublicationYear = origialPublicationYear;
  }

  public String getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(String averageRating) {
    this.averageRating = averageRating;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }
}
