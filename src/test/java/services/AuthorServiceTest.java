package services;

import dto.AuthorDelete;
import dto.AuthorId;
import dto.AuthorPost;
import model.Author;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {
    AuthorService service;
    AuthorRepository authorRepository;
    AuthorBookRepository authorBookRepository;
    BookRepository bookRepository;
    BuyBookRepository buyBookRepository;

    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        service = AuthorService.getInstance();
        authorRepository = mock(AuthorRepositoryImpl.class);
        authorBookRepository = mock(AuthorBookRepositoryImpl.class);
        bookRepository = mock(BookRepositoryImpl.class);
        buyBookRepository = mock(BuyBookRepositoryImpl.class);

        Field field = AuthorService.class.getDeclaredField("authorRepository");
        field.setAccessible(true);
        field.set(service, authorRepository);

        field = AuthorService.class.getDeclaredField("authorBookRepository");
        field.setAccessible(true);
        field.set(service, authorBookRepository);

        field = AuthorService.class.getDeclaredField("bookRepository");
        field.setAccessible(true);
        field.set(service, bookRepository);

        field = AuthorService.class.getDeclaredField("buyBookRepository");
        field.setAccessible(true);
        field.set(service, buyBookRepository);
    }

    @Test
    void getInstance() {
        Assertions.assertEquals(AuthorService.getInstance().getClass(), AuthorService.class);
    }
    @Test
    void getAllAuthors() {

        service.getAllAuthors();

        verify(authorRepository).getAll();
    }

    @Test
    void haveAuthorById() {
        when(authorRepository.findById(anyInt())).thenReturn(null);

        boolean ans = service.haveAuthorById(anyInt());

        verify(authorRepository).findById(anyInt());

        Assertions.assertFalse(ans);
    }

    @Test
    void changeAuthor() {
        Author author = new Author();

        service.changeAuthor(author);

        verify(authorRepository).update(author);
    }

    @Test
    void addAuthor() {
        AuthorPost authorPost = new AuthorPost();

        service.addAuthor(authorPost);

        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void deleteAuthor() {
        AuthorDelete authorDelete = new AuthorDelete();
        authorDelete.setAuthorId(0);
        when(authorBookRepository.findByAuthorId(anyInt())).thenReturn(null);
        when(authorBookRepository.findByBooksId(anyList())).thenReturn(null);

        service.deleteAuthor(authorDelete);

        verify(authorBookRepository).findByAuthorId(anyInt());
        verify(buyBookRepository).deleteByBooksId(anyList());
        verify(authorBookRepository).findByBooksId(anyList());
        verify(authorBookRepository).deleteAllFromBooksId(anyList());
        verify(bookRepository).deleteByBooksId(anyList());
        verify(authorRepository).deleteAuthorByIdIfNotHaveBooks(anyList());
        verify(authorRepository).delete(any(Author.class));
    }

    @Test
    void haveAuthorsById() {

        AuthorId authorId1 = new AuthorId();
        authorId1.setAuthorId(1);
        AuthorId authorId2 = new AuthorId();
        authorId2.setAuthorId(2);
        List<AuthorId> authorsId = new ArrayList<>();
        authorsId.add(authorId1);
        authorsId.add(authorId2);
        when(authorRepository.findById(1)).thenReturn(new Author());
        when(authorRepository.findById(2)).thenReturn(null);

        boolean ans = service.haveAuthorsById(authorsId);

        verify(authorRepository, times(2)).findById(anyInt());
        Assertions.assertFalse(ans);
    }

    @Test
    void haveAuthorsByIdDataIsEmpty() {
        List<AuthorId> authorsId = new ArrayList<>();

        boolean ans = service.haveAuthorsById(authorsId);

        verify(authorRepository, never()).findById(anyInt());
        Assertions.assertTrue(ans);
    }

    @Test
    void haveAuthorsByIdDataIsNull() {
        List<AuthorId> authorsId = null;

        boolean ans = service.haveAuthorsById(authorsId);

        verify(authorRepository, never()).findById(anyInt());
        Assertions.assertTrue(ans);
    }
}