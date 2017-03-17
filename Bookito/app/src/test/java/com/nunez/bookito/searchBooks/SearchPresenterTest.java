package com.nunez.bookito.searchBooks;

import com.nunez.bookito.models.Book;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static com.nunez.bookito.searchBooks.SearchBooksContract.Repository;
import static com.nunez.bookito.searchBooks.SearchBooksContract.View;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by paulnunez on 3/17/17.
 */
public class SearchPresenterTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  Repository repository;

  @Mock
  View view;

  SearchPresenter presenter;
  ArrayList<Book> MANY_BOOKS;
  final String QUERY = "Dracula";

  @Before
  public void setUp(){
    presenter = new SearchPresenter(view, repository);
    MANY_BOOKS = new ArrayList<>();
    MANY_BOOKS.add(new Book());
    MANY_BOOKS.add(new Book());
    MANY_BOOKS.add(new Book());
  }

  @Test
  public void shouldReturnBooks(){
    // given
    when(repository.searchBooks(QUERY)).thenReturn(MANY_BOOKS);

    // when
    presenter.searchBooks(QUERY);
    presenter.loadBooks(MANY_BOOKS);

    // then
    verify(view).showLoading();
    verify(view).hideLoading();
    verify(view).showBooks(MANY_BOOKS);
  }

  @Test
  public void noBooksFoundShouldPass(){
    //given
    final ArrayList<Book> EMPTY_LIST = new ArrayList<>();
    when(repository.searchBooks(QUERY)).thenReturn(EMPTY_LIST);

    //when
    presenter.searchBooks(QUERY);
    presenter.loadBooks(EMPTY_LIST);

    // then
    verify(view).showLoading();
    verify(view).showNoBooksFound();
  }

  @Test
  public void shouldHandleRepositoryErrors(){
//    when(booksRepository.getBooks()).thenReturn(Single.<List<Book>>error(new Throwable("boom")));
    //given
    when(repository.searchBooks(QUERY)).thenThrow(new RuntimeException("books falled apart!!"));

    //when
    presenter.searchBooks(QUERY);

    //then
    verify(view).showLoading();
    verify(view).hideLoading();
    verify(view).displayError();
  }

  @Test
  public void shouldPassNotQuerySended(){
    //when
    presenter.searchBooks("");

    //then
    verify(view, never()).showLoading();
    verify(repository, never()).searchBooks(""); //never called if string is empty
  }

  @Test
  public void shouldPassSpaceQuerySended(){
    //when
    presenter.searchBooks("   ");

    //then
    verify(view, never()).showLoading();
    verify(repository, never()).searchBooks(""); //never called if string is empty
  }
}