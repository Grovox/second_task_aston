package services;

import dto.*;
import model.Book;
import model.BuyBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.AuthorBookRepository;
import repo.AuthorRepository;
import repo.BookRepository;
import repo.BuyBookRepository;
import repo.impl.AuthorBookRepositoryImpl;
import repo.impl.AuthorRepositoryImpl;
import repo.impl.BookRepositoryImpl;
import repo.impl.BuyBookRepositoryImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class BookServiceTest {
    BookService service;
    AuthorRepository authorRepository;
    AuthorBookRepository authorBookRepository;
    BookRepository bookRepository;
    BuyBookRepository buyBookRepository;

    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        service = BookService.getInstance();
        bookRepository = mock(BookRepositoryImpl.class);
        authorRepository = mock(AuthorRepositoryImpl.class);
        authorBookRepository = mock(AuthorBookRepositoryImpl.class);
        buyBookRepository = mock(BuyBookRepositoryImpl.class);

        Field field = BookService.class.getDeclaredField("authorRepository");
        field.setAccessible(true);
        field.set(service, authorRepository);

        field = BookService.class.getDeclaredField("authorBookRepository");
        field.setAccessible(true);
        field.set(service, authorBookRepository);

        field = BookService.class.getDeclaredField("bookRepository");
        field.setAccessible(true);
        field.set(service, bookRepository);

        field = BookService.class.getDeclaredField("buyBookRepository");
        field.setAccessible(true);
        field.set(service, buyBookRepository);
    }

    @Test
    void getInstance() {
        Assertions.assertEquals(BookService.getInstance().getClass(), BookService.class);
    }

    @Test
    void getAllBook() {

        service.getAllBook();

        verify(bookRepository).getAll();
    }

    @Test
    void haveBookById() {
        when(bookRepository.findById(anyInt())).thenReturn(null);

        boolean ans = service.haveBookById(anyInt());

        verify(bookRepository).findById(anyInt());
        Assertions.assertFalse(ans);
    }

    @Test
    void changeBook() {
        BookPut bookPut = new BookPut();
        bookPut.setBookId(0);
        AuthorId authorId = new AuthorId();
        authorId.setAuthorId(0);
        List<AuthorId> authorsId = new ArrayList<>();
        authorsId.add(authorId);
        bookPut.setAuthorsId(authorsId);

        service.changeBook(bookPut);

        verify(authorBookRepository).deleteAllFromBookId(anyInt());
        verify(authorBookRepository).saveAll(anyList());
        verify(bookRepository).update(any(Book.class));
    }

    @Test
    void addBook() {
        BookPost bookPost = new BookPost();
        AuthorId authorId = new AuthorId();
        authorId.setAuthorId(0);
        List<AuthorId> authorsId = new ArrayList<>();
        authorsId.add(authorId);
        bookPost.setAuthorsId(authorsId);

        service.addBook(bookPost);

        verify(bookRepository).save(any(Book.class));
        verify(bookRepository).getLastId();
        verify(authorBookRepository).saveAll(anyList());
    }

    @Test
    void deleteBook() {
        BookDelete bookDelete = new BookDelete();
        bookDelete.setBookId(0);
        when(authorBookRepository.findByBookId(anyInt())).thenReturn(new ArrayList<>());

        service.deleteBook(bookDelete);

        verify(authorBookRepository).findByBookId(anyInt());
        verify(authorBookRepository).deleteAllFromBookId(anyInt());
        verify(buyBookRepository).deleteByBookId(anyInt());
        verify(bookRepository).delete(any(Book.class));
        verify(authorRepository).deleteAuthorByIdIfNotHaveBooks(anyList());
    }

    @Test
    void isEnoughBooksWithBuyBookPost() {
        BuyBookPost buyBookPost = new BuyBookPost();
        when(bookRepository.findById(anyInt())).thenReturn(new Book());

        service.isEnoughBooks(buyBookPost);

        verify(bookRepository).findById(anyInt());
    }

    @Test
    void IsEnoughBooksWithBuyBook() {
        BuyBook buyBook = new BuyBook();
        when(buyBookRepository.findById(anyInt())).thenReturn(new BuyBook());
        when(bookRepository.findById(anyInt())).thenReturn(new Book());

        service.isEnoughBooks(buyBook);

        verify(buyBookRepository).findById(anyInt());
        verify(bookRepository).findById(anyInt());
    }
}