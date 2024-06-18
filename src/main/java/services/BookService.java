package services;

import dto.*;
import mapper.BookMapper;
import model.AuthorBook;
import model.BuyBook;
import repo.AuthorBookRepository;
import repo.AuthorRepository;
import repo.BookRepository;
import repo.BuyBookRepository;
import repo.impl.AuthorBookRepositoryImpl;
import repo.impl.AuthorRepositoryImpl;
import repo.impl.BookRepositoryImpl;
import model.Book;
import repo.impl.BuyBookRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private BookRepository bookRepository = BookRepositoryImpl.getInstance();
    private AuthorRepository authorRepository = AuthorRepositoryImpl.getInstance();
    private AuthorBookRepository authorBookRepository = AuthorBookRepositoryImpl.getInstance();
    private BuyBookRepository buyBookRepository = BuyBookRepositoryImpl.getInstance();
    private static BookService instance;
    private BookService() {
    }

    public static synchronized BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    public List<BookGet> getAllBook() {

        return bookRepository.getAll();
    }

    public boolean haveBookById(int id) {
        return bookRepository.findById(id) != null;
    }

    public void changeBook(BookPut bookPut) {
        authorBookRepository.deleteAllFromBookId(bookPut.getBookId());

        List<AuthorBook> authorBooks = new ArrayList<>();
        List<AuthorId> authorsId = bookPut.getAuthorsId();
        if (authorsId != null && !authorsId.isEmpty()) {
            for (AuthorId authorid : authorsId) {
                AuthorBook authorBook = new AuthorBook();
                authorBook.setAuthorId(authorid.getAuthorId());
                authorBook.setBookId(bookPut.getBookId());
                authorBooks.add(authorBook);
            }
        }
        if (!authorBooks.isEmpty())
            authorBookRepository.saveAll(authorBooks);

        Book bookUpdate = BookMapper.bookPutToBook(bookPut);
        bookRepository.update(bookUpdate);
    }

    public void addBook(BookPost bookPost) {
        Book book = BookMapper.bookPostToBook(bookPost);
        bookRepository.save(book);

        int bookId = bookRepository.getLastId();

        List<AuthorBook> authorBooks = new ArrayList<>();
        if (bookPost.getAuthorsId() != null && !bookPost.getAuthorsId().isEmpty()) {
            for (AuthorId authorId : bookPost.getAuthorsId()) {
                AuthorBook authorBook = new AuthorBook();
                authorBook.setAuthorId(authorId.getAuthorId());
                authorBook.setBookId(bookId);
                authorBooks.add(authorBook);
            }
        }

        if (!authorBooks.isEmpty())
            authorBookRepository.saveAll(authorBooks);
    }

    public void deleteBook(BookDelete bookDelete) {
        List<AuthorBook> authorBooks = authorBookRepository.findByBookId(bookDelete.getBookId());
        authorBookRepository.deleteAllFromBookId(bookDelete.getBookId());

        buyBookRepository.deleteByBookId(bookDelete.getBookId());

        Book book = BookMapper.bookDeleteToBook(bookDelete);
        bookRepository.delete(book);

        List<Integer> authorsId = new ArrayList<>();
        if (authorBooks != null && !authorBooks.isEmpty()) {
            for (AuthorBook authorBook : authorBooks) {
                authorsId.add(authorBook.getAuthorId());
            }
        }
        authorRepository.deleteAuthorByIdIfNotHaveBooks(authorsId);
    }

    public boolean isEnoughBooks(BuyBookPost buyBookPost) {
        int amountBook = bookRepository.findById(buyBookPost.getBookId()).getAmount();
        return amountBook >= buyBookPost.getAmount();
    }

    public boolean isEnoughBooks(BuyBook buyBook) {
        BuyBook buyBookDB = buyBookRepository.findById(buyBook.getBuyBookId());
        if (buyBookDB.getBookId() == buyBook.getBookId()) {
            int amountBook = bookRepository.findById(buyBook.getBookId()).getAmount();
            return amountBook >= (buyBook.getAmount() - buyBookDB.getAmount());
        } else {
            int amountBook = bookRepository.findById(buyBook.getBookId()).getAmount();
            return amountBook >= buyBook.getAmount();
        }
    }
}
