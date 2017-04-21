package com.nunez.bookito.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by paulnunez on 3/17/17.
 */

@Root(name = "best_book", strict =  false)
public class Book {

  @Element(name = "id")
  private int id;

  @Element(name = "title")
  private String title;

  @Element(name = "author", required = false)
  private Author author;

  @Element(required = false)
  private String averageRating;

  @Element(name = "image_url")
  private String imageUrl;

  @Element(name = "small_image_url")
  private String smallImageUrl;

  public Book() {
  }

  public Book(int id, String title, Author author, String averageRating, String imageUrl, String smallImageUrl) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.averageRating = averageRating;
    this.imageUrl = imageUrl;
    this.smallImageUrl = smallImageUrl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getSmallImageUrl() {
    return smallImageUrl;
  }

  public void setSmallImageUrl(String smallImageUrl) {
    this.smallImageUrl = smallImageUrl;
  }

  public String getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(String averageRating) {
    this.averageRating = averageRating;
  }
}
